package com.example.notforgot.view.taskList

import com.example.notforgot.model.task.Task

interface TaskListView  {

    fun setupScreenForTaskList(tasks: List<Task>)

    fun openCreateTaskActivity()

    fun openAuthorisationActivity()

}