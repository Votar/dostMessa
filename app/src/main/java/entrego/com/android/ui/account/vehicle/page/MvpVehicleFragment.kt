package entrego.com.android.ui.account.vehicle.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.mvp.view.BaseMvpFragment
import entrego.com.android.storage.model.UserVehicleModel
import entrego.com.android.storage.preferences.EntregoStorage
import kotlinx.android.synthetic.main.fragment_vehicle.*

class MvpVehicleFragment : BaseMvpFragment<VehicleContract.View, VehicleContract.Presenter>(),
        VehicleContract.View {


    override var mPresenter: VehicleContract.Presenter = VehiclePresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_vehicle, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        requestModel()
    }

    fun setupListeners() {
        vehicle_fragment_swipe.setOnRefreshListener {
            requestModel()
        }
    }

    fun requestModel() {
        val token = EntregoStorage.getToken()
        mPresenter.requestModel(token)
    }

    override fun showProgress() {
        vehicle_fragment_swipe.isRefreshing = true
    }

    override fun hideProgress() {
        vehicle_fragment_swipe.isRefreshing = false
    }

    override fun setupView(vehicle: UserVehicleModel) {
        vehicle_brand.text = vehicle.brand
        vehicle_model.text = vehicle.model
        if (vehicle.year != 0)
            vehicle_year.text = vehicle.year.toString()
        if (vehicle.cylinders != 0)
            vehicle_cylinder.text = vehicle.cylinders.toString()
        vehicle_plate.text = vehicle.plate
    }


}