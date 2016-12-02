package entrego.com.entrego.ui.main.account.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.entrego.R
import entrego.com.entrego.storage.model.UserVehicleModel
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.web.model.request.common.UserVehicle
import kotlinx.android.synthetic.main.fragment_vecicle.*

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


        val vehicle = EntregoStorage(activity).getUserVehicle()
        if (vehicle != null)
            setupView(vehicle)
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