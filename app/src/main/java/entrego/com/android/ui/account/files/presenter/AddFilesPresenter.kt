package entrego.com.android.ui.account.files.presenter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.nguyenhoanglam.imagepicker.activity.ImagePicker
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity
import com.nguyenhoanglam.imagepicker.model.Image
import entrego.com.android.R
import entrego.com.android.ui.account.files.view.IAddFilesView
import java.util.*
import com.gun0912.tedpermission.PermissionListener
import android.widget.Toast
import com.gun0912.tedpermission.TedPermission


/**
 * Created by bertalt on 18.12.16.
 */
class AddFilesPresenter : IAddFilesPresenter {

    companion object {
        val RQT_CAMERA = 0x222
        val RQT_GALLERY = 0x333
    }

    var mView: IAddFilesView? = null
    override fun onCreate(view: IAddFilesView) {
        mView = view
    }

    override fun onDestroy() {
        mView = null
    }

    override fun handleResultActivity(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode === Activity.RESULT_OK && data != null)
            if (requestCode === RQT_GALLERY) {
                val images: ArrayList<Image> = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES)
                mView?.replaceDocumentHolder(images[0].path)

            } else if (requestCode === RQT_CAMERA) {
                val photo = data.extras?.get("data") as Bitmap
                mView?.replaceDocumentHolder(photo)
            }
    }

    override fun takePhotoFromCamera() {

        TedPermission(mView?.getActivityContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)//set permission
                .check()

    }

    override fun pickPhotoFromGallery() {
        ImagePicker.create(mView?.getActivityContext())
                .folderMode(true) // folder mode (false by default)
                .folderTitle(mView?.getActivityContext()?.getString(R.string.ui_image_folder)) // folder selection title
                .imageTitle(mView?.getActivityContext()?.getString(R.string.ui_tap_to_select)) // image selection title
                .single() // single mode
                .showCamera(false)
                .start(RQT_GALLERY)
    }

    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            mView?.getActivityContext()?.startActivityForResult(cameraIntent, RQT_CAMERA)
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            mView?.showMessage("Permission Denied\n")
        }
    }


}