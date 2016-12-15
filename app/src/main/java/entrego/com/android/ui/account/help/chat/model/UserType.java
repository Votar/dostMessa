package entrego.com.android.ui.account.help.chat.model;

/**
 * Created by bertalt on 16.10.16.
 */

public enum UserType {
    SELF(1), OTHER(2);

    private int value;

    UserType(int i) {
        value = i;
    }

    public int toInt(){
        return value;
    }

    public static UserType valueOf(int value) {
        if (value == 1) return SELF;
        else if (value == 2) return OTHER;
        return null;
    }
}
