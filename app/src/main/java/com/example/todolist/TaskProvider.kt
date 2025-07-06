package com.example.todolist

class TaskProvider {
    companion object {
        val TaskList = mutableListOf(
            Task(
                "Hacer Deberes",
                "lavar, barrer, trapear, limpiar vidrios",
                repeat = 0,
                "10/08/2024",
                alertEnable = false,
                "5:00",
                0
            )
        )
    }
}

fun updateTaskList(index: Int, newTask: Task) {
    val taskProvider = TaskProvider

    // Verifica que el índice es válido
    if (index in taskProvider.TaskList.indices) {
        // Reemplaza la tarea en la posición especificada
        taskProvider.TaskList[index] = newTask
        println("Tarea reemplazada en la posición $index")
    } else {
        println("Índice fuera de rango")
    }
}

fun deleteTaskList(index: List<Int>){
    val taskProvider = TaskProvider
    val sortedIndex = index.sortedDescending()

    for(index in sortedIndex){
        if(index in taskProvider.TaskList.indices){
            taskProvider.TaskList.removeAt(index)
        }else{
            println("Error")
        }
    }
}