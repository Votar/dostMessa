package com.entregoya.msngr.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.entregoya.msngr.BR;
import com.entregoya.msngr.entity.EntregoParcelType;
import com.entregoya.msngr.entity.EntregoPriceEntity;
import com.entregoya.msngr.entity.EntregoServiceCategory;
import com.entregoya.msngr.storage.model.CustomerModel;
import com.entregoya.msngr.storage.model.DeliveryModel;
import com.entregoya.msngr.storage.model.EntregoPath;


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
            history = new HistoryHolder(model.getHistory());
            customer = model.getCustomer();
            id = model.getId();
            status = model.getStatus();
            price = model.getPrice();
            path = model.getPath();
            category = model.getCategory();
            parcel = model.getParcel();
            notes = model.getNotes();
        } else {
            history = null;
            customer = null;
            id = -1;
            path = null;
            status = "";
            price = null;
            notes = null;
        }
        notifyPropertyChanged(BR.history);
        notifyPropertyChanged(BR.instance);
    }
    private EntregoParcelType parcel;
    private EntregoPriceEntity price;
    private EntregoServiceCategory category;
    private long id;
    @Bindable
    private CustomerModel customer;
    @Bindable
    private EntregoPath path;
    private String status = "";
    @Bindable
    private HistoryHolder history;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public EntregoPriceEntity getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public EntregoServiceCategory getCategory() {
        return category;
    }

    public EntregoParcelType getParcel() {
        return parcel;
    }

    public void setPath(EntregoPath path) {
        this.path = path;
    }

    public EntregoPath getPath() {
        return path;
    }


    public long getId() {
        return id;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    private Delivery(DeliveryModel deliveryModel) {

        instance = new Delivery(deliveryModel);

    }

    public HistoryHolder getHistory() {
        return history;
    }
}
