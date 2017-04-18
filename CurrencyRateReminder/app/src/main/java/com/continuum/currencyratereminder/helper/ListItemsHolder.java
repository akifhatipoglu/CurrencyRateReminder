package com.continuum.currencyratereminder.helper;

import android.graphics.Color;
import android.renderscript.Double2;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;
import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;

import org.w3c.dom.Text;

import currencyratereminder.continuum.com.currencyratereminder.R;

/**
 * Created by AkifHatipoğlu on 26.3.2017.
 */

public class ListItemsHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    public TextView currencyType, currency, amount, rate, rateAmount, buying;

    public ListItemsHolder(final View itemView) {
        super(itemView);
        currencyType = (TextView) itemView.findViewById(R.id.textViewCurrencyType);
        currency = (TextView) itemView.findViewById(R.id.textViewCurrency);
        amount = (TextView) itemView.findViewById(R.id.textViewAmount);
        rate = (TextView) itemView.findViewById(R.id.textViewRate);
        buying = (TextView) itemView.findViewById(R.id.textViewBuying);
        rateAmount = (TextView) itemView.findViewById(R.id.textViewRateAmount);
        itemView.setOnLongClickListener(this);
    }

    public void bindData(UserCurrencyDAO userCurrencyDAO, CurrenciesJsonDao currenciesJsonDao) {
        currencyType.setText(String.valueOf(userCurrencyDAO.getCurrencyType()));
        currency.setText(userCurrencyDAO.getCurrencyRate());
        amount.setText(userCurrencyDAO.getAmount());
        if (currenciesJsonDao != null && currenciesJsonDao.getBuying() != null) {
            buying.setText(currenciesJsonDao.getBuying().toString());

            Double gap = currenciesJsonDao.getBuying() - Double.parseDouble(userCurrencyDAO.getCurrencyRate());
            Double result = Double.parseDouble(userCurrencyDAO.getAmount()) * gap;
            String resultString = result.toString().length() > 6 ? result.toString().substring(0, 6) : result.toString();
            rate.setText(resultString + " TL");

            Double percent = (result * 100) / Double.parseDouble(userCurrencyDAO.getAmount());
            String percentString = percent.toString().length() > 5 ? percent.toString().substring(0, 5) : percent.toString();
            rateAmount.setText("%" + percentString);
            if (result > 0) {
                rate.setBackgroundColor(Color.GREEN);
                rateAmount.setBackgroundColor(Color.GREEN);
            } else if (result < 0) {
                rate.setBackgroundColor(Color.RED);
                rateAmount.setBackgroundColor(Color.RED);
            } else {
                rate.setBackgroundColor(Color.YELLOW);
                rateAmount.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(itemView.getContext(), rate.getText(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
