/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.engine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.TraceabilityFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * The purpose of this Utility class is to allow execution of the subset of Standard and non standard Acceleo
 * operations, as well as the small subset of OCL operations that impacts traceability.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <C>
 *            Will be either EClassifier for ecore or Classifier for UML.
 * @param <PM>
 *            Will be either EParameter for ecore or Parameter for UML.
 */
public final class AcceleoTraceabilityOperationVisitor<C, PM> {
	/** This will contain references towards the Class representing OCL primitive types. */
	private static final List<Class<?>> PRIMITIVE_CLASSES;

	/**
	 * Maps a source String to its Tokenizer. Needed for the implementation of the standard operation
	 * "strtok(String, Integer)" as currently specified.
	 */
	private static final Map<String, TraceabilityTokenizer> TOKENIZERS = new HashMap<String, TraceabilityTokenizer>();

	/** The evaluation visitor that spawned this operation visitor. */
	private AcceleoTraceabilityVisitor<EPackage, C, EOperation, EStructuralFeature, EEnumLiteral, PM, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> visitor;

	static {
		PRIMITIVE_CLASSES = new ArrayList<Class<?>>(10);
		PRIMITIVE_CLASSES.add(Character.class);
		PRIMITIVE_CLASSES.add(String.class);

		PRIMITIVE_CLASSES.add(Short.class);
		PRIMITIVE_CLASSES.add(Integer.class);
		PRIMITIVE_CLASSES.add(Long.class);
		PRIMITIVE_CLASSES.add(BigInteger.class);

		PRIMITIVE_CLASSES.add(Float.class);
		PRIMITIVE_CLASSES.add(Double.class);
		PRIMITIVE_CLASSES.add(BigDecimal.class);

		PRIMITIVE_CLASSES.add(Boolean.class);
	}

	/**
	 * Instantiates an operation visitor given its parent evaluation visitor.
	 * 
	 * @param visitor
	 *            Our parent evaluation visitor.
	 */
	public AcceleoTraceabilityOperationVisitor(
			AcceleoTraceabilityVisitor<EPackage, C, EOperation, EStructuralFeature, EEnumLiteral, PM, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> visitor) {
		this.visitor = visitor;
	}

