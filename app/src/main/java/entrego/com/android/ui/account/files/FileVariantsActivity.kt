package entrego.com.android.ui.account.files

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import entrego.com.android.R
import kotlinx.android.synthetic.main.activity_file_variants.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class FileVariantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_variants)

        setSupportActionBar(navigation_toolbar)
        nav_toolbar_back.setOnClickListener({ NavUtils.navigateUpFromSameTask(this) })

        file_variants_licence_item.setOnClickListener { startAddFilesActivity(AddFilesActivity.RQT_LICENCE) }
        file_variants_id_item.setOnClickListener { startAddFilesActivity(AddFilesActivity.RQT_ID) }
    }

    private fun startAddFilesActivity(requestCode: Int) {
        val intent = Intent(this, AddFilesActivity::class.java)
        intent.putExtra(AddFilesActivity.KEY_RQT_CODE, requestCode)
        startActivity(intent)
    }
}
