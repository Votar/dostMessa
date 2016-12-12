package entrego.com.entrego.ui.main.drawer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.entrego.R

/**
 * Created by bertalt on 09.12.16.
 */
class DrawerWithOrder : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        val view = inflater?.inflate(R.layout.fragment_drawer, container, false)


        return view
    }

}