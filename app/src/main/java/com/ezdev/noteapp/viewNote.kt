package com.ezdev.noteapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class viewNote : AppCompatActivity() {
    private lateinit var dao: DAO
    lateinit var addTitle: EditText
    lateinit var addContent: EditText
    lateinit var setDate: TextView
    var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note)

        addTitle = findViewById<EditText>(R.id.titleNote)
        addContent = findViewById<EditText>(R.id.contentNt)
        setDate = findViewById<TextView>(R.id.textDate)

        val intent = intent
        noteId = intent.getIntExtra("note_id", 1)
//        Log.e("note_id",noteId.toString())

        dao = DAO(this)
        val note = dao.getDataPosition(noteId)

        addTitle.setText(note.title)
        addContent.setText(note.content)
        setDate.setText(note.dateTime)

        var touchBack = findViewById<ImageButton>(R.id.touchBack)
        touchBack.setOnClickListener {
            updateData()
        }
    }
    fun updateData() {
        val title = addTitle.text.toString()
        val content = addContent.text.toString()
        val date = setDate.text.toString()

//        Log.e("title",title)

        val edit = Note(noteId,title, content, date)

        val checkSussess = dao.editNote(edit)
        if (checkSussess == true) {
            Toast.makeText(this, "Bản ghi được lưu!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "app lon", Toast.LENGTH_SHORT).show()
        }

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        updateData()
        super.onBackPressed()
    }
}