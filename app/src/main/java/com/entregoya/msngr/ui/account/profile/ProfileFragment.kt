package com.entregoya.msngr.ui.account.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import com.entregoya.msngr.ui.account.files.FileVariantsActivity
import com.entregoya.msngr.ui.account.help.HelpActivity
import com.entregoya.msngr.ui.account.history.DeliveryHistoryActivity
import com.entregoya.msngr.ui.account.profile.account.AccountActivity
import com.entregoya.msngr.util.event_bus.LogoutEvent
import kotlinx.android.synthetic.main.fragment_profile.*
import org.greenrobot.eventbus.EventBus

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
        profile_account.setOnClickListener { startAccountActivity() }
        profile_logout.setOnClickListener{ logout()}
    }


    private fun startAccountActivity() {
        startActivity(Intent(context, AccountActivity::class.java))
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
        val intent = Intent(context, FileVariantsActivity::class.java)
        startActivity(intent)
    }


    private fun logout() {
        EventBus.getDefault().post(LogoutEvent())
    }
}

