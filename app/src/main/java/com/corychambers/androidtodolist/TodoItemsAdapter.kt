package com.corychambers.androidtodolist

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class TodoItemsAdapter(private val todoItemsList: ArrayList<TodoItem>, val activity: MainActivity): RecyclerView.Adapter<TodoItemsAdapter.ViewHolder>() {

    class ViewHolder( val constraintLayout: ConstraintLayout): RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val constraintLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_item_layout, parent, false) as ConstraintLayout

        constraintLayout.setOnClickListener(View.OnClickListener {
            val nameTextView = constraintLayout.getChildAt(0) as TextView
            val urgencyTextView = constraintLayout.getChildAt(1) as TextView
            val nameText = nameTextView.text
            val urgencyVisibility = urgencyTextView.visibility
            val isItemUrgent = urgencyVisibility == 0

            val intent: Intent = Intent(parent.context, AddItemActivity::class.java)
            intent.putExtra("ITEM_NAME", nameText)
            intent.putExtra("ITEM_URGENCY", isItemUrgent)
            activity.startActivity(intent)
        })

        constraintLayout.setOnLongClickListener(View.OnLongClickListener {
            val position: Int = parent.indexOfChild(it)

            val todoItemRemove = activity.todoItemsList[position]
            val dbo = DatabaseOperations(parent.context)
            dbo.deleteItem(dbo, todoItemRemove)

            todoItemsList.removeAt(position)
            notifyItemRemoved(position)
            true
        })

        return ViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val constraintLayout = holder.constraintLayout
        val nameTextView = constraintLayout.getChildAt(0) as TextView
        val urgencyIcon = constraintLayout.getChildAt(1) as TextView
        val dateTextView = constraintLayout.getChildAt(2) as TextView
        nameTextView.text = todoItemsList[position].name
        urgencyIcon.visibility = if (todoItemsList[position].isUrgent) View.VISIBLE else View.INVISIBLE
        dateTextView.text = todoItemsList[position].dateString
    }

    override fun getItemCount(): Int {
        return todoItemsList.size
    }
}