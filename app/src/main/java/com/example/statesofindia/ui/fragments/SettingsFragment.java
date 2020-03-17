package com.example.statesofindia.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.View;

import com.example.statesofindia.R;
import com.example.statesofindia.services.NotificationWorker;
import com.example.statesofindia.util.NightMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private String NOTIFICATION_WORK = "nwork";
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        long current = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 30);

        if(calendar.getTimeInMillis() < current) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }


        final WorkManager manager = WorkManager.getInstance(requireActivity());
        final PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(
                NotificationWorker.class,
                1,
                TimeUnit.DAYS
                );
        workRequestBuilder.setInitialDelay(calendar.getTimeInMillis() - current, TimeUnit.MILLISECONDS);
        //workRequestBuilder.setInputData()

        SwitchPreference s = findPreference("switch_preference_1");
        s.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Boolean x = (Boolean) newValue;
                if (x) {
                    manager.enqueueUniquePeriodicWork(NOTIFICATION_WORK, ExistingPeriodicWorkPolicy.REPLACE, workRequestBuilder.build());
                } else {
                    manager.cancelUniqueWork(NOTIFICATION_WORK);
                }
                return true;
            }
        });

        ListPreference darkMode = findPreference(getString(R.string.pref_key_night));
        darkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String appTheme = (String) newValue;

                NightMode.NightModeEnum value = NightMode.NightModeEnum.valueOf(appTheme.toUpperCase(Locale.US));
                updateTheme(value.value);
                return true;
            }
        });


    }

    private void updateTheme(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
        requireActivity().recreate();
    }


/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_settings, container, false);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);

        NavController navController =
                Navigation.findNavController(requireActivity(),R.id.main_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}
