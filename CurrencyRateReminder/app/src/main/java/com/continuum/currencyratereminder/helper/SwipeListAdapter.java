package com.continuum.currencyratereminder.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;

import java.util.List;

import currencyratereminder.continuum.com.currencyratereminder.R;

/**
 * Created by AkifHatipoğlu on 21.3.2017.
 */

public class SwipeListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<UserCurrencyDAO> userCurrencyDAOList;

    public SwipeListAdapter(Activity activity, List<UserCurrencyDAO> userCurrencyDAOList) {
        this.activity = activity;
        this.userCurrencyDAOList = userCurrencyDAOList;
    }

    @Override
    public int getCount() {
        return userCurrencyDAOList.size();
    }

    @Override
    public Object getItem(int position) {
        return userCurrencyDAOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        TextView currencyType = (TextView) convertView.findViewById(R.id.textViewCurrencyType);
        TextView currency = (TextView) convertView.findViewById(R.id.textViewCurrency);
        TextView amount = (TextView) convertView.findViewById(R.id.textViewAmount);

        currencyType.setText(String.valueOf(userCurrencyDAOList.get(position).getCurrencyType()));
        currency.setText(userCurrencyDAOList.get(position).getCurrencyRate());
        amount.setText(userCurrencyDAOList.get(position).getAmount());

        return convertView;
    }
}
