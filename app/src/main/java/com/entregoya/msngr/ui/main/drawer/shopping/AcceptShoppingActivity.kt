package com.entregoya.msngr.ui.main.drawer.shopping

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.view.View
import com.entregoya.msngr.R
import com.entregoya.msngr.mvp.view.BaseMvpActivity
import com.entregoya.msngr.util.loading
import kotlinx.android.synthetic.main.activity_input_amount.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class AcceptShoppingActivity : BaseMvpActivity<ShoppingContract.View, ShoppingContract.Presenter>(),
        ShoppingContract.View {


    companion object {
        const val KEY_ORDER_ID = "ext_k_order"
        fun getIntent(ctx: Context, orderId: Long): Intent {
            val intent = Intent(ctx, AcceptShoppingActivity::class.java)
            intent.putExtra(KEY_ORDER_ID, orderId)
            return intent
        }
    }

    var mProgress: ProgressDialog? = null


    override fun getActivityContext(): Activity = this
    override fun setupPhotoHolder(picture: Bitmap) {
        input_amount_receipt_holder.setImageBitmap(picture)
    }

    override fun setupDefaultHolder() {
        input_amount_receipt_holder.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_box_black_24dp))
    }

    override var mPresenter: ShoppingContract.Presenter = ShoppingPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_amount)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        input_amount_btn.setOnClickListener { performResult() }
        input_amount_receipt_holder.setOnClickListener { mPresenter.requestPhoto() }
        deserializeIntent()
    }

    fun performResult() {
        val amount = input_amount_edit.text.toString()
        mPresenter.sendReceipt(amount)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun getRootView(): View? = activity_input_amount

    override fun deserializeIntent() {
        if (intent?.hasExtra(KEY_ORDER_ID) == true){
            val orderID = intent.getLongExtra(KEY_ORDER_ID, 0)
            mPresenter.setupOrderId(orderID)
        }
        else throw IllegalStateException("No order id in intent")
    }

    override fun showProgress() {
        mProgress = ProgressDialog(this)
        mProgress?.loading()
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }
}
