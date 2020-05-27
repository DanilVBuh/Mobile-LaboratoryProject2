package com.example.notforgot.view.createTask


import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.notforgot.R
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.presenter.createTask.CreateTaskPresenter
import kotlinx.android.synthetic.main.add_category_alert_dialog.*
import kotlinx.android.synthetic.main.create_task_layout.*
import java.util.*

class CreateTaskActivity : AppCompatActivity(), CreateTaskView {

    lateinit var presenter: CreateTaskPresenter
    var categorie = mutableListOf("hi")
    var prioritie = mutableListOf("hi")


    override fun setupCategorySpinner(categories: List<Category>) {
        categorie.clear()
        for (i in categories.indices) {
            categorie.add(categories[i].name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,categorie)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        categorySpinner.adapter = adapter
    }

    override fun setupPrioritySpinner(priorities: List<Priority>) {
        prioritie.clear()
        for (i in priorities.indices) {
            prioritie.add(priorities[i].name)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioritie)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        prioritySpinner.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task_layout)

        presenter = CreateTaskPresenter(this, applicationContext)

        presenter.onSetSpinners()

        saveTaskButton.setOnClickListener {
            presenter.onSaveButtonClick(taskNameEditText.text.toString(), descriptionText.text.toString())
        }
        // Set an on item selected listener for spinner object
        prioritySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                //Toast.makeText(applicationContext, "Spinner selected : ${parent.getItemAtPosition(position).toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

        addCategoryButton.setOnClickListener{
            val builder = AlertDialog.Builder(this)

            builder.setTitle("New category")

            val dialogLayout = layoutInflater.inflate(R.layout.add_category_alert_dialog, null)
            val newEditText  = dialogLayout.findViewById<EditText>(R.id.newCategoryText)
            builder.setView(dialogLayout)

            builder.setPositiveButton("SAVE"){dialog, which ->
                Toast.makeText(applicationContext,"Category added",Toast.LENGTH_SHORT).show()
                presenter.onAddCategory(newEditText.text.toString())

            }

            builder.setNegativeButton("CANCEL"){dialog,which ->
                Toast.makeText(applicationContext,"Category was not added",Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.setCancelable(false)
            dialog.show()
            Toast.makeText(applicationContext, "Разве вам не достаточно категорий?", Toast.LENGTH_SHORT).show()
        }

        setDateButton.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            setDateButton.text = sdf.format(cal.time)

        }

        setDateButton.setOnClickListener {
            DatePickerDialog(applicationContext, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

//        setDateButton.setOnClickListener{
//            presenter.onSetDate()
//        }
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }

    override fun taskAlreadyCreated() {
        finish()
    }
}
