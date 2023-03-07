package com.example.metris;

import android.os.Build;
import android.os.Environment;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {
    public final Drive mDriveService;
    String fileName;
    String fileId;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }

    public Task<java.io.File> downloadFile() {
        return Tasks.call(mExecutor, () -> {

            FileList result = mDriveService.files().list()
                    .setQ("'1BRvFu1gmDbMQdryZr1S-QhhjgVi7YxAT' in parents and trashed = false")
                    .setSpaces("drive")
                    .setFields("files(id)")
                    .execute();

            fileId = String.valueOf(result.getFiles().get(0).getId());

            try {
                File yourFile = new File(path() + "Harmonogram.xlsx");
                yourFile.createNewFile();
                OutputStream outputStream = new FileOutputStream(path() + "Harmonogram.xlsx");
                mDriveService.files().export(fileId, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        .executeMediaAndDownloadTo(outputStream);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e);
            }
            return null;
        });
    }

    public static String path() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        } else {
            dir = Environment.getExternalStorageDirectory() + "/Download/";
        }

        return dir;
    }

}