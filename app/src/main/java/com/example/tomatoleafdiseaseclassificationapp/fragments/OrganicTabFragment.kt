package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomatoleafdiseaseclassificationapp.R
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentConventionalTabBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentOrganicTabBinding

class OrganicTabFragment : Fragment() {
    private lateinit var fragmentOrganicTabBinding: FragmentOrganicTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOrganicTabBinding = FragmentOrganicTabBinding.inflate(inflater, container, false)
        return fragmentOrganicTabBinding.root
    }
}