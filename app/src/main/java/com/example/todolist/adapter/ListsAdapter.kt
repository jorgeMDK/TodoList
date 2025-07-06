package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.Task

class ListsAdapter(
    private val taskList:List<Task>,
    private val onDetailClickListener: (Task, position: Int) -> Unit,
    private val onLongClickListener: (Task, position: Int) -> Boolean
):RecyclerView.Adapter<ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = taskList[position]
        holder.render(item, onDetailClickListener, onLongClickListener)

    }
}