package com.continuum.currencyratereminder.helper;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;
import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;

import java.util.ArrayList;
import java.util.List;

import currencyratereminder.continuum.com.currencyratereminder.R;

/**
 * Created by AkifHatipoğlu on 26.3.2017.
 */

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsHolder> {
    private ArrayList<UserCurrencyDAO> mListItems;
    private List<CurrenciesJsonDao> mCurrenciesJsonItems;
    private Activity mActivity;

    public ListItemsAdapter(Activity activity, ArrayList<UserCurrencyDAO> ListItems) {
        mListItems = ListItems;
        mActivity = activity;
        mCurrenciesJsonItems = RetroJsonClient.getLatest();
    }


    @Override
    public ListItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View view = layoutInflater.inflate(R.layout.list_row, parent, false);
        return new ListItemsHolder(view);

    }

    @Override
    public void onBindViewHolder(ListItemsHolder holder, int position) {
        UserCurrencyDAO s = mListItems.get(position);
        holder.bindData(s, mCurrenciesJsonItems);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();

    }
}
