package com.entregoya.msngr.storage.realm

import com.entregoya.msngr.storage.model.DeliveryModel

interface StorageContract {
    fun cacheDeliveryModels(list : Array<DeliveryModel>)
//    fun getCachedDeliveryModels
}