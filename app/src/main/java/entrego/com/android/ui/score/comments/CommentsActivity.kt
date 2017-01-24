package entrego.com.android.ui.score.comments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import entrego.com.android.R
import entrego.com.android.entity.CommentPreviewEntity
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class CommentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        setSupportActionBar(navigation_toolbar)
    }

    override fun onStart() {
        super.onStart()
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        comments_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Handler().postDelayed({ setupAdapter() }, 1500)
    }


    fun setupAdapter() {
        comments_progress.visibility = View.GONE
        val commentsList = listOf<CommentPreviewEntity>(
                CommentPreviewEntity("Very kind good service", 5f),
                CommentPreviewEntity("The food arrived hot and fast", 4.5f),
                CommentPreviewEntity("Excellent service", 5f)
        )
        comments_recycler.adapter = CommentsAdapter(commentsList)
    }


}
