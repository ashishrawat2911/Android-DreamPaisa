package com.rawat.ashish.game.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rawat.ashish.game.R;

public class PaymentRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);
    }

    public void inAccount(View view) {getIntent(InAccountActivity.class);
    }

    public void payTm(View view) {getIntent(PayTmActivity.class);
    }

    public void payPal(View view) {
        getIntent(PayPalActivity.class);
    }

    private void getIntent(Class<?> cls) {
        startActivity(new Intent(PaymentRequestActivity.this, cls));
    }
}
