package com.continuum.currencyratereminder.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;
import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;

import java.util.List;

import currencyratereminder.continuum.com.currencyratereminder.R;

/**
 * Created by AkifHatipoğlu on 26.3.2017.
 */

public class ListItemsHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    public TextView currencyType, currency, amount, rate;

    public ListItemsHolder(final View itemView) {
        super(itemView);
        currencyType = (TextView) itemView.findViewById(R.id.textViewCurrencyType);
        currency = (TextView) itemView.findViewById(R.id.textViewCurrency);
        amount = (TextView) itemView.findViewById(R.id.textViewAmount);
        rate = (TextView) itemView.findViewById(R.id.textViewRate);
        itemView.setOnLongClickListener(this);
    }

    public void bindData(UserCurrencyDAO s, List<CurrenciesJsonDao> item) {
        currencyType.setText(String.valueOf(s.getCurrencyType()));
        currency.setText(s.getCurrencyRate());
        amount.setText(s.getAmount());
        if (item.size() > 0)
            rate.setText(item.get(0).getBuying().toString() + "-" + item.get(0).getSelling().toString());
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(itemView.getContext(), currencyType.getText() + "-" + currency.getText() + "-" + amount.getText(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
