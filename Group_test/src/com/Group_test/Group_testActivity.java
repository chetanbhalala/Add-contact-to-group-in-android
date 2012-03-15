package com.Group_test;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Group_testActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	ArrayList<String> favGroupId = new ArrayList<String>();
	ArrayList<String> favGroupName = new ArrayList<String>();
	ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*ContentValues groupValues;
        ContentResolver cr = this.getContentResolver();
        groupValues = new ContentValues();
        groupValues.put(ContactsContract.Groups.TITLE, "Test");
        cr.insert(ContactsContract.Groups.CONTENT_URI, groupValues);*/
        
        final String[] GROUP_PROJECTION = new String[] 
    			{
    				ContactsContract.Groups._ID, ContactsContract.Groups.TITLE 
    			};
    		Cursor cursor = getContentResolver().query(ContactsContract.Groups.CONTENT_URI, GROUP_PROJECTION, null,
    				null, ContactsContract.Groups.TITLE);

    		while (cursor.moveToNext()) {
    			String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups._ID));
    			Log.v("Test", id);

    			String gTitle = (cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE)));
    			favGroupId.add(id);
    			favGroupName.add(gTitle);

    			Log.v("Test", gTitle);
    			if (gTitle.contains("Favorite_")) {
    				gTitle = "Favorites";

    			}

    		}
    		
    		cursor.close();
    		
    		//set group in list view
    		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, favGroupName));
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		String groupId = favGroupId.get(position);	
		Log.d("groupId", ""+groupId);
		Intent i = new Intent(Group_testActivity.this,Detail.class);
		i.putExtra("id", "" + groupId);
		startActivity(i);
	}
}