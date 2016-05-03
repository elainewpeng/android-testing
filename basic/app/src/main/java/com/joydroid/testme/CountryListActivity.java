package com.joydroid.testme;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CountryListActivity extends ListActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_list);
    ListView lv = getListView();
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries,
        android.R.layout.simple_list_item_1);
    lv.setAdapter(adapter);
  }
}
