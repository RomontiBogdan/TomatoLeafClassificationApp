package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.tomatoleafdiseaseclassificationapp.R
import com.example.tomatoleafdiseaseclassificationapp.adapters.TabPagerAdapter
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentDetailsSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DetailsSheetFragment : BottomSheetDialogFragment(){
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
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var classificationResult: String
    private lateinit var image: Bitmap

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
    ): View {
        fragmentDetailsSheetBinding = FragmentDetailsSheetBinding.inflate(inflater, container, false)

        classificationResult = this.requireArguments().getString("result")!!
        image = this.requireArguments().getParcelable<Bitmap>("image")!!

        fragmentDetailsSheetBinding.diseaseTitle.text = classificationResult
        fragmentDetailsSheetBinding.imageView.setImageBitmap(image)

        tabLayout = fragmentDetailsSheetBinding.tabLayout
        viewPager2 = fragmentDetailsSheetBinding.viewPager2

        tabLayout.addTab(tabLayout.newTab().setText("Conventional"));
        tabLayout.addTab(tabLayout.newTab().setText("Bio"));
        tabLayout.addTab(tabLayout.newTab().setText("Organic"));

        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val tabAdapter = TabPagerAdapter(fragmentManager, lifecycle)
        viewPager2.adapter = tabAdapter

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

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

        val db = Firebase.firestore
        db.document("Treatments/Tomato/Diseases/$classificationResult")
            .get()
            .addOnSuccessListener {
                fragmentDetailsSheetBinding.causesDescr.text = it.get("Causes").toString()
                fragmentDetailsSheetBinding.symptomsDescr.text = it.get("Symptoms").toString()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return fragmentDetailsSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}