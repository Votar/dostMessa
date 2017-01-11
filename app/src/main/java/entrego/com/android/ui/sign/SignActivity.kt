package entrego.com.android.ui.sign

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.byox.drawview.enums.DrawingCapture
import entrego.com.android.R
import entrego.com.android.binding.Delivery
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.sign.presenter.ISignPresenter
import entrego.com.android.ui.sign.presenter.SignPresenter
import entrego.com.android.ui.sign.view.ISignView
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity(), ISignView {

    var progressDialog: ProgressDialog? = null


    val presenter: ISignPresenter = SignPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        presenter.onCreate(this)

        sign_dismiss.setOnClickListener {
            sign_drawing_ll.undo()
        }
        sign_next.setOnClickListener {
            val token = EntregoStorage(this).getToken()
            sign_drawing_ll.createCapture(DrawingCapture.BYTES)
            presenter.sendSign(token)

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
        finish()
    }

    override fun onFailureSign(message: String) {

        UserMessageUtil.showSnackMessage(activity_sign, message)

    }
}
