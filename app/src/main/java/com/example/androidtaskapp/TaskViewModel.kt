import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtaskapp.TaskInfo

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<TaskInfo>>()
    val tasks: LiveData<List<TaskInfo>> get() = _tasks

    private val _statusZeroCount = MutableLiveData<Int>()
    val statusZeroCount: LiveData<Int> get() = _statusZeroCount

    private val _statusOneCount = MutableLiveData<Int>()
    val statusOneCount: LiveData<Int> get() = _statusOneCount

    private val _statusTwoCount = MutableLiveData<Int>()
    val statusTwoCount: LiveData<Int> get() = _statusTwoCount

    fun setTasks(taskList: List<TaskInfo>) {
        _tasks.value = taskList
        updateStatusCounts(taskList)
    }

    private fun updateStatusCounts(taskList: List<TaskInfo>) {
        val statusZero = taskList.count { it.status == "0" }
        val statusOne = taskList.count { it.status == "1" }
        val statusTwo = taskList.count { it.status == "2" }

        _statusZeroCount.value = statusZero
        _statusOneCount.value = statusOne
        _statusTwoCount.value = statusTwo

        Log.d("TaskViewModel", "Status counts updated - 0: $statusZero, 1: $statusOne, 2: $statusTwo")
    }
}
