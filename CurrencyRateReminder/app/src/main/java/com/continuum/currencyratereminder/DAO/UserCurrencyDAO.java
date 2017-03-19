package com.continuum.currencyratereminder.DAO;

/**
 * Created by AkifHatipoğlu on 19.3.2017.
 */

public class UserCurrencyDAO {

    private String userId;
    private String userKey;
    private String CurrencyType;
    private String CurrencyRate;
    private String Amount;

    public UserCurrencyDAO(String userId, String userKey, String currencyType, String currencyRate, String amount) {
        this.userId = userId;
        this.userKey = userKey;
        CurrencyType = currencyType;
        CurrencyRate = currencyRate;
        Amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getCurrencyType() {
        return CurrencyType;
    }

    public void setCurrencyType(String currencyType) {
        CurrencyType = currencyType;
    }

    public String getCurrencyRate() {
        return CurrencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        CurrencyRate = currencyRate;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "UserCurrencyDAO{" +
                "userId='" + userId + '\'' +
                ", userKey='" + userKey + '\'' +
                ", CurrencyType='" + CurrencyType + '\'' +
                ", CurrencyRate='" + CurrencyRate + '\'' +
                ", Amount='" + Amount + '\'' +
                '}';
    }
}
