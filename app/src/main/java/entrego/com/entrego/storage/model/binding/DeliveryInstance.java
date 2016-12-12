package entrego.com.entrego.storage.model.binding;

import android.databinding.BaseObservable;

import entrego.com.entrego.location.diraction.Route;
import entrego.com.entrego.storage.model.CustomerModel;
import entrego.com.entrego.storage.model.DeliveryModel;
import entrego.com.entrego.storage.model.EntregoRouteModel;

/**
 * Created by bertalt on 09.12.16.
 */

public class DeliveryInstance extends BaseObservable {

    private static DeliveryInstance instance;

    public static DeliveryInstance getInstance() {
        return instance;
    }

    public static void createInstance(DeliveryModel model) {

        if (instance == null)
            instance = new DeliveryInstance(model.getId(), model.getCustomer(), model.getRoute());

    }

    private int id;
    private CustomerModel customer;
    private EntregoRouteModel route;
    private Route path;

    private DeliveryInstance(int id, CustomerModel customer, EntregoRouteModel route) {
        this.id = id;
        this.customer = customer;
        this.route = route;
    }


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
