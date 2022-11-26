package com.vrctech.aproundup.services;

import android.util.Log;

import com.vrctech.aproundup.Globals;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestClient {

    public static class HttpClient {
        private static OkHttpClient single_instance = null;
        OkHttpClient client;

        private HttpClient() {
            client = new OkHttpClient();
        }

        static OkHttpClient getClient() {
            if (single_instance == null)
                single_instance = new OkHttpClient();
            return single_instance;
        }
    }

    private static Request getRequest(String url){
        Log.d("SERVICE_REQUEST", url);
        return new Request.Builder()
                .url(url)
                .build();
    }

    private static JSONObject getJSONObjectFromResponse(Response response){
        try {
            JSONObject result = new JSONObject(Objects.requireNonNull(response.body()).string());
            Log.d("SERVICE_RESPONSE", result.toString());
            return result;
        } catch (JSONException | IOException | NullPointerException e) {
            return new JSONObject();
        }
    }

    private static JSONObject getJSONArrayFromResponse(Response response){
        try{
            JSONArray array = new JSONArray(Objects.requireNonNull(response.body()).string());
            JSONObject object = new JSONObject();
            object.put("stateWiseData", array);
            return object;
        } catch (JSONException | IOException | NullPointerException e) {
            return new JSONObject();
        }
    }

    public static void getTotalCases(RestCallback callback){
        HttpClient.getClient().newCall(getRequest(Globals.TOTAL_CASES_URL)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.result(getJSONObjectFromResponse(response));
            }
        });
    }

    public static void getStateWiseCases(RestCallback callback){
        HttpClient.getClient().newCall(getRequest(Globals.STATES_DATA_URL)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.result(getJSONArrayFromResponse(response));
            }
        });
    }

    public static void getAvailableDateForEpaper(String code, RestCallback callback){
        HttpClient.getClient().newCall(getRequest(Globals.PAPER_DATES + code)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.result(getJSONObjectFromResponse(response));
            }
        });
    }

    public static void getEPaperKeys(String paperCode, RestCallback callback){
        HttpClient.getClient().newCall(getRequest(Globals.PAPER_KEYS + paperCode)).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                callback.result(getJSONObjectFromResponse(response));
            }
        });
    }

}
