package com.tw.sportstest.retrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("nzin01312019187360.json")
    Call<JSONObject> getMatchDetails();
}
