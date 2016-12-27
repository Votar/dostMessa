package entrego.com.android.ui.account.help.reports.model

/**
 * Created by bertalt on 26.12.16.
 */
data class ReportModel(val id: Int,
                       val title: String,
                       val status: String,
                       val type: String,
                       val message :String = "",
                       val response:String?,
                       val timestamp: String) {
}