package entrego.com.android.util

import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import entrego.com.android.R
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


fun ImageView.loadPicasson(resId: Int) {
    Glide.with(this.context).load(resId).into(this)
}

fun Uri.getRealPathFromURI(context: Context?): String {

    if (context != null)
        try {
            Logger.logd(this.toString())
            var cursor: Cursor? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(this, proj, null, null, null)
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                return cursor.getString(column_index)
            } finally {
                if (cursor != null) {
                    cursor.close()
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()

            return ""
        }
    else
        return ""
}
