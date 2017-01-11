package entrego.com.android.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import entrego.com.android.BR;
import entrego.com.android.location.diraction.Route;
import entrego.com.android.storage.model.CustomerModel;
import entrego.com.android.storage.model.DeliveryModel;
import entrego.com.android.storage.model.DeliveryState;
import entrego.com.android.storage.model.EntregoRouteModel;

/**
 * Created by bertalt on 09.12.16.
 */

public class Delivery extends BaseObservable {

    @Bindable
    private static Delivery instance = new Delivery();

    private Delivery() {
    }

    public static Delivery getInstance() {
        return instance;
    }

    public synchronized void update(DeliveryModel model) {

        if (model != null) {
            route = model.getRoute();
            customer = model.getCustomer();
            id = model.getId();
            status = model.getStatus();
        } else {
            route = null;
            customer = null;
            id = 0;
            path = null;
            status = "";
        }
        notifyPropertyChanged(BR.route);
        notifyPropertyChanged(BR.instance);
    }

    private int id;
    @Bindable
    private CustomerModel customer;
    @Bindable
    private EntregoRouteModel route;
    @Bindable
    private Route path;

    public String getStatus() {
        return status;
    }

    private String status = "";

    private DeliveryState deliveryState = null;


    public void setPath(Route path) {
        this.path = path;
        notifyPropertyChanged(BR.instance);
    }

    public Route getPath() {
        return path;
    }

    public EntregoRouteModel getRoute() {
        return route;
    }

    public int getId() {
        return id;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    private Delivery(DeliveryModel deliveryModel) {

        instance = new Delivery(deliveryModel);

    }


}