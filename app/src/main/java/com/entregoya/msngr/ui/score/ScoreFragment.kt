package com.entregoya.msngr.ui.score

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.entregoya.msngr.R
import com.entregoya.msngr.entity.ScoreEntity
import com.entregoya.msngr.mvp.view.BaseMvpFragment
import com.entregoya.msngr.ui.score.comments.CommentsActivity
import com.entregoya.msngr.ui.score.tips.ImprovementTipsActivity
import com.entregoya.msngr.ui.score.updates.WeeklyUpdatesListActivity
import com.entregoya.msngr.util.buildRatingBar
import kotlinx.android.synthetic.main.best_messenger_charts.*
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.score_icnlude_main_data.*

class ScoreFragment : BaseMvpFragment<ScoreContract.View, ScoreContract.Presenter>(),
        ScoreContract.View {


    override var mPresenter: ScoreContract.Presenter = ScorePresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val view = inflater?.inflate(R.layout.fragment_score, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        mPresenter.requestBestMessenger()
    }

    override fun onResume() {
        super.onResume()
    }

    fun setupListeners() {
        score_comments_item.setOnClickListener { startCommentsActivity() }
        score_weekly_updates_item.setOnClickListener { startWeeklyListActivity() }
        score_tips_item.setOnClickListener { startTipsActivity() }
    }

    private fun startTipsActivity() {
        ImprovementTipsActivity.start(context)
    }

    private fun startWeeklyListActivity() {
        WeeklyUpdatesListActivity.start(context)
    }

    private fun startCommentsActivity() {
        val intent = Intent(context, CommentsActivity::class.java)
        startActivity(intent)
    }

    override fun setupScoreView(bestMessenger: ScoreEntity, userScore: ScoreEntity) {
        score_best_bar.buildRatingBar(bestMessenger.average)
        score_user_bar.buildRatingBar(userScore.average)
        score_total_delivery_value.text = userScore.completed.toString()
        score_total_cnceled_value.text = userScore.canceled.toString()
        score_total_declined_value.text = userScore.declined.toString()
        score_best_value.text = bestMessenger.average.toString()
        score_user_value.text = userScore.average.toString()
        score_user_score.text = userScore.average.toString()
    }

}