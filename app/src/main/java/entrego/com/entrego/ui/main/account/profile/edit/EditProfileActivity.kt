package entrego.com.entrego.ui.main.account.profile.edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import entrego.com.entrego.R

class EditProfileActivity : AppCompatActivity() {

    companion object{
        val RQT_CODE =0x691
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
}
