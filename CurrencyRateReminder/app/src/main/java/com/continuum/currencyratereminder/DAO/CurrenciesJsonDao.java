package com.continuum.currencyratereminder.DAO;

/**
 * Created by AkifHatipoğlu on 27.3.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrenciesJsonDao {

    @SerializedName("selling")
    @Expose
    private Double selling;
    @SerializedName("update_date")
    @Expose
    private Long updateDate;
    @SerializedName("currency")
    @Expose
    private Long currency;
    @SerializedName("buying")
    @Expose
    private Double buying;
    @SerializedName("change_rate")
    @Expose
    private Double changeRate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     *
     */
    public CurrenciesJsonDao() {
    }

    /**
     *
     * @param selling
     * @param name
     * @param buying
     * @param changeRate
     * @param code
     * @param fullName
     * @param updateDate
     * @param currency
     */
    public CurrenciesJsonDao(Double selling, Long updateDate, Long currency, Double buying, Double changeRate, String name, String fullName, String code) {
        super();
        this.selling = selling;
        this.updateDate = updateDate;
        this.currency = currency;
        this.buying = buying;
        this.changeRate = changeRate;
        this.name = name;
        this.fullName = fullName;
        this.code = code;
    }

    public Double getSelling() {
        return selling;
    }

    public void setSelling(Double selling) {
        this.selling = selling;
    }

    public CurrenciesJsonDao withSelling(Double selling) {
        this.selling = selling;
        return this;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public CurrenciesJsonDao withUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public CurrenciesJsonDao withCurrency(Long currency) {
        this.currency = currency;
        return this;
    }

    public Double getBuying() {
        return buying;
    }

    public void setBuying(Double buying) {
        this.buying = buying;
    }

    public CurrenciesJsonDao withBuying(Double buying) {
        this.buying = buying;
        return this;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public CurrenciesJsonDao withChangeRate(Double changeRate) {
        this.changeRate = changeRate;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrenciesJsonDao withName(String name) {
        this.name = name;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public CurrenciesJsonDao withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CurrenciesJsonDao withCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "CurrenciesJsonDao{" +
                "selling=" + selling +
                ", updateDate=" + updateDate +
                ", currency=" + currency +
                ", buying=" + buying +
                ", changeRate=" + changeRate +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}