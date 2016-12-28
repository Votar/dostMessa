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

public class DeliveryInstance extends BaseObservable {

    @Bindable
    private static DeliveryInstance instance = new DeliveryInstance();

    private DeliveryInstance() {
    }

    public static DeliveryInstance getInstance() {
        return instance;
    }

    public synchronized void update(DeliveryModel model) {

        if (model != null) {
            route = model.getRoute();
            customer = model.getCustomer();
            id = model.getId();
        } else {
            route = null;
            customer = null;
            id = 0;
            path = null;
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

    private DeliveryState deliveryState = null;

    public DeliveryState getDeliveryState() {
        return deliveryState;
    }

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

    private DeliveryInstance(DeliveryModel deliveryModel) {

        instance = new DeliveryInstance(deliveryModel);

    }


}
