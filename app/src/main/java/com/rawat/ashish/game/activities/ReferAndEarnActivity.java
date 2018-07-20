package com.rawat.ashish.game.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rawat.ashish.game.R;

public class ReferAndEarnActivity extends AppCompatActivity {
    Button inviteFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_refer_and_earn);
        inviteFriends = findViewById(R.id.inviteFriendsButton);
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packageName = getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = "Hey! Check out this amazing game app\nwhere you can play and earn money.\n";
                extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
            }
        });

    }


}
