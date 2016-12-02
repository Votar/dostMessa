package entrego.com.entrego.util.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * Created by bertalt on 01.12.16.
 */
class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
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