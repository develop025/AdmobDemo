package com.demo.bannerexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.AdapterStatus;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Main Activity. Inflates main activity xml and child fragments.
 */
public class MyActivity extends AppCompatActivity {

    private AdView adView;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        tvLog = findViewById(R.id.tvLog);

        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("2DBD06389D800646BB9BEA59A2392226", "25679938971D2B95253438941CD71269"))
                        .build());

        MobileAds.initialize(
                this,
                status -> {
                    log("Initialization complete");
                    for (Map.Entry<String, AdapterStatus> entry : status.getAdapterStatusMap().entrySet()) {
                        log("MobileAds:" + entry.getKey() + "=" + entry.getValue().getDescription() + " " + entry.getValue().getInitializationState() + " " + entry.getValue().getLatency());
                    }
                });

        AdRequest adRequest = new AdRequest.Builder().build();
        boolean isTestDevice = adRequest.isTestDevice(this);
        log("isTestDevice: " + isTestDevice);

        adView = findViewById(R.id.ad_view);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                log("Code to be executed when an ad finishes loading.");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                log("Code to be executed when an ad request fails." + adError.toString());
            }

            @Override
            public void onAdOpened() {
                log("Code to be executed when an ad opens an overlay that covers the screen.");
            }

            @Override
            public void onAdClicked() {
                log("Code to be executed when the user clicks on an ad.");
            }

            @Override
            public void onAdLeftApplication() {
                log("Code to be executed when the user has left the app.");
            }

            @Override
            public void onAdClosed() {
                log("Code to be executed when the user is about to return to the app after tapping on an ad.");
            }
        });

        if (adView.getAdSize() != null || adView.getAdUnitId() != null) {
            log("Load Ad.");
            adView.loadAd(adRequest);
        }
    }

    @SuppressLint("SetTextI18n")
    private void log(String s) {
        Log.d("Admob", s);
        tvLog.setText(tvLog.getText() + "\n" + s);
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (adView != null) {
            log("adView.pause");
            adView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            log("adView.resume");
            adView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (adView != null) {
            log("adView.destroy");
            adView.destroy();
        }
        super.onDestroy();
    }
}
