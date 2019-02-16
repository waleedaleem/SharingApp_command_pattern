package com.example.sharingapp;

import android.content.Context;

/**
 * Command to edit a pre-existing contact
 */
public class EditContactCommand extends Command {
    private ContactList contactList;
    private Contact oldContact;
    private Contact newContact;
    private Context context;

    public EditContactCommand(ContactList contactList, Contact oldContact, Contact newContact, Context context) {
        this.contactList = contactList;
        this.oldContact = oldContact;
        this.newContact = newContact;
        this.context = context;
    }

    public void execute() {
        // Check that username is unique AND username is changed (Note: if username was not changed
        // then this should be fine, because it was already unique.)
        if (!contactList.isUsernameAvailable(newContact.getUsername()) && !(oldContact.getUsername().equals(newContact.getUsername()))) {
            setIsExecuted(false);
            return;
        }
        contactList.deleteContact(oldContact);
        contactList.addContact(newContact);
        setIsExecuted(contactList.saveContacts(context));
    }
}
