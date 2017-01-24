package entrego.com.android.ui.account.files

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.ui.account.files.model.EntregoFileCategory
import entrego.com.android.ui.account.files.presenter.AddFilesPresenter
import entrego.com.android.ui.account.files.presenter.IAddFilesPresenter
import entrego.com.android.ui.account.files.view.IAddFilesView
import entrego.com.android.util.Logger
import entrego.com.android.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_add_files.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import android.graphics.BitmapFactory
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.util.loading
import entrego.com.android.util.snackSimple


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
        val token = EntregoStorage(this).getToken()
        when (intent.getIntExtra(KEY_RQT_CODE, 0)) {
            RQT_DRIVER_LICENCE -> mPresenter.uploadFileToServer(token, picture, EntregoFileCategory.DRIVER_LICENCE)
            RQT_PERSON_LICENCE -> mPresenter.uploadFileToServer(token, picture, EntregoFileCategory.PERSON_LICENCE)
            RQT_USER_PHOTO ->mPresenter.uploadFileToServer(token, picture, EntregoFileCategory.USER_PHOTO)
            else -> Logger.loge(TAG, "Unknown value for EntregoFileCategory from Intent by KEY_RQT_CODE")
        }
    }
}


