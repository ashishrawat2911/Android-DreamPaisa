package com.rawat.ashish.game.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.PaymentRequest;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRequestActivity extends AppCompatActivity {
    APIService mAPIService;
    SharedPreferences sharedPreferences;
    AlertDialog alertDialog;
    ProgressDialog progress;
    boolean startActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);
        alertDialog = new AlertDialog.Builder(
                this).create();
        mAPIService = APIClient.getClient().create(APIService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadProgressBar();
        loadUserDetails();

    }

    public void inAccount(View view) {
        if (startActivity)
            getIntent(InAccountActivity.class);
        else {
            AlertDialog("Insufficient Balance", "You cannot make a payment request");
        }
    }

    public void payTm(View view) {
        if (startActivity) getIntent(PayTmActivity.class);
        else {
            AlertDialog("Insufficient Balance", "You cannot make a payment request");
        }
    }

    public void payPal(View view) {
        if (startActivity) getIntent(PayPalActivity.class);
        else {
            AlertDialog("Insufficient Balance", "You cannot make a payment request");
        }
    }

    private void getIntent(Class<?> cls) {
        startActivity(new Intent(PaymentRequestActivity.this, cls));
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setIcon(R.drawable.back_icon)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PaymentRequestActivity.this.finish();
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

        mAPIService.getUser(sharedPreferences.getString(MyConstants.USER_ID, null)).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                String netCashString = response.body().getResult().getNetBalance();
                String stage = response.body().getResult().getStage();
                progress.dismiss();
                if (stage.equals("1") && netCashString.equals("20")) {
                    startActivity = true;
                } else if (stage.equals("2") && netCashString.equals("200")) {
                    startActivity = true;
                } else if (stage.equals("3") && netCashString.equals("500")) {
                    startActivity = true;
                } else if (stage.equals("4") && netCashString.equals("1000")) {
                    startActivity = true;
                } else {
                    AlertDialog("Insufficient Balance", "You cannot make a payment request");
                }

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(PaymentRequestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void AlertDialog(String title, String message) {
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_action_cancel);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
            }
        });
        alertDialog.show();
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(this);
        progress.setMessage("Loading data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

    }
}
