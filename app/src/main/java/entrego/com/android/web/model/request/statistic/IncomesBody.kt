package entrego.com.android.web.model.request.statistic

class IncomesBody(val from:Long, val to:Long, val timeZone:String){

    override fun toString(): String {
        return "IncomesBody(from=$from, to=$to, timeZone='$timeZone')"
    }
}
