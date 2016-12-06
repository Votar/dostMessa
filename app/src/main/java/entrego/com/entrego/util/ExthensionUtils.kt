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

/**
 * Created by bertalt on 29.11.16.
 */
object UserMessageUtil {
    @JvmStatic fun show(ctx: Context, message: String) {
        var text: String = ""
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

fun LatLng.toDirectionFormat(): String {

    return StringBuilder().
            append(this.latitude)
            .append(",")
            .append(this.longitude)
            .toString()

}