package entrego.com.android.ui.score

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.ui.score.comments.CommentsActivity
import kotlinx.android.synthetic.main.fragment_score.*

class ScoreFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val view = inflater?.inflate(R.layout.fragment_score, container, false)
        return view
    }
    override fun onStart() {
        super.onStart()
        setupListeners()
    }
    fun setupListeners() {
        score_comments_item.setOnClickListener { startCommentsActivity() }
    }
    private fun startCommentsActivity() {
        val intent = Intent(context, CommentsActivity::class.java)
        startActivity(intent)
    }
}