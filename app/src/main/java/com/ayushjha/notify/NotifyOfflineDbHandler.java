package com.ayushjha.notify;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ayushjha on 9/25/17.
 */

public class NotifyOfflineDbHandler {
    private static final int DATABASE_VERSION = 1;
    //DB Name
    private static final String DATABASE_NAME = "nDB";
    //table name

    private static final String TABLE_NOTES = "notes";

    private static final String KEY_ID = "id";
    private static final String KEY_NOTESTRING = "name";

    public static NotifyOfflineDbHandler mInstance = null;


    public NotifyOfflineDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "( "
                + KEY_ID + " TEXT PRIMARY KEY, " + KEY_NOTESTRING
                +" TEXT"+
                ")";

        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }


    //CRUD(Create Read Update Delete) operations

    void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getID());
        values.put(KEY_NOTESTRING, note.getNoteString());
        db.insertWithOnConflict(TABLE_NOTES, null, values,0);
        db.close();
    }

    Note getNote(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                        KEY_NOTESTRING}, KEY_ID + "=?",
                new String[] { String.valueOf(id)}, null, null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount()>0) {

            Note note = new Note((cursor.getString(0)), cursor.getString(1));

            return note;
        }
        return null;
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<note>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES +" ORDER BY id asc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(cursor.getString(0));
                note.setNoteString(cursor.getString(1));

                noteList.add(note);
            } while (cursor.moveToNext());
        }

        return noteList;
    }

    public int updateNote(Note note, String strId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getID());
        values.put(KEY_NOTESTRING, note.getNoteString();


        int id = (int) db.insertWithOnConflict(TABLE_NOTES, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        if (id == -1) {
            return db.update(TABLE_NOTES, values, "_id=?", new String[] {strId});  // number 1 is the _id here, update to variable for your code
        }

        return 0;


    }

    public void deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " =?", new String[] { note.getID()});
        db.close();
    }

    public int getNoteCount(){
        String countQuery = "SELECT * FROM "+ TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

// return count
        return count ;
    }


    public static NotifyOfflineDbHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotifyOfflineDbHandler(context.getApplicationContext());
        }
        return mInstance;
    }


}

