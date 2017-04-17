package com.entregoya.msngr.ui.account.files

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.account.files.model.EntregoFileCategory
import com.entregoya.msngr.ui.account.files.presenter.AddFilesPresenter
import com.entregoya.msngr.ui.account.files.presenter.IAddFilesPresenter
import com.entregoya.msngr.ui.account.files.view.IAddFilesView
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_add_files.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.util.loading
import com.entregoya.msngr.util.snackSimple
import com.entregoya.msngr.web.api.EntregoApi
import kotlinx.android.synthetic.main.fragment_account.*


class AddFilesActivity : AppCompatActivity(), IAddFilesView {


    companion object {
        val KEY_RQT_CODE = "ext_rqt_code"
        val RQT_DRIVER_LICENCE = 0x1558
        val RQT_PERSON_LICENCE = 0x1559
        val RQT_USER_PHOTO = 0x1600
        val TAG = "AddFilesActivity"
    }


    val mPresenter: IAddFilesPresenter = AddFilesPresenter()
    var mProgress: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_files)
        setSupportActionBar(navigation_toolbar)
        mPresenter.onCreate(this)
        setupLayouts()
    }


    override fun onStart() {
        super.onStart()
            nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
            add_files_camera.setOnClickListener {
                mPresenter.takePhotoFromCamera()
            }
            add_files_gallery.setOnClickListener {
                mPresenter.pickPhotoFromGallery()
            }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.handleResultActivity(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    private fun setupLayouts() {

        val url: String
        when (intent.getIntExtra(KEY_RQT_CODE, 0)) {
            RQT_DRIVER_LICENCE -> url = EntregoApi.REQUESTS.GET_DRIVER_LICENCE
            RQT_PERSON_LICENCE -> url = EntregoApi.REQUESTS.GET_ID_PHOTO
            RQT_USER_PHOTO -> url = EntregoApi.REQUESTS.GET_USER_PHOTO
            else -> throw IllegalStateException("Unknown EntregoFileCategory in intent")
        }

        Glide.with(this)
                .load(url)
                .error(R.drawable.ic_user_pic_holder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(add_files_doc_holder as ImageView)
    }

    override fun showMessage(message: String?) {
        UserMessageUtil.showSnackMessage(activity_add_files, message)
    }

    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.hide()
    }

    override fun successUpload() {
        activity_add_files.snackSimple(getString(R.string.success_upload_photo))
    }

    override fun getActivityContext(): Activity {
        return this
    }

    override fun replaceDocumentHolder(pic: Bitmap) {
        add_files_doc_holder.setImageBitmap(pic)
        uploadFileToServer(pic)
    }

    override fun replaceDocumentHolder(path: String) {
        Glide.with(this).load(path).into(add_files_doc_holder)
        val bm = BitmapFactory.decodeFile(path)
        if (bm != null)
            uploadFileToServer(bm)
        else
            showMessage(getString(R.string.cannot_load_image))
    }

    fun uploadFileToServer(picture: Bitmap) {
        val token = EntregoStorage.getToken()
        when (intent.getIntExtra(KEY_RQT_CODE, 0)) {
            RQT_DRIVER_LICENCE -> mPresenter.uploadFileToServer(token, picture, EntregoFileCategory.DRIVER_LICENCE)
            RQT_PERSON_LICENCE -> mPresenter.uploadFileToServer(token, picture, EntregoFileCategory.PERSON_LICENCE)
            RQT_USER_PHOTO -> mPresenter.uploadFileToServer(token, picture, EntregoFileCategory.USER_PHOTO)
            else -> throw IllegalStateException("Unknown EntregoFileCategory in intent")
        }
    }
}


