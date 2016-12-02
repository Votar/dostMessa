package entrego.com.entrego.ui.main.account.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.entrego.R

/**
 * Created by bertalt on 01.12.16.
 */
class ProfileFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_profile, container, false)

        return view
    }
}