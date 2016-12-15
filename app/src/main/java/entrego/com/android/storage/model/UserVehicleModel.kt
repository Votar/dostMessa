package entrego.com.android.storage.model

/**
 * Created by bertalt on 30.11.16.
 */
class UserVehicleModel(val brand: String = "",
                       val model: String = "",
                       val year: Int = 0,
                       val cylinders: Int = 0,
                       val plate: String = "") {


    override fun toString(): String {
        return "UserVehicleModel(brand='$brand', model='$model', year=$year, cylinders=$cylinders, plate='$plate')"
    }
}