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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.PaymentRequest;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayTmActivity extends AppCompatActivity {
    EditText amount, paytmNo;
    Button sendRequest;
    AlertDialog alertDialog;
    ProgressDialog progress;
    SharedPreferences sharedPreferences;
    APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tm);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        alertDialog = new AlertDialog.Builder(
                this).create();
        mAPIService = APIClient.getClient().create(APIService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        amount = findViewById(R.id.payTmAmount);
        paytmNo = findViewById(R.id.payTmNo);
        sendRequest = findViewById(R.id.payTmButton);
    }

    public void sendRequestPaytm(View view) {
        sendPost(sharedPreferences.getString(MyConstants.USER_ID, ""),
                getStringFromEditText(paytmNo)
                );
        loadProgressBar();
    }

    void AlertDialog(String title, String message, int icon) {
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(icon);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
            }
        });
        alertDialog.show();
    }

    void loadProgressBar() {
        progress = new ProgressDialog(this);
        progress.setMessage("Requesting");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    private void checkResultStatus(String status) {
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        switch (status) {

            default:
                AlertDialog(status, "Something Wrong", R.drawable.ic_action_cancel);
                break;
            case "Payment Request Added Successfully":
                startActivity(new Intent(this, MainActivity.class));
                setEveryThingToDefault();
                this.finish();
                break;
        }
    }

    private void setEveryThingToDefault() {
        setTextIntoEditText(amount);
        setTextIntoEditText(paytmNo);

    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    private void setTextIntoEditText(EditText editText) {
        editText.setText(null);
    }

    private void sendPost(String userId,
                          String paytmNo
    ) {
        mAPIService.payTmPaymentRequest(userId,
                paytmNo).enqueue(new Callback<PaymentRequest>() {
            @Override
            public void onResponse(Call<PaymentRequest> call, Response<PaymentRequest> response) {
                progress.dismiss();
                checkResultStatus(response.body().getResult());


            }

            @Override
            public void onFailure(Call<PaymentRequest> call, Throwable t) {
                progress.dismiss();
            }
        });
    }

}
