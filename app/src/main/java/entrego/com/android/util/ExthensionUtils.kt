package entrego.com.android.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.NavUtils
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import entrego.com.android.R
import entrego.com.android.storage.model.EntregoPoint
import entrego.com.android.binding.EntregoPointBinding

/**
 * Created by bertalt on 29.11.16.
 */
object UserMessageUtil {
    @JvmStatic fun show(ctx: Context, message: String?) {
        var text: String
        if (TextUtils.isEmpty(message)) {
            text = ctx.getString(R.string.er_default_network_error)
        } else
            text = message!!

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

fun EntregoPointBinding.toDirectionFormat(): String =
        StringBuilder()
                .append(this.latitude)
                .append(",")
                .append(this.longitude)
                .toString()


fun ImageView.loadPicasson(resId: Int){
    Picasso.with(this.context).load(resId).into(this)
}
