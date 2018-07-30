package com.rawat.ashish.game.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.User;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button signInButton;
    AlertDialog alertDialog;
    EditText phoneNumber, firstName, lastName, userName, password, referralCodeEditText;
    APIService mAPIService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    ProgressDialog progress;
    Spinner countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        alertDialog = new AlertDialog.Builder(
                this).create();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        signInButton = findViewById(R.id.signInButton);
        phoneNumber = findViewById(R.id.phoneNumber);
        firstName = findViewById(R.id.firstName);
        password = findViewById(R.id.signInPassword);
        lastName = findViewById(R.id.lastName);
        userName = findViewById(R.id.userName);

        referralCodeEditText = findViewById(R.id.referralCodeEditText);
        mAPIService = APIClient.getClient().create(APIService.class);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendPost(getStringFromEditText(userName),
                        getStringFromEditText(password),
                        getStringFromEditText(firstName),
                        getStringFromEditText(lastName),
                        getStringFromEditText(phoneNumber),
                        getStringFromEditText(referralCodeEditText));
                loadProgressBar();
            }

        });
    }


    private void sendPost(String username,
                          String password,
                          String firstName,
                          String lastName,
                          String phoneNumber,
                          String referralCode) {
        mAPIService.addUser(username,
                password,
                firstName,
                lastName,
                phoneNumber,
                "Mobile", referralCode).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progress.dismiss();
                Toast.makeText(SignUpActivity.this, response.body().getResult() + "\n" + response.body().getUserId(), Toast.LENGTH_SHORT).show();

                checkResultStatus(response.body().getResult(), response.body().getUserId());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progress.dismiss();
            }
        });
    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    private void setEveryThingToDefault() {
        setTextIntoEditText(userName);
        setTextIntoEditText(password);
        setTextIntoEditText(lastName);
        setTextIntoEditText(firstName);
        setTextIntoEditText(phoneNumber);
        setTextIntoEditText(referralCodeEditText);
    }

    private void checkResultStatus(String status, String userId) {
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        switch (status) {
            case "User already exists":
                AlertDialog("User already exists", "Please change the details", R.drawable.ic_action_cancel);
                break;
            case "Username already exists":
                AlertDialog("Username already exists", "Please change the username", R.drawable.ic_action_cancel);
                break;
            case "User Added Successfully":
                startActivity(new Intent(this, MainActivity.class));
                setEveryThingToDefault();
                setUserIdSharedPreferences(userId);
                this.finish();
                break;
        }
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
        progress.setMessage("Logging in");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    private void setTextIntoEditText(EditText editText) {
        editText.setText(null);
    }

    void setUserIdSharedPreferences(String userId) {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(MyConstants.USER_ID, userId);
        sharedPreferencesEditor.apply();

    }



}
