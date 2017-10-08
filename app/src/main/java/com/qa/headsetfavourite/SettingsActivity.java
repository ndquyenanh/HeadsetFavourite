package com.qa.headsetfavourite;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by sev_user on 20-May-15.
 */
public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                getFragmentManager().beginTransaction().replace(android.R.id.content, new Settings()).commit();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static class Settings extends PreferenceFragment{

         public Settings() {
         }

         @Override
         public void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);

             addPreferencesFromResource(R.xml.setting);
         }
     }
}
