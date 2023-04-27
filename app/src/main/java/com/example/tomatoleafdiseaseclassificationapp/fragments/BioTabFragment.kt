package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentBioTabBinding

class BioTabFragment : Fragment() {
    private lateinit var fragmentBioTabBinding: FragmentBioTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBioTabBinding = FragmentBioTabBinding.inflate(inflater, container, false)
        return fragmentBioTabBinding.root
    }

}