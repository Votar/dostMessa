package entrego.com.entrego.ui.main.account.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.entrego.R
import entrego.com.entrego.ui.help.HelpActivity

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
    }


    fun startHelpActivity() {

        val intent = Intent(context, HelpActivity::class.java)
        startActivity(intent)

    }
}

