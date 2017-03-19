package com.continuum.currencyratereminder;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import currencyratereminder.continuum.com.currencyratereminder.R;

public class AddCurrencyActivity extends AppCompatActivity {

    private static final String TAG = "AddCurrencyActivity";
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private Button btnAdd;
    private EditText edtCurrency,edtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_currency);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        edtCurrency = (EditText) findViewById(R.id.editTextCurrency);
        edtAmount = (EditText) findViewById(R.id.editTextAmount);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtCurrency.getText()) || TextUtils.isEmpty(edtAmount.getText())){
                    Snackbar.make(v, R.string.error_message_editText_empty , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                addFirebase();
            }
        });

    }

    private void addFirebase() {
        Log.d(TAG, "add Firebase:" + mAuth.getCurrentUser().getUid());
        String key = mDatabase.child(mAuth.getCurrentUser().getUid()).push().getKey();
        mDatabase.child(mAuth.getCurrentUser().getUid()).child(key).child("CurrencyType").setValue("USD");
        mDatabase.child(mAuth.getCurrentUser().getUid()).child(key).child("Currency").setValue("3.6");
        mDatabase.child(mAuth.getCurrentUser().getUid()).child(key).child("Amount").setValue("50000");
    }
}


