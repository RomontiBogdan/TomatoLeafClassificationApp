package com.example.tomatoleafdiseaseclassificationapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityTreatmentBinding

import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View



class TreatmentActivity : AppCompatActivity() {
    private lateinit var activityTreatmentBinding: ActivityTreatmentBinding
    private lateinit var treatmentArrowButton: ImageButton
    private lateinit var symptomsArrowButton: ImageButton
    private lateinit var causesArrowButton: ImageButton
    private lateinit var treatmentHiddenView: LinearLayout
    private lateinit var symptomsHiddenView: LinearLayout
    private lateinit var causesHiddenView: LinearLayout
    private lateinit var treatmentCardview: CardView
    private lateinit var symptomsCardview: CardView
    private lateinit var causesCardview: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTreatmentBinding = ActivityTreatmentBinding.inflate(layoutInflater)
        setContentView(activityTreatmentBinding.root)

        treatmentCardview = activityTreatmentBinding.treatmentCardview
        treatmentArrowButton = activityTreatmentBinding.treatmentArrowButton
        treatmentHiddenView = activityTreatmentBinding.treatmentHiddenView

        symptomsCardview = activityTreatmentBinding.symptomsCardview
        symptomsArrowButton = activityTreatmentBinding.symptomsArrowButton
        symptomsHiddenView = activityTreatmentBinding.symptomsHiddenView

        causesCardview = activityTreatmentBinding.causesCardview
        causesArrowButton = activityTreatmentBinding.causesArrowButton
        causesHiddenView = activityTreatmentBinding.causesHiddenView

        set_expandable(treatmentArrowButton, treatmentHiddenView, treatmentCardview)
        set_expandable(symptomsArrowButton, symptomsHiddenView, symptomsCardview)
        set_expandable(causesArrowButton, causesHiddenView, causesCardview)
    }

    private fun set_expandable(arrow : ImageButton, hiddenView : LinearLayout, cardView : CardView) {
        arrow.setOnClickListener {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                }
                hiddenView.visibility = View.GONE
                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                }
                hiddenView.visibility = View.VISIBLE
                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }
    }

}