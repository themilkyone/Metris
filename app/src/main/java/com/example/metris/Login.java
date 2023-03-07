package com.example.metris;

import static android.os.Build.VERSION.SDK_INT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Locale;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EditText kierowca = (EditText) findViewById(R.id.kierowca);
        Button dalej = (Button) findViewById(R.id.dalej);
        kierowca.setRawInputType(InputType.TYPE_CLASS_TEXT);
        kierowca.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        dalej.setBackgroundColor(getResources().getColor(R.color.teal_700));

        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString("style","white");
        editor.apply();

        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
            } else { //request for the permission
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }

        dalej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kierowca.length() > 6 && kierowca.getText().toString().contains(" ")) {
                    String imienazwisko = kierowca.getText().toString();
                    String[] rozdzial = imienazwisko.split(" ");
                    String imie = rozdzial[0];
                    String nazwisko = rozdzial[1];
                    char a = imie.charAt(0);
                    char b = nazwisko.charAt(0);
                    char c = nazwisko.charAt(1);
                    String login = ("" + a + b + c).toUpperCase(Locale.ROOT);
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.putString("keyname", login);
                    editor.apply();
                    editor.putString("name", imienazwisko);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intent);

                    System.out.println(login.toUpperCase(Locale.ROOT));


                } else {
                    Toast.makeText(Login.this, "Błędnie wprowadzone imię i nazwisko.", Toast.LENGTH_SHORT).show();
                    kierowca.setBackgroundResource(R.drawable.borderred);
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
            }
            });
    }
}