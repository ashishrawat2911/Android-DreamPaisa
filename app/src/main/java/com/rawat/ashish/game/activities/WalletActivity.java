package com.rawat.ashish.game.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletActivity extends AppCompatActivity {
    Button redeemButton;
    ProgressDialog progress;
    APIService apiService;
    SharedPreferences sharedPreferences;
    TextView referCash, selfIncome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);
        redeemButton = findViewById(R.id.redeemButton);
        referCash = findViewById(R.id.referCashTextView);
        selfIncome = findViewById(R.id.selfIncomeTextView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeemIntent = new Intent(WalletActivity.this, WithDrawActivity.class);
                startActivity(redeemIntent);
            }
        });
        apiService = APIClient.getClient().create(APIService.class);
        loadProgressBar();
        loadData();
    }

    private void loadData() {
        apiService.getUser(sharedPreferences.getString(MyConstants.USER_ID, null)).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                referCash.setText(response.body().getResult().getReferCash());
                selfIncome.setText(response.body().getResult().getEarnedCash());
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(WalletActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(this);
        progress.setMessage("Loading data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

}
