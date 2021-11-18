package com.example.composetodoapp.data.repository

import com.example.composetodoapp.data.database.ToDoDao
import com.example.composetodoapp.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val todoDao: ToDoDao) {

    fun getAllTasks(): Flow<List<ToDoTask>> = todoDao.getAllTasks()

    fun getTaskbyId(taskId: Int): Flow<ToDoTask> = todoDao.getTaskbyId(taskId)

    suspend fun insertTask(task: ToDoTask) = todoDao.insertTask(task)

    suspend fun updateTask(task: ToDoTask) = todoDao.updateTask(task)

    suspend fun deleteTask(task: ToDoTask) = todoDao.deleteTask(task)

    suspend fun deleteAllTask() = todoDao.deleteAllTask()

    fun searchByQuery(query: String): Flow<List<ToDoTask>> = todoDao.searchByQuery(query)

    fun filterByLowPriority(): Flow<List<ToDoTask>> = todoDao.filterByLowPriority()

    fun filterByHighPriority(): Flow<List<ToDoTask>> = todoDao.filterByHighPriority()
}