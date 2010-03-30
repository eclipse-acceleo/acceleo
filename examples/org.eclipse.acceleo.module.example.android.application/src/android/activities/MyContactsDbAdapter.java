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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyContactsDbAdapter {

    private static final String TAG = "MyContactsDbAdapter";
    
    public static final String KEY_ROWID = "_id";

    public static final String KEY_FIRSTNAME = "firstname";
    
    public static final String KEY_LASTNAME = "lastname";
    
    public static final String KEY_PHONENUMBER = "phonenumber";
    
    public static final String KEY_EMAIL = "email";
    
    public static final String KEY_COUNTRY = "country";

      
    private static final String DATABASE_NAME = "mycontacts_db";
    private static final String DATABASE_TABLE = "mycontacts";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
            "create table mycontacts (_id integer primary key autoincrement"
    	
                    + ", firstname text not null"

                    + ", lastname text not null"

                    + ", phonenumber text not null"

                    + ", email text not null"

                    + ", country integer not null"


                    + ");";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private final Context context;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion);
            db.execSQL("DROP TABLE IF EXISTS mycontacts");
            onCreate(db);
        }
    }

    public MyContactsDbAdapter(Context context) {
        this.context = context;
    }

    public MyContactsDbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        dbHelper.close();
    }

    public long createMyContacts(String firstName,String lastName,String phoneNumber,String email,int country) {
        ContentValues args = new ContentValues();
        
        args.put(KEY_FIRSTNAME, firstName);
        
        args.put(KEY_LASTNAME, lastName);
        
        args.put(KEY_PHONENUMBER, phoneNumber);
        
        args.put(KEY_EMAIL, email);
        
        args.put(KEY_COUNTRY, country);

        
        return db.insert(DATABASE_TABLE, null, args);
    }
    
    public boolean updateNote(long rowId, String firstName,String lastName,String phoneNumber,String email,int country) {
        ContentValues args = new ContentValues();
        
        args.put(KEY_FIRSTNAME, firstName);
        
        args.put(KEY_LASTNAME, lastName);
        
        args.put(KEY_PHONENUMBER, phoneNumber);
        
        args.put(KEY_EMAIL, email);
        
        args.put(KEY_COUNTRY, country);

        
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteMyContacts(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

     public Cursor fetchAllMyContacts() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID
        		
        		, KEY_FIRSTNAME
        		
        		, KEY_LASTNAME
        		
        		, KEY_PHONENUMBER
        		
        		, KEY_EMAIL
        		
        		, KEY_COUNTRY

        		
        	}, null, null, null, null, null);
    }

    public Cursor fetchNote(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID
        		
        		, KEY_FIRSTNAME
        		
        		, KEY_LASTNAME
        		
        		, KEY_PHONENUMBER
        		
        		, KEY_EMAIL
        		
        		, KEY_COUNTRY

        		
        	}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
