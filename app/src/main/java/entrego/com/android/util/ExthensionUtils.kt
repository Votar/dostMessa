package entrego.com.android.util

import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import entrego.com.android.R
import entrego.com.android.binding.EntregoPointBinding
import entrego.com.android.entity.IncomeEntity
import org.joda.time.DateTime
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.SimpleTimeZone.UTC_TIME
import org.joda.time.format.DateTimeFormat


/**
 * Created by bertalt on 29.11.16.
 */
object UserMessageUtil {
    @JvmStatic fun show(ctx: Context, message: String?) {
        val text: String
        if (message.isNullOrEmpty()) {
            text = ctx.getString(R.string.er_default_network_error)
        } else
            text = message!!

        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic fun show(ctx: Context, messageId: Int) {
        UserMessageUtil.show(ctx, ctx.getString(messageId))
    }


    @JvmStatic fun showSnackMessage(view: View, message: String?) {

        val text: String
        if (message.isNullOrEmpty()) {
            text = view.context.getString(R.string.er_default_network_error)
        } else
            text = message!!

        val snackBar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        val normalPadding = view.context.resources.getDimensionPixelSize(R.dimen.padding_medium)
        val primaryColor = ContextCompat.getColor(view.context, R.color.colorPrimary)
        sbMessageTextView.setTextColor(primaryColor)
        sbMessageTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.accept_icon, 0, 0, 0)
        sbMessageTextView.setCompoundDrawablePadding(normalPadding)
        snackBar.show()
    }

    @JvmStatic fun showSnackMessage(view: View, messageResId: Int) {
        showSnackMessage(view, view.context.getString(messageResId))
    }

}

fun View.snackSimple(message: String?) {
    UserMessageUtil.showSnackMessage(this, message)
}

fun ProgressDialog.loading() {
    this.setTitle(this.context.getString(R.string.ui_progress_dialog_title))
    this.setMessage(this.context.getString(R.string.ui_loading))
    this.show()
}

fun EntregoPointBinding.toDirectionFormat(): String =
        StringBuilder()
                .append(this.point.latitude)
                .append(",")
                .append(this.point.longitude)
                .toString()


fun ImageView.loadImg(resId: Int) {
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

fun View.buildRatingBar(rating: Double) {

    val density = this.resources.displayMetrics.density
    val sourceWidth = 250 * density
    val sourceHeight = (10 * density).toInt()
    val MAX_VALUE = 5
    val stepInBar = (sourceWidth / MAX_VALUE).toInt()
    val resultWidth = (rating * stepInBar).toInt()
    val userLayoutParams = LinearLayout.LayoutParams(resultWidth, sourceHeight)
    userLayoutParams.gravity = Gravity.CENTER_VERTICAL
    this.layoutParams = userLayoutParams
}

fun Float.formatRating(digits: Float) = java.lang.String.format("%${digits}f", this)

fun Bitmap.encodeToStringBase64(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
    val byteArray = baos.toByteArray()
    val photoEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
    return photoEncoded
}

fun ByteArray.encodeToStringBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)

fun Date.getTimeInUtc(): Long {
    val template = "MM/dd/yyyy KK:mm:ss a Z"
    val formatter = SimpleDateFormat(template, Locale.getDefault())
    val inUtc = formatter.format(this)
    return formatter.parse(inUtc).time
}

fun IncomeEntity.formattedDate(): String {
    val days = this.day
    val milliseconds: Long = days * 24 * 60 * 60 * 1000
    val fmt = DateTimeFormat.forPattern("dd/MM/yyyy")
    val date = DateTime.now().withMillis(milliseconds)
    return fmt.print(date)
}

fun ImageView.loadSimple(url: String) {
    Glide.with(this.context)
            .load(url)
            .into(this)
}
