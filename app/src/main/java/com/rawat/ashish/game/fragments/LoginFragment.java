package com.rawat.ashish.game.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.activities.MainActivity;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.Login;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    EditText loginEditText, passwordEditText;
    Button loginButton;
    TextView loginAlert;
    APIService mAPIService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    private ProgressDialog progress;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginEditText = rootView.findViewById(R.id.usernameLogin);
        passwordEditText = rootView.findViewById(R.id.passwordLogin);
        loginButton = rootView.findViewById(R.id.loginButton);
        loginAlert = rootView.findViewById(R.id.loginAlert);
        mAPIService = APIClient.getClient().create(APIService.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validate()) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    setUserIdSharedPreferences(true, "1");
                   /* getLoginResponse(getStringFromEditText(loginEditText),
                            getStringFromEditText(passwordEditText));
                    loginAlert.setText(null);
                    loadProgressBar();
                */}
            }
        });
        return rootView;
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
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Logging in");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    private void checkResultStatus(String status, String userId) {
        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
        if (status.equals("Details Incorrect")) {
            loginAlert.setText(R.string.username_or_password_incorrect);
        } else if (status.equals("Correct Details")) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            setUserIdSharedPreferences(true, userId);
            setEveryThingToDefault();
            getActivity().finish();
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
