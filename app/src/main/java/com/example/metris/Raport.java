package com.example.metris;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.StyleFlag;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

public class Raport extends AppCompatActivity {

    DriveServiceHelper driveServiceHelper;
    DatabaseHelper2 databaseHelper2;

    @SuppressLint({"UseCompatLoadingForColorStateLists", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WSTĘP
        databaseHelper2 = new DatabaseHelper2(this);


        setContentView(R.layout.raport);
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        TextView tvdate = findViewById(R.id.tvdate);
        final TextView[] dzientyg2 = {findViewById(R.id.dzientyg2)};
        TextView dzien = findViewById(R.id.dzien);
        TextView miesiac = findViewById(R.id.miesiac);
        TextView rok = findViewById(R.id.rok);
        EditText dataa = findViewById(R.id.dataa);
        CheckBox checkboxdata = findViewById(R.id.checkboxdata);
        Button wyslij = findViewById(R.id.wyslij);
        Button wyslane = findViewById(R.id.wyslane);

        ScrollView raport = findViewById(R.id.raport);
        View view = findViewById(R.id.view);
        ImageView logo = findViewById(R.id.logo);
        EditText czasp1 = findViewById(R.id.czasp1);
        EditText czasp2 = findViewById(R.id.czasp2);
        EditText czasp3 = findViewById(R.id.czasp3);
        EditText czasp4 = findViewById(R.id.czasp4);
        EditText czasj1 = findViewById(R.id.czasj1);
        EditText czasj2 = findViewById(R.id.czasj2);
        EditText czasj3 = findViewById(R.id.czasj3);
        EditText czasj4 = findViewById(R.id.czasj4);
        EditText podzial1 = findViewById(R.id.podzial1);
        EditText podzial2 = findViewById(R.id.podzial2);
        EditText podzial3 = findViewById(R.id.podzial3);
        EditText podzial4 = findViewById(R.id.podzial4);
        EditText ilosc1 = findViewById(R.id.ilosc1);
        EditText ilosc2 = findViewById(R.id.ilosc2);
        EditText ilosc3 = findViewById(R.id.ilosc3);
        EditText ilosc4 = findViewById(R.id.ilosc4);
        EditText nr1 = findViewById(R.id.nr1);
        EditText nr2 = findViewById(R.id.nr2);
        EditText nr3 = findViewById(R.id.nr3);
        EditText nr4 = findViewById(R.id.nr4);
        EditText uwagi1 = findViewById(R.id.uwagi1);
        EditText uwagi2 = findViewById(R.id.uwagi2);
        EditText uwagi3 = findViewById(R.id.uwagi3);
        EditText uwagi4 = findViewById(R.id.uwagi4);
        RelativeLayout spinnercontainer1 = findViewById(R.id.spinnercontainer1);
        RelativeLayout spinnercontainer2 = findViewById(R.id.spinnercontainer2);
        RelativeLayout spinnercontainer3 = findViewById(R.id.spinnercontainer3);
        RelativeLayout spinnercontainer4 = findViewById(R.id.spinnercontainer4);
        RelativeLayout spinnercontainer5 = findViewById(R.id.spinnercontainer5);
        RelativeLayout spinnercontainer6 = findViewById(R.id.spinnercontainer6);
        RelativeLayout spinnercontainer7 = findViewById(R.id.spinnercontainer7);
        RelativeLayout spinnercontainer8 = findViewById(R.id.spinnercontainer8);

        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner3 = findViewById(R.id.spinner3);
        Spinner spinner5 = findViewById(R.id.spinner5);
        Spinner spinner7 = findViewById(R.id.spinner7);

        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner4 = findViewById(R.id.spinner4);
        Spinner spinner6 = findViewById(R.id.spinner6);
        Spinner spinner8 = findViewById(R.id.spinner8);

        Calendar calendarr = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatt = new SimpleDateFormat("yyMMdd");
        String data4 = simpleDateFormatt.format(calendarr.getTime());
        final String[] data5 = {null};

        requestSignIn();

        getResources().getColorStateList(R.color.checkbox);
        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
        wyslane.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_200));

        if (isNetworkOnline()) {
            Toast.makeText(Raport.this, "Brak połączenia z internetem!", Toast.LENGTH_LONG).show();
        }

        if (databaseHelper2.sprawdzdate(data4) != null) {
            wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
            wyslij.setEnabled(false);
        } else {
            wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
            wyslij.setEnabled(true);
        }
        if (!databaseHelper2.sprawdzdate(data4).equals("")) {
            wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
            wyslij.setEnabled(false);
        } else {
            wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
            wyslij.setEnabled(true);
        }


        if ((myPrefs.getString("style", null).equals("dark"))) {
            raport.setBackgroundResource(R.color.dark);
            uwagi1.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi2.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi3.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi4.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi1.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi2.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi3.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            uwagi4.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            tvdate.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            dataa.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            view.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj1.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp1.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr1.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial1.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc1.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj2.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp2.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr2.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial2.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc2.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj3.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp3.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr3.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial3.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc3.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj4.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp4.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr4.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial4.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc4.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj1.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp1.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr1.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial1.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc1.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj2.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp2.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr2.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial2.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc2.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj3.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp3.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr3.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial3.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc3.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasj4.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            czasp4.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            nr4.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            podzial4.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            ilosc4.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            tvdate.setHintTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            view.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.white));
            checkboxdata.setTextColor(ContextCompat.getColor(Raport.this, R.color.white));
            logo.setImageResource(R.drawable.logohw);
        }


