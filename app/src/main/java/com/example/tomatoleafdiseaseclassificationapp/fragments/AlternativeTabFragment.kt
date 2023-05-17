package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomatoleafdiseaseclassificationapp.DiseaseInfoActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentAlternativeBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentBioTabBinding

class AlternativeTabFragment(val textDescription : String) : Fragment() {
    private lateinit var binding: FragmentAlternativeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlternativeBinding.inflate(inflater, container, false)
        binding.alternativeTabButton.setOnClickListener {
            (activity as DiseaseInfoActivity).addToHistory("Alternative")
        }
        binding.alternativeTreatmentTextView.text = textDescription
        return binding.root
    }

}