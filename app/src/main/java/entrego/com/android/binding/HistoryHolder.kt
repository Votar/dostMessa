package entrego.com.android.binding

import entrego.com.android.entity.EntregoWaypoint
import entrego.com.android.storage.model.PointStatus

class HistoryHolder(val value: Array<EntregoWaypoint>) {
    fun getCurrentPoint(): EntregoWaypoint {
        var currentPoint = value.findLast { (it.status == PointStatus.GOING && value.indexOf(it) != value.lastIndex) }
        if (currentPoint == null) {
            currentPoint = value.findLast { (it.status == PointStatus.WAITING && value.indexOf(it) != value.lastIndex) }
            if (currentPoint == null) {
                currentPoint = value.find { (it.status == PointStatus.PENDING && value.indexOf(it) != value.lastIndex) }
                if (currentPoint == null) {
                    currentPoint = value.findLast { (it.status == PointStatus.DONE && value.indexOf(it) != value.lastIndex) }
                    if (currentPoint == null)
                        return value[0]
                }
            }
        }
        return currentPoint
    }

    fun getGoingPoint(): EntregoWaypoint {
        val currentPoint = getCurrentPoint()
        if (currentPoint.status == PointStatus.DONE)
            return getDestinationPoint()
        else
            return currentPoint
    }

    fun getDestinationPoint(): EntregoWaypoint {
        val currentPointIndex = value.indexOf(getCurrentPoint())
        return value[currentPointIndex.plus(1)]
    }

    fun getNextPoint(): EntregoWaypoint? {
        val destinationIndex = value.indexOf(getDestinationPoint())
        val historyLastIndex = value.lastIndex
        if (destinationIndex != historyLastIndex)
            return value[destinationIndex.plus(1)]
        else
            return null
    }

    fun getOtherPoints(): List<EntregoWaypoint> {

        val indexNextPoint = value.indexOf(getDestinationPoint()).plus(1)
        if (indexNextPoint <= value.lastIndex)
            return value.asList().subList(indexNextPoint, value.count())
        else
            return emptyList<EntregoWaypoint>()
    }


}