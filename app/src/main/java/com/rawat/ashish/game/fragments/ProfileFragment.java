package com.rawat.ashish.game.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    ImageView profileImage;
    TextView name, id, accountCreated, mobileNumber;
    APIService mAPIService;
    SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name = view.findViewById(R.id.profileName);
        profileImage = view.findViewById(R.id.profileImageView);
        id = view.findViewById(R.id.profileId);
        accountCreated = view.findViewById(R.id.accountCreatedTime);
        mobileNumber = view.findViewById(R.id.profileNumber);
        mAPIService = APIClient.getClient().create(APIService.class);
        setProfile();
        return view;
    }

    private void setProfile() {
        mAPIService.getUser(sharedPreferences.getString(MyConstants.USER_ID, "")).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                setTextViewData(name, response.body().getResult().getFirstName() + " " + response.body().getResult().getLastName());
                setTextViewData(id, "User Id : " + response.body().getResult().getUserId());
                setTextViewData(accountCreated, "Account created on : " + response.body().getResult().getCreatedOn());
                setTextViewData(mobileNumber, "Mobile No.: " + response.body().getResult().getMobile());
                Glide.with(Objects.requireNonNull(getActivity()))
                        .load(response.body().getResult().getImage())
                        .thumbnail(0.5f)
                        .into(profileImage);
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTextViewData(TextView textViewData, String s) {
        textViewData.setText(s);
    }
}
