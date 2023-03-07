package com.example.metris;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

public class Eq extends AppCompatActivity {

    TextView txt;
    Bitmap scaledbmp, bmp;
    Date dateObj;
    DateFormat dateFormat;
    DriveServiceHelper driveServiceHelper;
    EditText nrrej;
    int pageWidth = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eq);
        requestSignIn();
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        EditText nr = (EditText) findViewById(R.id.nr);
        EditText miejsce = (EditText) findViewById(R.id.miejsce);
        EditText przekazujacy = (EditText) findViewById(R.id.przekazujacy);
        EditText przejmujacy = (EditText) findViewById(R.id.przejmujacy);
        EditText uwagi3 = (EditText) findViewById(R.id.uwagi3);
        ConstraintLayout asd = findViewById(R.id.asd);
        ConstraintLayout asdd = findViewById(R.id.asdd);
        ImageView logo = findViewById(R.id.logo);
        View view = findViewById(R.id.view);
        View view4 = findViewById(R.id.view4);
        View view5 = findViewById(R.id.view5);
        View view6 = findViewById(R.id.view6);
        View view7 = findViewById(R.id.view7);
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView8 = findViewById(R.id.textView8);
        TextView textView9 = findViewById(R.id.textView9);
        TextView textView10 = findViewById(R.id.textView10);
        TextView textView11 = findViewById(R.id.textView11);
        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        TextView textView14 = findViewById(R.id.textView14);
        TextView textView15 = findViewById(R.id.textView15);
        TextView textView16 = findViewById(R.id.textView16);
        TextView textView19 = findViewById(R.id.textView19);
        TextView textView22 = findViewById(R.id.textView22);
        TextView textView23 = findViewById(R.id.textView23);
        TextView textView27 = findViewById(R.id.textView27);
        TextView textView28 = findViewById(R.id.textView28);
        TextView textView29 = findViewById(R.id.textView29);
        TextView textView30 = findViewById(R.id.textView30);
        TextView textView33 = findViewById(R.id.textView35);
        TextView textView36 = findViewById(R.id.textView36);
        TextView textView37 = findViewById(R.id.textView37);
        TextView textView39 = findViewById(R.id.textView39);
        TextView textView40 = findViewById(R.id.textView40);
        TextView textView41 = findViewById(R.id.textView41);
        TextView textView42 = findViewById(R.id.textView42);
        TextView textView54 = findViewById(R.id.textView54);
        TextView textView55 = findViewById(R.id.textView55);
        TextView textView56 = findViewById(R.id.textView56);
        TextView textView57 = findViewById(R.id.textView57);
        TextView textView58 = findViewById(R.id.textView58);
        TextView textView59 = findViewById(R.id.textView59);
        CheckBox cb1 = (CheckBox) findViewById(R.id.cb1);
        CheckBox cb2 = (CheckBox) findViewById(R.id.cb2);
        CheckBox cb3 = (CheckBox) findViewById(R.id.cb3);
        CheckBox cb4 = (CheckBox) findViewById(R.id.cb4);
        CheckBox cb5 = (CheckBox) findViewById(R.id.cb5);
        CheckBox cb6 = (CheckBox) findViewById(R.id.cb6);
        CheckBox cb7 = (CheckBox) findViewById(R.id.cb7);
        CheckBox cb8 = (CheckBox) findViewById(R.id.cb8);
        CheckBox cb9 = (CheckBox) findViewById(R.id.cb9);
        CheckBox cb10 = (CheckBox) findViewById(R.id.cb10);
        CheckBox cb11 = (CheckBox) findViewById(R.id.cb11);
        CheckBox cb12 = (CheckBox) findViewById(R.id.cb12);
        CheckBox cb13 = (CheckBox) findViewById(R.id.cb13);
        CheckBox cb14 = (CheckBox) findViewById(R.id.cb14);
        CheckBox cb15 = (CheckBox) findViewById(R.id.cb15);
        CheckBox cb16 = (CheckBox) findViewById(R.id.cb16);
        CheckBox cb17 = (CheckBox) findViewById(R.id.cb17);
        CheckBox cb18 = (CheckBox) findViewById(R.id.cb18);
        CheckBox cb19 = (CheckBox) findViewById(R.id.cb19);
        CheckBox cb20 = (CheckBox) findViewById(R.id.cb20);
        CheckBox cb21 = (CheckBox) findViewById(R.id.cb21);
        CheckBox cb22 = (CheckBox) findViewById(R.id.cb22);
        CheckBox cb23 = (CheckBox) findViewById(R.id.cb23);
        CheckBox cb24 = (CheckBox) findViewById(R.id.cb24);
        CheckBox cb25 = (CheckBox) findViewById(R.id.cb25);
        CheckBox cb26 = (CheckBox) findViewById(R.id.cb26);
        CheckBox cb27 = (CheckBox) findViewById(R.id.cb27);
        CheckBox cb28 = (CheckBox) findViewById(R.id.cb28);
        CheckBox cb29 = (CheckBox) findViewById(R.id.cb29);

        txt = (TextView) findViewById(R.id.tvdate);
        TextView tvdate = (TextView) findViewById(R.id.tvdate);

        if ((myPrefs.getString("style", null).equals("dark"))) {
            asd.setBackgroundResource(R.color.dark);
            asdd.setBackgroundResource(R.color.dark);
            miejsce.setHintTextColor(getResources().getColor(R.color.white));
            przejmujacy.setHintTextColor(getResources().getColor(R.color.white));
            przekazujacy.setHintTextColor(getResources().getColor(R.color.white));
            nr.setHintTextColor(getResources().getColor(R.color.white));
            miejsce.setTextColor(getResources().getColor(R.color.white));
            przejmujacy.setTextColor(getResources().getColor(R.color.white));
            przekazujacy.setTextColor(getResources().getColor(R.color.white));
            nr.setTextColor(getResources().getColor(R.color.white));
            uwagi3.setTextColor(getResources().getColor(R.color.white));
            uwagi3.setHintTextColor(getResources().getColor(R.color.white));
            textView.setTextColor(getResources().getColor(R.color.white));
            textView2.setTextColor(getResources().getColor(R.color.white));
            textView3.setTextColor(getResources().getColor(R.color.white));
            textView4.setTextColor(getResources().getColor(R.color.white));
            textView5.setTextColor(getResources().getColor(R.color.white));
            textView10.setTextColor(getResources().getColor(R.color.white));
            textView11.setTextColor(getResources().getColor(R.color.white));
            textView12.setTextColor(getResources().getColor(R.color.white));
            textView13.setTextColor(getResources().getColor(R.color.white));
            textView14.setTextColor(getResources().getColor(R.color.white));
            textView15.setTextColor(getResources().getColor(R.color.white));
            textView16.setTextColor(getResources().getColor(R.color.white));
            textView19.setTextColor(getResources().getColor(R.color.white));
            textView22.setTextColor(getResources().getColor(R.color.white));
            textView23.setTextColor(getResources().getColor(R.color.white));
            textView27.setTextColor(getResources().getColor(R.color.white));
            textView28.setTextColor(getResources().getColor(R.color.white));
            textView29.setTextColor(getResources().getColor(R.color.white));
            textView30.setTextColor(getResources().getColor(R.color.white));
            textView33.setTextColor(getResources().getColor(R.color.white));
            textView36.setTextColor(getResources().getColor(R.color.white));
            textView37.setTextColor(getResources().getColor(R.color.white));
            textView39.setTextColor(getResources().getColor(R.color.white));
            textView40.setTextColor(getResources().getColor(R.color.white));
            textView41.setTextColor(getResources().getColor(R.color.white));
            textView42.setTextColor(getResources().getColor(R.color.white));
            textView54.setTextColor(getResources().getColor(R.color.white));
            textView57.setTextColor(getResources().getColor(R.color.white));
            textView55.setTextColor(getResources().getColor(R.color.white));
            textView56.setTextColor(getResources().getColor(R.color.white));
            textView58.setTextColor(getResources().getColor(R.color.white));
            textView59.setTextColor(getResources().getColor(R.color.white));
            textView8.setTextColor(getResources().getColor(R.color.white));
            textView9.setTextColor(getResources().getColor(R.color.white));
            logo.setImageResource(R.drawable.logohw);
            tvdate.setTextColor(getResources().getColor(R.color.white));
            view.setBackgroundColor(getResources().getColor(R.color.dark));
            view4.setBackgroundColor(getResources().getColor(R.color.white));
            view5.setBackgroundColor(getResources().getColor(R.color.white));
            view6.setBackgroundColor(getResources().getColor(R.color.white));
            view7.setBackgroundColor(getResources().getColor(R.color.white));
        }


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoh);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 267, 109, false);


        przejmujacy.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        przekazujacy.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        miejsce.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        Button wyslij = (Button) findViewById(R.id.wyslij);
        Button wyslane = (Button) findViewById(R.id.wyslane);

        /// KALENDARZ ////
        String dateTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        tvdate.setText(dateTime);

        wyslij.setBackgroundColor(getResources().getColor(R.color.teal_700));
        wyslane.setBackgroundColor(getResources().getColor(R.color.teal_200));

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
                if (nr.length() > 0) {
                    nr.setBackgroundResource(R.drawable.borderinput);
                } else {
                    nr.setBackgroundResource(R.drawable.border);
                }
            }
        };
        nr.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (miejsce.length() > 0) {
                    miejsce.setBackgroundResource(R.drawable.borderinput);
                } else {
                    miejsce.setBackgroundResource(R.drawable.border);
                }
            }
        };
        miejsce.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (przekazujacy.length() > 0) {
                    przekazujacy.setBackgroundResource(R.drawable.borderinput);
                } else {
                    przekazujacy.setBackgroundResource(R.drawable.border);
                }
            }
        };
        przekazujacy.addTextChangedListener(autoAddTextWatcher);

        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (przejmujacy.length() > 0) {
                    przejmujacy.setBackgroundResource(R.drawable.borderinput);
                } else {
                    przejmujacy.setBackgroundResource(R.drawable.border);
                }
            }
        };
        przejmujacy.addTextChangedListener(autoAddTextWatcher);

        wyslane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChooserDialog(Eq.this)
                        .withStartFile(Environment.getExternalStorageDirectory().getPath() + "/Download/Przekazania/")
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri uri = FileProvider.getUriForFile(Eq.this, getApplicationContext().getPackageName() + ".provider", pathFile);
                                intent.setDataAndType(uri, "application/pdf");
                                startActivity(intent);
                            }
                        })
                        // to handle the back key pressed or clicked outside the dialog:
                        .withOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                Log.d("CANCEL", "CANCEL");
                                dialog.cancel(); // MUST have
                            }
                        })
                        .build()
                        .show();
            }
        });

        wyslij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             {
                 if (nr.length() == 0) nr.setBackgroundResource(R.drawable.borderred);
                 if (miejsce.length() == 0) miejsce.setBackgroundResource(R.drawable.borderred);
                 if (przekazujacy.length() == 0) przekazujacy.setBackgroundResource(R.drawable.borderred);
                 if (przejmujacy.length() == 0) przejmujacy.setBackgroundResource(R.drawable.borderred);

                 }
                 if (nr.length() == 0 ||
                         miejsce.length() == 0  ||
                         przejmujacy.length() == 0 ||
                         przekazujacy.length() == 0) Toast.makeText(Eq.this, "Uzupełnij wymagane pola!", Toast.LENGTH_LONG).show();
                 else {
                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                 SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd HH-mm");
                 Date now = new Date();
                    String filePath = Environment.getExternalStorageDirectory().getPath() + "/Download/Przekazania/" + formatter.format(now) + ".pdf";
                    File file = new File(filePath);
                    PdfDocument myPdfDocument = new PdfDocument();
                    Paint myPaint = new Paint();
                    Paint titlePaint = new Paint();
                    Paint titlePaintCenter = new Paint();
                    Paint data = new Paint();
                    Paint tabelka = new Paint();
                    Paint towar = new Paint();
                    Paint montaz = new Paint();
                    Paint wymiary = new Paint();
                    Paint uwagip = new Paint();
                    Paint kwadrat = new Paint();
                    Paint kwadrat2 = new Paint();

                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                    Canvas canvas = myPage1.getCanvas();

                    canvas.drawBitmap(scaledbmp, 50, 50, myPaint);
                 dateObj = new Date();
                 Typeface type = ResourcesCompat.getFont(Eq.this, R.font.lato);
                 Typeface bold = ResourcesCompat.getFont(Eq.this, R.font.latob);
                    titlePaint.setTypeface(bold);
                    dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    data.setTextSize(20);
                    data.setTextAlign(Paint.Align.RIGHT);

                    canvas.drawText("Data: " + dateFormat.format(dateObj), pageWidth - 60, 90, data);
                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    canvas.drawText("Godzina: " + dateFormat.format(dateObj), pageWidth - 60, 130, data);
                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(bold);
                    titlePaint.setTextSize(40);
                    canvas.drawText("Dokument przekazania sprzętu", pageWidth / 2, 280, titlePaint);

                    titlePaint.setTypeface(type);
                    titlePaint.setTextAlign(Paint.Align.LEFT);
                    titlePaint.setTextSize(20);
                    canvas.drawText("Przekazujący: " + przekazujacy.getText(), 50, 380, titlePaint);
                    canvas.drawText("Przejmujący: " + przejmujacy.getText(), 630, 380, titlePaint);
                 canvas.drawText("Miejsce: " + miejsce.getText(), 50, 420, titlePaint);
                 canvas.drawText("Numer projektu: " + nr.getText(), 630, 420, titlePaint);
                    canvas.drawLine(50, 485, 1150, 485, tabelka);
                    canvas.drawLine(50, 495, 1150, 495, tabelka);
                    canvas.drawLine(50, 990, 580, 990, tabelka);

                 canvas.drawLine(620, 750, 1150, 750, tabelka);
                 canvas.drawLine(620, 950, 1150, 950, tabelka);

                 canvas.drawLine(600, 515, 600, 1460, tabelka);

                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(bold);
                    canvas.drawText("SLT", 325, 530, titlePaint);
                 canvas.drawText("Anchors", 875, 530, titlePaint);
                 canvas.drawText("PIT", 875, 780, titlePaint);
                 canvas.drawText("Inne", 875, 980, titlePaint);
                    titlePaint.setTypeface(type);
                    canvas.drawText("Przekazano", 450, 560, titlePaint);
                 canvas.drawText("Przekazano", 1070, 560, titlePaint);
                 canvas.drawText("Przekazano", 450, 1040, titlePaint);

                 canvas.drawText("Przekazano", 1070, 800, titlePaint);
                 canvas.drawText("Przekazano", 1070, 1000, titlePaint);

                    titlePaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Długa belka zielona", 320, 600, titlePaint);
                    canvas.drawText("Długa belka niebieska", 320, 640, titlePaint);
                    canvas.drawText("Zestaw niebieski 7x6 m", 320, 680, titlePaint);
                    canvas.drawText("Zestaw szary 7x6 m", 320, 720, titlePaint);
                    canvas.drawText("Zestaw szary skrzynkowy 7x6 m", 320, 760, titlePaint);
                    canvas.drawText("Zestaw czerwony 7x5 m", 320, 800, titlePaint);
                    canvas.drawText("Zestaw żółty 7x5 m", 320, 840, titlePaint);
                    canvas.drawText("Zestaw biały 7x5 m", 320, 880, titlePaint);
                 canvas.drawText("Zestaw HEB", 320, 920, titlePaint);
                 canvas.drawText("Belki białe 13,5 m", 320, 960, titlePaint);

                 titlePaint.setTypeface(bold);
                 canvas.drawText("DLT", 325, 1020, titlePaint);
                 titlePaint.setTypeface(type);

                 canvas.drawText("DLT1 (8G)", 320, 1080, titlePaint);
                 canvas.drawText("DLT2 (8G) (New)", 320, 1120, titlePaint);
                 canvas.drawText("DLT2 (PAX)", 320, 1160, titlePaint);
                 canvas.drawText("DLT3 (PAX)", 320, 1200, titlePaint);
                 canvas.drawText("DLT (Rental from DMT)", 320, 1240, titlePaint);
                 canvas.drawText("DLT4 (BLUE)", 320, 1280, titlePaint);
                 canvas.drawText("DLT5 (BLUE)", 270, 1320, titlePaint);
                 canvas.drawText("DLT (8G) (New)", 270, 1360, titlePaint);
                 canvas.drawText("Młot / Rama do badań DLT", 270, 1400, titlePaint);
                 canvas.drawText("Płyta dynamiczna", 270, 1440, titlePaint);

                 canvas.drawText("Zestaw SPT1/SPT5", 880, 600, titlePaint);
                 canvas.drawText("Zestaw SPT2", 880, 640, titlePaint);
                 canvas.drawText("Zestaw SPT3/SPT5", 880, 680, titlePaint);
                 canvas.drawText("Zestaw SPT4/SPT6", 880, 720, titlePaint);

                 canvas.drawText("PIT1", 880, 840, titlePaint);
                 canvas.drawText("PIT2 (New)", 880, 880, titlePaint);
                 canvas.drawText("PIT3 (New)", 880, 920, titlePaint);

                 canvas.drawText("Inne / Mieszane", 880, 1040, titlePaint);
                 canvas.drawText("Zestaw do pomiaru hałasu", 880, 1080, titlePaint);



                 
                 kwadrat.setStyle(Paint.Style.STROKE);
                 myPaint.setStrokeWidth(2);
                 kwadrat2.setTextSize(26);

                 canvas.drawRect(440, 585, 460, 605, kwadrat);
                 canvas.drawRect(440, 625, 460, 645, kwadrat);
                 canvas.drawRect(440, 665, 460, 685, kwadrat);
                 canvas.drawRect(440, 705, 460, 725, kwadrat);
                 canvas.drawRect(440, 745, 460, 765, kwadrat);
                 canvas.drawRect(440, 785, 460, 805, kwadrat);
                 canvas.drawRect(440, 825, 460, 845, kwadrat);
                 canvas.drawRect(440, 865, 460, 885, kwadrat);
                 canvas.drawRect(440, 905, 460, 925, kwadrat);
                 canvas.drawRect(440, 945, 460, 965, kwadrat);

                 canvas.drawRect(440, 1065, 460, 1085, kwadrat);
                 canvas.drawRect(440, 1105, 460, 1125, kwadrat);
                 canvas.drawRect(440, 1145, 460, 1165, kwadrat);
                 canvas.drawRect(440, 1185, 460, 1205, kwadrat);
                 canvas.drawRect(440, 1225, 460, 1245, kwadrat);
                 canvas.drawRect(440, 1265, 460, 1285, kwadrat);
                 canvas.drawRect(440, 1305, 460, 1325, kwadrat);
                 canvas.drawRect(440, 1345, 460, 1365, kwadrat);
                 canvas.drawRect(440, 1385, 460, 1405, kwadrat);
                 canvas.drawRect(440, 1425, 460, 1445, kwadrat);

                 canvas.drawRect(1060, 585, 1080, 605, kwadrat);
                 canvas.drawRect(1060, 625, 1080, 645, kwadrat);
                 canvas.drawRect(1060, 665, 1080, 685, kwadrat);
                 canvas.drawRect(1060, 705, 1080, 725, kwadrat);

                 canvas.drawRect(1060, 825, 1080, 845, kwadrat);
                 canvas.drawRect(1060, 865, 1080, 885, kwadrat);
                 canvas.drawRect(1060, 905, 1080, 925, kwadrat);

                 canvas.drawRect(1060, 1025, 1080, 1045, kwadrat);
                 canvas.drawRect(1060, 1065, 1080, 1085, kwadrat);



                 myPaint.setStrokeWidth(2);
                 titlePaint.setTextSize(26);
                 titlePaint.setTextAlign(Paint.Align.RIGHT);

                 if (cb1.isChecked()){
                     canvas.drawText("x", 456, 602, titlePaint);
                 }

                 if (cb2.isChecked()){
                     canvas.drawText("x", 456, 642, titlePaint);
                 }

                 if (cb3.isChecked()){
                     canvas.drawText("x", 456, 682, titlePaint);
                 }

                 if (cb4.isChecked()){
                     canvas.drawText("x", 456, 722, titlePaint);
                 }

                 if (cb5.isChecked()){
                     canvas.drawText("x", 456, 762, titlePaint);
                 }

                 if (cb6.isChecked()){
                     canvas.drawText("x", 456, 802, titlePaint);
                 }

                 if (cb7.isChecked()){
                     canvas.drawText("x", 456, 842, titlePaint);
                 }

                 if (cb8.isChecked()){
                     canvas.drawText("x", 456, 882, titlePaint);
                 }

                 if (cb9.isChecked()){
                     canvas.drawText("x", 456, 922, titlePaint);
                 }

                 if (cb10.isChecked()){
                     canvas.drawText("x", 456, 962, titlePaint);
                 }

                 if (cb11.isChecked()){
                     canvas.drawText("x", 456, 1082, titlePaint);
                 }

                 if (cb12.isChecked()){
                     canvas.drawText("x", 456, 1122, titlePaint);
                 }

                 if (cb13.isChecked()){
                     canvas.drawText("x", 456, 1162, titlePaint);
                 }

                 if (cb14.isChecked()){
                     canvas.drawText("x", 456, 1202, titlePaint);
                 }

                 if (cb15.isChecked()){
                     canvas.drawText("x", 456, 1242, titlePaint);
                 }

                 if (cb16.isChecked()){
                     canvas.drawText("x", 456, 1282, titlePaint);
                 }

                 if (cb17.isChecked()){
                     canvas.drawText("x", 456, 1322, titlePaint);
                 }
                 if (cb18.isChecked()){
                     canvas.drawText("x", 456, 1362, titlePaint);
                 }
                 if (cb19.isChecked()){
                     canvas.drawText("x", 456, 1402, titlePaint);
                 }
                 if (cb20.isChecked()){
                     canvas.drawText("x", 456, 1442, titlePaint);
                 }
                 if (cb21.isChecked()){
                     canvas.drawText("x", 1076, 602, titlePaint);
                 }
                 if (cb22.isChecked()){
                     canvas.drawText("x", 1076, 642, titlePaint);
                 }
                 if (cb23.isChecked()){
                     canvas.drawText("x", 1076, 682, titlePaint);
                 }
                 if (cb24.isChecked()) {
                     canvas.drawText("x", 1076, 722, titlePaint);
                 }
                 if (cb25.isChecked()){
                     canvas.drawText("x", 1076, 842, titlePaint);
                 }
                 if (cb26.isChecked()){
                     canvas.drawText("x", 1076, 882, titlePaint);
                 }
                 if (cb27.isChecked()) {
                     canvas.drawText("x", 1076, 922, titlePaint);
                 }
                 if (cb28.isChecked()){
                     canvas.drawText("x", 1076, 1042, titlePaint);
                 }
                 if (cb29.isChecked()){
                     canvas.drawText("x", 1076, 1082, titlePaint);
                 }


                 titlePaint.setTextAlign(Paint.Align.LEFT);

                 if (uwagi3.length() != 0) canvas.drawText("Uwagi: " + uwagi3.getText(), 50, 1520, titlePaint);

                 myPdfDocument.finishPage(myPage1);

                    try {
                        myPdfDocument.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.close();

                    try {
                     ProgressDialog progressDialog = new ProgressDialog(Eq.this);
                     progressDialog.setTitle("Wysyłam plik na serwer");
                     progressDialog.setMessage("Proszę czekać.");
                     progressDialog.show();

                     driveServiceHelper.createFile(filePath).addOnSuccessListener(new OnSuccessListener<String>() {
                         @Override
                         public void onSuccess(String s) {
                             progressDialog.dismiss();
                             Toast.makeText(getApplicationContext(), "Dokument przesłany poprawnie.", Toast.LENGTH_LONG).show();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             progressDialog.dismiss();
                             Toast.makeText(getApplicationContext(), "Błąd podczas przesyłania dokumentu. Sprawdź połączenie internetowe i spróbuj ponownie.", Toast.LENGTH_LONG).show();
                         }
                     });
                 }
                catch (Exception e) {
                 e.printStackTrace();
                 Toast.makeText(getApplicationContext(), "Błąd przesyłania. Sprawdź połączenie internetowe i spróbuj ponownie.", Toast.LENGTH_LONG).show();
                 requestSignIn();
                }

                }
            }
        });

    }

    private void requestSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this,signInOptions);
        startActivityForResult(client.getSignInIntent(), 400);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 400:
                if (resultCode == RESULT_OK) {
                    handleSignInIntent (data);
                }
                break;
        }



    }

    private void handleSignInIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        GoogleAccountCredential credential = GoogleAccountCredential.
                                usingOAuth2(Eq.this, Collections.singleton(DriveScopes.DRIVE_FILE));
                        credential.setSelectedAccount(googleSignInAccount.getAccount());
                        HttpTransport t = new NetHttpTransport();

                        Drive googleDriveService = new Drive.Builder(
                                t,
                                new GsonFactory(),
                                credential)
                                .setApplicationName("TDM")
                                .build();

                        driveServiceHelper = new DriveServiceHelper(googleDriveService);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    class DriveServiceHelper {
        SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        private final Executor mExecutor = Executors.newSingleThreadExecutor();
        private final Drive mDriveService;
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd HH-mm");
        Date now = new Date();
        String fileName = formatter.format(now) + " " + myPrefs.getString("keyname", null) + ".pdf";


        public DriveServiceHelper(Drive driveService) {
            mDriveService = driveService;
        }

        /**
         * Creates a text file in the user's My Drive folder and returns its file ID.
         */
        public Task<String> createFile(String filePath) {

            String parent = "175PA4xWGiRBiA8Lm9gzrDdlbznaj2Fjg";
            String name = fileName;
            return Tasks.call(mExecutor, () -> {
                com.google.api.services.drive.model.File metadata = new com.google.api.services.drive.model.File()
                        .setName(name)
                        .setParents(Collections.singletonList(parent));

                File file = new File(filePath);

                FileContent mediaContent = new FileContent("application/pdf", file);

                com.google.api.services.drive.model.File myFile = null;
                try {

                    myFile = mDriveService.files().create(metadata, mediaContent)
                            .setFields("id, parents").execute();
                    System.out.println("File ID: " + myFile.getId());
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
    }
    public static String path() {
        String dir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Przekazania/";
        } else {
            dir = Environment.getExternalStorageDirectory() + "/Download/Przekazania/";
        }

        return dir;
    }

}