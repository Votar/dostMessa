package entrego.com.entrego.storage.model.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import entrego.com.entrego.BR;
import entrego.com.entrego.location.diraction.Route;
import entrego.com.entrego.storage.model.CustomerModel;
import entrego.com.entrego.storage.model.DeliveryModel;
import entrego.com.entrego.storage.model.EntregoRouteModel;

/**
 * Created by bertalt on 09.12.16.
 */

public class DeliveryInstance extends BaseObservable {

    @Bindable
    private static DeliveryInstance instance = new DeliveryInstance();

    private DeliveryInstance() {    }

    public static DeliveryInstance getInstance() {
        return instance;
    }

    public void update(DeliveryModel model) {

        if (model != null) {
            route = model.getRoute();
            customer = model.getCustomer();
            id = model.getId();
        } else {
            route = null;
            customer = null;
            id = 0;
        }


        notifyPropertyChanged(BR.instance);

    }

    private int id;
    private CustomerModel customer;
    private EntregoRouteModel route;
    private Route path;


    public void setPath(Route path) {
        this.path = path;
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
