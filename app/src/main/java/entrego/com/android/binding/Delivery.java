package entrego.com.android.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import entrego.com.android.BR;
import entrego.com.android.entity.EntregoParcelType;
import entrego.com.android.entity.EntregoPriceEntity;
import entrego.com.android.entity.EntregoServiceCategory;
import entrego.com.android.storage.model.CustomerModel;
import entrego.com.android.storage.model.DeliveryModel;
import entrego.com.android.storage.model.EntregoPath;


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
        } else {
            history = null;
            customer = null;
            id = 0;
            path = null;
            status = "";
            price = null;
        }
        notifyPropertyChanged(BR.history);
        notifyPropertyChanged(BR.instance);
    }
    private EntregoParcelType parcel;
    private EntregoPriceEntity price;
    private EntregoServiceCategory category;
    private int id;
    @Bindable
    private CustomerModel customer;
    @Bindable
    private EntregoPath path;
    private String status = "";
    @Bindable
    private HistoryHolder history;


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


    public int getId() {
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
