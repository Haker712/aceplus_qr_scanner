package com.aceplus.aceplusqrscanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hakerfaker on 1/15/18.
 */

public class ShowActivity extends AppCompatActivity {

    TextView textViewName, textViewID, textViewApplyFor, textViewNRC, textViewDOB, textViewEmail, textViewRegisteredSection, textViewRegisteredDate;
    RadioButton radioButtonMorning, radioButtonEvening;
    Button buttonUpload;

    String name , id, apply_for, nrc, dob, email, registeredSection, registeredDate = "";
    String attendedSection = "Morning";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        textViewName = findViewById(R.id.textViewName);
        textViewID = findViewById(R.id.textViewID);
        textViewApplyFor = findViewById(R.id.textViewApplyFor);
        textViewNRC = findViewById(R.id.textViewNRC);
        textViewDOB = findViewById(R.id.textViewDOB);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewRegisteredSection = findViewById(R.id.textViewRegisteredSection);
        textViewRegisteredDate = findViewById(R.id.textViewRegisteredDate);

        radioButtonMorning = findViewById(R.id.radioButtonMorning);
        radioButtonEvening = findViewById(R.id.radioButtonEvening);
        radioButtonMorning.setChecked(true);

        buttonUpload = findViewById(R.id.buttonUpload);

        String content = getIntent().getStringExtra("content");

        try {
            name = content.split("-")[1];
            id = content.split("-")[0];
            apply_for = content.split("-")[2];
            nrc = content.split("-")[3];
            dob = content.split("-")[4];
            email = content.split("-")[5];
            registeredSection = content.split("-")[6];
            registeredDate = content.split("-")[7];
        } catch (Exception e) {
            Toast.makeText(this, "Wrong information.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        textViewName.setText(name);
        textViewID.setText(id);
        textViewApplyFor.setText(apply_for);
        textViewNRC.setText(nrc);
        textViewDOB.setText(dob);
        textViewEmail.setText(email);
        textViewRegisteredSection.setText(registeredSection);
        textViewRegisteredDate.setText(registeredDate);
        if (registeredSection.contains("Morning")) {
            radioButtonMorning.setChecked(true);
            radioButtonEvening.setChecked(false);
        }
        else {
            radioButtonEvening.setChecked(true);
            radioButtonMorning.setChecked(false);
        }

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButtonMorning.isChecked()) {
                    attendedSection = "Morning";
                }
                else {
                    attendedSection = "Evening";
                }

                UploadData uploadData = ApiServiceFactory.createService(UploadData.class);
                Call<UploadDataResponse> call = uploadData.uploadData(createParamData());
                call.enqueue(new Callback<UploadDataResponse>() {
                    @Override
                    public void onResponse(Call<UploadDataResponse> call, Response<UploadDataResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().getAceplus_status_code().equals("200")) {
                                Toast.makeText(ShowActivity.this, "Upload success.", Toast.LENGTH_SHORT).show();
                            }
                            else if (response.body().getAceplus_status_code().equals("201")) {
                                Toast.makeText(ShowActivity.this, "Already exists.", Toast.LENGTH_SHORT).show();
                            }
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadDataResponse> call, Throwable t) {
                        Toast.makeText(ShowActivity.this, "Upload fail.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String createParamData() {
        String str = "";
        UploadDataRequest uploadDataRequest = new UploadDataRequest();
        uploadDataRequest.setSite_activation_key("1234567");
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data data = new Data();
        data.setName(name);
        data.setId(id);
        data.setApply_for(apply_for);
        data.setNrc(nrc);
        data.setDob(dob);
        data.setEmail(email);
        data.setRegistered_section(registeredSection);
        data.setRegistered_date(registeredDate);
        data.setAttended_section(attendedSection);
        dataArrayList.add(data);
        uploadDataRequest.setData(dataArrayList);
        str = getJsonFromObject(uploadDataRequest);
        Log.i("request_string", str);
        return str;
    }

    private String getJsonFromObject(UploadDataRequest uploadDataRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(uploadDataRequest);
        return jsonString;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
