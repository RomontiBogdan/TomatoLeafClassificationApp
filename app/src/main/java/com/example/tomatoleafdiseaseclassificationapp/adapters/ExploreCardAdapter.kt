package com.example.tomatoleafdiseaseclassificationapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.tomatoleafdiseaseclassificationapp.R
import com.example.tomatoleafdiseaseclassificationapp.databinding.CardExploreBinding

class ExploreCardAdapter(private val exploreList: List<String>) :
    RecyclerView.Adapter<ExploreCardAdapter.ExploreHolder>() {
    private lateinit var mListener: OnItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreHolder {
        val itemBinding =
            CardExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ExploreHolder(itemBinding, mListener)
    }

    override fun getItemCount(): Int {
        return this.exploreList.size
    }

    override fun onBindViewHolder(holder: ExploreHolder, position: Int) {
        val exploreItem: String = exploreList[position]
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.card_load)
        holder.bind(exploreItem)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun getDisease(position: Int): String {
        return this.exploreList[position]
    }


    inner class ExploreHolder(
        private val itemBinding: CardExploreBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(exploreItem: String) {
            itemBinding.diseasesTitle.text = exploreItem

            itemBinding.cardViewExplore.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }
}
