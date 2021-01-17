package com.corychambers.androidtodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText

class AddItemActivity : AppCompatActivity() {

    private lateinit var addTodoName: EditText
    private lateinit var urgentCheck: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        addTodoName = findViewById(R.id.add_todo_name)
        urgentCheck = findViewById(R.id.urgent_check)
    }

    public fun cancelPressed(view: View) {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    public fun savePressed(view: View) {

    }
}