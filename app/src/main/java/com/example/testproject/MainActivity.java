package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TextView myTextView;
    private final Handler handler = new Handler();
    private final Updater updater = new Updater();

    private class Updater implements Runnable {

        private String getFormattedDatetime(long timeDifferenceMillis) {
            long days = timeDifferenceMillis / (1000 * 60 * 60 * 24);
            long hours = (timeDifferenceMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (timeDifferenceMillis % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (timeDifferenceMillis % (1000 * 60)) / 1000;

            return String.format(getString(R.string.countdown_format), Calendar.getInstance().get(Calendar.YEAR) + 1, days, hours, minutes, seconds);
        }

        private long getDatetimeDifference() {
            Date currentDate = new Date();

            Calendar targetCalendar = Calendar.getInstance();
            targetCalendar.set(targetCalendar.get(Calendar.YEAR) + 1, Calendar.JANUARY, 1, 0, 0, 0);

            return targetCalendar.getTimeInMillis() - currentDate.getTime();
        }

        private void update() {
            long datetimeDifference = this.getDatetimeDifference();
            String formattedString = this.getFormattedDatetime(datetimeDifference);

            myTextView.setText(formattedString);
        }

        @Override
        public void run() {
            this.update();
            handler.postDelayed(this, 1000);
        }
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.setLocale(this, "ru");  // en

        this.myTextView = findViewById(R.id.myTextView);
        handler.post(updater);
    }
}
