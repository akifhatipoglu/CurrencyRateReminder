package com.continuum.currencyratereminder.helper;

import android.util.Log;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AkifHatipoğlu on 27.3.2017.
 */

public class RetroJsonClient {

    private static final String TAG = RetroJsonClient.class.getSimpleName();
    /********
     * URLS
     *******/
    private static final String ROOT_URL = "http://www.doviz.com/api/v1/currencies/";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static RetroJsonApiService getApiService() {
        return getRetrofitInstance().create(RetroJsonApiService.class);
    }

    public static ArrayList<CurrenciesJsonDao> getLatest() {
        final ArrayList<CurrenciesJsonDao> currenciesJsonDaoList = new ArrayList<>();

        RetroJsonApiService api = RetroJsonClient.getApiService();

        Call<CurrenciesJsonDao> call = api.getUSDCurrency();
        call.enqueue(new Callback<CurrenciesJsonDao>() {
            @Override
            public void onResponse(Call<CurrenciesJsonDao> call, Response<CurrenciesJsonDao> response) {
                Log.d(TAG, "Response" + response.isSuccessful() + "-" + response.message());
                if (response.isSuccessful()) {
                    Log.d(TAG, "call" + "getLatest" + "Success" + response.body().toString());
                    currenciesJsonDaoList.add(response.body());
                } else {
                    Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString());
                }
            }

            @Override
            public void onFailure(Call<CurrenciesJsonDao> call, Throwable t) {
                Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString() + "-" + t.toString());
            }
        });

        return currenciesJsonDaoList;
    }
}
