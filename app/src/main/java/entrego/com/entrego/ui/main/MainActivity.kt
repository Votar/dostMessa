package entrego.com.entrego.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import entrego.com.entrego.R
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.util.ToastUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ToastUtil.show(this, "token " + EntregoStorage(this).getToken())
    }
}
