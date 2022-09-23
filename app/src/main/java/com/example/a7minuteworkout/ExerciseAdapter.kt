package com.example.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.ItemLayoutBinding

class ExerciseAdapter(val items:ArrayList<ExerciseList>):RecyclerView.Adapter<ExerciseAdapter.MyViewHolder>() {

    class MyViewHolder(val itemBinding:ItemLayoutBinding):RecyclerView.ViewHolder(itemBinding.root){
        var tv=itemBinding.textforrecyclerview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val txt=items[position].id
        holder.tv.text=txt.toString()
        when{
            items[position].isSelected ->{
                holder.tv.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.white_bg)
                holder.tv.setTextColor(Color.parseColor("#000000"))
            }
            items[position].isCompleted ->{
                holder.tv.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.accent_bg)
                holder.tv.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else ->{
                holder.tv.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.grey_bg)
                holder.tv.setTextColor(Color.parseColor("#000000"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}