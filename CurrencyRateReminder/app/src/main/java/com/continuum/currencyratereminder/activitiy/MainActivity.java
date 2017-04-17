package com.continuum.currencyratereminder.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.continuum.currencyratereminder.DAO.CurrenciesJsonDao;
import com.continuum.currencyratereminder.DAO.UserCurrencyDAO;
import com.continuum.currencyratereminder.helper.ListItemsAdapter;
import com.continuum.currencyratereminder.helper.RetroJsonClient;
import com.continuum.currencyratereminder.helper.SimpleDividerItemDecoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import currencyratereminder.continuum.com.currencyratereminder.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private RecyclerView mListItemsRecyclerView;
    private ListItemsAdapter mAdapter;
    private ArrayList<UserCurrencyDAO> userCurrencyList;
    private HashMap<String, CurrenciesJsonDao> currenciesJsonDaoList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userCurrencyList = new ArrayList<UserCurrencyDAO>();
        currenciesJsonDaoList = new HashMap<String, CurrenciesJsonDao>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mAuth.getCurrentUser() == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInAnonymously", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        mListItemsRecyclerView = (RecyclerView) findViewById(R.id.listItem_recycler_view);
        mListItemsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        mListItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "add button:" + mAuth.getCurrentUser().getUid());
                startActivity(new Intent(MainActivity.this, AddCurrencyActivity.class));
                finish();
            }
        });

        mDatabase.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w(TAG, "Success read value.");
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        userCurrencyList = new ArrayList<>();
        currenciesJsonDaoList = new HashMap<String, CurrenciesJsonDao>();
        Iterable<DataSnapshot> child = dataSnapshot.getChildren();
        for (DataSnapshot item : child) {
            userCurrencyList.add(item.getValue(UserCurrencyDAO.class));
        }
        fetchLatestCurrency();
        updateUI("fetchData");
    }

    private void updateUI(String stackTraceForLog) {
        Log.d(TAG, "updateUI" + " - " + stackTraceForLog);
        mAdapter = new ListItemsAdapter(MainActivity.this, userCurrencyList, currenciesJsonDaoList);
        mListItemsRecyclerView.setAdapter(mAdapter);
    }

    private void fetchLatestCurrency() {
        swipeRefreshLayout.setRefreshing(true);
        RetroJsonClient.getLatest("USD").enqueue(new Callback<CurrenciesJsonDao>() {
            @Override
            public void onResponse(Call<CurrenciesJsonDao> call, Response<CurrenciesJsonDao> response) {
                Log.d(TAG, "Response" + response.isSuccessful() + "-" + response.message());
                if (response.isSuccessful()) {
                    Log.d(TAG, "call" + "getLatest" + "Success" + response.body().toString());
                    currenciesJsonDaoList.put(response.body().getCode(), response.body());
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    updateUI("Callback");

                } else {
                    Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString());
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<CurrenciesJsonDao> call, Throwable t) {
                Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString() + "-" + t.toString());
            }
        });
        RetroJsonClient.getLatest("EUR").enqueue(new Callback<CurrenciesJsonDao>() {
            @Override
            public void onResponse(Call<CurrenciesJsonDao> call, Response<CurrenciesJsonDao> response) {
                Log.d(TAG, "Response" + response.isSuccessful() + "-" + response.message());
                if (response.isSuccessful()) {
                    Log.d(TAG, "call" + "getLatest" + "Success" + response.body().toString());
                    currenciesJsonDaoList.put(response.body().getCode(), response.body());
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    updateUI("Callback");

                } else {
                    Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString());
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<CurrenciesJsonDao> call, Throwable t) {
                Log.d(TAG, "call" + "getLatest" + "FAIL" + call.toString() + "-" + t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onRefresh() {
        fetchLatestCurrency();
        updateUI("onRefresh");
    }
}
