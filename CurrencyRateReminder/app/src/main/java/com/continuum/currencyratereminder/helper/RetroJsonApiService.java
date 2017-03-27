package com.continuum.currencyratereminder.helper;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by AkifHatipoğlu on 27.3.2017.
 */

public interface RetroJsonApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("all/latest")
    Call<List<CurrenciesJsonDao>> getAllCurrencies();

    @GET("USD/latest")
    Call<CurrenciesJsonDao> getUSDCurrency();

    @GET("EUR/latest")
    Call<CurrenciesJsonDao> getEURCurrency();

}
