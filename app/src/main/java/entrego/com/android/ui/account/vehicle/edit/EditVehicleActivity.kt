package entrego.com.android.ui.account.vehicle.edit

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import entrego.com.android.R
import entrego.com.android.ui.account.vehicle.edit.model.UserVehicle
import entrego.com.android.ui.account.vehicle.edit.presenter.EditVehiclePresenter
import entrego.com.android.ui.account.vehicle.edit.presenter.IEditVehiclePresenter
import entrego.com.android.ui.account.vehicle.edit.view.IEditVehicleView
import entrego.com.android.util.UserMessageUtil
import entrego.com.android.util.loading
import kotlinx.android.synthetic.main.activity_edit_vechile.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EditVehicleActivity : AppCompatActivity(), IEditVehicleView {
    companion object {
        val RQT_CODE = 0x692
    }

    var progress: ProgressDialog? = null
    val presenter: IEditVehiclePresenter = EditVehiclePresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vechile)
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(navigation_toolbar)
        navigation_toolbar.title = getString(R.string.titile_edit_vehicle)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        edit_vehicle_btn_save.setOnClickListener {
            val cylinders = edit_vehicle_edit_cylinders.text.toString()
            try {
                edit_vehicle_il_brand.error = null
                edit_vehicle_il_model.error = null
                edit_vehicle_il_plate.error = null

                presenter.updateVehicle(
                        edit_vehicle_edit_brand.text.toString(),
                        edit_vehicle_edit_model.text.toString(),
                        edit_vehicle_year_spinner.selectedItem as Int,
                        cylinders.toInt(),
                        edit_vehicle_edit_plate.text.toString()
                )
            } catch (ex: NumberFormatException) {
                showMessage(getString(R.string.error_number_cylinder_year))
            }
        }
        setupViews()
    }

    fun setupViews() {

        val vehicle = UserVehicle.getVehicle(this)
        val years = resources.getIntArray(R.array.years).toList()
        val yearAdapter = ArrayAdapter<Int>(this, R.layout.spinner_list_item, years)
        yearAdapter.setDropDownViewResource(R.layout.spinner_list_dropdown)
        edit_vehicle_year_spinner.adapter = yearAdapter
        var yearSpinnerPosition = yearAdapter.getPosition(2017)
        if (vehicle?.year != 0) {
            yearSpinnerPosition = yearAdapter.getPosition(vehicle?.year)
        }
        if (vehicle != null) {
            edit_vehicle_edit_brand.setText(vehicle.brand)
            edit_vehicle_edit_model.setText(vehicle.model)
            edit_vehicle_year_spinner.setSelection(yearSpinnerPosition)
            edit_vehicle_edit_cylinders.setText(vehicle.cylinders.toString())
            edit_vehicle_edit_plate.setText(vehicle.plate)
        }
    }

    override fun setErrorBrand(message: String) {
        edit_vehicle_edit_brand.requestFocus()
        edit_vehicle_il_brand.error = message
    }

    override fun setErrorModel(message: String) {
        edit_vehicle_edit_model.requestFocus()
        edit_vehicle_il_model.error = message
    }

    override fun setErrorPlate(message: String) {
        edit_vehicle_edit_plate.requestFocus()
        edit_vehicle_il_plate.error = message
    }

    override fun showProgress() {
        progress = ProgressDialog(this)
        progress?.loading()
    }

    override fun hideProgress() {
        progress?.dismiss()
    }

    override fun getContext(): Context {
        return applicationContext
    }

    override fun showMessage(message: String) {
        UserMessageUtil.showSnackMessage(activity_edit_vechile, message)
    }

    override fun onSuccessUpdate() {
        setResult(Activity.RESULT_OK)
        UserMessageUtil.showSnackMessage(activity_edit_vechile, R.string.success_vehicle_update)
    }
}
