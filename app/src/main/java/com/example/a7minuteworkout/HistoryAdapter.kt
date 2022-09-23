package com.example.a7minuteworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.HistoryItemLayoutBinding

class HistoryAdapter(val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding:HistoryItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        val id=binding.historyid
        val date=binding.historyDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=items[position]
        holder.date.text=item
        holder.id.text=(position+1).toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}