package entrego.com.entrego.ui.main.account.vehicle.edit

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import entrego.com.entrego.R
import entrego.com.entrego.ui.main.account.vehicle.edit.model.UserVehicle
import entrego.com.entrego.ui.main.account.vehicle.edit.presenter.EditVehiclePresenter
import entrego.com.entrego.ui.main.account.vehicle.edit.presenter.IEditVehiclePresenter
import entrego.com.entrego.ui.main.account.vehicle.edit.view.IEditVehicleView
import entrego.com.entrego.util.ToastUtil
import kotlinx.android.synthetic.main.activity_edit_vechile.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class EditVehicleActivity : AppCompatActivity(), IEditVehicleView {

    companion object {
        val RQT_CODE = 0x692
    }

    val presenter: IEditVehiclePresenter = EditVehiclePresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vechile)

        navigation_toolbar.title = getString(R.string.titile_edit_vehicle)

        setSupportActionBar(navigation_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        edit_vehicle_btn_save.setOnClickListener {
            presenter.updateVehicle(
                    edit_vehicle_edit_brand.text.toString(),
                    edit_vehicle_edit_model.text.toString(),
                    edit_vehicle_edit_year.text.toString().toInt(),
                    edit_vehicle_edit_cylinders.text.toString().toInt(),
                    edit_vehicle_edit_plate.text.toString()
            )
        }
        setupViews()
    }

    fun setupViews() {

        val vehicle = UserVehicle.getVehicle(this)
        if (vehicle != null) {

            edit_vehicle_edit_brand.setText(vehicle.brand)
            edit_vehicle_edit_model.setText(vehicle.model)
            edit_vehicle_edit_year.setText(vehicle.year.toString())
            edit_vehicle_edit_cylinders.setText(vehicle.cylinders.toString())
            edit_vehicle_edit_plate.setText(vehicle.plate)
        }
    }


    override fun showProgress() {
        navigation_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        navigation_progress.visibility = View.GONE
    }

    override fun getContext(): Context {
        return applicationContext
    }

    override fun showMessage(message: String) {
        ToastUtil.show(applicationContext, message)
    }

    override fun onSuccessUpdate() {

        setResult(Activity.RESULT_OK)
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)

        }
        return super.onOptionsItemSelected(item)

    }

}
