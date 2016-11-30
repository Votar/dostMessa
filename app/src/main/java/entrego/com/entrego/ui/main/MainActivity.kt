package entrego.com.entrego.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import entrego.com.entrego.R
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.AppCompatDrawableManager
import kotlinx.android.synthetic.main.activity_order.*
import java.util.*
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        setupViewPager(main_viewpager)
        main_tabs.setupWithViewPager(main_viewpager)
        setupTabIcons()
    }

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
