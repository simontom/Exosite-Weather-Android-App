package cz.saymon.android.exositeoneplatformrpc.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import cz.saymon.android.exositeoneplatformrpc.ui.fragments.DataportsListFragment
import cz.saymon.android.exositeoneplatformrpc.ui.fragments.PwmControlFragment

class ExositeTabsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return DataportsListFragment()
            1 -> return PwmControlFragment()
            else -> throw NotImplementedError("Fragment position is out of bounds: $position")
        }
    }

    override fun getCount(): Int {
        return 2
    }

}
