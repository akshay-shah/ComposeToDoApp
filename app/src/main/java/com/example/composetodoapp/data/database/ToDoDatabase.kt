package com.example.composetodoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetodoapp.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun getToDoDao(): ToDoDao
}