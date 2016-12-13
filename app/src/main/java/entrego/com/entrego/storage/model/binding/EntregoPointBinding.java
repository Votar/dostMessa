package entrego.com.entrego.storage.model.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import entrego.com.entrego.BR;

/**
 * Created by bertalt on 09.12.16.
 */

public class EntregoPointBinding extends BaseObservable{

    private double latitude;
    private double longitude;
    @Bindable private String address ="Loading...";
    @Bindable private String status ="";

    public EntregoPointBinding(double latitude, double longitude, String address, String status) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "EntregoPointBinding{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
