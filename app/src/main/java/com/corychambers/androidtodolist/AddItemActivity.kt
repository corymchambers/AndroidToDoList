package com.corychambers.androidtodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class AddItemActivity : AppCompatActivity() {

    private lateinit var itemnameEditText: EditText
    private lateinit var urgentCheck: CheckBox
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        itemnameEditText = findViewById(R.id.item_name_edit_text)
        urgentCheck = findViewById(R.id.urgent_check)
        titleTextView = findViewById(R.id.add_todo_title)

        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemUrgency = intent.getBooleanExtra("ITEM_URGENCY", false)

        if (itemName != null) {
            itemnameEditText.setText(itemName)
            titleTextView.setText(R.string.edit_item_message)
        }

        if (itemUrgency == true) {
            urgentCheck.isChecked = true
        }
    }

    public fun cancelPressed(view: View) {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    public fun savePressed(view: View) {

    }
}