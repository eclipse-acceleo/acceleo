/*******************************************************************************
 * Copyright (c) 2006, 2007, 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package android.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * MyContactsActivity.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class MyContactsEdit extends Activity {

	private EditText wFirstName;

	private EditText wLastName;

	private EditText wPhoneNumber;

	private EditText wEmail;

	private Spinner wCountry;

	private Button wSave;

	
	private Long rowId;
    private MyContactsDbAdapter dbHelper;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);
		dbHelper = new MyContactsDbAdapter(this);
	    dbHelper.open();
		setContentView(R.layout.edit);
	
		rowId = instanceState != null ? instanceState.getLong(MyContactsDbAdapter.KEY_ROWID) : null;
		if (rowId == null) {
			Bundle extras = getIntent().getExtras();
			rowId = extras != null ? extras.getLong(MyContactsDbAdapter.KEY_ROWID) : null;
		}
		
		initControls();
		
		populateFields();
	}

	private void initControls() {
		wFirstName = (EditText) findViewById(R.id.wFirstName);

		wLastName = (EditText) findViewById(R.id.wLastName);

		wPhoneNumber = (EditText) findViewById(R.id.wPhoneNumber);

		wEmail = (EditText) findViewById(R.id.wEmail);

		wCountry = (Spinner) findViewById(R.id.wCountry);

		wSave = (Button) findViewById(R.id.wSave);
		wSave.setText("Save");
		wSave.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				 setResult(RESULT_OK);
	        	 finish();
			}
		});

	}
	
    private void populateFields() {
        if (rowId != null) {
            Cursor c = dbHelper.fetchNote(rowId);
            startManagingCursor(c);
            
            wFirstName.setText(c.getString(c.getColumnIndexOrThrow(MyContactsDbAdapter.KEY_FIRSTNAME)));
            
            wLastName.setText(c.getString(c.getColumnIndexOrThrow(MyContactsDbAdapter.KEY_LASTNAME)));
            
            wPhoneNumber.setText(c.getString(c.getColumnIndexOrThrow(MyContactsDbAdapter.KEY_PHONENUMBER)));
            
            wEmail.setText(c.getString(c.getColumnIndexOrThrow(MyContactsDbAdapter.KEY_EMAIL)));
            
            wCountry.setSelection(c.getInt(c.getColumnIndexOrThrow(MyContactsDbAdapter.KEY_COUNTRY)));

        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MyContactsDbAdapter.KEY_ROWID, rowId);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        String firstName = wFirstName.getText().toString();
        
        String lastName = wLastName.getText().toString();
        
        String phoneNumber = wPhoneNumber.getText().toString();
        
        String email = wEmail.getText().toString();
        
        int country = wCountry.getSelectedItemPosition();


        if (rowId == null) {
            long id = dbHelper.createMyContacts(firstName,lastName,phoneNumber,email,country);
            if (id > 0) {
                rowId = id;
            }
        } else {
            dbHelper.updateNote(rowId,firstName,lastName,phoneNumber,email,country);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(wFirstName.getText().toString());

		result.append(wLastName.getText().toString());

		result.append(wPhoneNumber.getText().toString());

		result.append(wEmail.getText().toString());

		result.append(wCountry.getSelectedItemPosition());

		// We don't display the "Save" Button.


		return result.toString();
	}

}
