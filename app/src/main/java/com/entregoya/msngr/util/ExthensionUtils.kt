package com.entregoya.msngr.util

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.icu.text.TimeZoneFormat
import android.location.LocationManager
import android.net.Uri
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.EntregoPointBinding
import com.entregoya.msngr.entity.EntregoWaypoint
import com.entregoya.msngr.entity.IncomeEntity
import com.entregoya.msngr.storage.model.EntregoPoint
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.auth.AuthActivity
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.socket.SocketService
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.SimpleTimeZone.UTC_TIME
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


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
    this.setCancelable(false)
    this.setTitle(this.context.getString(R.string.ui_progress_dialog_title))
    this.setMessage(this.context.getString(R.string.ui_loading))
    this.setButton(Dialog.BUTTON_NEGATIVE,
            this.context.getString(android.R.string.cancel)
            , { dialog, which -> dismiss() })
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

fun View.buildRatingBar(rating: Float) {

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

fun String.getTimeInUtc(): Long {

    val template = "yyyy-MM-dd'T'HH:mm:ssZ"
    SimpleDateFormat.DEFAULT
    val formatter = SimpleDateFormat(template, Locale.getDefault())
    val inUtc = formatter.format(this)
    return formatter.parse(inUtc).time
}

//fun Long.formattedDate(): String {
//    val milliseconds: Long = this * 24 * 60 * 60 * 1000
//    val fmt = DateTimeFormat.forPattern("dd/MM/yyyy")
//    val date = DateTime.now().withMillis(milliseconds)
//    return fmt.print(date)
//}

fun Long.formattedDate(): String {
    val milliseconds: Long = this * 24 * 60 * 60 * 1000
    val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")
    val date = DateTime.now(DateTimeZone.UTC).withMillis(milliseconds)
    return fmt.print(date)
}

fun String.toDateTimeLong(): DateTime {
    return DateTime(this)
}

fun String.toFormattedDateTime(): String {
    val date = DateTime(this)
    return DateTimeFormat.forPattern("yyyy-MM-dd hh:MM:ss").print(date)
}


fun ImageView.loadSimple(url: String) {
    Glide.with(this.context)
            .load(url)
            .into(this)
}

fun Long.formattedDefaultDate(): String {
    val format = SimpleDateFormat("dd/MM/yy hh:mm aaa", Locale.getDefault())
    val timestamp = format.format(this)
    return timestamp
}

fun Array<EntregoWaypoint>.getStaticMapUrl(path: String?): String {
    val staticPartUrl = "https://maps.googleapis.com/maps/api/staticmap?autoscale=1" +
            "&size=600x300" +
            "&maptype=roadmap" +
            "&format=png" +
            "&path=weight:3%7Ccolor:blue%7Cenc:$path" +
            "&visual_refresh=true"
    val urlBuilder = StringBuilder()
    urlBuilder.append(staticPartUrl)

    this.forEachIndexed { i, point ->
        val coordinates = "" + this[i].waypoint.point.latitude +
                "," + this[i].waypoint.point.longitude
        val label = i + 1
        urlBuilder.append("&markers=size:mid%7Clabel:$label%7C$coordinates")
    }
    return urlBuilder.toString()
}

fun Context.isGpsEnable(): Boolean {
    val manager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}


fun View.showSnack(message: String?) {
    val text: String
    if (message.isNullOrEmpty()) {
        text = this.context.getString(R.string.er_default_network_error)
    } else
        text = message!!

    val snackBar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    val primaryColor = ContextCompat.getColor(this.context, R.color.colorPrimary)
    sbMessageTextView.setTextColor(primaryColor)
    snackBar.show()
}

fun View.showSnackError(message: String?) {
    val text: String
    if (message.isNullOrEmpty()) {
        text = this.context.getString(R.string.er_default_network_error)
    } else
        text = message!!

    val snackBar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
    val sbMessageTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
    val primaryColor = ContextCompat.getColor(this.context, R.color.colorTomato)
    sbMessageTextView.setTextColor(primaryColor)
    snackBar.show()
}

fun ImageView.loadMessengerPhoto() {
    Glide.with(this.context)
            .load(EntregoApi.REQUESTS.GET_USER_PHOTO)
            .error(R.drawable.ic_user_pic_holder)
            .skipMemoryCache(true)
            .into(this)
}


fun Context.logout() {
    stopService(Intent(this, SocketService::class.java))
    EntregoStorage.setToken("")
    val intent = AuthActivity.getIntentLogout(this)
    startActivity(intent)
}