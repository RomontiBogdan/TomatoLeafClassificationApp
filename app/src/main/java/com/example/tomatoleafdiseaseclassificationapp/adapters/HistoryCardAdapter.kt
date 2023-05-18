package com.example.tomatoleafdiseaseclassificationapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.tomatoleafdiseaseclassificationapp.R
import com.example.tomatoleafdiseaseclassificationapp.databinding.CardHistoryBinding
import com.example.tomatoleafdiseaseclassificationapp.models.HistoryCardModel

class HistoryCardAdapter(var historyList: List<HistoryCardModel>) :
    RecyclerView.Adapter<HistoryCardAdapter.HistoryHolder>() {
    private lateinit var mListener: OnRatingBarChangeListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val itemBinding =
            CardHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HistoryHolder(itemBinding, mListener)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val historyItem: HistoryCardModel = historyList[position]
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.card_load)
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    interface OnRatingBarChangeListener {
        fun onRatingBarChange(rating: Float, position: Int)

        fun onItemClick(position: Int)

    }

    fun setOnRatingBarChangeListener(listener: OnRatingBarChangeListener) {
        mListener = listener
    }

    fun getId(position: Int): String {
        return this.historyList[position].id
    }

    fun getTreatment(position: Int): String {
        return this.historyList[position].treatmentUsed
    }

    fun getDisease(position: Int): String {
        return this.historyList[position].diseaseName
    }

    inner class HistoryHolder(
        private val itemBinding: CardHistoryBinding,
        val listener: OnRatingBarChangeListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(historyItem: HistoryCardModel) {
            itemBinding.cardHistoryDiseaseName.text = historyItem.diseaseName
            itemBinding.cardHistoryTreatmentUsed.text = historyItem.treatmentUsed
            itemBinding.cardHistoryDate.text = historyItem.date
            if (historyItem.rating == 0)
                itemBinding.ratingTextView.visibility = View.GONE
            else {
                itemBinding.ratingBar.visibility = View.GONE
                itemBinding.ratingTextView.text = historyItem.rating.toString()
            }

            itemBinding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                listener.onRatingBarChange(rating, bindingAdapterPosition)
            }

            itemBinding.cardHistoryTreatmentUsed.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }

        }
    }
}
