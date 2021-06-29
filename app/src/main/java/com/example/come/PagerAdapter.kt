package com.example.come

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PagerAdapter(fm: FragmentManager, behaviour: Int, private val tabsNumber: Int) : FragmentPagerAdapter(fm, behaviour) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> return BlankFragment()
            1 -> return LaptopsFragment()
            2 -> return OfficeFragment()
            3 -> return FurnitureFragment()
            4 -> return WathcesFragment()
            5 -> return SportsFragment()
            6 -> return ToysFragment()
            7 -> return BooksFragment()
            8 -> return KitchenFragment()
            9 -> return AllFragment()
            else -> BlankFragment()
        }
    }

    override fun getCount(): Int {
        return tabsNumber
    }
}