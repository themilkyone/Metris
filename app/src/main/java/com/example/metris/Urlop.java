package com.example.metris;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
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


public class Urlop extends AppCompatActivity {
    Bitmap bmp, scaledbmp;
    DateFormat dateFormat;
    Date dateObj;
    DriveServiceHelper driveServiceHelper;
    int pageWidth = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urlop);
        requestSignIn();
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        ScrollView urlop = findViewById(R.id.urlop);
        TextView rodzaj = findViewById(R.id.rodzaj);
        TextView datap = findViewById(R.id.datap);
        TextView datak = findViewById(R.id.datak);
        EditText data1 = findViewById(R.id.data1);
        EditText data2 = findViewById(R.id.data2);
        EditText uwagi = findViewById(R.id.uwagi);
        View view = findViewById(R.id.view);
        ImageView logo = findViewById(R.id.logo);
        CheckBox bezplatny = findViewById(R.id.bezplatny);
        CheckBox wypoczynkowy = findViewById(R.id.wypoczynkowy);
        CheckBox nazadanie = findViewById(R.id.nazadanie);
        CheckBox okolicznosciowy = findViewById(R.id.okolicznosciowy);
        TextView tvdate = findViewById(R.id.tvdate);
        Button wyslij = findViewById(R.id.wyslij);
        Button wnioski = findViewById(R.id.wnioski);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoh);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 267, 109, false);

        getResources().getColorStateList(R.color.checkbox);

        if ((myPrefs.getString("style", null).equals("dark"))) {
            urlop.setBackgroundResource(R.color.dark);
            view.setBackgroundColor(getResources().getColor(R.color.white));
            rodzaj.setTextColor(getResources().getColor(R.color.white));
            datap.setTextColor(getResources().getColor(R.color.white));
            datak.setTextColor(getResources().getColor(R.color.white));
            data1.setTextColor(getResources().getColor(R.color.white));
            data2.setTextColor(getResources().getColor(R.color.white));
            uwagi.setHintTextColor(getResources().getColor(R.color.white));
            data1.setHintTextColor(getResources().getColor(R.color.white));
            data2.setHintTextColor(getResources().getColor(R.color.white));
            bezplatny.setTextColor(getResources().getColor(R.color.white));
            wypoczynkowy.setTextColor(getResources().getColor(R.color.white));
            nazadanie.setTextColor(getResources().getColor(R.color.white));
            okolicznosciowy.setTextColor(getResources().getColor(R.color.white));
            tvdate.setTextColor(getResources().getColor(R.color.white));
            logo.setImageResource(R.drawable.logohw);
        }

        Context context = this.getBaseContext();
        String dataDir;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            dataDir = context.getExternalFilesDir("") + "/Download/Wnioski/";
        } else {
            dataDir = Environment.getExternalStorageDirectory().getPath() + "/Download/";
        }

        bezplatny.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wypoczynkowy.setClickable(false);
                    nazadanie.setClickable(false);
                    okolicznosciowy.setClickable(false);
                } else {
                    wypoczynkowy.setClickable(true);
                    nazadanie.setClickable(true);
                    okolicznosciowy.setClickable(true);
                }
            }
        });

        wypoczynkowy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bezplatny.setClickable(false);
                    nazadanie.setClickable(false);
                    okolicznosciowy.setClickable(false);
                } else {
                    bezplatny.setClickable(true);
                    nazadanie.setClickable(true);
                    okolicznosciowy.setClickable(true);
                }
            }
        });

        nazadanie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bezplatny.setClickable(false);
                    wypoczynkowy.setClickable(false);
                    okolicznosciowy.setClickable(false);
                } else {
                    bezplatny.setClickable(true);
                    wypoczynkowy.setClickable(true);
                    okolicznosciowy.setClickable(true);
                }
            }
        });

        okolicznosciowy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bezplatny.setClickable(false);
                    wypoczynkowy.setClickable(false);
                    nazadanie.setClickable(false);
                } else {
                    bezplatny.setClickable(true);
                    wypoczynkowy.setClickable(true);
                    nazadanie.setClickable(true);
                }
            }
        });


        String dateTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        tvdate.setText(dateTime);

        wyslij.setBackgroundColor(getResources().getColor(R.color.teal_700));
        wnioski.setBackgroundColor(getResources().getColor(R.color.teal_200));

        Calendar Kalendarz = Calendar.getInstance();
        final int Rok = Kalendarz.get(Calendar.YEAR);
        final int Miesiąc = Kalendarz.get(Calendar.MONTH);
        final int Dzień = Kalendarz.get(Calendar.DAY_OF_MONTH);


        TextWatcher autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (data1.length() == 0) {
                    data1.setBackgroundResource(R.drawable.border);
                } else {
                    data1.setBackgroundResource(R.drawable.borderinput);
                }
            }

        };
        data1.addTextChangedListener(autoAddTextWatcher);
        autoAddTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (data2.length() == 0) {
                    data2.setBackgroundResource(R.drawable.border);
                } else {
                    data2.setBackgroundResource(R.drawable.borderinput);
                }
            }

        };
        data2.addTextChangedListener(autoAddTextWatcher);


        data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Urlop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Rok, int Miesiąc, int Dzień) {
                        Miesiąc = Miesiąc + 1;
                        String date = Dzień + "/" + Miesiąc + "/" + Rok;
                        data1.setText(date);
                    }
                }, Rok, Miesiąc, Dzień);
                datePickerDialog.show();
            }
        });

        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Urlop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Rok, int Miesiąc, int Dzień) {
                        Miesiąc = Miesiąc + 1;
                        String date = Dzień + "/" + Miesiąc + "/" + Rok;
                        data2.setText(date);
                    }
                }, Rok, Miesiąc, Dzień);
                datePickerDialog.show();
            }
        });

        wyslij.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          File file2 = new File(path());
                                          SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH-mm");
                                          Date now = new Date();
                                          String fileName = formatter.format(now);
                                          String filePath = path() + fileName + ".pdf";
                                          File file = new File(filePath);
                                          dateObj = new Date();
                                          if (data1.getText().toString().length() == 0 || data2.getText().toString().length() == 0) {
                                              Toast.makeText(Urlop.this, "Wybierz datę!", Toast.LENGTH_LONG).show();
                                          } else if (!wypoczynkowy.isChecked() &&
                                                  !nazadanie.isChecked() &&
                                                  !okolicznosciowy.isChecked() &&
                                                  !bezplatny.isChecked()) {
                                              Toast.makeText(Urlop.this, "Wybierz rodzaj urlopu!", Toast.LENGTH_LONG).show();
                                          } else if (data1.length() == 0) {
                                              data1.setBackgroundResource(R.drawable.borderred);
                                          } else if (data2.length() == 0) {
                                              data2.setBackgroundResource(R.drawable.borderred);
                                          } else {

                                              PdfDocument myPdfDocument = new PdfDocument();
                                              Paint myPaint = new Paint();
                                              Paint titlePaint = new Paint();
                                              Paint data = new Paint();
                                              Paint tabelka = new Paint();

                                              PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                                              PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                                              Canvas canvas = myPage1.getCanvas();

                                              canvas.drawBitmap(scaledbmp, 50, 50, myPaint);

                                              Typeface type = ResourcesCompat.getFont(Urlop.this, R.font.lato);
                                              Typeface bold = ResourcesCompat.getFont(Urlop.this, R.font.latob);
                                              titlePaint.setTypeface(bold);
                                              dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                              data.setTextSize(20);
                                              data.setTextAlign(Paint.Align.RIGHT);
                                              canvas.drawText("Data: " + dateFormat.format(dateObj), pageWidth - 60, 90, data);
                                              dateFormat = new SimpleDateFormat("HH:mm");
                                              canvas.drawText("Godzina: " + dateFormat.format(dateObj), pageWidth - 60, 130, data);

                                              titlePaint.setTextAlign(Paint.Align.CENTER);
                                              titlePaint.setTypeface(bold);
                                              titlePaint.setTextSize(40);
                                              canvas.drawText("Wniosek urlopowy", pageWidth / 2, 280, titlePaint);

                                              titlePaint.setTypeface(type);
                                              titlePaint.setTextAlign(Paint.Align.LEFT);
                                              titlePaint.setTextSize(20);
                                              canvas.drawText("Pracownik: " + myPrefs.getString("name", null), 50, 380, titlePaint);


                                              if (wypoczynkowy.isChecked())
                                                  canvas.drawText("Rodzaj urlopu: wypoczynkowy", 700, 380, titlePaint);
                                              if (bezplatny.isChecked())
                                                  canvas.drawText("Rodzaj urlopu: bezpłatny", 700, 380, titlePaint);
                                              if (nazadanie.isChecked())
                                                  canvas.drawText("Rodzaj urlopu: na żądanie", 700, 380, titlePaint);
                                              if (okolicznosciowy.isChecked())
                                                  canvas.drawText("Rodzaj urlopu: okolicznościowy", 700, 380, titlePaint);
                                              canvas.drawText("Data początku urlopu: " + data1.getText(), 50, 440, titlePaint);
                                              canvas.drawText("Data końca urlopu: " + data2.getText(), 700, 440, titlePaint);
                                              canvas.drawText("Uwagi: " + uwagi.getText(), 50, 560, titlePaint);
                                              canvas.drawLine(670, 705, 1070, 705, tabelka);


                                              titlePaint.setTypeface(type);
                                              titlePaint.setTextAlign(Paint.Align.CENTER);
                                              titlePaint.setTextSize(15);
                                              canvas.drawText("Podpis pracodawcy", 870, 730, titlePaint);
                                              myPdfDocument.finishPage(myPage1);


                                              try {
                                                  myPdfDocument.writeTo(new FileOutputStream(file));
                                              } catch (IOException e) {
                                                  e.printStackTrace();
                                              }
                                              myPdfDocument.close();

                                              try {
                                                  ProgressDialog progressDialog = new ProgressDialog(Urlop.this);
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
                                              } catch (Exception e) {
                                                  e.printStackTrace();
                                                  Toast.makeText(getApplicationContext(), "Błąd przesyłania. Sprawdź połączenie internetowe i spróbuj ponownie.", Toast.LENGTH_LONG).show();
                                                  requestSignIn();
                                              }

                                          }
                                      }
                                  }
        );

        wnioski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ChooserDialog(Urlop.this)
                        .withStartFile(path())
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri uri = FileProvider.getUriForFile(Urlop.this, getApplicationContext().getPackageName() + ".provider", pathFile);
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


    }

    private void requestSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(client.getSignInIntent(), 400);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 400:
                if (resultCode == RESULT_OK) {
                    handleSignInIntent(data);
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
                                usingOAuth2(Urlop.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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
        String fileName = formatter.format(now) + "";


        public DriveServiceHelper(Drive driveService) {
            mDriveService = driveService;
        }

        /**
         * Creates a text file in the user's My Drive folder and returns its file ID.
         */
        public Task<String> createFile(String filePath) {

            String parent = "1LqcP8UsyybALz-riquyeYl66crpvI0f-";
            String name = fileName + myPrefs.getString("keyname", null);
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
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Wnioski/";
        } else {
            dir = Environment.getExternalStorageDirectory() + "/Download/Wnioski/";
        }
        
        return dir;
    }
}
