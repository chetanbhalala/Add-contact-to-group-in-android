package com.Group_test;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;





public class Detail extends ListActivity implements OnClickListener{

	

	String[] names = { ContactsContract.Contacts._ID,
			ContactsContract.Contacts.DISPLAY_NAME,
	};
	private Button btn_add;
	private Button btn_cancle;
	
	ArrayList<String> group_contacts_name = new ArrayList<String>();
	ArrayList<String> group_contacts_id = new ArrayList<String>();
	ArrayList<String> selected_items_id = new ArrayList<String>();
	private String GROUP_ID;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		GROUP_ID = getIntent().getStringExtra("id");
		
		btn_add = (Button)findViewById(R.id.btn_add);
		btn_cancle = (Button)findViewById(R.id.btn_cancle);
		btn_add.setOnClickListener(this);
		btn_cancle.setOnClickListener(this);
		
		//setListAdapter(new ListViewAdapter_one(this));
		/*Cursor cus= getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, names, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
		
		startManagingCursor(cus);
		
		String form[]={ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID};
		int to []={R.id.checkBox1, R.id.text_textid};
		SimpleCursorAdapter sma= new SimpleCursorAdapter(this, R.layout.contect_list_row, cus, form, to);
		
		setListAdapter(sma);
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/
		
		try
		{
		Cursor cus= getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, names, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
		
		startManagingCursor(cus);
		
		if(cus != null)
		{
			while (cus.moveToNext()) {
					String iid = cus
							.getString(cus
									.getColumnIndex(ContactsContract.Contacts._ID));
					String name = cus
							.getString(cus
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					if(name != null && iid != null)
					{
						group_contacts_name.add(name);
						group_contacts_id.add(iid);
						Log.d("name :", ""+name);
						Log.d("iid :", ""+iid);
					}
			}
		}
		cus.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.d("error get contact :", ""+ e.getMessage().toString());
		}
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, group_contacts_name));
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_add)
		{
			if(selected_items_id.size() > 0)
			{
				selected_items_id.clear();
			}
			ListView parent = getListView();
			SparseBooleanArray choices = parent.getCheckedItemPositions();
		    for (int i = 0; i < choices.size(); i++)
		    {                
		        if(choices.valueAt(i) == true)
		        {
		        	selected_items_id.add(""+ group_contacts_id.get(i));
		        	Log.d("group id :", ""+ GROUP_ID);
		        	Log.d("selected items ids :", ""+ group_contacts_id.get(i));
		        	Log.d("selected items :", ""+ group_contacts_name.get(i));
		        }  
		    } 
		    
		    if(selected_items_id.size() > 0)
		    {
		    	add_group();
		    }
		    else
		    {
		    	Toast.makeText(this, "please select contact:", Toast.LENGTH_LONG).show();
		    }
		}
	}


	private void add_group() {
		// TODO Auto-generated method stub
		if(GROUP_ID.length() > 0)
		{
			for(int i = 0 ; i < selected_items_id.size(); i++)
			{
				try
				{
					/*ContentValues values = new ContentValues();
			        
			        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,
			               Integer.parseInt(""+ selected_items_id.get(i))); // 245 is a contact id, replace with selected contact id
			    	Toast.makeText(this, ""+ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, Toast.LENGTH_LONG).show();
			        
			        values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
			        		""+ GROUP_ID); // 3 is a group id, replace with selected group id
			        values
			                .put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
			                        ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
			
			      ContextWrapper context = this;
			      context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);*/
					
					// Add selected contact to selected group
			        ContentValues values = new ContentValues();
			        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,
			               Integer.parseInt(selected_items_id.get(i))); // 245 is a contact id, replace with selected contact id
			        values.put(
			                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
			                GROUP_ID); // 3 is a group id, replace with selected group id
			        values
			                .put(
			                        ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
			                        ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);

			      ContextWrapper context = this;
			      context.getContentResolver().insert(
			               ContactsContract.Data.CONTENT_URI, values);
			      // End add contact to group code
				}
				catch (Exception e) {
					// TODO: handle exception
					Log.d("add group error :", ""+ e.getMessage().toString());
				}
			}
		}
		
		finish();
	}
}
