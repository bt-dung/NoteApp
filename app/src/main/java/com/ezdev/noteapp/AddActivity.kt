package com.ezdev.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {

    private var dao: DAO? = null
    var addTitle: EditText? = null
    var addContent: EditText? = null
    var setDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note)
        addTitle = findViewById<EditText>(R.id.titleNote)
         addContent = findViewById<EditText>(R.id.contentNt)
         setDate = findViewById<TextView>(R.id.textDate)

        dao = DAO(this)

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        setDate!!.setText(currentDate)

        var touchBack = findViewById<ImageButton>(R.id.touchBack)
        touchBack.setOnClickListener {
            insertNote()
        }

    }

    fun insertNote() {
        val title = addTitle!!.text.toString()
        val content = addContent!!.text.toString()
        val date = setDate!!.text.toString()

        val inserted = Note(title, content, date)

        val checkSussess = dao?.insertData(inserted)
        if (checkSussess == true) {
            Toast.makeText(this, "Bản ghi được tạo!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Bạn cần tạo một chủ đề!!", Toast.LENGTH_SHORT).show()
        }


        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        insertNote()
        super.onBackPressed()
    }
}


