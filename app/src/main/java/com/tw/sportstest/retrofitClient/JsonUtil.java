package com.tw.sportstest.retrofitClient;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class JsonUtil {
    private static final String TAG = "JsonUtil";
    public static String STATUS = "status";
    public static String RESULT_CODE = "result_code";
    public static String MESSAGE = "message";

    public static JSONObject mainjson(Response<ResponseBody> response) {
        JSONObject jsonObject = null;
        try {
            String resp = response.body().string().trim();
            Log.e(TAG, "mainjson: " + resp);
            jsonObject = new JSONObject(resp);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject mainjson(String response) {
        JSONObject jsonObject = null;
        try {
            Log.e(TAG, "mainjson: " + response);
            jsonObject = new JSONObject(response);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String resp(Response<ResponseBody> response) {
        try {
            String resp = response.body().string().trim();
            Log.e(TAG, "resp: " + resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> resp(String response) {
        List<String> dataList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            Iterator iteratorObj = jsonResponse.keys();
            while (iteratorObj.hasNext()) {
                String key = (String) iteratorObj.next();
                String data = jsonResponse.getString(key);
                dataList.add(data);
                Log.e(TAG, "resp: " + data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
