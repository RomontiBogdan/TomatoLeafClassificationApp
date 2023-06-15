package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomatoleafdiseaseclassificationapp.DiseaseInfoActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentOrganicTabBinding

class OrganicTabFragment(val textDescription : String) : Fragment() {
    private lateinit var fragmentOrganicTabBinding: FragmentOrganicTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentOrganicTabBinding = FragmentOrganicTabBinding.inflate(inflater, container, false)
        fragmentOrganicTabBinding.organicTabButton.setOnClickListener {
            (activity as DiseaseInfoActivity).addToHistory("Organic")
        }
        fragmentOrganicTabBinding.organicTreatmentTextView.text = textDescription
        return fragmentOrganicTabBinding.root
    }
}