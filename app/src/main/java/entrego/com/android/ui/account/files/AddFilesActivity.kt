package entrego.com.android.ui.account.files

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.ui.account.files.presenter.AddFilesPresenter
import entrego.com.android.ui.account.files.presenter.IAddFilesPresenter
import entrego.com.android.ui.account.files.view.IAddFilesView
import entrego.com.android.util.UserMessageUtil
import kotlinx.android.synthetic.main.activity_add_files.*
import kotlinx.android.synthetic.main.navigation_toolbar.*


class AddFilesActivity : AppCompatActivity(), IAddFilesView {
    override fun showMessage(message: String?) {
        UserMessageUtil.show(this, message)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun getActivityContext(): Activity {
        return this
    }

    override fun replaceDocumentHolder(path: String) {
        Glide.with(this).load(path).into(add_files_doc_holder)
    }


    val mPresenter: IAddFilesPresenter = AddFilesPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_files)

        setSupportActionBar(navigation_toolbar)

        mPresenter.onCreate(this)
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
}