	/**
	 * Handles the "contains" non standard operation directly from the traceability visitor as we need to
	 * alter recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be considered.
	 * @param substring
	 *            The substring we need to be contained in <code>source</code>.
	 * @return <code>true</code> iff the string <code>source</code> contains the given <code>substring</code>.
	 *         Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitContainsOperation(String source, String substring) {
		boolean result = source.contains(substring);

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "endsWith" non standard operation directly from the traceability visitor as we need to
	 * alter recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be considered.
	 * @param substring
	 *            The substring we need at the end of the <code>source</code>.
	 * @return <code>true</code> iff the string <code>source</code> ends with the given <code>substring</code>
	 *         . Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitEndsWithOperation(String source, String substring) {
		boolean result = source.endsWith(substring);

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "first" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param endIndex
	 *            Index at which the substring ends.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitFirstOperation(String source, int endIndex) {
		final String result;
		if (endIndex < 0 || endIndex > source.length()) {
			result = source;
		} else {
			result = visitSubstringOperation(source, 0, endIndex);
		}
		return result;
	}

	/**
	 * Handles the "index" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to find a given index.
	 * @param substring
	 *            The substring we seek within <em>source</em>.
	 * @return Index of <em>substring</em> within <em>source</em>, -1 if it wasn't contained. Traceability
	 *         information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Integer visitIndexOperation(String source, String substring) {
		int result = source.indexOf(substring);
		final int digitCount;
		if (result == -1) {
			digitCount = 2;
		} else {
			digitCount = 1 + (int)Math.log(result);
		}

		/*
		 * We need to remove all recorded traces before this call, retaining the one that gave the region
		 * containing our index.
		 */
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			Iterator<GeneratedText> textIterator = entry.getValue().iterator();
			while (textIterator.hasNext()) {
				GeneratedText text = textIterator.next();
				if (text.getEndOffset() < result || text.getStartOffset() > result) {
					textIterator.remove();
				} else {
					text.setStartOffset(0);
					text.setEndOffset(digitCount);
				}
			}
		}
		trace.setOffset(digitCount);

		// Increment java index value by 1 for OCL
		result++;
		if (result == Integer.valueOf(0)) {
			result = Integer.valueOf(-1);
		}
		return Integer.valueOf(result);
	}

	/**
	 * Handles the "isAlphanum" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be considered.
	 * @return <code>true</code> iff the string is composed of alphanumeric characters. Traceability
	 *         information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitIsAlphanumOperation(String source) {
		boolean result = true;
		final char[] chars = source.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				result = false;
				break;
			}
		}

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "isAlpha" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String that is to be considered.
	 * @return <code>true</code> iff the string is composed of alphabetic characters. Traceability information
	 *         will have been changed directly within {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitIsAlphaOperation(String source) {
		boolean result = true;
		final char[] chars = source.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetter(c)) {
				result = false;
				break;
			}
		}

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "last" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param charCount
	 *            Number of characters to keep.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitLastOperation(String source, int charCount) {
		final String result;
		if (charCount < 0 || charCount > source.length()) {
			result = source;
		} else {
			result = visitSubstringOperation(source, source.length() - charCount, source.length());
		}
		return result;
	}

	/**
	 * Handles the "substitute" and "replace" standard operations directly from the traceability visitor so as
	 * to record accurate traceability information.
	 * 
	 * @param source
	 *            String within which we need to replace substrings.
	 * @param substring
	 *            The substring that is to be substituted.
	 * @param replacement
	 *            The String which will be inserted in <em>source</em> where <em>substring</em> was.
	 * @param substitutionTrace
	 *            Traceability information of the replacement.
	 * @param substituteAll
	 *            Indicates wheter we should substitute all occurences of the substring or only the first.
	 * @param useInvocationTrace
	 *            If <code>true</code>, {@link #invocationTraces} will be altered in place of the last
	 *            {@link AcceleoTraceabilityVisitor#recordedTraces}. Should only be <code>true</code> when
	 *            altering indentation.
	 * @return <em>source</em> with the first occurence of <em>substring</em> replaced by <em>replacement</em>
	 *         or <em>source</em> unchanged if it did not contain <em>substring</em>.
	 */
	public String visitReplaceOperation(String source, String substring, String replacement,
			ExpressionTrace<C> substitutionTrace, boolean substituteAll, boolean useInvocationTrace) {
		if (substring == null || replacement == null) {
			throw new NullPointerException();
		}

		Matcher sourceMatcher = Pattern.compile(substring).matcher(source);
		StringBuffer result = new StringBuffer();
		boolean hasMatch = sourceMatcher.find();
		// Note : despite its name, this could be negative
		int addedLength = 0;
		// FIXME This loop does _not_ take group references into account except "$0 at the start"
		boolean startsWithZeroGroupRef = replacement.startsWith("$0"); //$NON-NLS-1$
		while (hasMatch) {
			// If we've already changed the String size, take it into account
			int startIndex = sourceMatcher.start() + addedLength;
			int endIndex = sourceMatcher.end() + addedLength;
			if (startsWithZeroGroupRef) {
				startIndex = endIndex;
			}
			sourceMatcher.appendReplacement(result, replacement);
			int replacementLength = result.length() - startIndex;
			// We now remove from the replacementLength the length of the replaced substring
			replacementLength -= endIndex - startIndex;
			addedLength += replacementLength;

			// Note that we could be out of a file block's scope
			if (useInvocationTrace && visitor.getCurrentFiles().size() > 0) {
				// We need the starting index of these traces
				int offsetGap = -1;
				for (ExpressionTrace<C> trace : visitor.getInvocationTraces()) {
					for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
						for (GeneratedText text : entry.getValue()) {
							if (offsetGap == -1 || text.getStartOffset() < offsetGap) {
								offsetGap = text.getStartOffset();
							}
						}
					}
				}
				startIndex += offsetGap;
				endIndex += offsetGap;
				for (ExpressionTrace<C> trace : visitor.getInvocationTraces()) {
					changeTraceabilityIndicesOfReplaceOperation(trace, startIndex, endIndex,
							replacementLength);
				}
				GeneratedFile generatedFile = visitor.getCurrentFiles().getLast();
				final int fileLength = generatedFile.getLength();
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : substitutionTrace.getTraces()
						.entrySet()) {
					for (GeneratedText text : entry.getValue()) {
						GeneratedText copy = (GeneratedText)EcoreUtil.copy(text);
						copy.setStartOffset(copy.getStartOffset() + startIndex);
						copy.setEndOffset(copy.getEndOffset() + startIndex);
						generatedFile.getGeneratedRegions().add(copy);
						Iterator<ExpressionTrace<C>> traceIterator = visitor.getInvocationTraces().iterator();
						boolean inserted = false;
						while (traceIterator.hasNext() && !inserted) {
							inserted = insertTextInTrace(traceIterator.next(), copy);
						}
					}
				}
				generatedFile.setLength(fileLength + replacementLength);
			} else {
				ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
				changeTraceabilityIndicesOfReplaceOperation(trace, startIndex, endIndex, replacementLength);
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : substitutionTrace.getTraces()
						.entrySet()) {
					Set<GeneratedText> existingTraces = trace.getTraces().get(entry.getKey());
					if (existingTraces == null) {
						existingTraces = new LinkedHashSet<GeneratedText>();
						trace.getTraces().put(entry.getKey(), existingTraces);
					}
					for (GeneratedText text : entry.getValue()) {
						GeneratedText copy = (GeneratedText)EcoreUtil.copy(text);
						copy.setStartOffset(copy.getStartOffset() + startIndex);
						copy.setEndOffset(copy.getEndOffset() + startIndex);
						existingTraces.add(copy);
					}
				}
			}

			if (!substituteAll) {
				// Do once
				break;
			}
			hasMatch = sourceMatcher.find();
		}
		sourceMatcher.appendTail(result);
		return result.toString();
	}

	/**
	 * Takes care of the "reverse" non standard operations from the traceability visitor so as to properly
	 * reverse traceability information.
	 * 
	 * @param source
	 *            Collection we need to reverse.
	 * @return The reversed collection.
	 */
	public Collection<Object> visitReverseOperation(Collection<Object> source) {
		Collection<Object> result;
		final List<Object> temp = new ArrayList<Object>(source);
		Collections.reverse(temp);
		if (source instanceof LinkedHashSet<?>) {
			final Set<Object> reversedSet = new LinkedHashSet<Object>(temp);
			result = reversedSet;
		} else {
			result = temp;
		}

		boolean isPrimitiveCollection = true;
		Iterator<Object> valueIterator = source.iterator();
		while (valueIterator.hasNext() && isPrimitiveCollection) {
			isPrimitiveCollection = isPrimitive(valueIterator.next());
		}

		if (isPrimitiveCollection) {
			ExpressionTrace<C> trace = visitor.getLastExpressionTrace();

			// Retrieve the list of all regions, regardless of their input
			List<GeneratedText> regions = new ArrayList<GeneratedText>();
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
				regions.addAll(entry.getValue());
			}

			// Sort them so that we don't have to worry about their order
			Collections.sort(regions);

			int regionsLength = 0;
			if (regions.size() == source.size()) {
				// If there is one generated region for each source value, all the better
				for (int i = regions.size() - 1; i >= 0; i--) {
					GeneratedText region = regions.get(i);
					int startOffset = regionsLength;
					regionsLength += region.getEndOffset() - region.getStartOffset();
					region.setStartOffset(startOffset);
					region.setEndOffset(regionsLength);
				}
			} else {
				/*
				 * Otherwise, we'll have to assume the regions correspond to the value's toString() -Note that
				 * we'll never be here for non-primitive Collections-
				 */
				List<Integer> valueLengthes = new ArrayList<Integer>(source.size());
				for (Object value : source) {
					String stringValue = value.toString();
					valueLengthes.add(stringValue.length());
				}
				Collections.reverse(valueLengthes);

				for (int i = valueLengthes.size() - 1; i >= 0; i--) {
					int valueLength = valueLengthes.get(i);
					GeneratedText region = regions.get(i);
					int regionLength = region.getEndOffset() - region.getStartOffset();

					// go back all the way back to the first region for this value
					int valueRegionsStartIndex = i;
					int gap = valueLength - regionLength;
					while (gap > 0) {
						valueRegionsStartIndex--;
						GeneratedText previous = regions.get(valueRegionsStartIndex);
						int previousLength = previous.getEndOffset() - previous.getStartOffset();
						gap = gap - previousLength;
					}

					// We found the first region for this value. start reversing it
					for (int j = valueRegionsStartIndex; j < regions.size(); j++) {
						GeneratedText text = regions.get(j);

						int startOffset = regionsLength;
						regionsLength += text.getEndOffset() - text.getEndOffset();

						region.setStartOffset(startOffset);
						region.setEndOffset(regionsLength);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Takes care of the "sep" non standard operations from the traceability visitor so as to track
	 * traceability information for the added separators.
	 * 
	 * @param source
	 *            Collection in which we need to add the separators.
	 * @param separator
	 *            Separator that is to be added in-between elements.
	 * @param separatorTrace
	 *            Traceability information of the separator.
	 * @return The source collection, with <em>separator</em> inserted in-between each couple of elements from
	 *         the <em>source</em>.
	 */
	public Collection<Object> visitSepOperation(Collection<Object> source, String separator,
			ExpressionTrace<C> separatorTrace) {
		final Collection<Object> temp = new ArrayList<Object>(source.size() << 1);
		final List<String> stringSource = new ArrayList<String>();
		final Iterator<?> sourceIterator = source.iterator();
		while (sourceIterator.hasNext()) {
			Object nextSource = sourceIterator.next();
			temp.add(nextSource);
			stringSource.add(nextSource.toString());
			if (sourceIterator.hasNext()) {
				temp.add(separator);
			}
		}
		/*
		 * We'll assume all elements of the collection are Strings or will be printed as such, this should
		 * handle most cases.
		 */
		final int separatorLength = separator.length();
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		int currentSeparatorOffset = 0;
		for (int i = 0; i < stringSource.size() - 1; i++) {
			String element = stringSource.get(i);
			currentSeparatorOffset += element.length();

			// All traces starting after this offset must be switched by 'separatorLength'
			final Iterator<Map.Entry<InputElement, Set<GeneratedText>>> entryIterator = trace.getTraces()
					.entrySet().iterator();
			while (entryIterator.hasNext()) {
				final Iterator<GeneratedText> textIterator = entryIterator.next().getValue().iterator();
				while (textIterator.hasNext()) {
					GeneratedText text = textIterator.next();
					if (text.getStartOffset() >= currentSeparatorOffset) {
						text.setStartOffset(text.getStartOffset() + separatorLength);
						text.setEndOffset(text.getEndOffset() + separatorLength);
					}
				}
			}

			// Finally, Insert the separator region in the trace
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : separatorTrace.getTraces().entrySet()) {
				Set<GeneratedText> existingTraces = trace.getTraces().get(entry.getKey());
				if (existingTraces == null) {
					existingTraces = new LinkedHashSet<GeneratedText>();
					trace.getTraces().put(entry.getKey(), existingTraces);
				}
				for (GeneratedText text : entry.getValue()) {
					GeneratedText copy = (GeneratedText)EcoreUtil.copy(text);
					copy.setStartOffset(copy.getStartOffset() + currentSeparatorOffset);
					copy.setEndOffset(copy.getEndOffset() + currentSeparatorOffset);
					existingTraces.add(copy);
				}
			}

			// Don't forget that the next "separatorOffset" can only start after the current
			currentSeparatorOffset += separatorLength;
		}

		return temp;
	}

	/**
	 * Handles the "Collection::size" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            Collection that is to be considered.
	 * @return Size of the given Collection. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Integer visitSizeOperation(Collection<?> source) {
		changeTraceabilityIndicesIntegerReturn(source.size());

		return Integer.valueOf(source.size());
	}

	/**
	 * Handles the "String::size" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be considered.
	 * @return Size of the given String. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Integer visitSizeOperation(String source) {
		changeTraceabilityIndicesIntegerReturn(source.length());

		return Integer.valueOf(source.length());
	}

	/**
	 * Handles the "startsWith" non standard operation directly from the traceability visitor as we need to
	 * alter recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be considered.
	 * @param substring
	 *            The substring we need at the beginning of the <code>source</code>.
	 * @return <code>true</code> iff the string <code>source</code> starts with the given
	 *         <code>substring</code>. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitStartsWithOperation(String source, String substring) {
		boolean result = source.startsWith(substring);

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "strcmp" standard operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be compared with <code>other</code>.
	 * @param other
	 *            The string with which to compare <em>source</em>.
	 * @return An integer less than zero, equal to zero, or greater than zero depending on whether
	 *         <code>other</code> is lexicographically less than, equal to, or greater than
	 *         <code>source</code>. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Integer visitStrcmpOperation(String source, String other) {
		int result = source.compareTo(other);

		changeTraceabilityIndicesIntegerReturn(result);

		return Integer.valueOf(result);
	}

	/**
	 * Handles the "strstr" standard operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String from which to find a given substring.
	 * @param substring
	 *            The substring we seek within <em>source</em>.
	 * @return <code>true</code> if <code>substring</code> could be found in <code>source</code>,
	 *         <code>false</code> otherwise. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public Boolean visitStrstrOperation(String source, String substring) {
		boolean result = source.contains(substring);

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "strtok" standard operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            Source String in which tokenization has to take place.
	 * @param delimiters
	 *            Delimiters around which the <code>source</code> has to be split.
	 * @param flag
	 *            The value of this influences the token that needs be returned. A value of <code>0</code>
	 *            means the very first token will be returned, a value of <code>1</code> means the returned
	 *            token will be the next (as compared to the last one that's been returned).
	 * @return The first of all tokens if <code>flag</code> is <code>0</code>, the next token if
	 *         <code>flag</code> is <code>1</code>. Fails in {@link AcceleoEvaluationException} otherwise.
	 * @see org.eclipse.acceleo.engine.internal.environment.AcceleoLibraryOperationVisitor#strtok(String,
	 *      String, Integer)
	 */
	public String visitStrtokOperation(String source, String delimiters, Integer flag) {
		final String result;
		final TraceabilityTokenizer tokenizer;
		if (flag.intValue() == 0) {
			tokenizer = new TraceabilityTokenizer(source, delimiters);
			TOKENIZERS.put(source, tokenizer);
			result = tokenizer.nextToken();
		} else if (flag.intValue() == 1) {
			if (TOKENIZERS.containsKey(source)) {
				tokenizer = TOKENIZERS.get(source);
			} else {
				tokenizer = new TraceabilityTokenizer(source, delimiters);
				TOKENIZERS.put(source, tokenizer);
			}
			String token = ""; //$NON-NLS-1$
			if (tokenizer.hasMoreTokens()) {
				token = tokenizer.nextToken();
			}
			result = token;
		} else {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.IllegalTokenizerFlag", flag)); //$NON-NLS-1$
		}

		changeTraceabilityIndicesSubstringReturn(tokenizer.getLastOffset(), tokenizer.getNextOffset());

		return result;
	}

	/**
	 * Handles the "substring" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param startIndex
	 *            Index at which the substring starts.
	 * @param endIndex
	 *            Index at which the substring ends.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitSubstringOperation(String source, int startIndex, int endIndex) {
		changeTraceabilityIndicesSubstringReturn(startIndex, endIndex);

		return source.substring(startIndex, endIndex);
	}

	/**
	 * Handles the "tokenize" non-standard operation directly from the traceability visitor as we need to
	 * alter recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be tokenized.
	 * @param delims
	 *            Delimiters to tokenize around.
	 * @return The List of tokens <code>source</code> contains. Traceability information will have been
	 *         changed directly within {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public List<String> visitTokenizeOperation(String source, String delims) {
		final TraceabilityTokenizer tokenizer = new TraceabilityTokenizer(source, delims);
		final List<String> result = new ArrayList<String>();
		final ExpressionTrace<C> trace = visitor.getLastExpressionTrace();

		int[][] tokenRegions = new int[tokenizer.countTokens()][2];

		int tokenCount = 0;
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());

			int startIndex = tokenizer.getLastOffset();
			int endIndex = tokenizer.getNextOffset();

			tokenRegions[tokenCount][0] = startIndex;
			tokenRegions[tokenCount][1] = endIndex;
			tokenCount++;
		}

		// First of all, if we have but a single token, this acts as substring does
		if (result.size() == 1) {
			changeTraceabilityIndicesSubstringReturn(tokenRegions[0][0], tokenRegions[0][1]);
		} else {
			Set<Map.Entry<InputElement, Set<GeneratedText>>> traceRegions = trace.getTraces().entrySet();
			// If we only have a single region, we only need to split it
			if (trace.getTraces().size() == 1 && traceRegions.iterator().next().getValue().size() == 1) {
				Map.Entry<InputElement, Set<GeneratedText>> entry = traceRegions.iterator().next();
				Set<GeneratedText> generatedRegions = entry.getValue();
				GeneratedText firstRegion = generatedRegions.iterator().next();
				int length = tokenRegions[0][1] - tokenRegions[0][0];
				firstRegion.setStartOffset(0);
				firstRegion.setEndOffset(length);

				for (int i = 1; i < tokenRegions.length; i++) {
					int start = tokenRegions[i][0];
					int end = tokenRegions[i][1];
					int startOffset = length;
					length += end - start;

					GeneratedText newRegion = (GeneratedText)EcoreUtil.copy(firstRegion);
					newRegion.setStartOffset(startOffset);
					newRegion.setEndOffset(length);

					generatedRegions.add(newRegion);
				}
			} else {
				// Otherwise, we need to find back from which generated region a given token has been taken
				changeTraceabilityIndicesComplexTokenize(trace, tokenRegions);
			}
		}

		return result;
	}

	/**
	 * Handles the "trim" non-standard operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be trimmed.
	 * @return The trimmed String. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitTrimOperation(String source) {
		return visitTrimOperation(source, 0);
	}

	/**
	 * This will be used to trim the protected area markers.
	 * 
	 * @param source
	 *            String that is to be trimmed.
	 * @param gap
	 *            The marker might begin at an index superior to 0.
	 * @return The trimmed String. Traceability information will have been changed directly within
	 *         {@link AcceleoTraceabilityVisitor#recordedTraces}.
	 */
	public String visitTrimOperation(String source, int gap) {
		int start = 0;
		int end = source.length();
		char[] chars = source.toCharArray();
		while (start < end && chars[start] <= ' ') {
			start++;
		}
		while (start < end && chars[end - 1] <= ' ') {
			end--;
		}

		start += gap;
		end += gap;
		changeTraceabilityIndicesSubstringReturn(start, end);

		return source.trim();
	}

	/**
	 * isAlpha, isAlphanum and possibily other traceability impacting operations share the same "basic"
	 * behavior of altering indices to reflect a boolean return. This behavior is externalized here.
	 * 
	 * @param result
	 *            Boolean result of the operation. We'll only leave a four-characters long region if
	 *            <code>true</code>, five-characters long if <code>false</code>.
	 */
	private void changeTraceabilityIndicesBooleanReturn(boolean result) {
		// We'll keep only the very last trace and alter its indices
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		Map.Entry<InputElement, Set<GeneratedText>> lastEntry = null;
		GeneratedText lastRegion = null;
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			Iterator<GeneratedText> textIterator = entry.getValue().iterator();
			while (textIterator.hasNext()) {
				GeneratedText text = textIterator.next();
				if (lastRegion == null) {
					lastRegion = text;
					lastEntry = entry;
				} else if (text.getEndOffset() > lastRegion.getEndOffset()) {
					// lastEntry cannot be null once we get here
					assert lastEntry != null;
					lastEntry.getValue().remove(lastRegion);
					lastRegion = text;
					lastEntry = entry;
				} else {
					textIterator.remove();
				}
			}
		}
		final int length;
		if (result) {
			length = 4;
		} else {
			length = 5;
		}
		if (lastRegion != null) {
			lastRegion.setStartOffset(0);
			lastRegion.setEndOffset(length);
		}
		trace.setOffset(length);
	}

	/**
	 * This will alter the traceability information for complex tokenizations.
	 * <p>
	 * Specifically, this is aimed at altering traces for tokenization where tokens could be spanned accross
	 * multiple generated regions. For example,
	 * 
	 * <pre>
	 * ('arai' + 'bza' + 'geaa' + 'ab' + 'adaa')
	 * </pre>
	 * 
	 * Creates 5 generated regions : [ [0,4], [4,7], [7,11], [11,13], [13,17] ] while
	 * 
	 * <pre>
	 * ('arai' + 'bza' + 'geaa' + 'ab' + 'adaa').tokenize('a')
	 * </pre>
	 * 
	 * Creates 6 generated regions : [ [0,1], [1,2], [2, 4], [4,6], [6,7], [7,8] ] as the second token ("ibz")
	 * spans accross both the generated regions for the string literals 'arai' and 'bza'.
	 * </p>
	 * 
	 * @param trace
	 *            The trace we are to alter.
	 * @param tokenRegions
	 *            offsets of the token regions.
	 */
	private void changeTraceabilityIndicesComplexTokenize(ExpressionTrace<C> trace, int[][] tokenRegions) {
		/*
		 * Copy the map : none of the old generated regions will be kept : we'll replace all of them with the
		 * token regions.
		 */
		Map<InputElement, Set<GeneratedText>> traceCopy = new HashMap<InputElement, Set<GeneratedText>>(trace
				.getTraces());
		trace.getTraces().clear();

		int length = 0;
		for (int[] tokenRegion : tokenRegions) {
			int tokenStart = tokenRegion[0];
			int tokenEnd = tokenRegion[1];
			int tokenLength = tokenEnd - tokenStart;

			// We'll try and break our loops as soon as the whole token as been created
			int initialLength = length;

			Iterator<Map.Entry<InputElement, Set<GeneratedText>>> entryIterator = traceCopy.entrySet()
					.iterator();
			while (entryIterator.hasNext() && length < initialLength + tokenLength) {
				Map.Entry<InputElement, Set<GeneratedText>> entry = entryIterator.next();
				Iterator<GeneratedText> textIterator = entry.getValue().iterator();
				while (textIterator.hasNext() && length < initialLength + tokenLength) {
					GeneratedText text = textIterator.next();

					GeneratedText tokenText = null;

					/*
					 * We can be in one of four cases : The token is fully included into a region, the region
					 * is fully included in a token, the region ends with a token, or the region starts with a
					 * token.
					 */
					if (text.getStartOffset() < tokenStart && text.getEndOffset() > tokenEnd) {
						tokenText = TraceabilityFactory.eINSTANCE.createGeneratedText();
						tokenText.setStartOffset(length);
						tokenText.setEndOffset(length + tokenLength);
						tokenText.setModuleElement(text.getModuleElement());
						tokenText.setOutputFile(text.getOutputFile());
						tokenText.setSourceElement(text.getSourceElement());

						length += tokenLength;
					} else if (text.getStartOffset() > tokenStart && text.getEndOffset() < tokenEnd) {
						int textLength = text.getEndOffset() - text.getStartOffset();
						tokenText = (GeneratedText)EcoreUtil.copy(text);
						tokenText.setStartOffset(length);
						tokenText.setEndOffset(length + textLength);

						length += textLength;
					} else if (text.getStartOffset() > tokenStart && text.getEndOffset() > tokenEnd) {
						int tokenRegionLength = text.getEndOffset() - tokenStart;
						tokenText = TraceabilityFactory.eINSTANCE.createGeneratedText();
						tokenText.setStartOffset(length);
						tokenText.setEndOffset(length + tokenRegionLength);
						tokenText.setModuleElement(text.getModuleElement());
						tokenText.setOutputFile(text.getOutputFile());
						tokenText.setSourceElement(text.getSourceElement());

						length += tokenRegionLength;
					} else if (text.getStartOffset() < tokenStart && text.getEndOffset() < tokenEnd) {
						int tokenRegionLength = tokenEnd - text.getStartOffset();
						tokenText = TraceabilityFactory.eINSTANCE.createGeneratedText();
						tokenText.setStartOffset(length);
						tokenText.setEndOffset(length + tokenRegionLength);
						tokenText.setModuleElement(text.getModuleElement());
						tokenText.setOutputFile(text.getOutputFile());
						tokenText.setSourceElement(text.getSourceElement());

						length += tokenRegionLength;
					}

					if (tokenText != null) {
						InputElement tokenKey = entry.getKey();
						Set<GeneratedText> tokenTraces = trace.getTraces().get(tokenKey);
						if (tokenTraces == null) {
							tokenTraces = new LinkedHashSet<GeneratedText>();
							trace.getTraces().put(tokenKey, tokenTraces);
						}
						tokenTraces.add(tokenText);
					}
				}
			}
		}

		for (Map.Entry<InputElement, Set<GeneratedText>> entry : traceCopy.entrySet()) {
			entry.getValue().clear();
		}
		traceCopy.clear();

		trace.setOffset(length);
	}

	/**
	 * Size and possibily other traceability impacting operations share the same "basic" behavior of altering
	 * indices to reflect an integer return. This behavior is externalized here.
	 * 
	 * @param result
	 *            Integer result of the operation. We'll only leave a single region with its length equal to
	 *            the given integer's number of digits.
	 */
	private void changeTraceabilityIndicesIntegerReturn(int result) {
		// We'll keep only the very last trace and alter its indices
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		Map.Entry<InputElement, Set<GeneratedText>> lastEntry = null;
		GeneratedText lastRegion = null;
		final List<GeneratedText> rejectFromEntry = new ArrayList<GeneratedText>();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			Iterator<GeneratedText> textIterator = entry.getValue().iterator();
			while (textIterator.hasNext()) {
				GeneratedText text = textIterator.next();
				if (lastRegion == null) {
					lastRegion = text;
					lastEntry = entry;
				} else if (text.getEndOffset() > lastRegion.getEndOffset()) {
					// lastEntry cannot be null once we get here
					assert lastEntry != null;
					if (lastEntry != entry) {
						lastEntry.getValue().remove(lastRegion);
						lastEntry = entry;
					} else {
						rejectFromEntry.add(lastRegion);
					}
					lastRegion = text;
				} else {
					textIterator.remove();
				}
			}
			if (!rejectFromEntry.isEmpty()) {
				entry.getValue().removeAll(rejectFromEntry);
				rejectFromEntry.clear();
			}
		}
		int length = String.valueOf(result).length();
		if (lastRegion != null) {
			lastRegion.setStartOffset(0);
			lastRegion.setEndOffset(length);
		}
		trace.setOffset(length);
	}

	/**
	 * This will be in charge of altering traceability indices of the given <em>trace</em> to cope with a
	 * substitute or replace operation. That means changing all traces which offsets overlap with the range
	 * <em>[startIndex, endIndex]</em> so that the given <em>substitutionTrace</em> can be used in this range
	 * ; for a total length of <em>replacementLength</em>.
	 * 
	 * @param trace
	 *            The trace which offsets are to be altered.
	 * @param startIndex
	 *            Starting index of the range where a substring has been replaced.
	 * @param endIndex
	 *            Ending index of the range where a substring has been replaced.
	 * @param replacementLength
	 *            Total length of the replaced substring. This is not always equal to the length of the range
	 *            <em>[startIndex, endIndex]</em> as we <u>can</u> replace a small substring by a large one,
	 *            or the opposite ; which also mean that <em>replacementLength</em> <u>can</u> be negative.
	 */
	@SuppressWarnings("unchecked")
	private void changeTraceabilityIndicesOfReplaceOperation(ExpressionTrace<C> trace, int startIndex,
			int endIndex, int replacementLength) {
		/*
		 * Substrings that will be split in two by replaced substrings will need their own new GeneratedText
		 * instance, this Set will keep track of those so that we can add them in the trace later.
		 */
		List<GeneratedText> addedRegions = new ArrayList<GeneratedText>();

		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			Iterator<GeneratedText> textIterator = entry.getValue().iterator();
			while (textIterator.hasNext()) {
				GeneratedText text = textIterator.next();
				if (text.getEndOffset() < startIndex) {
					continue;
				}
				/*
				 * This can be one of five cases : 1) the text ends with a replaced substring, 2) the text
				 * starts with a replaced substring, 3) the text contains a replaced substring, 4) the text is
				 * contained within a replaced substring or 5) The text starts after the replacement
				 */
				if (text.getStartOffset() < startIndex && text.getEndOffset() == endIndex) {
					text.setEndOffset(startIndex);
				} else if (text.getStartOffset() == startIndex && text.getEndOffset() > endIndex) {
					text.setStartOffset(endIndex);
					text.setEndOffset(text.getEndOffset() + replacementLength);
				} else if (text.getStartOffset() < startIndex && text.getEndOffset() > endIndex) {
					// This instance of a GeneratedText is split in two by the substring
					GeneratedText endSubstring = (GeneratedText)EcoreUtil.copy(text);
					endSubstring.setStartOffset(endIndex + replacementLength);
					endSubstring.setEndOffset(text.getEndOffset() + replacementLength);
					text.setEndOffset(startIndex);
					if (text.eContainer() != null) {
						// We know this is a list
						((List<EObject>)text.eContainer().eGet(text.eContainingFeature())).add(endSubstring);
					}
					addedRegions.add(endSubstring);
				} else if (text.getStartOffset() >= startIndex && text.getEndOffset() <= endIndex) {
					if (text.eContainer() != null) {
						EcoreUtil.remove(text);
					}
					textIterator.remove();
				} else {
					text.setStartOffset(text.getStartOffset() + replacementLength);
					text.setEndOffset(text.getEndOffset() + replacementLength);
				}
			}
			// Insert all new regions in the trace
			entry.getValue().addAll(addedRegions);
			addedRegions.clear();
		}
	}

	/**
	 * Substring, trim and possibily other traceability impacting operations share the same "basic" behavior
	 * of altering start and end indices without changing "inside". This behavior is externalized here.
	 * 
	 * @param startIndex
	 *            Index at which the substring starts.
	 * @param endIndex
	 *            Index at which the substring ends.
	 */
	private void changeTraceabilityIndicesSubstringReturn(int startIndex, int endIndex) {
		ExpressionTrace<C> trace = visitor.getLastExpressionTrace();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			Iterator<GeneratedText> textIterator = entry.getValue().iterator();
			while (textIterator.hasNext()) {
				GeneratedText text = textIterator.next();
				if (text.getEndOffset() <= startIndex || text.getStartOffset() >= endIndex) {
					textIterator.remove();
				} else {
					/*
					 * We have four cases : either 1) the region overlaps with the start index, 2) it overlaps
					 * with the end index, 3) it overlaps with both or 4) it overlaps with none.
					 */
					if (text.getStartOffset() < startIndex && text.getEndOffset() > endIndex) {
						text.setStartOffset(0);
						text.setEndOffset(endIndex - startIndex);
					} else if (text.getStartOffset() < startIndex) {
						text.setStartOffset(0);
						text.setEndOffset(text.getEndOffset() - startIndex);
					} else if (text.getEndOffset() > endIndex) {
						text.setStartOffset(text.getStartOffset() - startIndex);
						text.setEndOffset(endIndex - startIndex);
					} else {
						text.setStartOffset(text.getStartOffset() - startIndex);
						text.setEndOffset(text.getEndOffset() - startIndex);
					}
				}
			}
		}
	}

	/**
	 * This will be used to insert the given <code>text</code> in the given <code>trace</code> iff the indices
	 * of this text correspond to one of the trace's texts start or end index.
	 * 
	 * @param trace
	 *            The trace in which we should insert <code>text</code>.
	 * @param text
	 *            The text that is to be inserted if it matches the trace.
	 * @return <code>true</code> if we managed to insert this text in the given trac, <code>false</code>
	 *         otherwise.
	 */
	private boolean insertTextInTrace(ExpressionTrace<C> trace, GeneratedText text) {
		boolean insert = false;
		final Iterator<Set<GeneratedText>> setIterator = trace.getTraces().values().iterator();
		while (setIterator.hasNext() && !insert) {
			Set<GeneratedText> candidate = setIterator.next();
			final Iterator<GeneratedText> iterator = candidate.iterator();
			while (iterator.hasNext() && !insert) {
				GeneratedText next = iterator.next();
				if (next.getStartOffset() == text.getEndOffset()
						|| next.getEndOffset() == text.getStartOffset()) {
					insert = true;
				}
			}
			if (insert) {
				candidate.add(text);
			}
		}
		return insert;
	}

	/**
	 * This will check whether the given value is an OCL primitive.
	 * 
	 * @param value
	 *            The value to check.
	 * @return <code>true</code> if this value is an OCL primitive.
	 */
	private boolean isPrimitive(Object value) {
		Class<?> valueClass = value.getClass();
		if (valueClass.isPrimitive()) {
			return true;
		}

		return PRIMITIVE_CLASSES.contains(valueClass);
	}
}
