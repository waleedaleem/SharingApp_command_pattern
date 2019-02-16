package com.example.sharingapp;

import android.content.Context;

class AddContactCommand extends Command {
    private final ContactList contactList;
    private final Contact contact;
    private final Context context;

    public AddContactCommand(ContactList contactList, Contact contact, Context context) {
        this.contactList = contactList;
        this.contact = contact;
        this.context = context;
    }

    @Override
    public void execute() {
        if (!contactList.isUsernameAvailable(contact.getUsername())) {
            setIsExecuted(false);
            return;
        }
        contactList.addContact(contact);
        setIsExecuted(contactList.saveContacts(context));
    }
}
