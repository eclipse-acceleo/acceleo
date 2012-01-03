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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferenceNodeVisitor;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * This implementation of a {@link IEclipsePreferences preference store} will act as a proxy between the
 * preference page and the actual plugin preference store. Changes in this preference store will only be
 * propagated to the plugin preferences when the user closes the preference page through "OK".
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ProxyPreferenceStore implements IEclipsePreferences {
	/** The preference store to which we are to propagate changes. */
	private final IEclipsePreferences instanceScope;

	/** The preference store that holds our default values. */
	private final IEclipsePreferences defaultScope;

	/** The buffer in which we'll store preference changes until propagation. */
	private final Map<String, String> preferences = new HashMap<String, String>();

	/** Holds the list of our preference change listeners. */
	private ListenerList preferenceChangeListeners;

	/** This will be in charge of listening to changes in the delegate and propagate them in this proxy. */
	private IPreferenceChangeListener delegateChangesListener;

	/**
	 * Instantiates our preference store given its delegate.
	 * 
	 * @param instanceScope
	 *            The preference store to which we are to propagate changes.
	 * @param defaultScope
	 *            The preference store that holds our default values.
	 */
	public ProxyPreferenceStore(IEclipsePreferences instanceScope, IEclipsePreferences defaultScope) {
		this.instanceScope = instanceScope;
		this.defaultScope = defaultScope;
		load();
		delegateChangesListener = new DelegateChangesListener();
		instanceScope.addPreferenceChangeListener(delegateChangesListener);
	}

	/**
	 * Resets this preference store to its default values.
	 */
	public void resetToDefault() {
		try {
			for (String key : defaultScope.keys()) {
				put(key, defaultScope.get(key, "")); //$NON-NLS-1$
			}
		} catch (BackingStoreException e) {
			// FIXME Shouldnt happen
		}
	}

	/**
	 * Disposes of this preference store.
	 */
	public void dispose() {
		instanceScope.removePreferenceChangeListener(delegateChangesListener);
		delegateChangesListener = null;
		preferenceChangeListeners.clear();
		try {
			clear();
		} catch (BackingStoreException e) {
			// FIXME Shouldnt happen
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#put(java.lang.String, java.lang.String)
	 */
	public void put(String key, String value) {
		String oldValue = preferences.put(key, value);
		if (oldValue == null || !oldValue.equals(value)) {
			firePreferenceChangeEvent(key, oldValue, value);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#get(java.lang.String, java.lang.String)
	 */
	public String get(String key, String def) {
		return preferences.get(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#remove(java.lang.String)
	 */
	public void remove(String key) {
		String oldValue = preferences.remove(key);
		firePreferenceChangeEvent(key, oldValue, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#clear()
	 */
	public void clear() throws BackingStoreException {
		for (String key : new LinkedHashSet<String>(preferences.keySet())) {
			remove(key);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#putInt(java.lang.String, int)
	 */
	public void putInt(String key, int value) {
		if (key == null) {
			throw new NullPointerException();
		}
		String newValue = Integer.toString(value);
		String oldValue = preferences.put(key, newValue);
		if (oldValue == null || !oldValue.equals(newValue)) {
			firePreferenceChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#getInt(java.lang.String, int)
	 */
	public int getInt(String key, int def) {
		String value = preferences.get(key);
		int result = def;
		if (value != null) {
			try {
				result = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				// Nothing to do : return the default value
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#putLong(java.lang.String, long)
	 */
	public void putLong(String key, long value) {
		if (key == null) {
			throw new NullPointerException();
		}
		String newValue = Long.toString(value);
		String oldValue = preferences.put(key, newValue);
		if (oldValue == null || !oldValue.equals(newValue)) {
			firePreferenceChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#getLong(java.lang.String, long)
	 */
	public long getLong(String key, long def) {
		String value = preferences.get(key);
		long result = def;
		if (value != null) {
			try {
				result = Long.parseLong(value);
			} catch (NumberFormatException e) {
				// Nothing to do : return the default value
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#putBoolean(java.lang.String, boolean)
	 */
	public void putBoolean(String key, boolean value) {
		if (key == null) {
			throw new NullPointerException();
		}
		String newValue = Boolean.toString(value);
		String oldValue = preferences.put(key, newValue);
		if (oldValue == null || !oldValue.equals(newValue)) {
			firePreferenceChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#getBoolean(java.lang.String, boolean)
	 */
	public boolean getBoolean(String key, boolean def) {
		String value = preferences.get(key);
		boolean result = def;
		if (value != null) {
			result = Boolean.parseBoolean(value);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#putFloat(java.lang.String, float)
	 */
	public void putFloat(String key, float value) {
		if (key == null) {
			throw new NullPointerException();
		}
		String newValue = Float.toString(value);
		String oldValue = preferences.put(key, newValue);
		if (oldValue == null || !oldValue.equals(newValue)) {
			firePreferenceChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#getFloat(java.lang.String, float)
	 */
	public float getFloat(String key, float def) {
		String value = preferences.get(key);
		float result = def;
		if (value != null) {
			try {
				result = Float.parseFloat(value);
			} catch (NumberFormatException e) {
				// Nothing to do : return the default value
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#putDouble(java.lang.String, double)
	 */
	public void putDouble(String key, double value) {
		if (key == null) {
			throw new NullPointerException();
		}
		String newValue = Double.toString(value);
		String oldValue = preferences.put(key, newValue);
		if (oldValue == null || !oldValue.equals(newValue)) {
			firePreferenceChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#getDouble(java.lang.String, double)
	 */
	public double getDouble(String key, double def) {
		String value = preferences.get(key);
		double result = def;
		if (value != null) {
			try {
				result = Double.parseDouble(value);
			} catch (NumberFormatException e) {
				// Nothing to do : return the default value
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#putByteArray(java.lang.String, byte[])
	 */
	public void putByteArray(String key, byte[] value) {
		if (key == null) {
			throw new NullPointerException();
		}
		String newValue = new String(value);
		String oldValue = preferences.put(key, newValue);
		if (oldValue == null || !oldValue.equals(newValue)) {
			firePreferenceChangeEvent(key, oldValue, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#getByteArray(java.lang.String, byte[])
	 */
	public byte[] getByteArray(String key, byte[] def) {
		String value = preferences.get(key);
		byte[] result = def;
		if (value != null) {
			result = value.getBytes();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#keys()
	 */
	public String[] keys() throws BackingStoreException {
		Set<String> keys = preferences.keySet();
		return keys.toArray(new String[keys.size()]);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#addPreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		if (preferenceChangeListeners == null) {
			preferenceChangeListeners = new ListenerList();
		}
		preferenceChangeListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#removePreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void removePreferenceChangeListener(IPreferenceChangeListener listener) {
		if (preferenceChangeListeners != null) {
			preferenceChangeListeners.remove(listener);
		}
	}

	/**
	 * This will be used to propagate all changes made to this proxy to its delegate preference store.
	 */
	public void propagate() {
		for (Map.Entry<String, String> entry : preferences.entrySet()) {
			String originValue = instanceScope.get(entry.getKey(), null);
			if (originValue == null || !originValue.equals(entry.getValue())) {
				instanceScope.put(entry.getKey(), entry.getValue());
			}
		}

		try {
			instanceScope.flush();
		} catch (BackingStoreException e) {
			// FIXME log
		}
	}

	/**
	 * Loads up all key<->value pairs from the delegate store.
	 */
	private void load() {
		try {
			// First load all default values
			for (String key : defaultScope.keys()) {
				preferences.put(key, defaultScope.get(key, "")); //$NON-NLS-1$
			}
			// Then override them with instance values
			for (String key : instanceScope.keys()) {
				preferences.put(key, instanceScope.get(key, "")); //$NON-NLS-1$
			}
		} catch (BackingStoreException e) {
			// FIXME shouldn't happen, log anyways
		}
	}

	/**
	 * Fires preference change events to all registered listeners.
	 * 
	 * @param key
	 *            Key of the preference that just changed.
	 * @param oldValue
	 *            Old value of said preference.
	 * @param newValue
	 *            New value of the preference.
	 */
	private void firePreferenceChangeEvent(String key, Object oldValue, Object newValue) {
		if (preferenceChangeListeners != null) {
			final PreferenceChangeEvent event = new PreferenceChangeEvent(this, key, oldValue, newValue);

			for (final Object listener : preferenceChangeListeners.getListeners()) {
				if (listener instanceof IPreferenceChangeListener) {
					ISafeRunnable job = new ISafeRunnable() {
						/**
						 * {@inheritDoc}
						 * 
						 * @see org.eclipse.core.runtime.ISafeRunnable#handleException(java.lang.Throwable)
						 */
						public void handleException(Throwable exception) {
							// already logged
						}

						/**
						 * {@inheritDoc}
						 * 
						 * @see org.eclipse.core.runtime.ISafeRunnable#run()
						 */
						public void run() throws Exception {
							((IPreferenceChangeListener)listener).preferenceChange(event);
						}
					};
					SafeRunner.run(job);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#removeNode()
	 */
	public void removeNode() throws BackingStoreException {
		// Useless in our case
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#node(java.lang.String)
	 */
	public Preferences node(String path) {
		// Useless in our case
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#accept(org.eclipse.core.runtime.preferences.IPreferenceNodeVisitor)
	 */
	public void accept(IPreferenceNodeVisitor visitor) throws BackingStoreException {
		// Useless in our case
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#childrenNames()
	 */
	public String[] childrenNames() throws BackingStoreException {
		// Useless in our case
		return new String[0];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#parent()
	 */
	public Preferences parent() {
		// Useless in our case
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#nodeExists(java.lang.String)
	 */
	public boolean nodeExists(String pathName) throws BackingStoreException {
		// Useless in our case
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#name()
	 */
	public String name() {
		// Useless in our case
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#absolutePath()
	 */
	public String absolutePath() {
		// Useless in our case
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#flush()
	 */
	public void flush() throws BackingStoreException {
		// Useless in our case
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.osgi.service.prefs.Preferences#sync()
	 */
	public void sync() throws BackingStoreException {
		// Useless in our case
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#addNodeChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener)
	 */
	public void addNodeChangeListener(INodeChangeListener listener) {
		// Useless in our case
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences#removeNodeChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener)
	 */
	public void removeNodeChangeListener(INodeChangeListener listener) {
		// Useless in our case
	}

	/**
	 * This will be used to react to changes made in our delegate preference store.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class DelegateChangesListener implements IPreferenceChangeListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener#preferenceChange(org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent)
		 */
		public void preferenceChange(PreferenceChangeEvent event) {
			String key = event.getKey();
			Object newValue = event.getNewValue();
			// should be a String
			if (newValue instanceof String) {
				String currentValue = preferences.get(key);
				if (currentValue == null || !currentValue.equals(newValue)) {
					put(key, (String)newValue);
				}
			}
		}
	}
}
