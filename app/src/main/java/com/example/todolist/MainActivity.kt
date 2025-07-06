package com.example.todolist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.ListsAdapter
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.AddTaskBinding
import com.example.todolist.databinding.EditTaskBinding
import java.util.Calendar

object GlobalVariables {
    var isSomeSelected: Int = 0
    var selectedItems:MutableList<Int> = mutableListOf()
}

private lateinit var binding: ActivityMainBinding
private lateinit var bindingAdd: AddTaskBinding

class MainActivity : AppCompatActivity() {

    private lateinit var listAdapter: ListsAdapter

    private var currentRepeatMode = 0
    private var currentSelectedDate = "-1"
    private var currentAlarmEnable = false
    private var currentAlarmSet = "-1"
    private var currentSelectedDay = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingAdd = AddTaskBinding.inflate(layoutInflater)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibDelete.visibility = View.GONE

        listAdapter = ListsAdapter(TaskProvider.TaskList,
            {position, task -> showTaskDetailDialog(position, task)},
            {position, task -> onLongClick(position, task)
            })

        initRecyclerView()

        binding.fvNew.setOnClickListener{
            newTask()
        }

        binding.ibDelete.setOnClickListener{
            deleteTask()
        }






    }

    /**
     * FUNCTIONS TO EDIT TASK
     */

    private fun editTask(id: Int){

        var currentTask = TaskProvider.TaskList[id]
        val dialog = Dialog(this)

        val newText = bindingAdd.newEtTitle
        val newDescription = bindingAdd.newEtDescription
        val button = bindingAdd.newBtAdd


        // CARGAR LOS VALORES DE LA TAREA SELECCIONADA
        newText.setText(currentTask.title)
        newDescription.setText(currentTask.description)
        currentRepeatMode = currentTask.repeat
        currentSelectedDate = currentTask.date
        currentAlarmEnable = currentTask.alertEnable
        currentAlarmSet = currentTask.alert
        currentSelectedDay = currentTask.selectedDay


        // Elimina la vista de su padre si ya tiene uno
        val parent = bindingAdd.root.parent as? ViewGroup
        parent?.removeView(bindingAdd.root)
        dialog.setContentView(bindingAdd.root)

        bindingAdd.spinnerDay.visibility = View.GONE
        bindingAdd.ivCalendar.visibility = View.GONE
        bindingAdd.tvRepeat.visibility = View.GONE

        bindingAdd.ivAlarm.isEnabled = false

        initSpinner()
        initSpinnerDays()
        checkBoxListener()
        dialog.show()

        bindingAdd.ivCalendar.setOnClickListener{// listener del calendario
            openDatePickerDialog()
        }

        bindingAdd.ivAlarm.setOnClickListener{// listener de Reloj
            openTimePickerDialog()
        }

        bindingAdd.chkAlarm.setOnClickListener{// listener checkbox
            if(bindingAdd.chkAlarm.isChecked){
                bindingAdd.ivAlarm.isEnabled = true
            }else{
                bindingAdd.ivAlarm.isEnabled = false
                currentAlarmSet = "-1"
            }
        }

        button.setOnClickListener {
            val currentTaskTitle = newText.text.toString()
            val currentTaskDescription = newDescription.text.toString()

            val newTask = Task(
                title = currentTaskTitle,
                description = currentTaskDescription,
                repeat = currentRepeatMode,
                date = currentSelectedDate,
                alertEnable = currentAlarmEnable,
                alert = currentAlarmSet,
                selectedDay = currentSelectedDay
            )
            if(currentTaskTitle.isNotEmpty() && currentTaskDescription.isNotEmpty()){
                updateTaskList(id, newTask)
                updateTask()
                listAdapter.notifyDataSetChanged()
                dialog.hide()
                defaultValues()
                newText.text.clear()
                newDescription.text.clear()
            }else(showError())
        }
    }

    /**
     * FUNCTIONS TO ADD TASK
     */

    private fun newTask() {
        defaultValues()
        val dialog = Dialog(this)

        val newText = bindingAdd.newEtTitle
        val newDescription = bindingAdd.newEtDescription
        val button = bindingAdd.newBtAdd

        // Elimina la vista de su padre si ya tiene uno
        val parent = bindingAdd.root.parent as? ViewGroup
        parent?.removeView(bindingAdd.root)
        dialog.setContentView(bindingAdd.root)

        bindingAdd.spinnerDay.visibility = View.GONE
        bindingAdd.ivCalendar.visibility = View.GONE
        bindingAdd.tvRepeat.visibility = View.GONE

        bindingAdd.ivAlarm.isEnabled = false

        initSpinner()
        initSpinnerDays()
        checkBoxListener()
        dialog.show()

        bindingAdd.ivCalendar.setOnClickListener{// listener del calendario
            openDatePickerDialog()
        }

        bindingAdd.ivAlarm.setOnClickListener{// listener de Reloj
            openTimePickerDialog()
        }

        bindingAdd.chkAlarm.setOnClickListener{// listener checkbox
            if(bindingAdd.chkAlarm.isChecked){
                bindingAdd.ivAlarm.isEnabled = true
            }else{
                bindingAdd.ivAlarm.isEnabled = false
                currentAlarmSet = "-1"
            }
        }
        dialog.setOnDismissListener{defaultValues()}


        button.setOnClickListener {
            val currentTaskTitle = newText.text.toString()
            val currentTaskDescription = newDescription.text.toString()
            if(currentTaskTitle.isNotEmpty() && currentTaskDescription.isNotEmpty()){
                TaskProvider.TaskList.add(
                    Task(
                        currentTaskTitle,
                        currentTaskDescription,
                        currentRepeatMode,
                        currentSelectedDate,
                        currentAlarmEnable,
                        currentAlarmSet,
                        currentSelectedDay))
                updateTask()
                listAdapter.notifyDataSetChanged()
                dialog.hide()
                defaultValues()
                newText.text.clear()
                newDescription.text.clear()


            }else(
                    showError()
                    )
        }

    }

    private fun openDatePickerDialog() {
        // Obtener la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Crear y mostrar el DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Aquí puedes manejar la fecha seleccionada
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                println("FECHA SELECCIONADA: $selectedDate")
                currentSelectedDate = selectedDate
            },
            year,
            month,
            day
        )

        // Manejar el evento de cancelar
        datePickerDialog.setOnCancelListener {
           currentSelectedDate = "-1"
        }

        // O también puedes usar setOnDismissListener
        datePickerDialog.setOnDismissListener {
            currentSelectedDate = "-1"
        }

        datePickerDialog.show()
    }

    private fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                // Maneja la hora seleccionada
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                // Actualiza tu UI o realiza alguna acción con la hora
                println("Hora seleccionada: $selectedTime")
                currentAlarmSet = selectedTime // actualiza el identificador global
            },
            hour,
            minute,
            false // Usa el formato de 24 horas. Cambia a false para 12 horas
        )

        timePickerDialog.show()
    }

    private fun showError() {
        Toast.makeText(this,"Titulo, Descripcion no pueden estar vacios", Toast.LENGTH_SHORT).show()
    }

    private fun updateTask() {
        listAdapter.notifyDataSetChanged()
    }

    private fun defaultValues(){
        currentRepeatMode = 0
        currentSelectedDate = "-1"
        currentAlarmEnable = false
        currentAlarmSet = "-1"
        currentSelectedDay = 0
    }

    /**
     * FUNCTIONS TO SHOW TASK
     */

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun showTaskDetailDialog(taskModel: Task, task: Int) {
        val dialogBinding = EditTaskBinding.inflate(LayoutInflater.from(this))

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBuilder.show()

        /**
         * CONFIGURE TITLE AND DESCRIPTION
         */
        dialogBinding.cvTvTitle.text = taskModel.title
        dialogBinding.cvTvDescription.text = taskModel.description

        /**
         * CONFIGURE TEXT VIEW DAYS
         */
        val str = "Se repite: "
        if(taskModel.repeat == 4 || taskModel.repeat == 6){
            dialogBinding.cvTvDays.text = "Todos los: " + when(taskModel.selectedDay){
                0 -> "Lunes"
                1 -> "Martes"
                2 -> "Miercoles"
                3 -> "Jueves"
                4 -> "Viernes"
                5 -> "Sabado"
                else -> "Domingo"
            }
        }else{
            dialogBinding.cvTvDays.text = ""
        }

        /**
         * CONFIGURE TEXT VIEW REPEAT
         */
        dialogBinding.cvTvRepeat.text = when(taskModel.repeat){
            0 -> str + "Nunca"
            1 -> str + "una vez"
            2 -> str + "Diario"
            3 -> str + "Cada 3 dias"
            4 -> str + "Semanalmente"
            5 -> str + "quincenalmente"
            else -> str + "Mensualmente"
        }

        /**
         * CONFIGURE ALARM IMAGE TINT AND TEXT
         */
        if(taskModel.alert == "-1"){
            // colorear gris la imagen
            dialogBinding.cdIvAlarm.imageTintList = ColorStateList.valueOf(R.color.disabled_text_color)
            dialogBinding.cdIvAlarm.imageTintMode = PorterDuff.Mode.SRC_IN
            dialogBinding.cvTvTextAlarm.text = "no establecida"
            dialogBinding.cvTvTextAlarm.setTextColor(ContextCompat.getColor(dialogBinding.cvTvDays.context, R.color.disabled_text_color))
        }else{
            dialogBinding.cvTvTextAlarm.text = "Establecia a las: " + taskModel.alert
        }


        // BUTTON EDIT TASK
        dialogBinding.dialogBtEdit.setOnClickListener{
            dialogBuilder.dismiss()
            editTask(task)
        }


        // BUTTON CLOSE
        dialogBinding.cvIvClose.setOnClickListener {
            dialogBuilder.dismiss()
        }
    }

    private fun onSelectedItemEvent(position: Int){
        if (GlobalVariables.selectedItems.size > 0){
            binding.ibSearch.visibility = View.VISIBLE
            binding.ibMenu.visibility = View.VISIBLE
            binding.ibDelete.visibility = View.GONE
        }else{
        binding.ibSearch.visibility = View.GONE
        binding.ibMenu.visibility = View.GONE
        binding.ibDelete.visibility = View.VISIBLE}

    }

    private fun onLongClick(taskModel: Task, position: Int): Boolean{
        //Toast.makeText(this,"Click largo manejado correctamente", Toast.LENGTH_SHORT).show()
        //onSelectedItemEvent(position)


        return true
    }

    /**
     *DELETE TASKS
     */

    private fun deleteTask(){
        deleteTaskList(GlobalVariables.selectedItems)
        GlobalVariables.selectedItems.clear()
        updateTask()
    }

    /**
     * MISCELLANEOUS
     */
    private fun initRecyclerView(){
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = listAdapter
    }

    private fun checkBoxListener(){
        bindingAdd.chkAlarm.setOnClickListener{
            bindingAdd.ivAlarm.isEnabled = bindingAdd.chkAlarm.isChecked
            currentAlarmEnable = bindingAdd.chkAlarm.isChecked // modifica el identificador global
        }
    }

    private fun initSpinner() {
        /* Encuentra el Spinner en el layout */
        val spinner: Spinner = bindingAdd.mySpinner
        val imageButton: ImageButton = bindingAdd.ivCalendar

        // Crea un ArrayAdapter usando el array de cadenas y un layout predeterminado
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_opciones,
            R.layout.spinner_item
        )
        // Especifica el layout a usar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(R.layout.spinner_item)
        // Aplica el adaptador al spinner
        spinner.adapter = adapter

        // Configura el valor inicial del Spinner y actualiza la variable currentRepeatMode
        val initialPosition = spinner.selectedItemPosition
        currentRepeatMode = initialPosition

        //listener del spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentRepeatMode = position

                when(position){
                    //MENSUALMENTE
                    6 -> {
                        imageButton.isEnabled = true
                        imageButton.visibility = View.VISIBLE
                        bindingAdd.spinnerDay.visibility = View.GONE
                        bindingAdd.tvRepeat.visibility = View.GONE
                    }
                    //SEMANALMENTE
                    4 -> {
                        bindingAdd.spinnerDay.visibility = View.VISIBLE
                        bindingAdd.tvRepeat.visibility = View.VISIBLE
                        imageButton.isEnabled = false
                        imageButton.visibility = View.GONE
                    }
                    else -> {
                        bindingAdd.tvRepeat.visibility = View.GONE
                        imageButton.isEnabled = false
                        bindingAdd.spinnerDay.visibility = View.GONE
                        imageButton.visibility = View.GONE
                    }

                }


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {// en caso de que no se seleccione nada
                currentRepeatMode = 0
            }
        }
        return

    }

    private fun initSpinnerDays() {
        /* Encuentra el Spinner en el layout */
        val spinner: Spinner = bindingAdd.spinnerDay

        // Crea un ArrayAdapter usando el array de cadenas y un layout predeterminado
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_options_days,
            android.R.layout.simple_spinner_dropdown_item // estilo de spinner cerrado
        )
        // Especifica el layout a usar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(R.layout.spinner_item) //estilo de spinner abierto
        // Aplica el adaptador al spinner
        spinner.adapter = adapter

        // Configura el valor inicial del Spinner y actualiza la variable currentRepeatMode
        val initialPosition = spinner.selectedItemPosition
        currentSelectedDay = initialPosition

        //listener del spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                // Actualiza la variable con el nuevo valor seleccionado
                currentSelectedDay = position

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {// en caso de que no se seleccione nada
                currentSelectedDay = 0
            }
        }
        return

    }

}