package com.example.zeeshan.supermario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {
    public boolean is_remember_me, trialExpired;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final long ONE_DAY = 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String installDate = preferences.getString("InstallDate", null);
        if (installDate == null) {
            // First run, so save the current date
            SharedPreferences.Editor editor = preferences.edit();
            Date now = new Date();
            String dateString = formatter.format(now);
            editor.putString("InstallDate", dateString);
            // Commit the edits!
            editor.commit();
        } else {
            // This is not the 1st run, check install date
            try {
                Date before = (Date) formatter.parse(installDate);
                Date now = new Date();
                long diff = now.getTime() - before.getTime();
                long days = diff / ONE_DAY;
                if (days > 10) { // More than 30 days?
                    // Expired !!!
                    trialExpired = true;
                }
            } catch (Exception e) {
            }
        }

        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                finish();

                // Start home activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }, 2000);


//        }
    }



}
