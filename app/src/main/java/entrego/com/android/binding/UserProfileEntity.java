package entrego.com.android.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import entrego.com.android.storage.model.UserProfileModel;



public class UserProfileEntity extends BaseObservable{

    private static UserProfileEntity sUserProfileEntity =new UserProfileEntity();
    public static UserProfileEntity getInstance(){
        return sUserProfileEntity;
    }

    @Bindable
    private UserProfileModel userProfile;
    public UserProfileModel getProfile(){
        return userProfile;
    }
    public void update(UserProfileModel model){
        userProfile = model;
        notifyPropertyChanged(BR.userProfile);
    }

}
