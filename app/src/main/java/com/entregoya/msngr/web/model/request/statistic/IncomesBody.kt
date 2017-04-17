package com.entregoya.msngr.web.model.request.statistic

class IncomesBody(val from:String, val to:String, val zone:String){

    override fun toString(): String {
        return "IncomesBody(from=$from, to=$to, zone='$zone')"
    }
}
