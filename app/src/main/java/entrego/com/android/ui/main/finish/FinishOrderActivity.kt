package entrego.com.android.ui.main.finish

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils

import entrego.com.android.R
import entrego.com.android.entity.EntregoPriceEntity
import entrego.com.android.ui.main.RootActivity
import entrego.com.android.util.GsonHolder
import kotlinx.android.synthetic.main.activity_finish_order.*

class FinishOrderActivity : AppCompatActivity() {

    companion object {
        val KEY_PRICE = "ext_k_price"
        fun getIntent(ctx: Context, price: EntregoPriceEntity): Intent {

            val intent = Intent(ctx, FinishOrderActivity::class.java)
            val json = GsonHolder.instance
                    .toJson(price, EntregoPriceEntity::class.java)
            intent.putExtra(KEY_PRICE, json)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_order)
        deserializeIntent()
        setupListeners()
    }

    private fun setupListeners() {
        finish_order_btn_ok.setOnClickListener {
            NavUtils.navigateUpFromSameTask(this)
        }
    }

    private fun deserializeIntent() {
        if (intent?.hasExtra(KEY_PRICE) == true) {
            val json = intent.getStringExtra(KEY_PRICE)
            val price = GsonHolder.instance.fromJson(json, EntregoPriceEntity::class.java)
            finish_order_amount.setText(price.toView())

        } else throw IllegalStateException("No price in intent")
    }
}
