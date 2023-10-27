package com.ezdev.noteapp//package com.ezdev.noteapp
//
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.util.Log
//import java.io.File
//import java.io.FileOutputStream
//
//class DbHelper(private  val context: Context){
//    companion object {
//        private val DbName  = "DBAppNote.db"
//    }
//    fun openDatabase():SQLiteDatabase{
//        val dbFile = context.getDatabasePath(DbName)
//        val file = File(dbFile.toString())
//        if(file.exists()){
//            Log.e(".","file da ton tai")
//        }
//        else{
//            copyDatabase(dbFile)
//        }
//        return SQLiteDatabase.openDatabase(dbFile.path,null,SQLiteDatabase.OPEN_READWRITE)
//    }
//
//    private fun copyDatabase(dbFile: File?) {
//        val openDB= context.assets.open(DbName)
//        val outputStream = FileOutputStream(dbFile)
//        val buffer = ByteArray(1024)
//        while(openDB.read(buffer)>0){
//            outputStream.write(buffer)
//        }
//        outputStream.flush()
//        outputStream.close()
//        openDB.close()
//    }
//
//}