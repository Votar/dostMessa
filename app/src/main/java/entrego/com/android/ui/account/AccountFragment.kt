package entrego.com.android.ui.account

import android.content.Intent
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.databinding.library.baseAdapters.BR
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import entrego.com.android.R
import entrego.com.android.binding.UserProfileEntity
import entrego.com.android.databinding.FragmentAccountBinding
import entrego.com.android.ui.account.files.AddFilesActivity
import entrego.com.android.ui.account.profile.ProfileFragment
import entrego.com.android.ui.account.profile.edit.EditProfileActivity
import entrego.com.android.ui.account.vehicle.edit.EditVehicleActivity
import entrego.com.android.ui.account.vehicle.new.MvpVehicleFragment
import entrego.com.android.util.Logger
import entrego.com.android.util.ui.ViewPagerAdapter
import entrego.com.android.web.api.EntregoApi
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {
    private val tabTitles = intArrayOf(
            R.string.ui_title_profile,
            R.string.ui_title_vehicle)

    var binder: FragmentAccountBinding? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = FragmentAccountBinding.inflate(inflater, container, false)
        binder?.userProfile = UserProfileEntity.getInstance()
        return binder?.root
    }

    val mProfileChangedListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            when (p1) {
                BR.userProfile -> setupUserPic()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        profile_btn_edit.setOnClickListener {
            when (account_tabs.selectedTabPosition) {
                0 -> startActivityForResult(Intent(context, EditProfileActivity::class.java), EditProfileActivity.RQT_CODE)
                1 -> startActivityForResult(Intent(context, EditVehicleActivity::class.java), EditVehicleActivity.RQT_CODE)
            }
        }
        account_user_pic_holder.setOnClickListener { startEditProfilePhotoActivity() }

        UserProfileEntity.getInstance().addOnPropertyChangedCallback(mProfileChangedListener)
    }

    override fun onResume() {
        super.onResume()
        Logger.logd("Account resumed")
        setupViewPager(account_viewpager)
        account_tabs.setupWithViewPager(account_viewpager)
        setupUserPic()
    }

    override fun onStop() {
        super.onStop()
        UserProfileEntity.getInstance().removeOnPropertyChangedCallback(mProfileChangedListener)
    }

    fun setupUserPic() {
        val profile = binder?.userProfile?.profile
        Glide.with(context)
                .load(EntregoApi.REQUESTS.GET_USER_PHOTO)
                .error(R.drawable.ic_user_pic_holder)
                .skipMemoryCache(true)
                .diskCacheStrategy( DiskCacheStrategy.NONE)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        account_user_edit_pic.visibility = View.INVISIBLE
                        return false
                    }
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        account_user_edit_pic.visibility = View.VISIBLE
                        return false
                    }
                })
                .into(account_user_pic_holder as ImageView)

    }

    private fun startEditProfilePhotoActivity() {
        val intent = Intent(activity, AddFilesActivity::class.java)
        intent.putExtra(AddFilesActivity.KEY_RQT_CODE, AddFilesActivity.RQT_USER_PHOTO)
        startActivity(intent)
    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProfileFragment(), getString(tabTitles[0]))
        adapter.addFragment(MvpVehicleFragment(), getString(tabTitles[1]))
        viewPager.adapter = adapter
    }
}