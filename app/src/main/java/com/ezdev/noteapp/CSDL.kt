package com.ezdev.noteapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CSDL(context: Context):SQLiteOpenHelper (context, "DBNoteApp.db",null,1){
    override fun onCreate(db: SQLiteDatabase) {
        db?.execSQL("Create table Note(id integer primary key autoincrement, nameTitle text, Content text, Date strftime)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Note")
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

}