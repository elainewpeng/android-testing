package com.joydroid.testme.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.suitebuilder.annotation.Suppress;

public class MyProviderTest extends ProviderTestCase2<MyProvider> {
    private MyProvider provider;

    public MyProviderTest() {
        this("MyProviderTest");
    }

    public MyProviderTest(String name) {
        super(MyProvider.class, MyProvider.AUTHORITY);
        setName(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setContext(getMockContext());
        provider = getProvider();
    }

    public void testDeleteById() {
        Uri uri = MyProvider.ProviderUri.POJO.uri();
        int actual = provider.delete(uri, "_id = ?", new String[]{"1"});

        assertEquals(1, actual);
    }

    public void testQueryBySegmentReturnsCorrectNumberOfRows() {
        Uri uri = MyProvider.ProviderUri.POJO.uri();
        Cursor c = provider.query(uri, null, null, null, null);
        try {
            int actual = c.getCount();

            assertEquals(2, actual);
        } finally {
            c.close();
        }
    }

    @Suppress
    public void testInsertUsingContentResolver() {
        ContentValues values = new ContentValues();

        values.put("_id", 3);
        values.put("name", "Pluto");
        Uri newUri = getMockContentResolver().insert(MyProvider.ProviderUri.POJO.uri(), values);
        assertNotNull(newUri);
    }
}
