package com.continuum.currencyratereminder.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;

import currencyratereminder.continuum.com.currencyratereminder.R;

/**
 * Created by AkifHatipoğlu on 26.3.2017.
 */

public class ListItemsHolder extends RecyclerView.ViewHolder {

    public TextView currencyType, currency, amount;

    public ListItemsHolder(View itemView) {
        super(itemView);
        currencyType = (TextView) itemView.findViewById(R.id.textViewCurrencyType);
        currency = (TextView) itemView.findViewById(R.id.textViewCurrency);
        amount = (TextView) itemView.findViewById(R.id.textViewAmount);

    }

    public void bindData(UserCurrencyDAO s) {
        currencyType.setText(String.valueOf(s.getCurrencyType()));
        currency.setText(s.getCurrencyRate());
        amount.setText(s.getAmount());

    }
}
