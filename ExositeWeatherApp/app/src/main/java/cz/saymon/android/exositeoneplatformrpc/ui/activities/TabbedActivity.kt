package cz.saymon.android.exositeoneplatformrpc.ui.activities

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import cz.saymon.android.exositeoneplatformrpc.R
import cz.saymon.android.exositeoneplatformrpc.ui.SnackbarDisplayer
import cz.saymon.android.exositeoneplatformrpc.ui.adapters.ExositeTabsPagerAdapter
import kotlinx.android.synthetic.main.activity_tabbed.*

class TabbedActivity : AppCompatActivity(), SnackbarDisplayer {

    private var tabsPagerAdapter: ExositeTabsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)

        setSupportActionBar(toolbar)

        tabsPagerAdapter = ExositeTabsPagerAdapter(supportFragmentManager)
        container.adapter = tabsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun snackbarCoordinatorLayout() = main_content

}
