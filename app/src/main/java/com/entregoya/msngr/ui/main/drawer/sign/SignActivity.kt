package com.entregoya.msngr.ui.main.drawer.sign

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.byox.drawview.enums.DrawingCapture
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.main.drawer.sign.presenter.ISignPresenter
import com.entregoya.msngr.ui.main.drawer.sign.presenter.SignPresenter
import com.entregoya.msngr.ui.main.drawer.sign.view.ISignView
import com.entregoya.msngr.ui.main.finish.FinishOrderActivity
import com.entregoya.msngr.util.Logger
import com.entregoya.msngr.util.UserMessageUtil
import com.entregoya.msngr.util.loading
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

    override fun onSuccessSign(){
    startActivity(FinishOrderActivity.getIntent(this, Delivery.getInstance().price))
    Delivery.getInstance().update(null)
}

override fun onFailureSign(message: String?) {
    UserMessageUtil.showSnackMessage(activity_sign, message)
}
}
