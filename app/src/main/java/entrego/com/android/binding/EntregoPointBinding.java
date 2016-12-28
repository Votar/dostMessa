package entrego.com.android.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.android.gms.maps.model.LatLng;

import entrego.com.android.BR;

/**
 * Created by bertalt on 09.12.16.
 */

public class EntregoPointBinding extends BaseObservable {


    private LatLng point;
    private String address = "";
    @Bindable
    private String status = "";

    public EntregoPointBinding(LatLng point, String address, String status) {
        this.point = point;
        this.address = address;
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
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
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
