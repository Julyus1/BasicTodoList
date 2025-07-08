package com.example.basictodolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TodoAdaptor (val context: Context, val todoList: MutableList<TodoItem>): RecyclerView.Adapter<TodoAdaptor.TodoViewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_layout, parent, false)
        return TodoViewholder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewholder, position: Int) {
        holder.title.text = todoList[position].title
        holder.isChecked.isChecked = todoList[position].isDone

        holder.isChecked.setOnCheckedChangeListener{_, ischecked->
            if(ischecked) {
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    CoroutineScope(Dispatchers.IO).launch {
                        context.todosDataStore.updateData { currentTodos ->
                            val currentList = currentTodos.itemsList

                            val newlist = currentList.toMutableList().apply {
                                removeAt(position)
                            }

                            currentTodos.toBuilder()
                                .clearItems()
                                .addAllItems(newlist)
                                .build()
                        }
                    }
                }
            }
        }
    }

    class TodoViewholder(view: View): RecyclerView.ViewHolder(view) {
        val title:TextView = view.findViewById(R.id.tvTitle)
        val isChecked:CheckBox = view.findViewById(R.id.cbIsDone)

    }
}