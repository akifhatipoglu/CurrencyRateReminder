package com.continuum.currencyratereminder.helper;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;

import retrofit2.Call;
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

    public static  Call<CurrenciesJsonDao> getLatest() {
        RetroJsonApiService api = RetroJsonClient.getApiService();
        Call<CurrenciesJsonDao> call = api.getUSDCurrency();
        return call;
    }
    /**
     *
     * */
}
