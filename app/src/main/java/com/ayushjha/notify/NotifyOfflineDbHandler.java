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
    private static final String DATABASE_NAME = "wllDB3";
    //table name

    private static final String TABLE_NOTES = "waypoints";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LATLON = "latlon";
    private static final String KEY_LATLON1 = "latlon1";

    private static final String KEY_PLACEBEARING = "placeBearing";
    private static final String KEY_PLACEDIST = "placeDist";
    private static final String KEY_PLACENAME = "placeName";
    private static final String KEY_PLACELAT ="placeLat";
    private static final String KEY_PLACELON = "placeLon";

    private static final String KEY_PLACENAME1 = "placeName1";
    private static final String KEY_PLACELAT1 ="placeLat1";
    private static final String KEY_PLACELON1 = "placeLon1";
    private static final String KEY_PLACEBEARING1 = "placeBearing1";
    private static final String KEY_FORMAT = "format";
    public static NotifyOfflineDbHandler mInstance = null;


    public NotifyOfflineDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "( "
                + KEY_ID + " TEXT PRIMARY KEY, " + KEY_NAME + " TEXT,"
                + KEY_LATLON + " TEXT," + KEY_LATLON1 + " TEXT," + KEY_PLACEBEARING + " TEXT," + KEY_PLACEDIST + " TEXT,"+ KEY_PLACENAME + " TEXT,"
                + KEY_PLACELAT + " TEXT,"+KEY_PLACELON+" TEXT," + KEY_PLACENAME1 +" TEXT," + KEY_PLACELAT1+" TEXT,"+KEY_PLACELON1+" TEXT," + KEY_PLACEBEARING1 +" TEXT,"+ KEY_FORMAT+" TEXT"+
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
        values.put(KEY_NAME, note.getName());
        values.put(KEY_LATLON, note.getLat());
        values.put(KEY_LATLON1, note.getLon());

        values.put(KEY_PLACEBEARING, note.getPlaceBearing());
        values.put(KEY_PLACEDIST, note.getPlaceDistance());
        values.put(KEY_PLACENAME, note.getPlaceName());
        values.put(KEY_PLACELAT, note.getPlaceLat());
        values.put(KEY_PLACELON, note.getPlaceLon());

        values.put(KEY_PLACENAME1, note.getPlaceName1());
        values.put(KEY_PLACELAT1, note.getPlaceLat1());
        values.put(KEY_PLACELON1, note.getPlaceLon1());
        values.put(KEY_PLACEBEARING1, note.getPlaceBearing1());
        values.put(KEY_FORMAT, note.getFormat());

        db.insertWithOnConflict(TABLE_NOTES, null, values,0);
        db.close();
    }

    Note getNote(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                        KEY_NAME, KEY_LATLON,KEY_LATLON1,KEY_PLACEBEARING,KEY_PLACEDIST,KEY_PLACENAME,KEY_PLACELAT,KEY_PLACELON,KEY_PLACENAME1,KEY_PLACELAT1,KEY_PLACELON1,KEY_PLACEBEARING1,KEY_FORMAT}, KEY_ID + "=?",
                new String[] { String.valueOf(id)}, null, null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        if(cursor.getCount()>0) {

            Note note = new Note((cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12), cursor.getString(13));

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
                note.setName(cursor.getString(1));
                note.setLat(cursor.getString(2));
                note.setLon(cursor.getString(3));
                note.setPlaceBearing(cursor.getString(4));
                note.setPlaceDistance(cursor.getString(5));
                note.setPlaceName(cursor.getString(6));
                note.setPlaceLat(cursor.getString(7));
                note.setPlaceLon(cursor.getString(8));
                note.setPlaceName1(cursor.getString(9));
                note.setPlaceLat1(cursor.getString(10));
                note.setPlaceLon1(cursor.getString(11));
                note.setPlaceBearing1(cursor.getString(12));
                note.setFormat(cursor.getString(13));

                noteList.add(note);
            } while (cursor.moveToNext());
        }

        return noteList;
    }

    public int updateNote(Note note, String strId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getID());
        values.put(KEY_NAME, note.getName());
        values.put(KEY_LATLON, note.getLat());
        values.put(KEY_LATLON1, note.getLon());

        values.put(KEY_PLACEBEARING,note.getPlaceBearing());
        values.put(KEY_PLACEDIST,note.getPlaceDistance());
        values.put(KEY_PLACENAME, note.getPlaceName());
        values.put(KEY_PLACELAT, note.getPlaceLat());
        values.put(KEY_PLACELON, note.getPlaceLon());

        values.put(KEY_PLACENAME1,note.getPlaceName1());
        values.put(KEY_PLACELAT1,note.getPlaceLat1());
        values.put(KEY_PLACELON1,note.getPlaceLon1());
        values.put(KEY_PLACEBEARING1,note.getPlaceBearing1());
        values.put(KEY_FORMAT,note.getFormat());

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

