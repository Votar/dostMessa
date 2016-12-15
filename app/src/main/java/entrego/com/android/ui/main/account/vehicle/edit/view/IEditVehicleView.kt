package entrego.com.android.ui.main.account.vehicle.edit.view

import android.content.Context

/**
 * Created by bertalt on 02.12.16.
 */
interface IEditVehicleView {

    fun showProgress()
    fun hideProgress()
    fun getContext(): Context
    fun showMessage(message: String)
    fun onSuccessUpdate()
    fun setErrorBrand(message:String)
    fun setErrorModel(message:String)
    fun setErrorPlate(message:String)

}