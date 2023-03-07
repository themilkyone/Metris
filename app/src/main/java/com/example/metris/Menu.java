package com.example.metris;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.io.File;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (!myPrefs.contains("keyname")) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

        setContentView(R.layout.menu);
        ImageButton raport = findViewById(R.id.raport);
        ImageButton harm = findViewById(R.id.harm);
        ImageButton urlop = findViewById(R.id.urlop);
        ImageButton eq = findViewById(R.id.eq);
        ImageButton bulb = findViewById(R.id.bulb);
        ImageView logo = findViewById(R.id.logo);
        ConstraintLayout menul = findViewById(R.id.menul);
        TextView tdmtext = findViewById(R.id.tdmtext);
        TextView version = findViewById(R.id.version);

        if (myPrefs.getString("style", null) == null) {
            SharedPreferences.Editor editor3 = myPrefs.edit();
            editor3.putString("style", "white");
            editor3.apply();
        }

        if (myPrefs.getString("style", null) != null) {
            if ((myPrefs.getString("style", null).equals("dark"))) {
                bulb.setImageResource(R.drawable.dark);
                menul.setBackgroundResource(R.color.dark);
                tdmtext.setTextColor(getResources().getColor(R.color.white));
                version.setTextColor(getResources().getColor(R.color.white));
                logo.setImageResource(R.drawable.logow);
            }
            if ((myPrefs.getString("style", null).equals("white"))) {
                bulb.setImageResource(R.drawable.light);
                menul.setBackgroundResource(R.color.white);
                tdmtext.setTextColor(getResources().getColor(R.color.black));
                version.setTextColor(getResources().getColor(R.color.black));
                logo.setImageResource(R.drawable.logo);
            }
        }

        File file;
        if (SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Raporty/");
        } else {
            file = new File(Environment.getExternalStorageDirectory() + "/Download/Raporty/");
        }
        if (!file.exists()) {
            file.mkdir();
        }

        File file2;
        if (SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Przekazania/");
        } else {
            file2 = new File(Environment.getExternalStorageDirectory() + "/Download/Przekazania/");
        }
        if (!file2.exists()) {
            file2.mkdir();
        }

        File file3;
        if (SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Wnioski/");
        } else {
            file3 = new File(Environment.getExternalStorageDirectory() + "/Download/Wnioski/");
        }
        if (!file3.exists()) {
            file3.mkdir();
        }

//        // Don't think I even need this?
//        GoogleCredential credential = new GoogleCredential();
//        credential.createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
//
//        HttpTransport transport = AndroidHttp.newCompatibleTransport();
//        JsonFactory factory = JacksonFactory.getDefaultInstance();
//        final Sheets sheets = new Sheets.Builder(transport, factory, credential)
//                .setApplicationName("Metris")
//                .build();
//        final String sheetID = "16JmytU_ku9OFgUFd422h1bosYCILbCwjDQcH3HRFPhE";
//
//        new Thread() {
//            @Override
//            public void run() {
//                ValueRange a2 = null;
//
//                try {
//                    a2 = sheets.spreadsheets().values()
//                            .get(sheetID, myPrefs.getString("keyname", null) + "!a2")
//                            .setKey("AIzaSyAkWDAljrCDAtugTIonIV09_wKGj4OvlSc")
//                            .execute();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                SharedPreferences.Editor czyJestHarm = myPrefs.edit();
//                if (a2 != null && a2.getValues() != null) {
//                    czyJestHarm.putString("a2", a2.getValues().toString());
//                }
//                if (a2 != null && a2.getValues() != null && a2.getValues().toString().equals("[[]]"))
//                    czyJestHarm.putString("a2", a2.getValues().toString());
//                czyJestHarm.apply();
//                if (a2 == null) {
//                    czyJestHarm.putString("a2", "");
//                    czyJestHarm.apply();
//                }
//
//            }
//        }.start();


        ActivityCompat.requestPermissions(Menu.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        raport.setOnClickListener(view -> {

            Intent i = new Intent(getApplicationContext(), Raport.class);
            startActivity(i);

        });

        urlop.setOnClickListener(view -> {

            Intent i = new Intent(getApplicationContext(), Urlop.class);
            startActivity(i);

        });


        harm.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), HarmActivity.class);
            startActivity(i);


//            new Thread() {
//                @Override
//                public void run() {
//                    ValueRange a2 = null;
//
//                    try {
//                        a2 = sheets.spreadsheets().values()
//                                .get(sheetID, myPrefs.getString("keyname", null) + "!a2")
//                                .setKey("AIzaSyAkWDAljrCDAtugTIonIV09_wKGj4OvlSc")
//                                .execute();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    SharedPreferences.Editor czyJestHarm = myPrefs.edit();
//                    if (a2 != null && a2.getValues() != null) {
//                        czyJestHarm.putString("a2", a2.getValues().toString());
//                    }
//                    if (a2 != null && a2.getValues() != null && a2.getValues().toString().equals("[[]]"))
//                        czyJestHarm.putString("a2", a2.getValues().toString());
//                    czyJestHarm.apply();
//                    if (a2 == null) {
//                        czyJestHarm.putString("a2", "");
//                        czyJestHarm.apply();
//                    }
//
//                }
//            }.start();
//
//            try {
//                if (myPrefs.getString("a2", null) != null) {
//                    try {
//                        if (myPrefs.getString("a2", null).length() == 0) Toast.makeText(menu.this, "Brak harmonogramu.", Toast.LENGTH_SHORT).show();
//                        else {
//                            Intent i = new Intent(getApplicationContext(), harm.class);
//                            startActivity(i);
//                        }} catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(menu.this, "Brak harmonogramu.", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(menu.this, "Brak harmonogramu.", Toast.LENGTH_SHORT).show();
//                    System.out.println("dupa");
//                }
//
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(menu.this, "Brak harmonogramu.", Toast.LENGTH_SHORT).show();
//            }

        });

        eq.setOnClickListener(view -> {

            Intent i = new Intent(getApplicationContext(), Eq.class);
            startActivity(i);

        });

        bulb.setOnClickListener(view -> {
            if ((myPrefs.getString("style", null).equals("dark"))) {
                SharedPreferences.Editor editor2 = myPrefs.edit();
                editor2.putString("style", "white");
                editor2.apply();
                bulb.setImageResource(R.drawable.light);
                menul.setBackgroundResource(R.color.white);
                logo.setImageResource(R.drawable.logo);
                tdmtext.setTextColor(getResources().getColor(R.color.black));
                version.setTextColor(getResources().getColor(R.color.black));
            } else if ((myPrefs.getString("style", null).equals("white"))) {
                SharedPreferences.Editor editor3 = myPrefs.edit();
                editor3.putString("style", "dark");
                editor3.apply();
                bulb.setImageResource(R.drawable.dark);
                menul.setBackgroundResource(R.color.dark);
                tdmtext.setTextColor(getResources().getColor(R.color.white));
                version.setTextColor(getResources().getColor(R.color.white));
                logo.setImageResource(R.drawable.logow);
            }
        });

        new AppUpdater(this)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.json")
                .start();

    }
}