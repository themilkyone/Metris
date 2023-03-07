package com.example.metris;

import static android.app.Activity.RESULT_OK;
import static com.example.metris.CalendarUtils.monthDate;
import static com.example.metris.CalendarUtils.monthDateInt;
import static com.example.metris.CalendarUtils.selectedDate;
import static com.example.metris.CalendarUtils.weekDate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.evrencoskun.tableview.TableView;
import com.example.metris.tableview.TableViewAdapter;
import com.example.metris.tableview.TableViewListener;
import com.example.metris.tableview.TableViewModel;
import com.example.metris.tableview.model.Cell;
import com.example.metris.tableview.model.ColumnHeader;
import com.example.metris.tableview.model.RowHeader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

public class Harm extends Fragment {
    private TextView monthYearText;
    private TextView monthText;
    DriveServiceHelper driveServiceHelper;
    private TableView mTableView;
    List<ColumnHeader> myHeaderList = new ArrayList<>();
    List<RowHeader> myRowList = new ArrayList<>();
    List<List<Cell>> myCellsList = new ArrayList<>();
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    boolean rangeBelowRange;
    boolean rangeAboveRange;
    int firstColumn;
    int month;
    int trows;
    int remainingColumns;
    String sheetName;
    ProgressDialog progressDialog;
    ImageButton monthLeft;
    ImageButton monthRight;
    ImageButton weekLeft;
    ImageButton weekRight;

