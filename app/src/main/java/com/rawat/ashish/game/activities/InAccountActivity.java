package com.rawat.ashish.game.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
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

public class InAccountActivity extends AppCompatActivity {
    EditText amount, holderName, bankAccountNo, iFSCCode, bankName;
    Button sendRequest;
    AlertDialog alertDialog;
    ProgressDialog progress;
    SharedPreferences sharedPreferences;
    APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_account);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        alertDialog = new AlertDialog.Builder(
                this).create();
        mAPIService = APIClient.getClient().create(APIService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        amount = findViewById(R.id.inAccountAmount);
        holderName = findViewById(R.id.inAccountHolderName);
        bankAccountNo = findViewById(R.id.inAccountAccountNo);
        iFSCCode = findViewById(R.id.inAccountIFSCCode);
        bankName = findViewById(R.id.inAccountBankName);
        sendRequest = findViewById(R.id.inAccountButton);
    }

    public void sendRequestInAccount(View view) {
        sendPost(sharedPreferences.getString(MyConstants.USER_ID, ""),
                getStringFromEditText(holderName),
                getStringFromEditText(bankAccountNo),
                getStringFromEditText(iFSCCode),
                getStringFromEditText(bankName));
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
        setTextIntoEditText(holderName);
        setTextIntoEditText(iFSCCode);
        setTextIntoEditText(bankAccountNo);
        setTextIntoEditText(bankName);
    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    private void setTextIntoEditText(EditText editText) {
        editText.setText(null);
    }

    private void sendPost(String userId,
                          String holderName,
                          String accountNumber,
                          String ifsc,
                          String bankName
    ) {
        mAPIService.inAccountPaymentRequest(userId,
                holderName,
                accountNumber,
                ifsc,
                bankName).enqueue(new Callback<PaymentRequest>() {
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
