package com.joydroid.testme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
  private Pojo data;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final EditText messageInput = (EditText) findViewById(R.id.main_input_message);
    Button capitalizeButton = (Button) findViewById(R.id.main_button_capitalize);
    capitalizeButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        String input = messageInput.getText().toString();
        messageInput.setText(input.toUpperCase());
      }
    });

    Button contactsButton = (Button) findViewById(R.id.main_button_contacts);
    contactsButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
      }
    });

    data = new Pojo(100);
  }

  public Pojo getData() {
    return data;
  }

  public static void methodThatShouldThrowException() throws Exception {
    throw new Exception("This is an exception");
  }
}
