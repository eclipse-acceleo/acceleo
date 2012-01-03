/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferenceLinkArea;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * This page will provide the users a mean to change the colors of the Acceleo template editor, along with a
 * preview of the changes, import and export facilities...
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEditorColoringPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	/** Holds a reference to the preferences node providing default values for our preferences. */
	private IEclipsePreferences defaultScope;

	/** Holds a reference to the preferences node providing overridden values for our preferences. */
	private IEclipsePreferences instanceScope;

	/** This will be used as a proxy between the preference page and the instance scope. */
	private ProxyPreferenceStore proxyScope;

	/** This will keep a reference to the Tree viewer displaying the different available colors. */
	private TreeViewer colorViewer;

	/** List of the categories we are to display in the color viewer. */
	private List<ColorCategory> colorCategories;

	/** The button displayed in order to let users select their color. */
	private ColorButton colorButton;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		defaultScope = new DefaultScope().getNode(AcceleoUIActivator.PLUGIN_ID);
		instanceScope = new InstanceScope().getNode(AcceleoUIActivator.PLUGIN_ID);
		proxyScope = new ProxyPreferenceStore(instanceScope, defaultScope);

		AcceleoColor[] templateColors = new AcceleoColor[] {AcceleoColor.TEMPLATE,
				AcceleoColor.TEMPLATE_NAME, AcceleoColor.TEMPLATE_PARAMETER,
				AcceleoColor.TEMPLATE_OCL_EXPRESSION, AcceleoColor.TEMPLATE_OCL_KEYWORD, };
		ColorCategory templateCategory = new ColorCategory(AcceleoColorMessages
				.getString("org.eclipse.acceleo.template.category"), templateColors); //$NON-NLS-1$

		AcceleoColor[] queryColors = new AcceleoColor[] {AcceleoColor.QUERY, AcceleoColor.QUERY_NAME,
				AcceleoColor.QUERY_PARAMETER, AcceleoColor.QUERY_RETURN, };
		ColorCategory queryCategory = new ColorCategory(AcceleoColorMessages
				.getString("org.eclipse.acceleo.query.category"), queryColors); //$NON-NLS-1$

		AcceleoColor[] generalColors = new AcceleoColor[] {AcceleoColor.COMMENT, AcceleoColor.MODULE_NAME,
				AcceleoColor.FOR, AcceleoColor.IF, AcceleoColor.LET, AcceleoColor.DEFAULT,
				AcceleoColor.KEYWORD, AcceleoColor.LITERAL, AcceleoColor.OCL_EXPRESSION,
				AcceleoColor.OCL_KEYWORD, AcceleoColor.VARIABLE, AcceleoColor.PROTECTED_AREA, };
		ColorCategory generalCategory = new ColorCategory(AcceleoColorMessages
				.getString("org.eclipse.acceleo.general.category"), generalColors); //$NON-NLS-1$

		colorCategories = new ArrayList<ColorCategory>();
		colorCategories.add(templateCategory);
		colorCategories.add(queryCategory);
		colorCategories.add(generalCategory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		super.performDefaults();
		proxyScope.resetToDefault();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		proxyScope.propagate();

		return super.performOk();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		// Link to the general color pages
		final PreferenceLinkArea textEditorLink = new PreferenceLinkArea(parent, SWT.NONE,
				"org.eclipse.ui.preferencePages.GeneralTextEditor", //$NON-NLS-1$
				AcceleoUIMessages.getString("AcceleoEditorColoringPreferencePage.link"), //$NON-NLS-1$
				(IWorkbenchPreferenceContainer)getContainer(), null);
		final GridData linkGrid = new GridData();
		linkGrid.grabExcessHorizontalSpace = true;
		linkGrid.horizontalSpan = 3;
		linkGrid.horizontalAlignment = GridData.FILL;
		textEditorLink.getControl().setLayoutData(linkGrid);

		Composite colorComposite = new Composite(parent, SWT.NONE);
		colorComposite.setLayout(new GridLayout());

		Label foregroundLabel = new Label(colorComposite, SWT.LEFT);
		foregroundLabel.setText(AcceleoUIMessages
				.getString("AcceleoEditorColoringPreferencePage.foreground.label") + ':'); //$NON-NLS-1$
		foregroundLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Color list area
		Composite preferenceEditComposite = new Composite(colorComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		preferenceEditComposite.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		preferenceEditComposite.setLayoutData(gd);

		colorViewer = new TreeViewer(preferenceEditComposite, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		colorViewer.setLabelProvider(new ColorViewerLabelProvider());
		colorViewer.setContentProvider(new ColorViewerContentProvider());
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = convertHeightInCharsToPixels(5);
		colorViewer.getControl().setLayoutData(gd);
		colorViewer.setInput(colorCategories);

		// Color chooser area
		Composite styleComposite = new Composite(preferenceEditComposite, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		styleComposite.setLayout(layout);
		styleComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label colorChoiceLabel = new Label(styleComposite, SWT.LEFT);
		colorChoiceLabel.setText(AcceleoUIMessages
				.getString("AcceleoEditorColoringPreferencePage.color.label") + ':'); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalAlignment = GridData.BEGINNING;
		colorChoiceLabel.setLayoutData(gd);

		colorButton = new ColorButton(styleComposite);
		Button foregroundButton = colorButton.getButton();
		foregroundButton.setEnabled(false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.BEGINNING;
		foregroundButton.setLayoutData(gd);

		// Preview Area
		Label previewLabel = new Label(colorComposite, SWT.LEFT);
		previewLabel
				.setText(AcceleoUIMessages.getString("AcceleoEditorColoringPreferencePage.preview.label") + ':'); //$NON-NLS-1$
		previewLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Control previewEditor = createPreviewViewer(colorComposite);
		gd = new GridData(GridData.FILL_BOTH);
		final int widthHint = 20;
		gd.widthHint = convertWidthInCharsToPixels(widthHint);
		gd.heightHint = convertHeightInCharsToPixels(5);
		previewEditor.setLayoutData(gd);

		// Setting up listeners
		colorViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				handleColorSelection();
			}
		});

		foregroundButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleColorChange();
			}
		});

		return colorComposite;
	}

	/**
	 * This will be called from selection changes in the {@link #colorViewer}.
	 */
	private void handleColorSelection() {
		IStructuredSelection selection = (IStructuredSelection)colorViewer.getSelection();
		if (selection.getFirstElement() instanceof ColorItem) {
			ColorItem colorItem = (ColorItem)selection.getFirstElement();
			colorButton.getButton().setEnabled(true);
			colorButton.setColorValue(getRGB(colorItem.getColor()));
		} else {
			colorButton.getButton().setEnabled(false);
		}
	}

	/**
	 * This will be called whenever the user changes one of the possible colors.
	 */
	private void handleColorChange() {
		IStructuredSelection selection = (IStructuredSelection)colorViewer.getSelection();
		if (selection.getFirstElement() instanceof ColorItem) {
			ColorItem colorItem = (ColorItem)selection.getFirstElement();
			RGB rgb = colorButton.getColorValue();
			String rgbString = StringConverter.asString(rgb);
			proxyScope.put(colorItem.getColor().getPreferenceKey(), rgbString);
		}
	}

	/**
	 * Retrieves the preference value corresponding to the given {@link AcceleoColor}.
	 * 
	 * @param color
	 *            {@link AcceleoColor} for which we need an RGB value.
	 * @return The preference value corresponding to the given {@link AcceleoColor}.
	 */
	private RGB getRGB(AcceleoColor color) {
		IPreferencesService service = Platform.getPreferencesService();
		String defaultValue = StringConverter.asString(color.getDefault());

		String value = service.get(color.getPreferenceKey(), defaultValue, getPreferenceLookupOrder());
		RGB rgbValue = StringConverter.asRGB(value);

		return rgbValue;
	}

	/**
	 * Returns the order in which preferences should be looked up.
	 * 
	 * @return The order in which preferences should be looked up.
	 */
	private IEclipsePreferences[] getPreferenceLookupOrder() {
		return new IEclipsePreferences[] {proxyScope, instanceScope, defaultScope, };
	}

	/**
	 * Creates the preview editor.
	 * 
	 * @param parent
	 *            Parent composite of this editor.
	 * @return The preview editor.
	 */
	private Control createPreviewViewer(Composite parent) {
		final SourceViewer previewViewer = new SourceViewer(parent, null, null, false, SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);
		final AcceleoConfiguration configuration = new AcceleoConfiguration(AcceleoUIActivator.getDefault()
				.getPreferenceStore(), getPreferenceLookupOrder());

		IDocument document = new Document();
		previewViewer.setDocument(document);

		previewViewer.configure(configuration);
		previewViewer.setEditable(false);

		String previewContent = AcceleoColorMessages.getString("editor.preview"); //$NON-NLS-1$
		document.set(previewContent);

		// Setup syntax highlighting and partitioning
		IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoPartitionScanner(),
				AcceleoPartitionScanner.LEGAL_CONTENT_TYPES);
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);

		final IPreferenceChangeListener preferenceListener = new IPreferenceChangeListener() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener#preferenceChange(org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent)
			 */
			public void preferenceChange(PreferenceChangeEvent event) {
				configuration.adaptToPreferenceChanges(event);
				previewViewer.invalidateTextPresentation();
			}
		};

		proxyScope.addPreferenceChangeListener(preferenceListener);
		previewViewer.getTextWidget().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				proxyScope.removePreferenceChangeListener(preferenceListener);
			}
		});

		return previewViewer.getControl();
	}

	/**
	 * Label provider for our {@link #colorViewer}.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ColorViewerLabelProvider extends LabelProvider implements IColorProvider {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
		 */
		public Color getBackground(Object element) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
		 */
		public Color getForeground(Object element) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof ColorCategory) {
				return ((ColorCategory)element).getName();
			}
			return ((ColorItem)element).getName();
		}
	}

	/**
	 * Content provider for our {@link #colorViewer}.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ColorViewerContentProvider implements ITreeContentProvider {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				List<ColorCategory> input = (List<ColorCategory>)inputElement;
				return input.toArray(new ColorCategory[input.size()]);
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ColorCategory) {
				return ((ColorCategory)parentElement).getChildren();
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		public Object getParent(Object element) {
			if (element instanceof ColorItem) {
				return ((ColorItem)element).getCategory();
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		public boolean hasChildren(Object element) {
			return element instanceof ColorCategory;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// We do not need to react to input changes
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
			// Nothing to dispose
		}
	}

	/**
	 * Represents a category in the color viewer.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ColorCategory {
		/** List of this category's children. */
		private final ColorItem[] children;

		/** Human-readable name of this category. */
		private final String displayName;

		/**
		 * Instantiates a Category given its children colors.
		 * 
		 * @param displayName
		 *            Display name of this category.
		 * @param children
		 *            The colors that are to be displayed as children of this category.
		 */
		public ColorCategory(String displayName, AcceleoColor[] children) {
			this.displayName = displayName;
			this.children = new ColorItem[children.length];
			for (int i = 0; i < children.length; i++) {
				this.children[i] = new ColorItem(this, children[i]);
			}
		}

		/**
		 * Returns the name of this category.
		 * 
		 * @return The name of this category.
		 */
		public String getName() {
			return displayName;
		}

		/**
		 * Returns the children of this category.
		 * 
		 * @return The children of this category.
		 */
		public ColorItem[] getChildren() {
			return children;
		}
	}

	/**
	 * Wraps an {@link AcceleoColor} for our tree viewer.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ColorItem {
		/** Category in which this item is displayed. */
		private final ColorCategory category;

		/** The wrapped color. */
		private final AcceleoColor color;

		/**
		 * Instantiates a tree item given its wrapped color.
		 * 
		 * @param category
		 *            The category this color belongs to.
		 * @param color
		 *            The color we are to wrap.
		 */
		public ColorItem(ColorCategory category, AcceleoColor color) {
			this.category = category;
			this.color = color;
		}

		/**
		 * Returns the wrapped {@link AcceleoColor}.
		 * 
		 * @return The wrapped {@link AcceleoColor}.
		 */
		public AcceleoColor getColor() {
			return color;
		}

		/**
		 * Returns the human-readable name for the wrapped color.
		 * 
		 * @return The human-readable name for the wrapped color.
		 */
		public String getName() {
			return AcceleoColorMessages.getString(color.getPreferenceKey());
		}

		/**
		 * Returns the category in which this item is displayed.
		 * 
		 * @return The category in which this item is displayed.
		 */
		public ColorCategory getCategory() {
			return category;
		}
	}
}
