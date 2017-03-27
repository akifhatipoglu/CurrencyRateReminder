package com.continuum.currencyratereminder.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AkifHatipoğlu on 27.3.2017.
 */

public class RetroJsonClient {
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
}
