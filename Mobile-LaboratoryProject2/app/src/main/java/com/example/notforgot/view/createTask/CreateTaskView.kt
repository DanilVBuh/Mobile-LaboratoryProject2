package com.example.notforgot.view.createTask

import com.example.notforgot.model.category.Category
import com.example.notforgot.model.priority.Priority

interface CreateTaskView {

    fun taskAlreadyCreated()

    fun setupPrioritySpinner(priorities: List<Priority>)

    fun setupCategorySpinner(categories: List<Category>)

}