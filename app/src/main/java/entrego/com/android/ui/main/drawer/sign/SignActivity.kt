package entrego.com.android.ui.main.drawer.sign

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.byox.drawview.enums.DrawingCapture
import entrego.com.android.R
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.drawer.sign.presenter.ISignPresenter
import entrego.com.android.ui.main.drawer.sign.presenter.SignPresenter
import entrego.com.android.ui.main.drawer.sign.view.ISignView
import entrego.com.android.util.Logger
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity(), ISignView {

    var progressDialog: ProgressDialog? = null
    val TAG = "SignActivity"

    val presenter: ISignPresenter = SignPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        presenter.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        if (Delivery.getInstance().price != null)
            sign_amount_value?.text = Delivery.getInstance().price.toView()

        sign_drawing_ll.backgroundColor = ContextCompat.getColor(this, R.color.colorTransparent)
        sign_dismiss.setOnClickListener {
            sign_drawing_ll.undo()
        }
        sign_next.setOnClickListener {
            val token = EntregoStorage.getToken()
            val signPic = sign_drawing_ll.createCapture(DrawingCapture.BYTES)
            if (signPic is ByteArray) {
                Logger.logd("BYTEARRAY : " + signPic.size.toString())
                presenter.sendSign(token, signPic)
            } else
                Logger.loge(TAG, "Cannot parse image from DrawView")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress() {
        progressDialog = ProgressDialog(this)
        progressDialog?.loading()
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun onSuccessSign() {
        Delivery.getInstance().update(null)
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onFailureSign(message: String?) {
        UserMessageUtil.showSnackMessage(activity_sign, message)
    }
}
