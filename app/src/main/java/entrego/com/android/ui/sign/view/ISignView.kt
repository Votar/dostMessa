package entrego.com.android.ui.sign.view

/**
 * Created by bertalt on 13.12.16.
 */
interface ISignView {
    fun showProgress()
    fun hideProgress()
    fun onSuccessSign()
    fun onFailureSign(message:String)
}