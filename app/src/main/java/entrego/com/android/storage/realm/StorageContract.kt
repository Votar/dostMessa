package entrego.com.android.storage.realm

interface StorageContract {

    fun addFavoritePlace(address: String): Boolean
    fun getFavoritesList(): List<String>
    fun removeFavorite(address: String): Boolean
}