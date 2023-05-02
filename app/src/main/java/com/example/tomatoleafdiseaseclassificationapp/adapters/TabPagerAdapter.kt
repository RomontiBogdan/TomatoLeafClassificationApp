package com.example.tomatoleafdiseaseclassificationapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tomatoleafdiseaseclassificationapp.fragments.BioTabFragment
import com.example.tomatoleafdiseaseclassificationapp.fragments.ConventionalTabFragment
import com.example.tomatoleafdiseaseclassificationapp.fragments.OrganicTabFragment

class TabPagerAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> {
                return BioTabFragment()
            }
            2 -> {
                return OrganicTabFragment()
            }
        }
        return ConventionalTabFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}