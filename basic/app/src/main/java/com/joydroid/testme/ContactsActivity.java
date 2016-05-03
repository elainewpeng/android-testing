package com.joydroid.testme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import timber.log.Timber;

/**
 * This Activity requires some permissions:
 * <ul>
 * <li><code>android.permission.WRITE_EXTERNAL_STORAGE</code>
 * <li><code>android.permission.READ_CONTACTS</code>
 * <li><code>android.permission.WRITE_CONTACTS</code>
 * </ul>
 */
public class ContactsActivity extends AppCompatActivity
    implements ActivityCompat.OnRequestPermissionsResultCallback {

  public static final int PERMISSION_REQ_CODE_EXTERMAL_STORAGE = 101;
  public static final int PERMISSION_REQ_CODE_CALL_PHONE = 102;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contacts);

    requestPermissionIfNeeded(Manifest.permission.WRITE_EXTERNAL_STORAGE,
        PERMISSION_REQ_CODE_EXTERMAL_STORAGE);

    requestPermissionIfNeeded(Manifest.permission.CALL_PHONE, PERMISSION_REQ_CODE_CALL_PHONE);
  }

  private void requestPermissionIfNeeded(String permission, int appPermissionReqCode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(this, permission)
          != PackageManager.PERMISSION_GRANTED) {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)) {
          Timber.d("shouldShowRequestPermissionRationale = true");
          // Show an expanation to the user *asynchronously* -- don't block
          // this thread waiting for the user's response! After the user
          // sees the explanation, try again to request the permission.

        } else {
          ActivityCompat.requestPermissions(this, new String[] { permission },
              appPermissionReqCode);
        }
      }
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQ_CODE_EXTERMAL_STORAGE:
        for (int result : grantResults) {
          if (result == PackageManager.PERMISSION_GRANTED) {
            writeExternalFile();
          }
        }
        break;
      case PERMISSION_REQ_CODE_CALL_PHONE:
        for (int result : grantResults) {
          if (result == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:9115555"));
            startActivity(intent);
          }
        }
        break;
      default:
        finish();
    }
  }

  private void writeExternalFile() {
    File dir = Environment.getExternalStorageDirectory();
    File file = new File(dir, "dummyName");
    Timber.d("file= " + file);
    BufferedOutputStream os = null;
    try {
      os = new BufferedOutputStream(new FileOutputStream(file));
      byte[] bytesToWrite = "This is a sample".getBytes();
      os.write(bytesToWrite);
    } catch (IOException e) {
      throw new SecurityException(
          "Should have detected that android.permission.WRITE_EXTERNAL_STORAGE is required.");
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          Timber.e(e, "Could not close stream");
        }
      }
    }
  }
}
