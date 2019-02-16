package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 */
public class EditContactActivity extends AppCompatActivity {

    private ContactList contact_list = new ContactList();
    private Contact contact;
    private EditText email;
    private EditText username;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        context = getApplicationContext();
        contact_list.loadContacts(context);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        contact = contact_list.getContact(pos);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        username.setText(contact.getUsername());
        email.setText(contact.getEmail());
    }

    public void saveContact(View view) {

        String email_str = email.getText().toString();

        if (email_str.equals("")) {
            email.setError("Empty field!");
            return;
        }

        if (!email_str.contains("@")){
            email.setError("Must be an email address!");
            return;
        }

        String username_str = username.getText().toString();
        String id = contact.getId(); // Reuse the contact id

        Contact updated_contact = new Contact(username_str, email_str, id);
        EditContactCommand editContactCommand = new EditContactCommand(contact_list, contact, updated_contact, context);
        editContactCommand.execute();
        if (!editContactCommand.isExecuted()) {
            username.setError("Username already taken!");
            return;
        }

        // End EditContactActivity
        finish();
    }

    public void deleteContact(View view) {

        DeleteContactCommand delete_contact_command = new DeleteContactCommand(contact_list, contact, context);
        delete_contact_command.execute();
        if (!delete_contact_command.isExecuted()){
            return;
        }

        // End EditContactActivity
        finish();
    }
}
