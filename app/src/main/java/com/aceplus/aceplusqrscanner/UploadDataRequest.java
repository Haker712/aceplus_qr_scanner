package com.aceplus.aceplusqrscanner;

import java.util.ArrayList;

/**
 * Created by hakerfaker on 1/15/18.
 */

public class UploadDataRequest {
    String site_activation_key;

    ArrayList<Data> data;

    public String getSite_activation_key() {
        return site_activation_key;
    }

    public void setSite_activation_key(String site_activation_key) {
        this.site_activation_key = site_activation_key;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
