package entrego.com.entrego.ui.main.account.vehicle.edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entrego.com.entrego.R

class EditVehicleActivity : AppCompatActivity() {

    companion object{
        val RQT_CODE =0x692
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vechile)
    }
}
