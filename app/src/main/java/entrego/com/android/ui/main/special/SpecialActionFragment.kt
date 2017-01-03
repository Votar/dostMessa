package entrego.com.android.ui.main.special

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import kotlinx.android.synthetic.main.dialog_special_action.*

class SpecialActionFragment : DialogFragment() {
    companion object {
        fun newInstance(): DialogFragment {
            return SpecialActionFragment()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater?.inflate(R.layout.dialog_special_action, container, false)

        return v
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        special_action_dismiss.setOnClickListener { this.dismiss() }
        special_action_details_btn.setOnClickListener { startSpecialActionActivity() }
    }

    private fun startSpecialActionActivity() {
        SpecialDetailsActivity.start(context)
    }
}
