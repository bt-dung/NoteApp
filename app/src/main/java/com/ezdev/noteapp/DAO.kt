package com.ezdev.noteapp

import android.content.Context
import android.util.Log
import android.content.ContentValues as ContentValues

class DAO(context: Context) {
    private val csdl = CSDL(context)

    fun insertData(note: Note ): Boolean {
        if(note.title.equals("")){
            return false
        }
        // Gets the data repository in write mode
        val db = csdl.writableDatabase
        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put("nameTitle", note.title)
            put("Content", note.content)
            put("Date", note.dateTime)
        }
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert("NOTE", null, values)

        return newRowId != -1L
    }
    fun getData(): List<Note> {
        val db = csdl.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM NOTE", null)
        val notes = mutableListOf<Note>()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val note = Note(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nameTitle")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Content")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Date")),
                )
                notes.add(note)
            }
        }
        cursor.close()

        return notes
    }
    fun deleteData(id: List<Int>): Boolean{
        val db = csdl.writableDatabase
        for(i in 0.. id.size-1){
            db.delete("Note","id=?", arrayOf(id[i].toString()))
        }
        return true
    }
    fun editNote(note: Note) : Boolean{
        if(note.title.equals("")){
            return false
        }
        val db = csdl.writableDatabase
//        Log.e("note",note.toString())

        val values = ContentValues().apply {
            put("nameTitle", note.title)
            put("Content", note.content)
            put("Date", note.dateTime)
        }
        val success = db.update("NOTE", values,"id=?", arrayOf(note.id.toString()))
        db.close()
        return success != -1
    }
    fun getDataPosition(id: Int) : Note{
        val db = csdl.writableDatabase
        val getData = db.rawQuery("select * from NOTE where id = ?", arrayOf(id.toString()))

        lateinit var note: Note
        if(getData.count > 0){
            getData.moveToNext()
            note = Note(
                getData.getInt(getData.getColumnIndexOrThrow("id")),
                getData.getString(getData.getColumnIndexOrThrow("nameTitle")),
                getData.getString(getData.getColumnIndexOrThrow("Content")),
                getData.getString(getData.getColumnIndexOrThrow("Date")),
            )
        }

        Log.e("note",note.toString())
        return note

    }




}
