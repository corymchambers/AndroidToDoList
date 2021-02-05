package com.corychambers.androidtodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var todoItemRecyclerView: RecyclerView
    private lateinit var recyclerAdapeter: TodoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var allItemsButton: Button
    private lateinit var todaysItemsButton: Button
    private lateinit var pastItemsButton: Button

    var todoItemsList = ArrayList<TodoItem>()
    var todaysItemsList = ArrayList<TodoItem>()
    var pastItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle("The Greatest To Do List App")
        supportActionBar?.setSubtitle("For now...")

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor) {
            while(moveToNext()) {
                val itemName = cursor.getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = cursor.getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val itemDate = cursor.getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))
                val isUrgent = itemUrgency != 0

                val newTodoItem = TodoItem(itemName, isUrgent)
                newTodoItem.dateString = itemDate

                todoItemsList.add(newTodoItem)

                if (itemDate == getDateAsString()) {
                    todaysItemsList.add(newTodoItem)
                } else {
                    pastItemsList.add(newTodoItem)
                }
            }
        }

        todoItemRecyclerView = findViewById(R.id.todo_item_recycler_view)
        allItemsButton = findViewById(R.id.all_items_button)
        todaysItemsButton = findViewById(R.id.todays_items_button)
        pastItemsButton = findViewById(R.id.past_items_button)

        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdapeter = TodoItemsAdapter(todoItemsList, this)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapeter
        }
    }

    fun displayAllItems(view: View) {
        // fill this out and hook up function to switch the colors
    }

    public fun displayTodaysItems(view: View) {
        recyclerAdapeter = TodoItemsAdapter(todaysItemsList, this)
        todaysItemsButton.setBackgroundResource(R.color.colorLight)
        pastItemsButton.setBackgroundResource(R.color.colorAccent)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapeter
        }
    }

    public fun displayPastItems(view: View) {
        recyclerAdapeter = TodoItemsAdapter(pastItemsList, this)
        pastItemsButton.setBackgroundResource(R.color.colorLight)
        todaysItemsButton.setBackgroundResource(R.color.colorAccent)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapeter
        }
    }

    fun updateTabColors(btn: String) {

    }

    public fun navToAddItemAction(view: View) {
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    fun getDateAsString(): String {
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()

        return "$year/$month/$day"
    }
}