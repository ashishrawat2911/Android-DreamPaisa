package com.rawat.ashish.game.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.Login;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText loginEditText, passwordEditText;
    Button loginButton;
    TextView loginAlert;
    APIService mAPIService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loginEditText = findViewById(R.id.usernameLogin);
        passwordEditText = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        loginAlert = findViewById(R.id.loginAlert);
        mAPIService = APIClient.getClient().create(APIService.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validate()) {
                    setUserIdSharedPreferences(true, "1");
                    getLoginResponse(getStringFromEditText(loginEditText),
                            getStringFromEditText(passwordEditText));
                    loginAlert.setText(null);
                    loadProgressBar();
                }
            }
        });
    }

    private boolean Validate() {
        if (loginEditText.getText().toString().equals("")) {
            loginAlert.setText(R.string.empty_username_field);
            return false;
        } else if (passwordEditText.getText().toString().equals("")) {
            loginAlert.setText(R.string.empty_password_field);
            return false;
        } else if (loginEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
            loginAlert.setText(R.string.empty_username_and_password);
            return false;
        }
        return true;
    }

    private void getLoginResponse(String username, String password) {
        mAPIService.login(username, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                progress.dismiss();
                checkResultStatus(response.body().getResult(), response.body().getUserId());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(this);
        progress.setMessage("Logging in");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    private void checkResultStatus(String status, String userId) {
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        if (status.equals("Details Incorrect")) {
            loginAlert.setText(R.string.username_or_password_incorrect);
        } else if (status.equals("Correct Details")) {
            startActivity(new Intent(this, MainActivity.class));
            setUserIdSharedPreferences(true, userId);
            setEveryThingToDefault();
            this.finish();
        }
    }

    private void setEveryThingToDefault() {
        setTextIntoEditText(loginEditText);
        setTextIntoEditText(passwordEditText);

    }


    private void setTextIntoEditText(EditText editText) {
        editText.setText(null);
    }

    void setUserIdSharedPreferences(boolean b, String userId) {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(MyConstants.LOGGED_IN, b);
        sharedPreferencesEditor.putString(MyConstants.USER_ID, userId);
        sharedPreferencesEditor.apply();
    }

    private String getStringFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }


}
