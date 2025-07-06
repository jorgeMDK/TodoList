package com.example.todolist.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.GlobalVariables
import com.example.todolist.R
import com.example.todolist.Task
import com.example.todolist.databinding.ItemTaskBinding

class ListViewHolder(view: View):RecyclerView.ViewHolder(view){

    private val binding = ItemTaskBinding.bind(view)

    fun render(taskModel: Task,
               onDetailClickListener: (Task, position: Int) -> Unit,
               onLongClickListener: (Task, position: Int) -> Boolean
    ){
        binding.itemTvTitle.text = taskModel.title
        binding.itemBody.text = taskModel.description
        var selected = false


        // ACTUALIZAR EL IMAGE VIEW DE ALERTA Y REPETICIÓN
        val alertImageResource = if (taskModel.alertEnable) {
            R.drawable.alert // Imagen cuando hay alerta

        } else {
            R.drawable.noalert // Imagen cuando no hay alerta
        }

        val repeatImageResource = if (taskModel.repeat != 0) {
            R.drawable.repeat // Imagen cuando hay alerta
        } else {
            R.drawable.norepeat // Imagen cuando no hay alerta
        }
        binding.itemImgAlert.setImageResource(alertImageResource)
        binding.itemImgRepeat.setImageResource(repeatImageResource)

        // CLICK CORTO
        itemView.setOnClickListener {
            selected = !selected
            if (GlobalVariables.selectedItems.isNotEmpty()){
                if (selected){
                    binding.itemTask.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.disabled_text_color))
                    GlobalVariables.selectedItems.add(adapterPosition)
                }else{
                    binding.itemTask.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.item_background))
                    GlobalVariables.selectedItems.removeAt(adapterPosition)
                }

                onLongClickListener(taskModel, adapterPosition)
                true
            }else{
                onDetailClickListener(taskModel, adapterPosition)
            }
            }

        // CLICK LARGO
        itemView.setOnLongClickListener{
            true
        }

        fun longClick(){
            selected = !selected
            if (selected){
                binding.itemTask.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.disabled_text_color))
                GlobalVariables.selectedItems.add(adapterPosition)
            }else{
                binding.itemTask.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.item_background))
                GlobalVariables.selectedItems.removeAt(adapterPosition)
            }
            onLongClickListener(taskModel, adapterPosition)
        }
    }
}