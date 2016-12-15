package entrego.com.android.ui.help.chat.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import entrego.com.android.R;
import entrego.com.android.ui.help.chat.model.ChatMessage;
import entrego.com.android.ui.help.chat.model.UserType;


/**
 * Created by bertalt on 16.10.16.
 */

public class ChatThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = ChatThreadAdapter.class.getSimpleName();
    private List<ChatMessage> messageArrayList;
    private static String today;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTimestamp;

        public ViewHolder(View view) {
            super(view);
            tvMessage = (TextView) itemView.findViewById(R.id.message);
            tvTimestamp = (TextView) itemView.findViewById(R.id.timestamp);
        }
    }

    public ChatThreadAdapter() {
        messageArrayList = new ArrayList<>();
    }

    public ChatThreadAdapter(List<ChatMessage> content) {

        this();
        messageArrayList.addAll(content);

        Calendar calendar = Calendar.getInstance();
    }

    public boolean addMessage(ChatMessage message) {

        if (messageArrayList.add(message)) {
            notifyDataSetChanged();

            return true;
        } else
            return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (UserType.valueOf(viewType) == UserType.SELF) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_self, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_other, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        return messageArrayList.get(position)
                .getUserType().toInt();

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatMessage message = messageArrayList.get(position);

        ((ViewHolder) holder).tvMessage.setText(message.getText());

        //format timestamp
        String timestamp = getTimeStamp(message.getTimestamp());

        ((ViewHolder) holder).tvTimestamp.setText(timestamp);
    }

    private static String getTimeStamp(Date dateStr) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String timestamp = format.format(dateStr);
        return timestamp;
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
