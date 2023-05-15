package com.example.tomatoleafdiseaseclassificationapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tomatoleafdiseaseclassificationapp.databinding.CardHistoryBinding
import com.example.tomatoleafdiseaseclassificationapp.models.HistoryCardModel

class HistoryCardAdapter(var historyList: List<HistoryCardModel>) : RecyclerView.Adapter<HistoryCardAdapter.HistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val itemBinding= CardHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HistoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val historyItem: HistoryCardModel = historyList[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class HistoryHolder(val itemBinding: CardHistoryBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(historyItem: HistoryCardModel) {
            itemBinding.cardHistoryTitle.text = historyItem.diseaseName
            itemBinding.cardHistoryDate.text = historyItem.date.toString()
        }
    }
}
