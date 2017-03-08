package entrego.com.android.ui.incomes.history.details

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import com.google.gson.Gson
import entrego.com.android.R
import entrego.com.android.databinding.ActivityDetailsOfServiceBinding
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.ui.incomes.history.details.view.IDetailsOfServiceView
import entrego.com.android.util.GsonHolder
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.getStaticMapUrl
import entrego.com.android.util.loadSimple
import kotlinx.android.synthetic.main.activity_details_of_service.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class DetailsOfServiceActivity : AppCompatActivity(), IDetailsOfServiceView {

    companion object {
        val KEY_MODEL = "ext_key_m"
        fun getIntent(context: Context, model: DeliveryModel): Intent {
            val intent = Intent(context, DetailsOfServiceActivity::class.java)
            intent.putExtra(KEY_MODEL, GsonHolder.instance.toJson(model, DeliveryModel::class.java))
            return intent
        }
    }

    lateinit var mUrlStaticMap: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(navigation_toolbar)
        val binding = DataBindingUtil.setContentView<ActivityDetailsOfServiceBinding>(this, R.layout.activity_details_of_service)
        if (intent.hasExtra(KEY_MODEL)) {
            val jsonModel = intent.getStringExtra(KEY_MODEL)
            val model = GsonHolder.instance.fromJson(jsonModel, DeliveryModel::class.java)
            binding.model = model
            binding.startPoint = model.history[0].waypoint
            val lastIndex = model.history.lastIndex
            binding.finishPoint = model.history[lastIndex].waypoint
            val path = model.path.line
            mUrlStaticMap = model.history.getStaticMapUrl(path)
        }
    }

    override fun onStart() {
        super.onStart()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        if (!mUrlStaticMap.isNullOrEmpty())
            details_service_stat_map.loadSimple(mUrlStaticMap)
    }

    override fun onShowView() {

    }

    override fun onShowMessage(message: String) {
        UserMessageUtil.showSnackMessage(activity_details_of_service, message)
    }

    override fun onShowProgress() {

    }

    override fun onHideProgress() {

    }

}
