package entrego.com.android.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.android.gms.maps.model.LatLng;

import entrego.com.android.BR;
import entrego.com.android.storage.model.PointStatus;

public class EntregoPointBinding extends BaseObservable {


    private LatLng point;
    private String address = "";
    @Bindable
    private PointStatus status;

    public EntregoPointBinding(LatLng point, String address, String status) {
        this.point = point;
        this.address = address;
        this.status = PointStatus.valueOf(status);
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
        this.status = PointStatus.valueOf(status);
        notifyPropertyChanged(BR.status);
    }

    public String getAddress() {
        return address;
    }

    public PointStatus getStatus() {
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
