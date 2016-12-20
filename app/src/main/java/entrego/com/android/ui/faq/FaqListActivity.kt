package entrego.com.android.ui.faq

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import entrego.com.android.R
import kotlinx.android.synthetic.main.activity_faq_list.*
import kotlinx.android.synthetic.main.navigation_toolbar.*
import java.util.*

class FaqListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq_list)
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }
        nav_toolbar_faq.visibility = View.GONE
        setupView()
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        faq_recycler.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        faq_recycler.addItemDecoration(dividerItemDecoration)

        val titles = resources.getStringArray(R.array.faq_titles)
        val bodies = resources.getStringArray(R.array.faq_body)
        val faqList: ArrayList<Pair<String, String>> = ArrayList()
        for (i in 0..titles.size - 1)
            faqList.add(Pair(titles[i], bodies[i]))
        faq_recycler.adapter = FaqAdapter(faqList, faqClickListener)
    }


    val faqClickListener = object : FaqAdapter.FaqClickListener {
        override fun onFaqClicked(title: String, message: String) {
            val intent = Intent(applicationContext, FaqDetailActivity::class.java)
            intent.putExtra(FaqDetailActivity.EXT_TITLE, title)
            intent.putExtra(FaqDetailActivity.EXT_MSG, message)
            startActivity(intent)
        }
    }
}
