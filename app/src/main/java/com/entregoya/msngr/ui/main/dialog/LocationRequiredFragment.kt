package com.entregoya.msngr.ui.main.dialog

import android.app.Dialog
import android.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AlertDialog
import org.greenrobot.eventbus.util.ErrorDialogManager
import android.support.v4.content.ContextCompat.startActivity
import android.app.Activity
import android.net.Uri


/**
 * Created by bertalt on 06.01.17.
 */
class LocationRequiredFragment : DialogFragment() {

    companion object {
        val TAG = "LocationRequiredFragment"
        fun show(fragmentManager: android.support.v4.app.FragmentManager) {
            if (fragmentManager.findFragmentByTag(TAG) != null)
                return
            LocationRequiredFragment().show(fragmentManager, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.alert)
        builder.setMessage(R.string.dialog_location_permission_required)
                .setPositiveButton(R.string.grant_permission, { dialog, id ->
                    startInstalledAppDetailsActivity(activity)
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                })
        // Create the AlertDialog object and return it
        return builder.create()
    }

    fun startInstalledAppDetailsActivity(context: Activity?) {
        if (context == null) {
            return
        }
        val i = Intent()
        i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.data = Uri.parse("package:" + context.packageName)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        context.startActivity(i)
    }


}