package com.example.basictodolist

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.todosDataStore: DataStore<Todos> by dataStore(
    fileName = "todos.pb",
    serializer = TodoSerializer
)