package com.example.tomatoleafdiseaseclassificationapp

import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityTreatmentBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentDetailsSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsSheetFragment : BottomSheetDialogFragment() {
    private lateinit var fragmentDetailsSheetBinding: FragmentDetailsSheetBinding
    private lateinit var treatmentArrowButton: ImageButton
    private lateinit var symptomsArrowButton: ImageButton
    private lateinit var causesArrowButton: ImageButton
    private lateinit var treatmentHiddenView: LinearLayout
    private lateinit var symptomsHiddenView: LinearLayout
    private lateinit var causesHiddenView: LinearLayout
    private lateinit var treatmentCardview: CardView
    private lateinit var symptomsCardview: CardView
    private lateinit var causesCardview: CardView

    private fun set_expandable(arrow : ImageButton, hiddenView : LinearLayout, cardView : CardView) {
        arrow.setOnClickListener {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            Log.d("EXPANDABLE","set_expandable() started")
            if (hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenView.visibility = View.GONE
                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenView.visibility = View.VISIBLE
                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailsSheetBinding = FragmentDetailsSheetBinding.inflate(inflater, container, false)

        treatmentCardview = fragmentDetailsSheetBinding.treatmentCardview
        treatmentArrowButton = fragmentDetailsSheetBinding.treatmentArrowButton
        treatmentHiddenView = fragmentDetailsSheetBinding.treatmentHiddenView

        symptomsCardview = fragmentDetailsSheetBinding.symptomsCardview
        symptomsArrowButton = fragmentDetailsSheetBinding.symptomsArrowButton
        symptomsHiddenView = fragmentDetailsSheetBinding.symptomsHiddenView

        causesCardview = fragmentDetailsSheetBinding.causesCardview
        causesArrowButton = fragmentDetailsSheetBinding.causesArrowButton
        causesHiddenView = fragmentDetailsSheetBinding.causesHiddenView

        set_expandable(treatmentArrowButton, treatmentHiddenView, treatmentCardview)
        set_expandable(symptomsArrowButton, symptomsHiddenView, symptomsCardview)
        set_expandable(causesArrowButton, causesHiddenView, causesCardview)
        return fragmentDetailsSheetBinding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}