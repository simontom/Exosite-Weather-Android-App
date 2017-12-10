package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cz.saymon.android.exositeoneplatformrpc.ui.fragments.PlaceholderFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when (position) {
            0 -> return PlaceholderFragment.newInstance(position + 1)
            1 -> return PlaceholderFragment.newInstance(position + 1)
            else ->throw IllegalStateException("Position not allowed: $position")
        }
    }

    override fun getCount(): Int {
        return 2
    }
}