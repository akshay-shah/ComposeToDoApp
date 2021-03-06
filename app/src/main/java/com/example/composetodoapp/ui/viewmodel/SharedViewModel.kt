package com.example.composetodoapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetodoapp.data.models.Priority
import com.example.composetodoapp.data.models.Priority.LOW
import com.example.composetodoapp.data.models.ToDoTask
import com.example.composetodoapp.data.repository.DataStoreRepository
import com.example.composetodoapp.data.repository.ToDoRepository
import com.example.composetodoapp.utils.Action
import com.example.composetodoapp.utils.Action.ADD
import com.example.composetodoapp.utils.Action.DELETE
import com.example.composetodoapp.utils.Action.DELETE_ALL
import com.example.composetodoapp.utils.Action.NO_ACTION
import com.example.composetodoapp.utils.Action.UNDO
import com.example.composetodoapp.utils.Action.UPDATE
import com.example.composetodoapp.utils.RequestState
import com.example.composetodoapp.utils.SearchAppBarState
import com.example.composetodoapp.utils.SearchAppBarState.CLOSED
import com.example.composetodoapp.utils.SearchAppBarState.TRIGGERED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(NO_ACTION)
    val showDeleteAllDialog: MutableState<Boolean> = mutableStateOf(false)

    val id = mutableStateOf(0)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(LOW)

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(CLOSED)
    val searchTextString: MutableState<String> = mutableStateOf("")

    private val _searchTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchTasks

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTask: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    init {
        getAllTasks()
        readSortState()
    }

    val lowPriorityTasks: StateFlow<List<ToDoTask>> = repository.filterByLowPriority().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTasks: StateFlow<List<ToDoTask>> = repository.filterByHighPriority().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun setSortState(priority: Priority) {
        viewModelScope.launch {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getTaskbyId(taskId).collect {
                _selectedTask.value = it
            }
        }
    }

    fun updateFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = LOW
        }
    }

    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                        _sortState
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks().collect {
                    _allTasks.value = RequestState.Success(it)
                }

            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.insertTask(toDoTask)
        }
        searchAppBarState.value = CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTask()
        }
    }

    fun searchDatabase(query: String) {
        _searchTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchByQuery(query = "%$query%").collect {
                    _searchTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = TRIGGERED
    }

    fun performDataBaseOperation(action: Action) {
        when (action) {
            ADD -> {
                addTask()
            }
            UPDATE -> {
                updateTask()
            }
            DELETE -> {
                deleteTask()
            }
            DELETE_ALL -> {
                deleteAllTasks()
            }
            UNDO -> {
                addTask()
            }
            else -> {

            }
        }
        this.action.value = NO_ACTION
    }

    fun validateFields(): Boolean =
        title.value.isNotEmpty() && description.value.isNotEmpty()
}