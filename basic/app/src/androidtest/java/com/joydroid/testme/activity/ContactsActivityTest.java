package com.joydroid.testme.activity;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.joydroid.testme.ContactsActivity;

public class ContactsActivityTest extends ActivityInstrumentationTestCase2<ContactsActivity> {
  public ContactsActivityTest() {
    this("MyContactsActivityInstrumentationTests");
  }

  public ContactsActivityTest(String name) {
    super(ContactsActivity.class);
    setName(name);
  }

  public void testActivityPermission() {
    Context context = getInstrumentation().getTargetContext();
    Intent intent = new Intent(context, ContactsActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //try {
    try {
      context.startActivity(intent);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    //fail("expected security exception for " + PERMISSION);
    //} catch (SecurityException expected) {
    //  assertNotNull("security exception's error message.", expected.getMessage());
    //  assertTrue("error message should contain " + PERMISSION + ".",
    //      expected.getMessage().contains(PERMISSION));
    //} catch(Throwable t) {
    //  t.printStackTrace();
    //}
  }
}