//        KALENDARZ
        String dateTime;
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat[] simpleDateFormat = new SimpleDateFormat[1];
        simpleDateFormat[0] = new SimpleDateFormat("dd.MM.yy HH:mm");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yy.MM.dd");
        String dateTime11 = simpleDateFormat2.format(calendar.getTime()).replace(".", "");
        dateTime = simpleDateFormat[0].format(calendar.getTime());
        tvdate.setText(dateTime);
        final String[] dateTime1 = {null};
        String dzientyg;
        final SimpleDateFormat[] simpleDateFormat1 = new SimpleDateFormat[1];
        simpleDateFormat1[0] = new SimpleDateFormat("EEE");
        dzientyg = simpleDateFormat1[0].format(calendar.getTime());

        dataa.setVisibility(View.INVISIBLE);
        checkboxdata.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (databaseHelper2.sprawdzdate(dateTime11) != null) {
                wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                wyslij.setEnabled(false);
            } else {
                wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                wyslij.setEnabled(true);
            }
            if (!databaseHelper2.sprawdzdate(dateTime11).equals("")) {
                wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                wyslij.setEnabled(false);
            } else {
                wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                wyslij.setEnabled(true);
            }


            if (isChecked) {
                checkboxdata.setTextSize(0);
                dataa.setVisibility(View.VISIBLE);
                Calendar Kalendarz = Calendar.getInstance();
                int Rok = Kalendarz.get(Calendar.YEAR);
                int Miesiąc = Kalendarz.get(Calendar.MONTH);
                int Dzień = Kalendarz.get(Calendar.DAY_OF_MONTH);

                if (dataa.getText().toString().length() == 0) {
                    wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                    wyslij.setEnabled(false);
                } else {
                    wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                    wyslij.setEnabled(true);
                }

                final String[] sMonth = new String[1];
                final String[] sDzień = new String[1];
                final String[] sRok = new String[1];

                DatePickerDialog datePickerDialog = new DatePickerDialog(Raport.this, (view1, Rok12, Miesiąc12, Dzień12) -> {

                    Miesiąc12 = Miesiąc12 + 1;

                    if (Miesiąc12 < 10) {
                        sMonth[0] = "0" + Miesiąc12;
                    } else {
                        sMonth[0] = String.valueOf(Miesiąc12);
                    }

                    if (Dzień12 < 10) {
                        sDzień[0] = "0" + Dzień12;
                    } else {
                        sDzień[0] = String.valueOf(Dzień12);
                    }

                    sRok[0] = Integer.toString(Rok12 - 2000);
                    String date2 = sRok[0] + sMonth[0] + sDzień[0];
                    GregorianCalendar gregorianCalendar = new GregorianCalendar(Rok12, Miesiąc12 - 1, Dzień12 - 1);
                    int dotw = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
                    String date = sDzień[0] + "." + sMonth[0] + "." + sRok[0];
                    dateTime1[0] = sRok[0] + sMonth[0] + sDzień[0] + "";
                    if (databaseHelper2.sprawdzdate(date2) != null) {
                        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                        wyslij.setEnabled(false);
                    } else {
                        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                        wyslij.setEnabled(true);
                    }
                    if (!databaseHelper2.sprawdzdate(date2).equals("")) {
                        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                        wyslij.setEnabled(false);
                    } else {
                        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                        wyslij.setEnabled(true);
                    }

                    switch (dotw) {
                        case 1:
                            dzientyg2[0].setText("pon.");
                            break;
                        case 2:
                            dzientyg2[0].setText("wt.");
                            break;
                        case 3:
                            dzientyg2[0].setText("śr.");
                            break;
                        case 4:
                            dzientyg2[0].setText("czw.");
                            break;
                        case 5:
                            dzientyg2[0].setText("pt.");
                            break;
                        case 6:
                            dzientyg2[0].setText("sob.");
                            break;
                        case 7:
                            dzientyg2[0].setText("nd.");
                            break;
                    }

                    dzien.setText(sDzień[0]);
                    miesiac.setText(sMonth[0]);
                    rok.setText(sRok[0]);

                    dataa.setText(date);

                    if (dataa.getText().toString().length() != 0)
                        checkboxdata.setBackgroundResource(R.drawable.borderinput);
                    else checkboxdata.setBackgroundResource(R.drawable.border);
                    System.out.println(sRok[0] + " " + sMonth[0] + sDzień[0]);
                }, Rok, Miesiąc, Dzień);
                datePickerDialog.show();
            } else {
                checkboxdata.setTextSize(16);
                dataa.setVisibility(View.INVISIBLE);
                dataa.setText("");
                checkboxdata.setBackgroundResource(R.drawable.border);
            }

            if (isChecked && dataa.length() > 7) {
                checkboxdata.setBackgroundResource(R.drawable.borderinput);

            }
        });
        Calendar Kalendarz = Calendar.getInstance();
        final int Rok = Kalendarz.get(Calendar.YEAR);
        final int Miesiąc = Kalendarz.get(Calendar.MONTH);
        final int Dzień = Kalendarz.get(Calendar.DAY_OF_MONTH);


        dataa.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(Raport.this, (view12, Rok1, Miesiąc1, Dzień1) -> {
                Miesiąc1 = Miesiąc1 + 1;
                String sMonth;
                if (Miesiąc1 < 10) {
                    sMonth = "0" + Miesiąc1;
                } else {
                    sMonth = String.valueOf(Miesiąc1);
                }
                String date = Dzień1 + "." + sMonth + "." + Rok1;

                String sDzień;
                String sRok;

                if (Dzień1 < 10) {
                    sDzień = "0" + Dzień1;
                } else {
                    sDzień = String.valueOf(Dzień1);
                }
                sRok = Integer.toString(Rok1 - 2000);
                String date2 = sRok + sMonth + sDzień;

                rok.setText(sRok);
                miesiac.setText(sMonth);
                dzien.setText(sDzień);

                dataa.setText(date);
                if (databaseHelper2.sprawdzdate(date2) != null) {
                    wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                    wyslij.setEnabled(false);
                } else {
                    wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                    wyslij.setEnabled(true);
                }
                if (!databaseHelper2.sprawdzdate(date2).equals("")) {
                    wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                    wyslij.setEnabled(false);
                } else {
                    wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.teal_700));
                    wyslij.setEnabled(true);
                }
            }, Rok, Miesiąc, Dzień);
            datePickerDialog.show();
        });