    public Harm() {
        super(R.layout.fragment_main);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        requestSignIn();
        monthYearText = view.findViewById(R.id.monthYearTV);
        monthText = view.findViewById(R.id.monthText);
        selectedDate = LocalDate.now();

        month = Integer.parseInt(monthDateInt(selectedDate));

        setWeekView();
        setMonthView();
        weekLeft = view.findViewById(R.id.weekLeft);
        weekRight = view.findViewById(R.id.weekRight);
        monthLeft = view.findViewById(R.id.monthLeft);
        monthRight = view.findViewById(R.id.monthRight);

        weekLeft.setOnClickListener(view1 -> {
            monthLeft.setClickable(false);
            monthRight.setClickable(false);
            weekLeft.setClickable(false);
            weekRight.setClickable(false);
            previousWeekAction();
            AsyncTaskRunner runner = new AsyncTaskRunner();
            String sleepTime = "0";
            runner.execute(sleepTime);
        });

        weekRight.setOnClickListener(view12 -> {
            monthLeft.setClickable(false);
            monthRight.setClickable(false);
            weekLeft.setClickable(false);
            weekRight.setClickable(false);
                    nextWeekAction();
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    String sleepTime = "0";
                    runner.execute(sleepTime);
                    initializeTableView();
                }
        );

        monthLeft.setOnClickListener(view1 -> {
            monthLeft.setClickable(false);
            monthRight.setClickable(false);
            weekLeft.setClickable(false);
            weekRight.setClickable(false);
            previousMonthAction();
            firstColumn = -91;
            weekLeft.setVisibility(View.INVISIBLE);
            weekRight.setVisibility(View.VISIBLE);
            int dateYear = Integer.parseInt("20" + getSheetName().substring(0, 2));
            int dateMonth = Integer.parseInt(getSheetName().substring(3, 5));
            LocalDate date = LocalDate.of(dateYear, dateMonth, 1);
            monthYearText.setText("Tydz. " + weekDate(date));

            monthRight.setVisibility(View.VISIBLE);
            if (checkSheets(dateYear + "." + dateMonth)) monthLeft.setVisibility(View.INVISIBLE);
            selectedDate = date;
            driveServiceHelper.downloadFile().addOnSuccessListener(file -> {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "0";
                runner.execute(sleepTime);
            });
        });

        monthRight.setOnClickListener(view1 -> {
            monthLeft.setClickable(false);
            monthRight.setClickable(false);
            weekLeft.setClickable(false);
            weekRight.setClickable(false);
            nextMonthAction();
            firstColumn = -91;
            weekLeft.setVisibility(View.INVISIBLE);
            weekRight.setVisibility(View.VISIBLE);
            int dateYear = Integer.parseInt("20" + getSheetName().substring(0, 2));
            int dateMonth = Integer.parseInt(getSheetName().substring(3, 5));
            LocalDate date = LocalDate.of(dateYear, dateMonth, 1);
            monthYearText.setText("Tydz. " + weekDate(date));
            monthLeft.setVisibility(View.VISIBLE);
            if (checkSheets(dateYear + "." + dateMonth)) monthRight.setVisibility(View.INVISIBLE);
            selectedDate = date;
            driveServiceHelper.downloadFile().addOnSuccessListener(file -> {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "0";
                runner.execute(sleepTime);
            });
        });


        // Let's get TableView
        mTableView = view.findViewById(R.id.tableview);

        return view;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            String resp;
            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            getData().addOnSuccessListener(lists -> {
                initializeTableView();
                monthLeft.setClickable(true);
                monthRight.setClickable(true);
                weekLeft.setClickable(true);
                weekRight.setClickable(true);
                if (rangeBelowRange) {
                    weekLeft.setVisibility(View.INVISIBLE);
                } else {
                    weekLeft.setVisibility(View.VISIBLE);
                }
                if (rangeAboveRange) {
                    weekRight.setVisibility(View.INVISIBLE);
                } else {
                    weekRight.setVisibility(View.VISIBLE);
                }
                int dateYear = Integer.parseInt("20" + getSheetName().substring(0, 2));
                int dateMonth = Integer.parseInt(getSheetName().substring(3, 5));
                LocalDate date = LocalDate.of(dateYear, dateMonth, 1);
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM");
                String sheetRight = date.plusMonths(1).format(dateTimeFormatter);
                if (checkSheets(sheetRight)) {
                    monthRight.setVisibility(View.INVISIBLE);
                } else {
                    monthRight.setVisibility(View.VISIBLE);
                }
                String sheetLeft = date.minusMonths(1).format(dateTimeFormatter);
                if (checkSheets(sheetLeft)) {
                    monthLeft.setVisibility(View.INVISIBLE);
                } else {
                    monthLeft.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();
            }).addOnFailureListener(e -> {
                e.printStackTrace();
                progressDialog.dismiss();
                if (monthLeft.getVisibility() == View.INVISIBLE && monthRight.getVisibility() == View.INVISIBLE){
                    Toast.makeText(getActivity(), "Za dużo wysłanych żądań. Zresetuj aplikacje.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Błąd podczas ładowania harmonogramu.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            });
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    "Pobieranie harmonogramu",
                    "Proszę czekać");
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    private class AsyncTaskRunner2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            String resp;
            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            driveServiceHelper.downloadFile().addOnSuccessListener(file -> getData().addOnSuccessListener(lists -> {
                initializeTableView();
                monthLeft.setClickable(true);
                monthRight.setClickable(true);
                weekLeft.setClickable(true);
                weekRight.setClickable(true);
                if (rangeBelowRange) {
                    weekLeft.setVisibility(View.INVISIBLE);
                } else {
                    weekLeft.setVisibility(View.VISIBLE);
                }
                if (rangeAboveRange) {
                    weekRight.setVisibility(View.INVISIBLE);
                } else {
                    weekRight.setVisibility(View.VISIBLE);
                }
                int dateYear = Integer.parseInt("20" + getSheetName().substring(0, 2));
                int dateMonth = Integer.parseInt(getSheetName().substring(3, 5));
                LocalDate date = LocalDate.of(dateYear, dateMonth, 1);
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM");
                String sheetRight = date.plusMonths(1).format(dateTimeFormatter);
                if (checkSheets(sheetRight)) {
                    monthRight.setVisibility(View.INVISIBLE);
                } else {
                    monthRight.setVisibility(View.VISIBLE);
                }
                String sheetLeft = date.minusMonths(1).format(dateTimeFormatter);
                if (checkSheets(sheetLeft)) {
                    monthLeft.setVisibility(View.INVISIBLE);
                } else {
                    monthLeft.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();
            }).addOnFailureListener(e -> {
                e.printStackTrace();
                        progressDialog.dismiss();
                    }
            )).addOnFailureListener(e -> {
                e.printStackTrace();
                progressDialog.dismiss();
            });
        }


        @Override
        protected void onPreExecute() {
            try {
                driveServiceHelper.downloadFile();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Błąd podczas ładowania harmonogramu.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            progressDialog = ProgressDialog.show(getActivity(),
                    "Pobieranie harmonogramu",
                    "Proszę czekać", true);
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    public void setWeekView() {
        monthYearText.setText("Tydz. " + weekDate(selectedDate));
    }

    public void setMonthView() {
        String month = monthDate(selectedDate);
        switch (month) {
            case "January":
                month = "Styczeń";
                break;
            case "February":
                month = "Luty";
                break;
            case "March":
                month = "Marzec";
                break;
            case "April":
                month = "Kwiecień";
                break;
            case "May":
                month = "Maj";
                break;
            case "June":
                month = "Czerwie";
                break;
            case "July":
                month = "Lipiec";
                break;
            case "August":
                month = "Sierpień";
                break;
            case "September":
                month = "Wrzesień";
                break;
            case "October":
                month = "Październik";
                break;
            case "November":
                month = "Listopad";
                break;
            case "December":
                month = "Grudzień";
                break;

        }

        monthText.setText(month);
    }


    public void previousWeekAction() {
        if (selectedDate.getMonth().equals(selectedDate.minusWeeks(1).getMonth())) {
            selectedDate = selectedDate.minusWeeks(1);
            setWeekView();
            setMonthView();
            getSheetName();
        } else {
            getSheetName();
            int dateYear = Integer.parseInt("20" + getSheetName().substring(0, 2));
            selectedDate = LocalDate.of(dateYear, selectedDate.getMonth(), 1);
        }
    }

    public void nextWeekAction() {
        if (selectedDate.getMonth().equals(selectedDate.plusWeeks(1).getMonth())) {
            selectedDate = selectedDate.plusWeeks(1);
            setWeekView();
            setMonthView();
            getSheetName();
        } else {
            getSheetName();
            int dateYear = Integer.parseInt("20" + getSheetName().substring(0, 2));
            int dayInt = selectedDate.getDayOfMonth();
            switch (selectedDate.getDayOfWeek().toString()) {
                case "MONDAY":
                    dayInt = dayInt + 7;
                    break;
                case "TUESDAY":
                    dayInt = dayInt + 6;
                    break;
                case "WEDNESDAY":
                    dayInt = dayInt + 5;
                    break;
                case "THURSDAY":
                    dayInt = dayInt + 4;
                    break;
                case "FRIDAY":
                    dayInt = dayInt + 3;
                    break;
                case "SATURDAY":
                    dayInt = dayInt + 2;
                    break;
                case "SUNDAY":
                    dayInt = dayInt + 1;
                    break;
            }
            selectedDate = LocalDate.of(dateYear, selectedDate.getMonth(), dayInt);
        }
    }

    public void previousMonthAction() {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
        setWeekView();
        getSheetName();
    }

    public void nextMonthAction() {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
        setWeekView();
        getSheetName();
    }

    public String getSheetName() {
        DateFormat monthFormat = new SimpleDateFormat("yy.MM");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(selectedDate.atStartOfDay(defaultZoneId).toInstant());
        sheetName = monthFormat.format(date);
        return sheetName;
    }

    public Task<List<List<Object>>> getData() {
        myCellsList.clear();
        myHeaderList.clear();
        myRowList.clear();
        return Tasks.call(mExecutor, () -> {
            Workbook wb = new Workbook(DriveServiceHelper.path() + "Harmonogram.xlsx");
            System.out.println(wb.getAbsolutePath());
            Worksheet ws = wb.getWorksheets().get(wb.getWorksheets().getActiveSheetName());
            System.out.println(DriveServiceHelper.path() + "Harmonogram.xlsx");
            int dayInt = 1;
            int tcols = ws.getCells().getMaxDisplayRange().getColumnCount() - 1;
            if (firstColumn > -1) dayInt = CalendarUtils.dayInt(selectedDate);
            firstColumn = 0;
            String dayName = CalendarUtils.dayName(selectedDate).toUpperCase(Locale.ROOT);


            switch (dayName) {
                case "MONDAY":
                    firstColumn = dayInt;
                    break;
                case "TUESDAY":
                    firstColumn = dayInt - 1;
                    break;
                case "WEDNESDAY":
                    firstColumn = dayInt - 2;
                    break;
                case "THURSDAY":
                    firstColumn = dayInt - 3;
                    break;
                case "FRIDAY":
                    firstColumn = dayInt - 4;
                    break;
                case "SATURDAY":
                    firstColumn = dayInt - 5;
                    break;
                case "SUNDAY":
                    firstColumn = dayInt - 6;
                    break;
            }


            int totalColumns = 7;

            if (firstColumn < -20) firstColumn = -20;

            switch (firstColumn) {
                case -20:
                case -5:
                    totalColumns = 1;
                    firstColumn = 1;
                    break;
                case -4:
                    totalColumns = 2;
                    firstColumn = 1;
                    break;
                case -3:
                    totalColumns = 3;
                    firstColumn = 1;
                    break;
                case -2:
                    totalColumns = 4;
                    firstColumn = 1;
                    break;
                case -1:
                    totalColumns = 5;
                    firstColumn = 1;
                    break;
                case 0:
                    totalColumns = 6;
                    firstColumn = 1;
                    break;
            }

            remainingColumns = tcols - firstColumn;
            Range range = ws.getCells().createRange(2, firstColumn, 1, totalColumns);
            trows = ws.getCells().getLastDataRow(0);
            List<Object> rows = new ArrayList<>();
            List<Object> headers = new ArrayList<>();
            List<List<Object>> cellRows = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");

            int tcols1 = range.getColumnCount();

            try {
                for (int i = 0; i < trows - 3; i++) {
                    rows.add(ws.getCells().get(i + 3, 0).getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            int dateMonth = Integer.parseInt(getSheetName().substring(3, 5));
            if (dateMonth == 1 || dateMonth == 3 || dateMonth == 5 || dateMonth == 7 || dateMonth == 8 || dateMonth == 10 || dateMonth == 12) {
                for (int i = range.getFirstColumn(); i < (range.getFirstColumn() + tcols1); i++) {
                    if (i>31) break;
                    int day = ws.getCells().get(2, i).getIntValue();
                    String dayOfWeek = String.valueOf(LocalDate.of(Integer.parseInt("20" + getSheetName().substring(0, 2)), Integer.parseInt(getSheetName().substring(3, 5)), day).format(formatter));
                    headers.add(day + " " + dayOfWeek);

                }

                for (int i = 3; i <= trows + 3; i++) {
                    List<Object> cells = new ArrayList<>();
                    for (int j = range.getFirstColumn(); j < (range.getFirstColumn() + tcols1); j++) {
                        if (j > 31) {
                            cellRows.add(cells);
                            break;
                        }
                        Object o = ws.getCells().get(i, j).getValue();
                        if (o == null) o = " ";
                        cells.add(o);
                    }
                    cellRows.add(cells);
                }
            } else if (dateMonth == 2) {

                for (int i = range.getFirstColumn(); i < (range.getFirstColumn() + tcols1); i++) {
                    if (i>28) break;
                    int day = ws.getCells().get(2, i).getIntValue();
                    String dayOfWeek = String.valueOf(LocalDate.of(Integer.parseInt("20" + getSheetName().substring(0, 2)), Integer.parseInt(getSheetName().substring(3, 5)), day).format(formatter));
                    headers.add(day + " " + dayOfWeek);

                }

                for (int i = 3; i <= trows + 3; i++) {

                    List<Object> cells = new ArrayList<>();
                    for (int j = range.getFirstColumn(); j < (range.getFirstColumn() + tcols1); j++) {
                        if (j > 28) {
                            cellRows.add(cells);
                            break;
                        }
                        Object o = ws.getCells().get(i, j).getValue();
                        if (o == null) o = " ";
                        cells.add(o);

                    }
                    cellRows.add(cells);
                }
            } else {

                for (int i = range.getFirstColumn(); i < (range.getFirstColumn() + tcols1); i++) {
                    if (i>30) break;
                    int day = ws.getCells().get(2, i).getIntValue();
                    String dayOfWeek = String.valueOf(LocalDate.of(Integer.parseInt("20" + getSheetName().substring(0, 2)), Integer.parseInt(getSheetName().substring(3, 5)), day).format(formatter));
                    headers.add(day + " " + dayOfWeek);

                }
                for (int i = 3; i <= trows + 3; i++) {

                    List<Object> cells = new ArrayList<>();
                    for (int j = range.getFirstColumn(); j < (range.getFirstColumn() + tcols1); j++) {
                        if (j > 30) {
                            cellRows.add(cells);
                            break;
                        }
                        Object o = ws.getCells().get(i, j).getValue();
                        if (o == null) o = " ";
                        cells.add(o);

                    }
                    cellRows.add(cells);
                }
            }

            List<List<Object>> headersAndRows = new ArrayList<>();
            try {
                headersAndRows.add(headers);
                headersAndRows.add(rows);
            } catch (Exception e) {
                e.printStackTrace();
            }

            headersAndRows.addAll(cellRows);
            try {

                List<Object> headers1 = headersAndRows.get(0);
                List<Object> rows1 = headersAndRows.get(1);
                for (Object s : headers1) {
                    s = ""+s;
                    myHeaderList.add(new ColumnHeader("", s.toString().replace(" ", System.lineSeparator())));
                }
                for (Object s : rows1) {
                    s = ""+s;
                    myRowList.add(new RowHeader("", s.toString()));
                }

                long workers = trows;

                Cell cell;
                try {
                    for (int j = 0; j <= workers - 4; j++) {
                        List<Cell> c = new ArrayList<>();
                        List<Object> cells2 = headersAndRows.get(j + 2);
                        for (Object s : cells2) {
                            cell = new Cell(0 + "-" + 0, s.toString().replace(" ", System.lineSeparator()));
                            c.add(cell);
                        }
                        myCellsList.add(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (Objects.requireNonNull(myHeaderList.get(0).getContent()).toString().charAt(0) == '1' && Objects.requireNonNull(myHeaderList.get(0).getContent()).toString().charAt(1) == ('\n')) {
                    rangeBelowRange = true;
                    rangeAboveRange = false;
                } else {
                    rangeBelowRange = false;
                }


                if (dateMonth == 1 || dateMonth == 3 || dateMonth == 5 || dateMonth == 7 || dateMonth == 8 || dateMonth == 10 || dateMonth == 12) {
                    if (Objects.requireNonNull(myHeaderList.get(myHeaderList.size() - 1).getContent()).toString().startsWith("31")) {
                        rangeBelowRange = false;
                        rangeAboveRange = true;
                    } else {
                        rangeAboveRange = false;
                    }
                } else if (dateMonth == 2) {
                    if (Objects.requireNonNull(myHeaderList.get(myHeaderList.size() - 1).getContent()).toString().startsWith("28")) {
                        rangeBelowRange = false;
                        rangeAboveRange = true;
                    } else {
                        rangeAboveRange = false;
                    }
                } else {
                    if (Objects.requireNonNull(myHeaderList.get(myHeaderList.size() - 1).getContent()).toString().startsWith("30")) {
                        rangeBelowRange = false;
                        rangeAboveRange = true;
                    } else {
                        rangeAboveRange = false;
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Błąd podczas ładowania harmonogramu.", Toast.LENGTH_LONG).show();
            }
            return null;
        });
    }

    private boolean checkSheets(String probSheetName) {
        Workbook wb = null;
        try {
            wb = new Workbook(DriveServiceHelper.path() + "Harmonogram.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wb != null) {
            for (int i = 0; i <= wb.getWorksheets().getCount(); i++) {
                if (wb.getWorksheets().get(probSheetName) != null)
                    return false;
            }
        }
        return true;

    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel);

        mTableView.setAdapter(tableViewAdapter);
        mTableView.setTableViewListener(new TableViewListener(mTableView));

        mTableView.setMinimumWidth(Resources.getSystem().getDisplayMetrics().widthPixels);


        tableViewAdapter.setAllItems(myHeaderList, myRowList, myCellsList);

        mTableView.setHasFixedWidth(false);

    }

    private void requestSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE ))
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(getActivity().getApplicationContext(), signInOptions);
        startActivityForResult(client.getSignInIntent(), 400);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                handleSignInIntent(data);
            }
        }
    }

    private void handleSignInIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(googleSignInAccount -> {
                    GoogleAccountCredential credential = GoogleAccountCredential.
                            usingOAuth2(requireActivity().getApplicationContext(), Collections.singleton(DriveScopes.DRIVE));
                    credential.setSelectedAccount(googleSignInAccount.getAccount());

                    HttpTransport t = new NetHttpTransport();

                    Drive googleDriveService = new Drive.Builder(
                            t,
                            new GsonFactory(),
                            credential)
                            .setApplicationName("metris")
                            .build();

                    driveServiceHelper = new DriveServiceHelper(googleDriveService);
                    AsyncTaskRunner2 runner = new AsyncTaskRunner2();
                    String sleepTime = "0";
                    runner.execute(sleepTime);
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }

}



