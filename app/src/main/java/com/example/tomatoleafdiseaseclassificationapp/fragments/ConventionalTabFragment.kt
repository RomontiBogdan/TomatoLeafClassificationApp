package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomatoleafdiseaseclassificationapp.DiseaseInfoActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentConventionalTabBinding

class ConventionalTabFragment(val textDescription : String) : Fragment() {
    private lateinit var fragmentConventionalTabBinding: FragmentConventionalTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentConventionalTabBinding =
            FragmentConventionalTabBinding.inflate(inflater, container, false)
        fragmentConventionalTabBinding.conventionalTabButton.setOnClickListener {
            (activity as DiseaseInfoActivity).addToHistory("Conventional")
        }
        fragmentConventionalTabBinding.conventionalTreatmentTextView.text = textDescription
        return fragmentConventionalTabBinding.root
    }
}