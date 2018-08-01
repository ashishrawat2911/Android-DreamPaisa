package com.rawat.ashish.game.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.UpdatedBalance;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletActivity extends AppCompatActivity {
    Button redeemButton;
    ProgressDialog progress;
    APIService apiService;
    SharedPreferences sharedPreferences;
    TextView referCash, selfIncome, stages, redeemableAmount;
    BigDecimal Refern = null, Earn = null, Selfinc = null, redeemamt = null;
    Integer Stage = 1, go = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);
        redeemButton = findViewById(R.id.redeemButton);
        referCash = findViewById(R.id.referCashTextView);
        selfIncome = findViewById(R.id.selfIncomeTextView);
        redeemableAmount = findViewById(R.id.redeemableAmountTextView);
        stages = findViewById(R.id.stageTextView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        redeemButton.setEnabled(false);
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeemIntent = new Intent(WalletActivity.this, PaymentRequestActivity.class);
                startActivity(redeemIntent);
            }
        });
        apiService = APIClient.getClient().create(APIService.class);
        loadProgressBar();
        loadUserDetails();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setIcon(R.drawable.back_icon)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WalletActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    private void loadUserDetails() {
        apiService.getUser(sharedPreferences.getString(MyConstants.USER_ID, null)).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                Earn = new BigDecimal(response.body().getResult().getEarnedCash());
                Refern = new BigDecimal(response.body().getResult().getReferCash());
                Selfinc = new BigDecimal(response.body().getResult().getNetBalance());
                Stage = Integer.parseInt(response.body().getResult().getStage());

                if ((Earn != null) && (Refern != null) && (Selfinc != null)) {


                    if ((Refern.compareTo(BigDecimal.ZERO) > 0) && (Earn.compareTo(BigDecimal.ZERO)) > 0) {
                        if (Refern.compareTo(Earn) >= 0) {//edit refer=refer-earn and earn =0 and netamount+=earn
                            Refern = (Refern.subtract(Earn));
                            Selfinc = Selfinc.add(Earn);
                            Earn = BigDecimal.ZERO;
                            updateCash(Refern.toString(), Earn.toString(), Selfinc.toString());
                            loadUserDetails();
                            Log.d("Wallet Activity", "onDataAvailable: " + Refern + "  " + Earn + "  " + Selfinc);
                        } else if (Refern.compareTo(Earn) < 0) {//edit refer
                            Selfinc = Selfinc.add(Refern);
                            Refern = BigDecimal.ZERO;
                            Earn = BigDecimal.ZERO;
                            updateCash(Refern.toString(), Earn.toString(), Selfinc.toString());
                            loadUserDetails();
                            Log.d("Wallet Activity", "onDataAvailable: " + Refern + "  " + Earn + "  " + Selfinc);
                            //send editted parameters

                        }


                    } else {

                        referCash.setText("Rs." + response.body().getResult().getReferCash());
                        selfIncome.setText("Rs." + response.body().getResult().getNetBalance());
                        stages.setText("Stage:" + response.body().getResult().getStage());

                        if (Stage == 1 && Selfinc.compareTo(new BigDecimal(20)) >= 0) {
                            redeemableAmount.setText("Rs.20");
                            redeemamt = new BigDecimal("20");
                            go = 1;
                        } else if (Stage == 2 && Selfinc.compareTo(new BigDecimal(200)) >= 0) {
                            redeemableAmount.setText("Rs.100");
                            go = 1;
                            redeemamt = new BigDecimal("100");
                        } else if (Stage == 3 && Selfinc.compareTo(new BigDecimal(500)) >= 0) {
                            redeemableAmount.setText("Rs.200");
                            go = 1;
                            redeemamt = new BigDecimal("200");
                        } else if (Stage == 4 && Selfinc.compareTo(new BigDecimal(1000)) >= 0) {
                            redeemableAmount.setText("Rs.300");
                            go = 1;
                            redeemamt = new BigDecimal("300");
                        } else redeemableAmount.setText("Rs 0");
                        if (go == 1) {
                            redeemButton.setEnabled(true);
                        }

                        progress.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(WalletActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCash(String referCash, String earnedCash, String netBalance) {


        apiService.updateCash(sharedPreferences.getString(MyConstants.USER_ID, null), referCash, earnedCash, netBalance).enqueue(new Callback<UpdatedBalance>() {
            @Override
            public void onResponse(Call<UpdatedBalance> call, Response<UpdatedBalance> response) {
                Toast.makeText(WalletActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UpdatedBalance> call, Throwable t) {
                Toast.makeText(WalletActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(this);
        progress.setMessage("Loading data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        progress.show();

    }

}
