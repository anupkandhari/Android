package com.example.android.contactmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.android.contactmanager.data.DatabaseHandler;
import com.example.android.contactmanager.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        contactArrayList = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);



//        db.addContact(new Contact("Jamie","213986"));
//        db.addContact(new Contact("Robb","098765"));
//        db.addContact(new Contact("Bran","40678765"));
//        db.addContact(new Contact("Jon","768345"));
//
//        db.addContact(new Contact("Arya","3445"));
//        db.addContact(new Contact("Sansa","6665"));
//        db.addContact(new Contact("Tyrion","5344"));
//        db.addContact(new Contact("Robert","96534"));
//        db.addContact(new Contact("Jorah","158285"));
//        db.addContact(new Contact("Hodor","78130"));


        List<Contact> contactList = db.getAllContacts();

        for (Contact contact : contactList) {
            Log.d("MainActivity", "onCreate: " + contact.getName());
            contactArrayList.add(contact.getName());
        }

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                contactArrayList
        );

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("List", "onItemClick: " + contactArrayList.get(position));

            }
        });

    }

}