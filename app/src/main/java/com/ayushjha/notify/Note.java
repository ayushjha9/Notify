package com.ayushjha.notify;

/**
 * Created by ayushjha on 9/25/17.
 */

public class Note {
    String _id;
    String _noteString;

    public Note(String id,String noteString){
        this._id=id;
        this._noteString = noteString;

    }

    public Note() {

    }

    public String getID(){
        return this._id;
    }

    public void setID(String id){
        this._id=id;
    }

    public String getNoteString(){
        return this._noteString;
    }

    public void setNoteString(String noteString){
        this._noteString = noteString;
    }

}
