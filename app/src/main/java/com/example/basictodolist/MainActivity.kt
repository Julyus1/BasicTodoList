package com.example.basictodolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale
import com.example.basictodolist.todosDataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var tvYear: TextView
    private lateinit var tvMonth: TextView
    private lateinit var tvDaynum: TextView
    private lateinit var tvDay: TextView
    private lateinit var fbAdd: FloatingActionButton
    private lateinit var savebtn: Button
    private lateinit var etNewtodo: EditText

    private lateinit var rvTodo: RecyclerView
    private lateinit var todoAdaptor: TodoAdaptor

    private val sampleList = mutableListOf<TodoItem>(TodoItem("Read a book", false), TodoItem("Play dota", false), TodoItem("Go for a walk", false))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvYear = findViewById(R.id.tvYear)
        tvMonth = findViewById(R.id.tvMonth)
        tvDaynum = findViewById(R.id.tvDaynum)
        tvDay = findViewById(R.id.tvDay)

        fbAdd = findViewById(R.id.fbAdd)

        var currentdate: Date = Date()

        val dayFormat: SimpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val monthFormat:SimpleDateFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val dateFormat: SimpleDateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val yearFormat: SimpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())

        val dayName:String = dayFormat.format(currentdate)
        val daynum:String = dateFormat.format(currentdate)
        val yearName:String = yearFormat.format(currentdate)
        val monthName:String = monthFormat.format(currentdate)

        val dataStore = this.todosDataStore
        lifecycleScope.launch {
            dataStore.data.collect { todosProto ->
                val items = todosProto.itemsList.map { todo ->
                    TodoItem(todo.title, todo.isDone)
                }

                todoAdaptor = TodoAdaptor(applicationContext,items.toMutableList())

        tvDay.text = dayName
        tvDaynum.text = daynum
        tvYear.text = yearName
        tvMonth.text = monthName



        rvTodo = findViewById(R.id.rvTodo)


        rvTodo.adapter = todoAdaptor
        rvTodo.layoutManager = LinearLayoutManager(applicationContext)



fbAdd.setOnClickListener{
    val bottomSheet = BottomSheetFragment()
     bottomSheet.show(supportFragmentManager, "BottomSheetFragment")
    bottomSheet.TodoAdd = { todo ->
        lifecycleScope.launch {
            val newTodo = Todo.newBuilder()
                .setTitle(todo)
                .setIsDone(false)
                .build()

            todosDataStore.updateData { currentTodo ->
                currentTodo.toBuilder()
                    .addItems(newTodo)
                    .build()
            }
        }

    }
}



        }
}}}