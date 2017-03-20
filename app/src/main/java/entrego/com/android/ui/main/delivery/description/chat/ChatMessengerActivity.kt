package entrego.com.android.ui.main.delivery.description.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import entrego.com.android.R
import entrego.com.android.mvp.view.BaseMvpActivity
import entrego.com.android.ui.main.delivery.description.chat.model.ChatMessageEntity
import entrego.com.android.ui.main.delivery.description.chat.model.adapter.ChatThreadAdapter
import entrego.com.android.util.GsonHolder
import entrego.com.android.web.socket.SocketContract
import entrego.com.android.web.socket.model.ChatSocketMessage
import kotlinx.android.synthetic.main.activity_chat_messenger.*
import kotlinx.android.synthetic.main.navigation_toolbar.*

class ChatMessengerActivity : BaseMvpActivity<ChatContract.View, ChatContract.Presenter>(),
        ChatContract.View {


    companion object {
        const val KEY_ORDER_ID = "ext_k_order_id"
        const val KEY_USER_ID = "ext_k_user_id"
        fun getIntent(ctx: Context, orderId: Long, userId: Long): Intent {

            val intent = Intent(ctx, ChatMessengerActivity::class.java)
            intent.putExtra(KEY_ORDER_ID, orderId)
            intent.putExtra(KEY_USER_ID, userId)
            return intent
        }
    }

    val mAdapter = ChatThreadAdapter()
    var mOrderId: Long = 0
    override var mPresenter: ChatContract.Presenter = ChatMessengerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_messenger)
        setupLayouts()
        deserializeIntent()
        registerChatReceiver()
        mPresenter.requestChatHistory()
    }

    private fun deserializeIntent() {
        if (intent.hasExtra(KEY_ORDER_ID)) {
            mOrderId = intent.getLongExtra(KEY_ORDER_ID, 0)
            mPresenter.setupOrderId(mOrderId)
        } else throw IllegalStateException("No orderId in intent")
        if (intent.hasExtra(KEY_USER_ID)) {
            val userId = intent.getLongExtra(KEY_USER_ID, 0)
            mPresenter.setupSenderId(userId)
        } else throw IllegalStateException("No userId in intent")
    }

    fun setupLayouts() {
        nav_toolbar_back.setOnClickListener { NavUtils.navigateUpFromSameTask(this) }

        chat_refresh_layout.isEnabled = false
        val layoutManager = LinearLayoutManager(this)
        chat_recycler.layoutManager = layoutManager
        chat_recycler.itemAnimator = DefaultItemAnimator()
        chat_recycler.adapter = mAdapter
        chat_send.setOnClickListener {

            val message = chat_user_message.text.toString()
            if (message.isNotEmpty()) {
                chat_user_message.setText("")
                mPresenter.sendMessage(mOrderId, message)
            }
        }

        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val colorDarkBlue = ContextCompat.getColor(this, R.color.colorDarkBlue)
        chat_refresh_layout.setColorSchemeColors(colorAccent, colorDarkBlue)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterChatReceiver()
    }


    val mSocketMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(SocketContract.ReceivedChatMessageBySocketEvent.KEY_MESSAGE) == true) {
                val jsonMessage = intent.getStringExtra(SocketContract.ReceivedChatMessageBySocketEvent.KEY_MESSAGE)
                val message = GsonHolder.instance
                        .fromJson(jsonMessage, ChatSocketMessage::class.java)

                mPresenter.showMessage(message)

            } else throw IllegalStateException("No message in intent for sendind by socket")
        }

    }


    override fun registerChatReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mSocketMessageReceiver,
                IntentFilter(SocketContract.ReceivedChatMessageBySocketEvent.Companion.ACTION))
    }

    override fun unregisterChatReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSocketMessageReceiver)
    }

    override fun popupMessage(message: ChatMessageEntity) {
        mAdapter.addMessage(message)
        chat_recycler.scrollToPosition(mAdapter.itemCount - 1)
    }

    override fun popupMessages(listMessages: List<ChatMessageEntity>) {
        mAdapter.addMessages(listMessages)
        chat_recycler.scrollToPosition(mAdapter.itemCount - 1)
    }

    override fun showProgress() {
        chat_refresh_layout.isEnabled = true
        chat_refresh_layout.isRefreshing = true
    }

    override fun hideProgress() {
        chat_refresh_layout.isRefreshing = false
        chat_refresh_layout.isEnabled = false
    }

    override fun getRootView(): View = activity_chat_messenger

}
