package entrego.com.android.ui.help.chat.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bertalt on 16.10.16.
 */

public class ChatMessage {

    private Date timestamp;
    private String text;
    private UserType userType;


    public ChatMessage(String text, int userId) {

        Calendar calendar = Calendar.getInstance();

        this.timestamp = calendar.getTime();
        this.text = text;
        this.userType = UserType.valueOf(userId);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public UserType getUserType() {
        return userType;
    }
}
