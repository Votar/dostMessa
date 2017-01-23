package entrego.com.android.ui.main.dialog

import android.app.Dialog
import android.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
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
class GPSRequiredFragment : DialogFragment() {

    companion object {
        val TAG = "GPSRequiredFragment"
        fun show(supportFragmentManager: android.support.v4.app.FragmentManager) {
            if (supportFragmentManager.findFragmentByTag(TAG) != null)
                return
            GPSRequiredFragment().show(supportFragmentManager, TAG)
        }

        fun dismiss(supportFragmentManager: android.support.v4.app.FragmentManager?): Unit {
            val fragment = supportFragmentManager?.findFragmentByTag(TAG) as? DialogFragment
            fragment?.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.alert)
        builder.setMessage(R.string.dialog_gps_required)
                .setPositiveButton(R.string.turn_on, { dialog, id ->
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                })
        // Create the AlertDialog object and return it
        return builder.create()
    }
}