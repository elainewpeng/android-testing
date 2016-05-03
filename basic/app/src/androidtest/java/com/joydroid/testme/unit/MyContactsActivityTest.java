package com.joydroid.testme.unit;

import android.net.Uri;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;

public class MyContactsActivityTest extends AndroidTestCase {
  private final static String PKG = "com.blundell.tut";

  public MyContactsActivityTest() {
    this("ContactsActivityTest");
  }
  public MyContactsActivityTest(String name) {
    setName(name);
  }

  //Does NOT work, has to be instrumentation test !
  //public void testActivityPermission() {
  //  try {
  //    assertActivityRequiresPermission(PKG, ACTIVITY, PERMISSION);
  //  } catch (Exception e) {
  //    e.printStackTrace();
  //  }
  //}


  public void testReadingContacts() {
    Uri URI = ContactsContract.AUTHORITY_URI;
    String PERMISSION = android.Manifest.permission.READ_CONTACTS;
    assertReadingContentUriRequiresPermission(URI, PERMISSION);
  }

  public void testWritingContacts() {
    Uri URI = ContactsContract.AUTHORITY_URI;
    String PERMISSION = android.Manifest.permission.WRITE_CONTACTS;
    assertWritingContentUriRequiresPermission(URI, PERMISSION);
  }
}
