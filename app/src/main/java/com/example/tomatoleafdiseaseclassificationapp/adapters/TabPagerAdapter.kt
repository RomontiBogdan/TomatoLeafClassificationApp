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
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    fun removeFragment(position: Int) {
        mFragmentList.removeAt(position)
    }
}