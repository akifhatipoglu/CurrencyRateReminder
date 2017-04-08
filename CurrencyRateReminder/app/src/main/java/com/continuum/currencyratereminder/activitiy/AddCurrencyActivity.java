package com.continuum.currencyratereminder.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;
import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;
import com.continuum.currencyratereminder.helper.RetroJsonClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;

import currencyratereminder.continuum.com.currencyratereminder.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCurrencyActivity extends AppCompatActivity {

    private static final String TAG = AddCurrencyActivity.class.getSimpleName();
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private Button btnAdd;
    private EditText edtCurrency, edtAmount;
    private MaterialSpinner spinnerCurrencyType;
    private ProgressBar progressBarForSpinner;
    private static List<String> currencyTypeList = Arrays.asList("USD", "EUR");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_currency);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        edtCurrency = (EditText) findViewById(R.id.editTextCurrency);
        edtAmount = (EditText) findViewById(R.id.editTextAmount);
        spinnerCurrencyType = (MaterialSpinner) findViewById(R.id.spinnerCurrencyType);
        spinnerCurrencyType.setItems(currencyTypeList);
        progressBarForSpinner = (ProgressBar) findViewById(R.id.progressBarForSpinner);
        progressBarForSpinner.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtCurrency.getText().toString()) || TextUtils.isEmpty(edtAmount.getText().toString())) {
                    Snackbar.make(v, R.string.error_message_editText_empty, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                addFirebase();
                startActivity(new Intent(AddCurrencyActivity.this, MainActivity.class));
                finish();
            }
        });
        spinnerCurrencyType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                fetchLatestCurrency(item);
            }
        });
        fetchLatestCurrency(currencyTypeList.get(0));
    }

    private void addFirebase() {
        Log.d(TAG, "add Firebase:" + mAuth.getCurrentUser().getUid());
        String key = mDatabase.child(mAuth.getCurrentUser().getUid()).push().getKey();
        //TODO: UserCurrencyDoa 'dan user bilgileri çıkartılabilir.
        UserCurrencyDAO userCurrency = new UserCurrencyDAO(mAuth.getCurrentUser().getUid(), key, "USD", edtCurrency.getText().toString(), edtAmount.getText().toString());
        mDatabase.child(mAuth.getCurrentUser().getUid()).child(key).setValue(userCurrency);
    }

    private void fetchLatestCurrency(String item) {
        progressBarForSpinner.setVisibility(View.VISIBLE);
        RetroJsonClient.getLatest(item).enqueue(new Callback<CurrenciesJsonDao>() {
            @Override
            public void onResponse(Call<CurrenciesJsonDao> call, Response<CurrenciesJsonDao> response) {
                Log.d(TAG, "Response" + response.isSuccessful() + "-" + response.message());
                if (response.isSuccessful()) {
                    Log.d(TAG, "call" + "getLatest" + "Success" + response.body().toString());
                    //currenciesJsonDaoList.add(response.body());
                    edtCurrency.setText(response.body().getBuying().toString());
                    progressBarForSpinner.setVisibility(View.GONE);

                } else {
                    Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString());
                    progressBarForSpinner.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<CurrenciesJsonDao> call, Throwable t) {
                Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString() + "-" + t.toString());
                progressBarForSpinner.setVisibility(View.GONE);
            }
        });
    }
}


