package com.example.prasetyo.moviedb.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.api.ApiEndPoint;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.service.MovieDailyReminder;
import com.example.prasetyo.moviedb.service.MovieUpcomingReminder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingActivity extends SettingPreference {

    private static final String TAG = SettingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        private SwitchPreference switchReminder;
        private SwitchPreference switchToday;

        private MovieDailyReminder dailyReceiver = new MovieDailyReminder();
        private MovieUpcomingReminder upcomingReceiver = new MovieUpcomingReminder();

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);


            switchReminder = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
            switchReminder.setOnPreferenceChangeListener(this);
            switchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            switchToday.setOnPreferenceChangeListener(this);

            Preference myPref = findPreference(getString(R.string.key_lang));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean configurationValues = (boolean) newValue;

            if(key.equals(getString(R.string.key_today_reminder))){
                if(configurationValues){
                    dailyReceiver.setDailyAlarm(getActivity());
                }else{
                    dailyReceiver.cancelDailyAlarm(getActivity());
                }
            } else {
                if(configurationValues){
                    setNewReleaseAlarm();
                }else{
                    upcomingReceiver.cancelUpcomingAlarm(getActivity());
                }
            }

            return true;
        }

        private void setNewReleaseAlarm() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String dateNow = dateFormat.format(date);
            final List<Movie> data = new ArrayList<>();
            ApiEndPoint apiEndPoint = new ApiEndPoint();
            AndroidNetworking.get(apiEndPoint.urlUpcoming())
                    .setTag(this)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            Log.d(TAG, "RESULT " + response.toString());
                            try {
                                JSONArray array = response.getJSONArray("results");
                                // Loop through the array elements
                                for (int i = 0; i < array.length(); i++) {
                                    // Get current json object
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    Movie movie = new Movie();

                                    movie.setTitle(jsonObject.getString("original_title"));
                                    movie.setId(jsonObject.getString("id"));
                                    movie.setOverview(jsonObject.getString("overview"));
                                    movie.setDate(jsonObject.getString("release_date"));
                                    movie.setRating(jsonObject.getString("vote_average"));
                                    movie.setVoter(jsonObject.getString("vote_count"));
                                    movie.setPoster("http://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path"));
                                    movie.setBanner("http://image.tmdb.org/t/p/original" + jsonObject.getString("backdrop_path"));

                                    if (movie.getDate().equals(dateNow)) {
                                        data.add(movie);
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.e(TAG, "ERROR : " + error.getMessage());
                        }
                    });
        }

    }

}
