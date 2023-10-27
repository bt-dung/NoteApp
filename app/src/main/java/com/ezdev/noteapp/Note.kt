package com.ezdev.noteapp

import com.google.firebase.database.ValueEventListener

data class Note(val id: Int, var title: String, var content: String, var dateTime: String) {
    var check: Boolean = false
    constructor(title: String, content: String, dateTime: String) : this(
        id = 0,
        title = title,
        content = content,
        dateTime = dateTime
    )

}
