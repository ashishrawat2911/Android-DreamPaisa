package com.rawat.ashish.game.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button signInButton;
    AlertDialog alertDialog;
    EditText phoneNumber, firstName, lastName, password, city, referralCodeEditText;
    APIService mAPIService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    ProgressDialog progress;
    Spinner countrySpinner;
    private static final String DEFAULT_LOCAL = "India";
    String spinnerText;
    String userName, countryName, countryCode;

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
        countrySpinner = findViewById(R.id.countryCodeSpinnerSignUp);
        city = findViewById(R.id.citySignUp);
        referralCodeEditText = findViewById(R.id.referralCodeEditText);
        mAPIService = APIClient.getClient().create(APIService.class);
        setUpSpinner();
        setUpUsername();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, userName + "\n" + countryName + "\n" + countryCode, Toast.LENGTH_SHORT).show();

                sendPost(getStringFromEditText(password),
                        getStringFromEditText(firstName),
                        getStringFromEditText(lastName),
                        getStringFromEditText(phoneNumber),
                        getStringFromEditText(city),
                        getStringFromEditText(referralCodeEditText));
                loadProgressBar();
            }

        });
    }

    private void setUpSpinner() {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.layout_spinner, countries);
        countrySpinner.setAdapter(adapter);
        countrySpinner.setSelection(adapter.getPosition(DEFAULT_LOCAL));
        countryName = countrySpinner.getSelectedItem().toString();
        Locale locale1 = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        countryCode = locale1.getCountry();

    }

    private void setUpUsername() {
        userName = getStringFromEditText(firstName) + getStringFromEditText(lastName) +
                DateFormat.getDateTimeInstance().format(new Date());

    }

    private void sendPost(String password,
                          String firstName,
                          String lastName,
                          String phoneNumber,
                          String city,
                          String referralCode) {
        mAPIService.addUser(userName,
                password,
                firstName,
                lastName,
                phoneNumber,
                "Mobile", city, countryName, countryCode, referralCode).enqueue(new Callback<User>() {
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
        setTextIntoEditText(city);
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
            default:
                AlertDialog("Error", status, R.drawable.ic_action_cancel);
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
