package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomatoleafdiseaseclassificationapp.DiseaseInfoActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentBioTabBinding

class BioTabFragment(val textDescription : String) : Fragment() {
    private lateinit var fragmentBioTabBinding: FragmentBioTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBioTabBinding = FragmentBioTabBinding.inflate(inflater, container, false)
        fragmentBioTabBinding.bioTabButton.setOnClickListener {
            (activity as DiseaseInfoActivity).addToHistory("Bio")
        }
        fragmentBioTabBinding.bioTreatmentTextView.text = textDescription
        return fragmentBioTabBinding.root
    }

}