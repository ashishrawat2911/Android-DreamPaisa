package com.rawat.ashish.game.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;

public class ReferAndEarnActivity extends AppCompatActivity {
    Button inviteFriends;
    SharedPreferences sharedPreferences;
    TextView referCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_refer_and_earn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ReferAndEarnActivity.this);
        inviteFriends = findViewById(R.id.inviteFriendsButton);
        referCodeTextView=findViewById(R.id.referCodeTextView);
        referCodeTextView.setText(sharedPreferences.getString(MyConstants.MY_REFERRAL_CODE,""));
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareRef();
            }
        });

    }

    private void shareRef() {
        String packageName = getPackageName();
        Intent appShareIntent = new Intent(Intent.ACTION_SEND);
        appShareIntent.setType("text/plain");
        String extraText = "Hey! Check out this amazing game app\nwhere you can play and earn money.\n";
        extraText += "https://play.google.com/store/apps/details?id=" + packageName;
        extraText += "\n\nUse this refer code\n\n" + sharedPreferences.getString(MyConstants.MY_REFERRAL_CODE, "");
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        startActivity(appShareIntent);
    }


}
