package entrego.com.android.ui.account.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.ui.account.files.AddFilesActivity
import entrego.com.android.ui.account.help.HelpActivity
import entrego.com.android.ui.account.history.DeliveryHistoryActivity
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by bertalt on 01.12.16.
 */
class ProfileFragment : Fragment() {

    var helpItem: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()

        helpItem = view?.findViewById(R.id.profile_help)
        helpItem?.setOnClickListener { startHelpActivity() }
        profile_routes.setOnClickListener { startDeliveryHistoryActivity() }
        profile_files.setOnClickListener { startAddFilesActivity() }
    }


    fun startHelpActivity() {
        val intent = Intent(context, HelpActivity::class.java)
        startActivity(intent)
    }

    fun startDeliveryHistoryActivity() {
        val intent = Intent(context, DeliveryHistoryActivity::class.java)
        startActivity(intent)
    }

    fun startAddFilesActivity() {
        val intent = Intent(context, AddFilesActivity::class.java)
        startActivity(intent)
    }
}

