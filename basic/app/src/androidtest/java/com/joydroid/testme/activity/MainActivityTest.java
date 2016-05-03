package com.joydroid.testme.activity;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.joydroid.testme.MainActivity;
import com.joydroid.testme.R;

import static android.test.MoreAsserts.assertNotContainsRegex;
import static android.test.ViewAsserts.assertOnScreen;
import static android.test.ViewAsserts.assertRightAligned;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private MainActivity activity;
  private EditText messageInput;
  private Button capitalizeButton;
  private Button contactsButton;
  private TextView linkTextView;
  private Instrumentation instrumentation;

  public MainActivityTest() {
    this("MainActivityTest");
  }

  public MainActivityTest(String name) {
    super(MainActivity.class);
    setName(name);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    // this must be called before getActivity()
    // disabling touch mode allows for sending key events
    setActivityInitialTouchMode(false);

    activity = getActivity();
    instrumentation = getInstrumentation();
    linkTextView = (TextView) activity.findViewById(R.id.main_text_link);
    messageInput = (EditText) activity.findViewById(R.id.main_input_message);
    capitalizeButton = (Button) activity.findViewById(R.id.main_button_capitalize);
    contactsButton = (Button) activity.findViewById(R.id.main_button_contacts);
  }

  public void testLayout() {
    View root = activity.getWindow().getDecorView();
    testLayout(root);

    root = messageInput.getRootView();
    testLayout(root);

    root = capitalizeButton.getRootView();
    testLayout(root);
  }

  private void testLayout(View root) {
    assertOnScreen(root, messageInput);
    assertOnScreen(root, capitalizeButton);
    assertRightAligned(messageInput, capitalizeButton, 0);
  }

  @UiThreadTest
  public void testNoErrorInCapitalization() {
    String msg = "this is a sample";
    messageInput.setText(msg);
    capitalizeButton.performClick();
    String actual = messageInput.getText().toString();
    String notExpectedRegexp = "(?i:ERROR)";
    assertNotContainsRegex("Capitalization found error:", notExpectedRegexp, actual);
  }

  public void testFollowLink() {
    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
    intentFilter.addDataScheme("http");
    intentFilter.addCategory(Intent.CATEGORY_BROWSABLE);

    Instrumentation inst = getInstrumentation();
    ActivityMonitor monitor = inst.addMonitor(intentFilter, null, false);
    TouchUtils.clickView(this, linkTextView);
    monitor.waitForActivityWithTimeout(3000);
    int monitorHits = monitor.getHits();
    inst.removeMonitor(monitor);

    assertEquals(1, monitorHits);
  }

  public void testSendKeyInts() {
    requestMessageInputFocus();

    sendKeys(KeyEvent.KEYCODE_H, KeyEvent.KEYCODE_E, KeyEvent.KEYCODE_E, KeyEvent.KEYCODE_E,
        KeyEvent.KEYCODE_Y, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_ENTER);
    String actual = messageInput.getText().toString();

    assertEquals("HEEEY", actual);
  }

  public void testSendKeyString() {
    requestMessageInputFocus();

    sendKeys("H 3*E Y DPAD_DOWN ENTER");
    String actual = messageInput.getText().toString();

    assertEquals("HEEEY", actual);
  }

  public void testSendRepeatedKeys() {
    requestMessageInputFocus();

    sendRepeatedKeys(1, KeyEvent.KEYCODE_H, 3, KeyEvent.KEYCODE_E, 1, KeyEvent.KEYCODE_Y, 1,
        KeyEvent.KEYCODE_DPAD_DOWN, 1, KeyEvent.KEYCODE_ENTER);
    String actual = messageInput.getText().toString();

    assertEquals("HEEEY", actual);
  }

  public void testCapitalizationSendingKeys() {
    String keysSequence = "T E S T SPACE M E";

    requestMessageInputFocus();

    sendKeys(keysSequence);
    TouchUtils.clickView(this, capitalizeButton);
    String actual = messageInput.getText().toString();
    assertEquals("URI_PATH_POJO ME", actual);
  }

  public void testStartContacts() {
    try {
      TouchUtils.clickView(this, contactsButton);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testDummy() {
    assertNotNull(activity.getData());
  }

  private void requestMessageInputFocus() {
    try {
      runTestOnUiThread(new Runnable() {
        @Override public void run() {
          messageInput.requestFocus();
        }
      });
    } catch (Throwable throwable) {
      fail("Could not request focus.");
    }
    instrumentation.waitForIdleSync();
  }
}
