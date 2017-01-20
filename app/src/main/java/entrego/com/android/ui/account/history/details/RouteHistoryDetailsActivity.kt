package entrego.com.android.ui.account.history.details

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.google.gson.Gson
import entrego.com.android.R
import entrego.com.android.databinding.ActivityRouteHistoryDetailsBinding
import entrego.com.android.storage.model.DeliveryModel
import kotlinx.android.synthetic.main.activity_route_history_details.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class RouteHistoryDetailsActivity : AppCompatActivity() {

    companion object {
        private const val DELIVERY_KEY = "hist_del_k"
        fun start(context: Context, delivery: DeliveryModel) {
            val intent = Intent(context, RouteHistoryDetailsActivity::class.java)
            intent.putExtra(DELIVERY_KEY, Gson().toJson(delivery, DeliveryModel::class.java))
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityRouteHistoryDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_route_history_details)
        val deliveryJson = intent.getStringExtra(DELIVERY_KEY)
        val delivery = Gson().fromJson(deliveryJson, DeliveryModel::class.java)
        binding.model = delivery
        binding.startPoint = delivery.route.waypoints[0]
        val wayLastIndex = delivery.route.waypoints.lastIndex
        binding.destinationPoint = delivery.route.waypoints[wayLastIndex]

        val durationPoints = delivery.route.waypoints
                .filterIndexed { i, entregoPointBinding -> i != 0 }
                .map { it.address }
        route_history_details_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        route_history_details_recycler.adapter = DurationAdapter(durationPoints)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
    }
}
