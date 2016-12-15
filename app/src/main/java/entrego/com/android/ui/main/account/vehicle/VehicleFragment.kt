package entrego.com.android.ui.main.account.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.main.account.vehicle.edit.EditVehicleActivity
import entrego.com.android.ui.main.account.vehicle.edit.model.UserVehicle
import kotlinx.android.synthetic.main.fragment_vecicle.*
import kotlinx.android.synthetic.main.vehicle_empty_view.*

/**
 * Created by bertalt on 01.12.16.
 */
class VehicleFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view = inflater?.inflate(R.layout.fragment_vecicle, container, false)

        return view
    }


    override fun onResume() {
        super.onResume()


        val vehicle = UserVehicle.getVehicle(context)

        if (vehicle != null)
            setupView(vehicle)
        else {
            vehicle_main_content.visibility = View.GONE
            vehicle_progress.visibility = View.VISIBLE
            UserVehicle.refresh(context, object : UserVehicle.ResultListener {
                override fun onSuccessRefresh(userVehicle: UserVehicleModel) {
                    hideProgress(userVehicle)
                }

                override fun onFailureRefresh(message: String) {

                    hideProgress(null)

                }

            })
        }

    }

    fun hideProgress(userVehicle: UserVehicleModel?) {
        vehicle_progress?.visibility = View.GONE

        if (userVehicle != null) {
            vehicle_main_content?.visibility = View.VISIBLE
            setupView(userVehicle)
        } else {
            vehicle_empty_view?.visibility = View.VISIBLE
            vehicle_btn_add?.setOnClickListener {
                startActivityForResult(Intent(activity, EditVehicleActivity::class.java), EditVehicleActivity.RQT_CODE)
            }
        }
    }


    fun setupView(vehicle: UserVehicleModel) {

        vehicle_brand.text = vehicle.brand
        vehicle_model.text = vehicle.model
        if (vehicle.year != 0)
            vehicle_year.text = vehicle.year.toString()
        if (vehicle.cylinders != 0)
            vehicle_cylinder.text = vehicle.cylinders.toString()
        vehicle_plate.text = vehicle.plate

    }

}