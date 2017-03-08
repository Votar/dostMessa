package entrego.com.android.binding;

import android.databinding.BaseObservable;

import com.google.android.gms.maps.model.LatLng;

public class EntregoPointBinding extends BaseObservable {


    private LatLng point;
    private String address = "";


    public EntregoPointBinding(LatLng point, String address, String status) {
        this.point = point;
        this.address = address;
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



    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "EntregoPointBinding{" +
                ", address='" + address + '\'' +
                '}';
    }
}
