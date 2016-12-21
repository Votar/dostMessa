package entrego.com.android.ui.score

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import entrego.com.android.R
import entrego.com.android.ui.score.comments.CommentsActivity
import entrego.com.android.util.buildRatingBar
import kotlinx.android.synthetic.main.best_messenger_charts.*
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
        setupBars()
    }

    override fun onResume() {
        super.onResume()
    }

    fun setupListeners() {
        score_comments_item.setOnClickListener { startCommentsActivity() }
    }

    private fun startCommentsActivity() {
        val intent = Intent(context, CommentsActivity::class.java)
        startActivity(intent)
    }

    fun setupBars() {
        //TODO:remove mock values
        val userRating = 3.22
        val bestRating = 4.89
        score_best_value.text = bestRating.toString()
        score_best_bar.buildRatingBar(bestRating)
        score_user_value.text = userRating.toString()
        score_user_bar.buildRatingBar(userRating)
    }
}