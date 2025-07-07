package com.example.basictodolist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BottomSheetFragment: BottomSheetDialogFragment(){

    var TodoAdd: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTodo = view.findViewById<EditText>(R.id.etTodo)
        val savebtn = view.findViewById<Button>(R.id.btnSave)
        val close = view.findViewById<TextView>(R.id.tvDismiss)

        val closeTextColor = close.currentTextColor

   close.setOnLongClickListener {
       close.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))

       lifecycleScope.launch {
           delay(700)
           close.setTextColor(closeTextColor)
       }
       true
   }


        close.setOnClickListener{ dismiss()}

        savebtn.setOnClickListener {
            val newTodo = etTodo.text.toString()
            if(newTodo.isNotEmpty()){
                TodoAdd?.invoke(newTodo)
                dismiss()
            } else{
               etTodo.error = "Please enter a todo"
            }

        }

    }



}