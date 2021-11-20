package com.example.composetodoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.composetodoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE id= :taskId")
    fun getTaskbyId(taskId: Int): Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: ToDoTask)

    @Update
    suspend fun updateTask(task: ToDoTask)

    @Delete
    suspend fun deleteTask(task: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM todo_table WHERE title LIKE :query OR description LIKE :query")
    fun searchByQuery(query: String): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun filterByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun filterByHighPriority(): Flow<List<ToDoTask>>
}