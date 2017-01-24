package entrego.com.android.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import entrego.com.android.BR;
import entrego.com.android.location.diraction.Route;
import entrego.com.android.storage.model.CustomerModel;
import entrego.com.android.storage.model.DeliveryModel;
import entrego.com.android.storage.model.EntregoRouteModel;
import entrego.com.android.web.model.response.delivery.EntregoPrice;


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
            size = model.getSize();
            price = model.getPrice();
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

    private EntregoPrice price;

    private int id;
    @Bindable
    private CustomerModel customer;
    @Bindable
    private EntregoRouteModel route;
    @Bindable
    private Route path;
    private String status = "";
    private String size = "";


    public EntregoPrice getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
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

    private Delivery(DeliveryModel deliveryModel) {

        instance = new Delivery(deliveryModel);

    }


}
