package entrego.com.entrego.ui.main.account

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.entrego.R
import entrego.com.entrego.ui.main.account.profile.ProfileFragment
import entrego.com.entrego.ui.main.account.profile.VehicleFragment
import entrego.com.entrego.ui.main.account.profile.edit.EditProfileActivity
import entrego.com.entrego.ui.main.account.vehicle.edit.EditVehicleActivity
import entrego.com.entrego.util.Logger
import entrego.com.entrego.util.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_account.*

/**
 * Created by bertalt on 01.12.16.
 */
class AccountFragment : Fragment() {


    private val tabTitles = intArrayOf(
            R.string.ui_title_profile,
            R.string.ui_title_vehicle)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        val view = inflater?.inflate(R.layout.fragment_account, container, false)


        return view
    }


    override fun onStart() {
        super.onStart()
        profile_btn_edit.setOnClickListener {
            when (account_tabs.selectedTabPosition) {
                0 -> startActivityForResult(Intent(context, EditProfileActivity::class.java), EditProfileActivity.RQT_CODE)
                1 -> startActivityForResult(Intent(context, EditVehicleActivity::class.java), EditVehicleActivity.RQT_CODE)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Logger.logd("Account resumed")
        setupViewPager(account_viewpager)
        account_tabs.setupWithViewPager(account_viewpager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.logd("Account destroyed")
    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProfileFragment(), getString(tabTitles[0]))
        adapter.addFragment(VehicleFragment(), getString(tabTitles[1]))
        viewPager.adapter = adapter
    }


}