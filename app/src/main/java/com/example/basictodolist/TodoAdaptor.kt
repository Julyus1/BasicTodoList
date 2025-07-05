package com.example.basictodolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoAdaptor (val context: Context, val todoList: MutableList<Todo>): RecyclerView.Adapter<TodoAdaptor.TodoViewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_layout, parent, false)
        return TodoViewholder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewholder, position: Int) {
        holder.title.text = todoList[position].Title
        holder.isChecked.isChecked = todoList[position].isDone

        holder.isChecked.setOnCheckedChangeListener{_, ischecked->
            if(ischecked){
                todoList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemChanged(position, todoList.size)
            }
        }
    }

    class TodoViewholder(view: View): RecyclerView.ViewHolder(view) {
        val title:TextView = view.findViewById(R.id.tvTitle)
        val isChecked:CheckBox = view.findViewById(R.id.cbIsDone)

    }
}