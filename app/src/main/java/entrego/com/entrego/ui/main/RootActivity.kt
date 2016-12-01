package entrego.com.entrego.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatDrawableManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import entrego.com.entrego.R
import kotlinx.android.synthetic.main.activity_order.*
import java.util.*

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        setupViewPager(main_viewpager)
        main_tabs.setupWithViewPager(main_viewpager)
        setupTabIcons()
    }

    private val tabIcons = intArrayOf(
            R.drawable.ic_home,
            R.drawable.ic_account,
            R.drawable.ic_attach_money,
            R.drawable.ic_star_border)

    private val tabTitles = intArrayOf(
            R.string.ui_title_home,
            R.string.ui_title_account,
            R.string.ui_title_income,
            R.string.ui_title_score)

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(Fragment(), getString(tabTitles[0]))
        adapter.addFrag(Fragment(), getString(tabTitles[1]))
        adapter.addFrag(Fragment(), getString(tabTitles[2]))
        adapter.addFrag(Fragment(), getString(tabTitles[3]))

        viewPager.adapter = adapter
    }

    fun setupTabIcons() {

        for (i in 0..tabTitles.size - 1) {

            val nextOne = LayoutInflater.from(this).inflate(R.layout.tab_view, null)
            (nextOne.findViewById(R.id.tab_text) as TextView).text = getString(tabTitles[i])
            val icon = AppCompatDrawableManager.get().getDrawable(applicationContext, tabIcons[i])
            (nextOne.findViewById(R.id.tab_icon) as ImageView).setImageDrawable(icon)
            main_tabs.getTabAt(i)?.customView = nextOne
        }
    }



    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.root, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList: ArrayList<Fragment>
        private val mFragmentTitleList: ArrayList<String>

        init {
            mFragmentList = ArrayList()
            mFragmentTitleList = ArrayList()
        }


        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList.get(position)
        }
    }

}
