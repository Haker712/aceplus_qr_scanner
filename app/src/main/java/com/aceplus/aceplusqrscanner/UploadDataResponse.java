package com.aceplus.aceplusqrscanner;

import java.util.ArrayList;

/**
 * Created by hakerfaker on 1/15/18.
 */

public class UploadDataResponse {
    String aceplus_status_code;
    String aceplus_status_message;

    public String getAceplus_status_code() {
        return aceplus_status_code;
    }

    public void setAceplus_status_code(String aceplus_status_code) {
        this.aceplus_status_code = aceplus_status_code;
    }

    public String getAceplus_status_message() {
        return aceplus_status_message;
    }

    public void setAceplus_status_message(String aceplus_status_message) {
        this.aceplus_status_message = aceplus_status_message;
    }
}
