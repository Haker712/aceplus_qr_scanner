package com.aceplus.aceplusqrscanner;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by hakerfaker on 1/15/18.
 */

public interface UploadData {
    @FormUrlEncoded
    @POST("upload_data")
    Call<UploadDataResponse> uploadData(@Field("param_data") String paramString);
}
