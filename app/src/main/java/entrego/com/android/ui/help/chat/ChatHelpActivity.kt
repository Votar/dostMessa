package entrego.com.android.ui.help.chat

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import entrego.com.android.R
import entrego.com.android.ui.help.chat.model.ChatMessage
import entrego.com.android.ui.help.chat.model.adapter.ChatThreadAdapter
import entrego.com.android.ui.help.chat.presenter.ChatPresenter
import entrego.com.android.ui.help.chat.presenter.IChatPresenter
import entrego.com.android.ui.help.chat.view.IChatView
import kotlinx.android.synthetic.main.activity_chat_help.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class ChatHelpActivity : AppCompatActivity(), IChatView {


    var presenter: IChatPresenter = ChatPresenter()
    var mAdapter: ChatThreadAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_help)

        navigation_toolbar.title = getString(R.string.ui_chatting)
        setSupportActionBar(navigation_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)


        val layoutManager = LinearLayoutManager(this)


        chat_recycler.setLayoutManager(layoutManager)
        chat_recycler.setItemAnimator(DefaultItemAnimator())

        mAdapter = ChatThreadAdapter()
        chat_recycler.setAdapter(mAdapter)

        presenter.onCreate(this)

        chat_send.setOnClickListener {

            if (chat_user_message.text.toString().isNotEmpty()) {
                presenter.sendMessage(chat_user_message.text.toString())
                chat_user_message.setText("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoadingProgress() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingProgress() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChatContext(): Context {
        return applicationContext
    }

    override fun showMessage(message: ChatMessage) {
        if(mAdapter!= null){
            mAdapter?.addMessage(message)
            chat_recycler.scrollToPosition(mAdapter?.itemCount!!-1)
        }
    }

}
