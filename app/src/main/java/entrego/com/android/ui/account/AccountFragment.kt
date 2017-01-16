package entrego.com.android.ui.account

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.ui.account.files.AddFilesActivity
import entrego.com.android.ui.account.profile.ProfileFragment
import entrego.com.android.ui.account.profile.UserProfile
import entrego.com.android.ui.account.profile.VehicleFragment
import entrego.com.android.ui.account.profile.edit.EditProfileActivity
import entrego.com.android.ui.account.vehicle.edit.EditVehicleActivity
import entrego.com.android.util.Logger
import entrego.com.android.util.loadImg
import entrego.com.android.util.ui.ViewPagerAdapter
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

        account_user_edit_pic.setOnClickListener { startEditProfilePhotoActivity() }

        val profile = UserProfile.getProfile(activity)
        if (profile?.userPicUrl.isNullOrEmpty()) {
            Glide.with(activity)
                    .load(R.drawable.ic_user_pic_holder)
                    .into(account_user_pic_holder)
            account_user_edit_pic.visibility = View.GONE
        } else {
            Glide.with(activity)
                    .load(profile?.userPicUrl)
                    .error(R.drawable.ic_user_pic_holder)
                    .placeholder(R.drawable.ic_user_pic_holder)
                    .into(account_user_pic_holder)
            account_user_edit_pic.visibility = View.VISIBLE

        }

    }

    private fun startEditProfilePhotoActivity() {
        val intent = Intent(activity, AddFilesActivity::class.java)
        intent.putExtra(AddFilesActivity.KEY_RQT_CODE, AddFilesActivity.RQT_USER_PHOTO)
        startActivity(intent)
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