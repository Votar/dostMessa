package entrego.com.entrego.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.NavUtils
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import entrego.com.entrego.R
import entrego.com.entrego.storage.model.EntregoPoint
import entrego.com.entrego.storage.model.binding.EntregoPointBinding

/**
 * Created by bertalt on 29.11.16.
 */
object UserMessageUtil {
    @JvmStatic fun show(ctx: Context, message: String) {
        var text: String
        if (TextUtils.isEmpty(message)) {
            text = ctx.getString(R.string.er_default_network_error)
        } else
            text = message

        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic fun show(ctx: Context, messageId: Int) {
        UserMessageUtil.show(ctx, ctx.getString(messageId))
    }

}

fun ProgressDialog.loading() {
    this.setTitle(this.context.getString(R.string.ui_progress_dialog_title))
    this.setMessage(this.context.getString(R.string.ui_loading))
    this.show()
}

fun EntregoPoint.toDirectionFormat(): String =
        StringBuilder()
                .append(this.latitude)
                .append(",")
                .append(this.longitude)
                .toString()

fun EntregoPointBinding.toDirectionFormat(): String =
        StringBuilder()
                .append(this.latitude)
                .append(",")
                .append(this.longitude)
                .toString()
