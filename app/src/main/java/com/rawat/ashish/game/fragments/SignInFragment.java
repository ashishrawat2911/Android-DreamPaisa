package com.rawat.ashish.game.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.activities.MainActivity;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.User;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    Button signInButton;
    AlertDialog alertDialog;
    EditText phoneNumber, firstName, lastName, userName, password, referralCodeEditText;
    APIService mAPIService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    ProgressDialog progress;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        alertDialog = new AlertDialog.Builder(
                getActivity()).create();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        signInButton = rootView.findViewById(R.id.signInButton);
        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        firstName = rootView.findViewById(R.id.firstName);
        password = rootView.findViewById(R.id.signInPassword);
        lastName = rootView.findViewById(R.id.lastName);
        userName = rootView.findViewById(R.id.userName);
        referralCodeEditText = rootView.findViewById(R.id.referralCodeEditText);
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

        return rootView;
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
                Toast.makeText(getActivity(), response.body().getResult() + "\n" + response.body().getUserId(), Toast.LENGTH_SHORT).show();

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
        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
        switch (status) {
            case "User already exists":
                AlertDialog("User already exists", "Please change the details", R.drawable.ic_action_cancel);
                break;
            case "Username already exists":
                AlertDialog("Username already exists", "Please change the username", R.drawable.ic_action_cancel);
                break;
            case "User Added Successfully":
                startActivity(new Intent(getActivity(), MainActivity.class));
                setEveryThingToDefault();
                setUserIdSharedPreferences(userId);
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
        progress = new ProgressDialog(getActivity());
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