//        SPINNERY

        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.add("Polowa");
            adapter.add("Biurowa");
            adapter.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);
            spinner1.setSelection(adapter.getCount());
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.add("Polowa");
            adapter.add("Biurowa");
            adapter.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);
            spinner1.setSelection(adapter.getCount());
        }


        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };

            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter3.add("Polowa");
            adapter3.add("Biurowa");
            adapter3.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);
            spinner3.setSelection(adapter3.getCount());
        } else {
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter3.add("Polowa");
            adapter3.add("Biurowa");
            adapter3.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);
            spinner3.setSelection(adapter3.getCount());
        }
        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };

            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter5.add("Polowa");
            adapter5.add("Biurowa");
            adapter5.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner5.setAdapter(adapter5);
            spinner5.setSelection(adapter5.getCount());
        } else {
            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter5.add("Polowa");
            adapter5.add("Biurowa");
            adapter5.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner5.setAdapter(adapter5);
            spinner5.setSelection(adapter5.getCount());
        }

        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };

            adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter7.add("Polowa");
            adapter7.add("Biurowa");
            adapter7.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner7.setAdapter(adapter7);
            spinner7.setSelection(adapter7.getCount());
        } else {
            ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter7.add("Polowa");
            adapter7.add("Biurowa");
            adapter7.add("Rodzaj"); //This is the text that will be displayed as hint.

            adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner7.setAdapter(adapter7);
            spinner7.setSelection(adapter7.getCount());
        }

        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter2.add("DLT");
            adapter2.add("DLT-W");
            adapter2.add("H");
            adapter2.add("INKL");
            adapter2.add("INW");
            adapter2.add("MONTAŻ");
            adapter2.add("PIT");
            adapter2.add("R");
            adapter2.add("SLT");
            adapter2.add("SLT-K");
            adapter2.add("SPR");
            adapter2.add("WIB");
            adapter2.add("INNY");
            adapter2.add("Badania"); //This is the text that will be displayed as hint.
            spinner2.setAdapter(adapter2);
            spinner2.setSelection(adapter2.getCount());
        } else {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter2.add("DLT");
            adapter2.add("DLT-W");
            adapter2.add("H");
            adapter2.add("INKL");
            adapter2.add("INW");
            adapter2.add("MONTAŻ");
            adapter2.add("PIT");
            adapter2.add("R");
            adapter2.add("SLT");
            adapter2.add("SLT-K");
            adapter2.add("SPR");
            adapter2.add("WIB");
            adapter2.add("INNY");
            adapter2.add("Badania"); //This is the text that will be displayed as hint.
            spinner2.setAdapter(adapter2);
            spinner2.setSelection(adapter2.getCount());
        }

        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter4.add("DLT");
            adapter4.add("DLT-W");
            adapter4.add("H");
            adapter4.add("INKL");
            adapter4.add("INW");
            adapter4.add("MONTAŻ");
            adapter4.add("PIT");
            adapter4.add("R");
            adapter4.add("SLT");
            adapter4.add("SLT-K");
            adapter4.add("SPR");
            adapter4.add("WIB");
            adapter4.add("INNY");
            adapter4.add("Badania"); //This is the text that will be displayed as hint.
            spinner4.setAdapter(adapter4);
            spinner4.setSelection(adapter4.getCount());
        } else {
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter4.add("DLT");
            adapter4.add("DLT-W");
            adapter4.add("H");
            adapter4.add("INKL");
            adapter4.add("INW");
            adapter4.add("MONTAŻ");
            adapter4.add("PIT");
            adapter4.add("R");
            adapter4.add("SLT");
            adapter4.add("SLT-K");
            adapter4.add("SPR");
            adapter4.add("WIB");
            adapter4.add("INNY");
            adapter4.add("Badania"); //This is the text that will be displayed as hint.
            spinner4.setAdapter(adapter4);
            spinner4.setSelection(adapter4.getCount());
        }

        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter6.add("DLT");
            adapter6.add("DLT-W");
            adapter6.add("H");
            adapter6.add("INKL");
            adapter6.add("INW");
            adapter6.add("MONTAŻ");
            adapter6.add("PIT");
            adapter6.add("R");
            adapter6.add("SLT");
            adapter6.add("SLT-K");
            adapter6.add("SPR");
            adapter6.add("WIB");
            adapter6.add("INNY");
            adapter6.add("Badania"); //This is the text that will be displayed as hint.
            spinner6.setAdapter(adapter6);
            spinner6.setSelection(adapter6.getCount());
        } else {
            ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter6.add("DLT");
            adapter6.add("DLT-W");
            adapter6.add("H");
            adapter6.add("INKL");
            adapter6.add("INW");
            adapter6.add("MONTAŻ");
            adapter6.add("PIT");
            adapter6.add("R");
            adapter6.add("SLT");
            adapter6.add("SLT-K");
            adapter6.add("SPR");
            adapter6.add("WIB");
            adapter6.add("INNY");
            adapter6.add("Badania"); //This is the text that will be displayed as hint.
            spinner6.setAdapter(adapter6);
            spinner6.setSelection(adapter6.getCount());
        }

        if (myPrefs.getString("style", null).equals("dark")) {
            ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, R.layout.spinner_item_dark) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };

            adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter8.add("DLT");
            adapter8.add("DLT-W");
            adapter8.add("H");
            adapter8.add("INKL");
            adapter8.add("INW");
            adapter8.add("MONTAŻ");
            adapter8.add("PIT");
            adapter8.add("R");
            adapter8.add("SLT");
            adapter8.add("SLT-K");
            adapter8.add("SPR");
            adapter8.add("WIB");
            adapter8.add("INNY");
            adapter8.add("Badania"); //This is the text that will be displayed as hint.
            spinner8.setAdapter(adapter8);
            spinner8.setSelection(adapter8.getCount());
        } else {
            ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, R.layout.spinner_item) {
                @Override
                public int getCount() {
                    return super.getCount() - 1; // you dont display last item. It is used as hint.
                }
            };
            adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter8.add("DLT");
            adapter8.add("DLT-W");
            adapter8.add("H");
            adapter8.add("INKL");
            adapter8.add("INW");
            adapter8.add("MONTAŻ");
            adapter8.add("PIT");
            adapter8.add("R");
            adapter8.add("SLT");
            adapter8.add("SLT-K");
            adapter8.add("SPR");
            adapter8.add("WIB");
            adapter8.add("INNY");
            adapter8.add("Badania"); //This is the text that will be displayed as hint.
            spinner8.setAdapter(adapter8);
            spinner8.setSelection(adapter8.getCount());
        }


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //ZMIANA OBWÓDKI TEKSTU
        TextWatcher autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nr1.length() > 0) {
                    nr1.setBackgroundResource(R.drawable.borderinput);
                } else {
                    nr1.setBackgroundResource(R.drawable.border);
                }
            }
        };
        nr1.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasj1.length() > 0) {
                    czasj1.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasj1.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasj1.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasp1.length() > 0) {
                    czasp1.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasp1.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasp1.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (podzial1.length() > 0) {
                    podzial1.setBackgroundResource(R.drawable.borderinput);
                } else {
                    podzial1.setBackgroundResource(R.drawable.border);
                }
            }


        };
        podzial1.addTextChangedListener(autoAddTextWatcher);
        podzial1.setText("100");
        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ilosc1.length() > 0) {
                    ilosc1.setBackgroundResource(R.drawable.borderinput);
                } else {
                    ilosc1.setBackgroundResource(R.drawable.border);
                }
            }


        };
        ilosc1.addTextChangedListener(autoAddTextWatcher);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp1 = spinner1.getSelectedItem().toString();
                if (!sp1.equals("Rodzaj"))
                    spinnercontainer1.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp2 = spinner2.getSelectedItem().toString();
                if (!sp2.equals("Badania"))
                    spinnercontainer2.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nr2.length() > 0) {
                    nr2.setBackgroundResource(R.drawable.borderinput);
                } else {
                    nr2.setBackgroundResource(R.drawable.border);
                }
            }


        };
        nr2.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasj2.length() > 0) {
                    czasj2.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasj2.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasj2.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasp2.length() > 0) {
                    czasp2.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasp2.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasp2.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (podzial2.length() > 0) {
                    podzial2.setBackgroundResource(R.drawable.borderinput);
                } else {
                    podzial2.setBackgroundResource(R.drawable.border);
                }
            }


        };
        podzial2.addTextChangedListener(autoAddTextWatcher);
        podzial2.setOnTouchListener((view13, motionEvent) -> {
            if (podzial1.getText().toString().equals("100")) {
                podzial1.setText("50");
                podzial2.setText("50");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        nr2.setOnTouchListener((view14, motionEvent) -> {
            if (podzial1.getText().toString().equals("100")) {
                podzial1.setText("50");
                podzial2.setText("50");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        czasj2.setOnTouchListener((view15, motionEvent) -> {
            if (podzial1.getText().toString().equals("100")) {
                podzial1.setText("50");
                podzial2.setText("50");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        czasp2.setOnTouchListener((view16, motionEvent) -> {
            if (podzial1.getText().toString().equals("100")) {
                podzial1.setText("50");
                podzial2.setText("50");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        ilosc2.setOnTouchListener((view17, motionEvent) -> {
            if (podzial1.getText().toString().equals("100")) {
                podzial1.setText("50");
                podzial2.setText("50");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        podzial2.setOnTouchListener((view18, motionEvent) -> {
            if (podzial1.getText().toString().equals("100")) {
                podzial1.setText("50");
                podzial2.setText("50");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ilosc2.length() > 0) {
                    ilosc2.setBackgroundResource(R.drawable.borderinput);
                } else {
                    ilosc2.setBackgroundResource(R.drawable.border);
                }
            }


        };
        ilosc2.addTextChangedListener(autoAddTextWatcher);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp3 = spinner3.getSelectedItem().toString();
                if (!sp3.equals("Rodzaj"))
                    spinnercontainer3.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp4 = spinner4.getSelectedItem().toString();
                if (!sp4.equals("Badania"))
                    spinnercontainer4.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nr3.length() > 0) {
                    nr3.setBackgroundResource(R.drawable.borderinput);
                } else {
                    nr3.setBackgroundResource(R.drawable.border);
                }
            }


        };
        nr3.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasj3.length() > 0) {
                    czasj3.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasj3.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasj3.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasp3.length() > 0) {
                    czasp3.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasp3.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasp3.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (podzial3.length() > 0) {
                    podzial3.setBackgroundResource(R.drawable.borderinput);
                } else {
                    podzial3.setBackgroundResource(R.drawable.border);
                }
            }


        };
        podzial3.addTextChangedListener(autoAddTextWatcher);
        podzial3.setOnTouchListener((view19, motionEvent) -> {
            if (podzial1.getText().toString().equals("50") && (podzial2.getText().toString().equals("50"))) {
                podzial1.setText("33.3");
                podzial2.setText("33.3");
                podzial3.setText("33.3");
                podzial3.setOnTouchListener(null);
            }
            return false;
        });

        nr3.setOnTouchListener((view110, motionEvent) -> {
            if (podzial1.getText().toString().equals("50") && (podzial2.getText().toString().equals("50"))) {
                podzial1.setText("33.3");
                podzial2.setText("33.3");
                podzial3.setText("33.3");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        czasj3.setOnTouchListener((view111, motionEvent) -> {
            if (podzial1.getText().toString().equals("50") && (podzial2.getText().toString().equals("50"))) {
                podzial1.setText("33.3");
                podzial2.setText("33.3");
                podzial3.setText("33.3");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        czasp3.setOnTouchListener((view112, motionEvent) -> {
            if (podzial1.getText().toString().equals("50") && (podzial2.getText().toString().equals("50"))) {
                podzial1.setText("33.3");
                podzial2.setText("33.3");
                podzial3.setText("33.3");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        ilosc3.setOnTouchListener((view113, motionEvent) -> {
            if (podzial1.getText().toString().equals("50") && (podzial2.getText().toString().equals("50"))) {
                podzial1.setText("33.3");
                podzial2.setText("33.3");
                podzial3.setText("33.3");
                podzial2.setOnTouchListener(null);
            }
            return false;
        });

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ilosc3.length() > 0) {
                    ilosc3.setBackgroundResource(R.drawable.borderinput);
                } else {
                    ilosc3.setBackgroundResource(R.drawable.border);
                }
            }


        };
        ilosc3.addTextChangedListener(autoAddTextWatcher);

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp5 = spinner5.getSelectedItem().toString();
                if (!sp5.equals("Rodzaj"))
                    spinnercontainer5.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp6 = spinner4.getSelectedItem().toString();
                if (!sp6.equals("Badania"))
                    spinnercontainer6.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nr4.length() > 0) {
                    nr4.setBackgroundResource(R.drawable.borderinput);
                } else {
                    nr4.setBackgroundResource(R.drawable.border);
                }
            }


        };
        nr4.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasj4.length() > 0) {
                    czasj4.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasj4.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasj4.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (czasp4.length() > 0) {
                    czasp4.setBackgroundResource(R.drawable.borderinput);
                } else {
                    czasp4.setBackgroundResource(R.drawable.border);
                }
            }


        };
        czasp4.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (podzial4.length() > 0) {
                    podzial4.setBackgroundResource(R.drawable.borderinput);
                } else {
                    podzial4.setBackgroundResource(R.drawable.border);
                }
            }


        };
        podzial4.addTextChangedListener(autoAddTextWatcher);
        podzial4.setOnTouchListener((view114, motionEvent) -> {
            if (podzial1.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3")))) {
                podzial1.setText("25");
                podzial2.setText("25");
                podzial3.setText("25");
                podzial4.setText("25");
                podzial4.setOnTouchListener(null);
            }
            return false;
        });

        nr4.setOnTouchListener((view120, motionEvent) -> {
            if (podzial1.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3")))) {
                podzial1.setText("25");
                podzial2.setText("25");
                podzial3.setText("25");
                podzial4.setText("25");
                podzial4.setOnTouchListener(null);
            }
            return false;
        });

        czasj4.setOnTouchListener((view115, motionEvent) -> {
            if (podzial1.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3")))) {
                podzial1.setText("25");
                podzial2.setText("25");
                podzial3.setText("25");
                podzial4.setText("25");
                podzial4.setOnTouchListener(null);
            }
            return false;
        });

        czasp4.setOnTouchListener((view116, motionEvent) -> {
            if (podzial1.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3")))) {
                podzial1.setText("25");
                podzial2.setText("25");
                podzial3.setText("25");
                podzial4.setText("25");
                podzial4.setOnTouchListener(null);
            }
            return false;
        });

        ilosc4.setOnTouchListener((view117, motionEvent) -> {
            if (podzial1.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3") && (podzial2.getText().toString().equals("33.3")))) {
                podzial1.setText("25");
                podzial2.setText("25");
                podzial3.setText("25");
                podzial4.setText("25");
                podzial4.setOnTouchListener(null);
            }
            return false;
        });


        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ilosc4.length() > 0) {
                    ilosc4.setBackgroundResource(R.drawable.borderinput);
                } else {
                    ilosc4.setBackgroundResource(R.drawable.border);
                }
            }


        };
        ilosc4.addTextChangedListener(autoAddTextWatcher);

        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp7 = spinner7.getSelectedItem().toString();
                if (!sp7.equals("Rodzaj"))
                    spinnercontainer7.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp8 = spinner8.getSelectedItem().toString();
                if (!sp8.equals("Badania"))
                    spinnercontainer8.setBackgroundResource(R.drawable.borderinput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//        WYSYŁANIE

        String dataDir = path();

        wyslij.setOnClickListener(view118 -> {
//                IFY
            String sp1 = spinner1.getSelectedItem().toString();
            String sp2 = spinner2.getSelectedItem().toString();
            if (czasp1.length() == 0) czasp1.setBackgroundResource(R.drawable.borderred);
            if (czasj1.length() == 0) czasj1.setBackgroundResource(R.drawable.borderred);
            if (sp1.equals("Rodzaj")) spinnercontainer1.setBackgroundResource(R.drawable.borderred);
            if (sp2.equals("Badania"))
                spinnercontainer2.setBackgroundResource(R.drawable.borderred);
            if (podzial1.length() == 0) podzial1.setBackgroundResource(R.drawable.borderred);
            if (ilosc1.length() == 0) ilosc1.setBackgroundResource(R.drawable.borderred);
            if (nr1.length() == 0) nr1.setBackgroundResource(R.drawable.borderred);
            if (czasp1.length() == 0 || czasj1.length() == 0 || sp1.equals("Rodzaj") || sp2.equals("Badania") || podzial1.length() == 0 || ilosc1.length() == 0 || nr1.length() == 0)
                Toast.makeText(Raport.this, "Uzupełnij wymagane pola!", Toast.LENGTH_LONG).show();
            else if (isNetworkOnline()) {
                Toast.makeText(Raport.this, "Brak połączenia z internetem!", Toast.LENGTH_SHORT).show();
            } else {

                try {
//              EXCEL
                    Workbook wbk;
                    try {
                        wbk = new Workbook(dataDir + myPrefs.getString("keyname", null) + ".xlsx");
                    } catch (Exception e) {
                        e.printStackTrace();
                        wbk = new Workbook();
                        File file = new File(dataDir + "logi3.txt");
                        PrintStream ps = null;
                        try {
                            ps = new PrintStream(file);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace(Objects.requireNonNull(ps));
                    }

                    Worksheet worksheet = wbk.getWorksheets().get(0);

                    try {
                        wbk.getWorksheets().removeAt(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                  ŁĄCZENIA
                    Cells cells = worksheet.getCells();
                    cells.merge(0, 0, 1, 4);
                    cells.merge(1, 0, 2, 1);
                    cells.merge(1, 1, 2, 1);
                    cells.merge(1, 2, 2, 1);
                    cells.merge(1, 4, 2, 1);
                    cells.merge(1, 5, 1, 4);
                    cells.merge(1, 9, 1, 2);
                    cells.merge(1, 11, 2, 1);
                    cells.merge(1, 3, 2, 1);

//                  TEKSTY
                    worksheet.getCells().get(1, 0).setValue("Rok");
                    worksheet.getCells().get(1, 1).setValue("Miesiąc");
                    worksheet.getCells().get(1, 2).setValue("Dzień");
                    worksheet.getCells().get(1, 3).setValue("Dzień tyg.");
                    worksheet.getCells().get(1, 4).setValue("Numer projektu");
                    worksheet.getCells().get(1, 5).setValue("Praca");
                    worksheet.getCells().get(2, 5).setValue("Podział %");
                    worksheet.getCells().get(2, 6).setValue("Rodzaj");
                    worksheet.getCells().get(2, 7).setValue("Czas pracy");
                    worksheet.getCells().get(2, 8).setValue("Czas jazdy");
                    worksheet.getCells().get(1, 9).setValue("Badania");
                    worksheet.getCells().get(2, 9).setValue("Rodzaj");
                    worksheet.getCells().get(2, 10).setValue("Ilość");
                    worksheet.getCells().get(2, 11).setValue("Uwagi");

                    worksheet.getCells().get(0, 0).setValue(myPrefs.getString("keyname", null));

//                  WYMIARY
                    cells.setColumnWidth(0, 4.3);
                    cells.setColumnWidth(1, 4.3);
                    cells.setColumnWidth(2, 4.3);
                    cells.setColumnWidth(3, 4.3);
                    cells.setColumnWidth(4, 12.40);
                    cells.setColumnWidth(5, 8);
                    cells.setColumnWidth(6, 8);
                    cells.setColumnWidth(7, 9);
                    cells.setColumnWidth(8, 8.8);
                    cells.setColumnWidth(9, 8);
                    cells.setColumnWidth(5, 8);
                    cells.setColumnWidth(11, 68);
                    cells.setRowHeight(2, 36);

//                  STYLE

                    worksheet.freezePanes(3, 4, 200, 200);
                    Style a = worksheet.getCells().getColumns().get(0).getStyle();
                    a.setForegroundColor(Color.getLightGray());
                    a.setPattern(BackgroundType.SOLID);
                    a.setBorder(BorderType.RIGHT_BORDER, 7, Color.getBlack());
                    a.setBorder(BorderType.BOTTOM_BORDER, 7, Color.getBlack());
                    a.setBorder(BorderType.VERTICAL, 7, Color.getBlack());
                    a.setBorder(BorderType.HORIZONTAL, 7, Color.getBlack());

                    a.setBorder(1, 7, Color.getBlack());
                    a.setBorder(2, 7, Color.getBlack());
                    a.setBorder(3, 7, Color.getBlack());
                    a.setBorder(4, 7, Color.getBlack());

                    StyleFlag a1 = new StyleFlag();
                    a1.setCellShading(true);
                    a1.setBorders(true);
                    cells.getColumns().get(0).applyStyle(a, a1);
                    cells.getColumns().get(1).applyStyle(a, a1);
                    cells.getColumns().get(2).applyStyle(a, a1);
                    cells.getColumns().get(3).applyStyle(a, a1);


                    StyleFlag b1 = new StyleFlag();
                    b1.setBorders(true);
                    b1.setCellShading(true);

                    Style b = worksheet.getCells().getRows().get(1).getStyle();
                    b.setForegroundColor(Color.getLightGray());
                    b.setPattern(BackgroundType.SOLID);
                    b.setBorder(BorderType.RIGHT_BORDER, 1, Color.getBlack());
                    b.setBorder(BorderType.BOTTOM_BORDER, 1, Color.getBlack());
                    b.setBorder(BorderType.VERTICAL, 1, Color.getBlack());
                    b.setBorder(BorderType.HORIZONTAL, 1, Color.getBlack());
                    b.setBorder(1, 1, Color.getBlack());
                    b.setBorder(2, 1, Color.getBlack());
                    b.setBorder(3, 1, Color.getBlack());
                    b.setBorder(4, 1, Color.getBlack());
                    cells.get("D1").setStyle(b, b1);
                    cells.getRows().get(1).applyStyle(b, b1);
                    cells.getRows().get(2).applyStyle(b, b1);


//                      OSOBA
                    Style style = worksheet.getCells().get(0, 0).getStyle();
                    Font font = style.getFont();
                    font.setSize(18);
                    font.setBold(true);
                    style.setBackgroundColor(Color.getWhite());
                    style.setForegroundColor(Color.getWhite());
                    cells.get(0, 0).setStyle(style);
//                      NAGŁÓWKI
                    Style style3 = cells.get("A3").getStyle();
                    style3.setRotationAngle(90);
                    style3.setHorizontalAlignment(TextAlignmentType.CENTER);
                    style3.setVerticalAlignment(TextAlignmentType.CENTER);
                    cells.get("A3").setStyle(style3);
                    cells.get("A2").setStyle(style3);
                    cells.get("B3").setStyle(style3);
                    cells.get("B2").setStyle(style3);
                    cells.get("C3").setStyle(style3);
                    cells.get("C2").setStyle(style3);
                    cells.get("D3").setStyle(style3);
                    cells.get("D2").setStyle(style3);

                    Style style2 = worksheet.getCells().get("F2").getStyle();
                    style2.setHorizontalAlignment(TextAlignmentType.CENTER);
                    style2.setVerticalAlignment(TextAlignmentType.CENTER);
                    Font font2 = style2.getFont();
                    font2.setBold(true);
                    cells.get("F2").setStyle(style2);
                    cells.get("J2").setStyle(style2);
                    cells.getRows().get(1).applyStyle(style2, a1);

                    Style style4 = worksheet.getCells().get("F3").getStyle();
                    style4.setHorizontalAlignment(TextAlignmentType.CENTER);
                    style4.setVerticalAlignment(TextAlignmentType.CENTER);
                    cells.get("E2").setStyle(style4);
                    cells.get("E3").setStyle(style4);
                    cells.get("F3").setStyle(style4);
                    cells.get("G3").setStyle(style4);
                    cells.get("H3").setStyle(style4);
                    cells.get("I3").setStyle(style4);
                    cells.get("J3").setStyle(style4);
                    cells.get("K3").setStyle(style4);
                    cells.get("L3").setStyle(style4);

//                    DANE
                    int j = 3;
                    String s;

                    for (int i = 2; i <= j; i++) {
                        s = worksheet.getCells().get(j, 4).getStringValue();
                        if (s.length() != 0) j++;
                        s = worksheet.getCells().get(j, 4).getStringValue();
                        if (s.length() == 0) {
                            if (!checkboxdata.isChecked()) {
                                worksheet.getCells().get(j, 0).putValue("" + dateTime.charAt(6) + dateTime.charAt(7));
                                worksheet.getCells().get(j, 1).putValue("" + dateTime.charAt(3) + dateTime.charAt(4));
                                worksheet.getCells().get(j, 2).putValue("" + dateTime.charAt(0) + dateTime.charAt(1));
                                worksheet.getCells().get(j, 3).putValue(dzientyg);
                            } else {
                                worksheet.getCells().get(j, 0).putValue(rok.getText().toString());
                                worksheet.getCells().get(j, 1).putValue(miesiac.getText().toString());
                                worksheet.getCells().get(j, 2).putValue(dzien.getText().toString());
                                worksheet.getCells().get(j, 3).putValue(dzientyg2[0].getText().toString());
                            }
                            worksheet.getCells().get(j, 4).putValue("X");
                            worksheet.getCells().get(j, 4).putValue(nr1.getText().toString());
                            worksheet.getCells().get(j, 5).putValue(podzial1.getText().toString());
                            worksheet.getCells().get(j, 6).putValue(spinner1.getSelectedItem().toString());
                            worksheet.getCells().get(j, 7).putValue(czasp1.getText().toString());
                            worksheet.getCells().get(j, 8).putValue(czasj1.getText().toString());
                            worksheet.getCells().get(j, 9).putValue(spinner2.getSelectedItem().toString());
                            worksheet.getCells().get(j, 10).putValue(ilosc1.getText().toString());

                            worksheet.getCells().get(j, 11).putValue(uwagi1.getText().toString());//nr

                            if (nr2.length() != 0) {
                                if (!checkboxdata.isChecked()) {
                                    worksheet.getCells().get(j + 1, 0).putValue("" + dateTime.charAt(6) + dateTime.charAt(7));
                                    worksheet.getCells().get(j + 1, 1).putValue("" + dateTime.charAt(3) + dateTime.charAt(4));
                                    worksheet.getCells().get(j + 1, 2).putValue("" + dateTime.charAt(0) + dateTime.charAt(1));
                                    worksheet.getCells().get(j + 1, 3).putValue(dzientyg);
                                } else {
                                    worksheet.getCells().get(j + 1, 0).putValue(rok.getText().toString());
                                    worksheet.getCells().get(j + 1, 1).putValue(miesiac.getText().toString());
                                    worksheet.getCells().get(j + 1, 2).putValue(dzien.getText().toString());
                                    worksheet.getCells().get(j + 1, 3).putValue(dzientyg2[0].getText().toString());
                                }
                                worksheet.getCells().get(j + 1, 4).putValue(nr2.getText().toString());
                                worksheet.getCells().get(j + 1, 5).putValue(podzial2.getText().toString());
                                worksheet.getCells().get(j + 1, 6).putValue(spinner3.getSelectedItem().toString());
                                worksheet.getCells().get(j + 1, 7).putValue(czasp2.getText().toString());
                                worksheet.getCells().get(j + 1, 8).putValue(czasj2.getText().toString());
                                worksheet.getCells().get(j + 1, 9).putValue(spinner4.getSelectedItem().toString());
                                worksheet.getCells().get(j + 1, 10).putValue(ilosc2.getText().toString());
                                worksheet.getCells().get(j + 1, 11).putValue(uwagi2.getText().toString());//nr
                            }

                            if (nr3.length() != 0) {
                                if (!checkboxdata.isChecked()) {
                                    worksheet.getCells().get(j + 2, 0).putValue("" + dateTime.charAt(6) + dateTime.charAt(7));
                                    worksheet.getCells().get(j + 2, 1).putValue("" + dateTime.charAt(3) + dateTime.charAt(4));
                                    worksheet.getCells().get(j + 2, 2).putValue("" + dateTime.charAt(0) + dateTime.charAt(1));
                                    worksheet.getCells().get(j + 2, 3).putValue(dzientyg);
                                } else {
                                    worksheet.getCells().get(j + 2, 0).putValue(rok.getText().toString());
                                    worksheet.getCells().get(j + 2, 1).putValue(miesiac.getText().toString());
                                    worksheet.getCells().get(j + 2, 2).putValue(dzien.getText().toString());
                                    worksheet.getCells().get(j + 2, 3).putValue(dzientyg2[0].getText().toString());
                                }
                                worksheet.getCells().get(j + 2, 4).putValue(nr3.getText().toString());
                                worksheet.getCells().get(j + 2, 5).putValue(podzial3.getText().toString());
                                worksheet.getCells().get(j + 2, 6).putValue(spinner5.getSelectedItem().toString());
                                worksheet.getCells().get(j + 2, 7).putValue(czasp3.getText().toString());
                                worksheet.getCells().get(j + 2, 8).putValue(czasj3.getText().toString());
                                worksheet.getCells().get(j + 2, 9).putValue(spinner6.getSelectedItem().toString());
                                worksheet.getCells().get(j + 2, 10).putValue(ilosc3.getText().toString());
                                worksheet.getCells().get(j + 2, 11).putValue(uwagi3.getText().toString());
                            }

                            if (nr4.length() != 0) {
                                if (!checkboxdata.isChecked()) {
                                    worksheet.getCells().get(j + 3, 0).putValue("" + dateTime.charAt(6) + dateTime.charAt(7));
                                    worksheet.getCells().get(j + 3, 1).putValue("" + dateTime.charAt(3) + dateTime.charAt(4));
                                    worksheet.getCells().get(j + 3, 2).putValue("" + dateTime.charAt(0) + dateTime.charAt(1));
                                    worksheet.getCells().get(j + 3, 3).putValue(dzientyg);
                                } else {
                                    worksheet.getCells().get(j + 3, 0).putValue(rok.getText().toString());
                                    worksheet.getCells().get(j + 3, 1).putValue(miesiac.getText().toString());
                                    worksheet.getCells().get(j + 3, 2).putValue(dzien.getText().toString());
                                    worksheet.getCells().get(j + 3, 3).putValue(dzientyg2[0].getText().toString());
                                }
                                worksheet.getCells().get(j + 3, 4).putValue(nr4.getText().toString());
                                worksheet.getCells().get(j + 3, 5).putValue(podzial4.getText().toString());
                                worksheet.getCells().get(j + 3, 6).putValue(spinner7.getSelectedItem().toString());
                                worksheet.getCells().get(j + 3, 7).putValue(czasp4.getText().toString());
                                worksheet.getCells().get(j + 3, 8).putValue(czasj4.getText().toString());
                                worksheet.getCells().get(j + 3, 9).putValue(spinner8.getSelectedItem().toString());
                                worksheet.getCells().get(j + 3, 10).putValue(ilosc4.getText().toString());
                                worksheet.getCells().get(j + 3, 11).putValue(uwagi4.getText().toString());
                            }
                            break;
                        }
                    }

                    try {
                        wbk.save(dataDir + myPrefs.getString("keyname", null) + ".xlsx");
                    } catch (Exception e) {
                        e.printStackTrace();
                        File file = new File(dataDir + "logi12.txt");
                        PrintStream ps = null;
                        try {
                            ps = new PrintStream(file);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace(Objects.requireNonNull(ps));
                    }

                    if (dataa.length() > 0) {
                        data5[0] = dateTime1[0];
                    } else {
                        data5[0] = simpleDateFormatt.format(calendarr.getTime());
                    }

                    try {
                        Task<String> getId = driveServiceHelper.searchFile();
                        getId.addOnCompleteListener(task -> {
                            String idd = "";
                            if (getId.isSuccessful()) {
                                if (getId.getResult() != null) idd = getId.getResult();

                                if (idd.length() < 15) {
                                    ProgressDialog progressDialog = new ProgressDialog(Raport.this);
                                    progressDialog.setTitle("Wysyłam plik na serwer");
                                    progressDialog.setMessage("Proszę czekać.");
                                    progressDialog.show();

                                    driveServiceHelper.createFile(dataDir + myPrefs.getString("keyname", null) + ".xlsx").addOnSuccessListener(s1 -> {
                                        progressDialog.dismiss();
                                        AddData(data5[0]);
                                        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                                        wyslij.setEnabled(false);
                                        Toast.makeText(getApplicationContext(), "Dokument przesłany poprawnie.", Toast.LENGTH_LONG).show();
                                    }).addOnFailureListener(e -> {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "1. Błąd przesyłania. Sprawdź połączenie internetowe.", Toast.LENGTH_LONG).show();

                                        File file = new File(dataDir + "logi1.txt");
                                        PrintStream ps = null;
                                        try {
                                            ps = new PrintStream(file);
                                        } catch (FileNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                        e.printStackTrace(Objects.requireNonNull(ps));
                                    });
                                } else {
                                    ProgressDialog progressDialog2 = new ProgressDialog(Raport.this);
                                    progressDialog2.setTitle("Uaktualniam plik na serwerze.");
                                    progressDialog2.setMessage("Proszę czekać.");
                                    progressDialog2.show();

                                    driveServiceHelper.updateFile(dataDir + myPrefs.getString("keyname", null) + ".xlsx").addOnSuccessListener(s12 -> {
                                        AddData(data5[0]);
                                        wyslij.setBackgroundColor(ContextCompat.getColor(Raport.this, R.color.lgrey));
                                        wyslij.setEnabled(false);
                                        progressDialog2.dismiss();
                                        Toast.makeText(getApplicationContext(), "Dokument przesłany poprawnie.", Toast.LENGTH_LONG).show();
                                    }).addOnFailureListener(e -> {
                                        progressDialog2.dismiss();
                                        Toast.makeText(getApplicationContext(), "2. Błąd przesyłania. Sprawdź połączenie internetowe.", Toast.LENGTH_LONG).show();
                                        File file = new File(dataDir + "logi1.txt");
                                        PrintStream ps = null;
                                        try {
                                            ps = new PrintStream(file);
                                        } catch (FileNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                        e.printStackTrace(Objects.requireNonNull(ps));
                                    });
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "3. Błąd przesyłania. Sprawdź połączenie internetowe.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    File file = new File(dataDir + "logi1.txt");
                    PrintStream ps = null;
                    try {
                        ps = new PrintStream(file);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace(Objects.requireNonNull(ps));
                }
            }
        });


//WYSŁANE
        wyslane.setOnClickListener(view119 -> openXLS());
    }


    private void openXLS() {
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String dataDir = path();
        File xls = new File(dataDir + myPrefs.getString("keyname", null) + ".xlsx");
        Uri uri = FileProvider.getUriForFile(Raport.this, getApplicationContext().getPackageName() + ".provider", xls);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Raport.this, "Brak aplikacji do otwierania plików excela.", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope(DriveScopes.DRIVE_FILE)).build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(client.getSignInIntent(), 400);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                handleSignInIntent(data);
            }
        }
    }

    private void handleSignInIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(googleSignInAccount -> {
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(Raport.this, Collections.singleton(DriveScopes.DRIVE_FILE));
            credential.setSelectedAccount(googleSignInAccount.getAccount());
            HttpTransport t = new NetHttpTransport();
            Drive googleDriveService = new Drive.Builder(t, new GsonFactory(), credential).setApplicationName("TDM").build();

            driveServiceHelper = new DriveServiceHelper(googleDriveService);
        }).addOnFailureListener(e -> {

        });
    }

    public void AddData(String data5) {
        boolean insertData = databaseHelper2.addData(data5);
    }

    class DriveServiceHelper {
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        private final Executor mExecutor = Executors.newSingleThreadExecutor();
        private final Drive mDriveService;

        public DriveServiceHelper(Drive driveService) {
            mDriveService = driveService;
        }

        /**
         * Creates a text file in the user's My Drive folder and returns its file ID.
         */
        public Task<String> createFile(String filePath) {

            String parent = "1Sy8t615DoOAOc6m2L6MOICoDBBrpz3Wx";
            String name = myPrefs.getString("keyname", null) + ".xlsx";
            return Tasks.call(mExecutor, () -> {
                com.google.api.services.drive.model.File metadata = new com.google.api.services.drive.model.File().setName(name).setParents(Collections.singletonList(parent));

                File file = new File(filePath);

                FileContent mediaContent = new FileContent("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", file);

                com.google.api.services.drive.model.File myFile = null;
                try {

                    myFile = mDriveService.files().create(metadata, mediaContent).setFields("id, parents").execute();
                    return myFile.getId();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (myFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }

                return myFile.getId();
            });
        }

        public Task<String> updateFile(String filePath) {
            String name = myPrefs.getString("keyname", null) + ".xlsx";
            return Tasks.call(mExecutor, () -> {
                com.google.api.services.drive.model.File metadata = new com.google.api.services.drive.model.File().setName(name);

                File file = new File(filePath);

                FileContent mediaContent = new FileContent("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", file);

                com.google.api.services.drive.model.File myFile = null;
                try {
                    myFile = mDriveService.files().update(myPrefs.getString("id", null), metadata, mediaContent).setFields("id").execute();
                    return myFile.getId();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (myFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }

                return myFile.getId();
            });
        }

        public Task<String> searchFile() {

            return Tasks.call(mExecutor, () -> {
                String fileId = null;
                String pageToken = null;


                FileList result = mDriveService.files().list().setQ("name = '" + myPrefs.getString("keyname", null) + ".xlsx' and '1Sy8t615DoOAOc6m2L6MOICoDBBrpz3Wx' in parents and trashed = false").setSpaces("drive").setFields("files(id)").setPageToken(pageToken).execute();
                for (com.google.api.services.drive.model.File f : result.getFiles()) {
                    fileId = f.getId();
                }

                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("id", fileId);
                editor.apply();

                return fileId;
            });
        }
    }

    public static String path() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Raporty/";
        } else {
            dir = Environment.getExternalStorageDirectory() + "/Download/Raporty/";
        }

        return dir;
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return !status;
    }

}