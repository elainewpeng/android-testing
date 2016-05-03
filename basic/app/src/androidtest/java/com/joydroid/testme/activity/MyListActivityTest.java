package com.joydroid.testme.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ListView;
import com.joydroid.testme.CountryListActivity;

public class MyListActivityTest extends ActivityInstrumentationTestCase2<CountryListActivity> {

  private CountryListActivity activity;
  private ListView listView;

  public MyListActivityTest() {
    this("MyListActivityTest");
  }

  public MyListActivityTest(String name) {
    super(CountryListActivity.class);
    setName(name);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();

    activity = getActivity();
    listView = activity.getListView();
  }

  public void testListAdapterItemsLoaded() {
    int expectedItemPosition = 6;
    String expected = "Anguilla";

    String actual = listView.getAdapter().getItem(expectedItemPosition).toString();

    assertEquals("Wrong content", actual, expected);
  }

  public void testListScrolling() {
    listView.scrollTo(0, 0);

    TouchUtils.dragQuarterScreenUp(this, activity);
    int actualItemPosition = listView.getFirstVisiblePosition();

    assertTrue("Wrong position", actualItemPosition > 0);
  }
}
