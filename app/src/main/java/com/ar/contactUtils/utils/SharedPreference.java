package com.ar.contactUtils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.ar.contactUtils.model.Contact;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "CONTACT_APP";
    public static final String CONTACT_CHECK = "Contact_check";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining contact.
    public void saveContacts(Context context, List<Contact> contacts) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonContacts = gson.toJson(contacts);
        editor.putString(CONTACT_CHECK, jsonContacts);
        editor.commit();
    }

    public void addContact(Context context, Contact contact) {
        List<Contact> contacts = getContacts(context);
        if (contacts == null)
            contacts = new ArrayList<Contact>();
        contacts.add(contact);
        saveContacts(context, contacts);
        getContacts(context);
    }

    public void removeContact(Context context, Contact contact) {
        List<Contact> contacts = getContacts(context);
        if (contacts == null)
            contacts = new ArrayList<Contact>();

        contacts.remove(contact);
        saveContacts(context, contacts);
        getContacts(context);

    }

    public ArrayList<Contact> getContacts(Context context) {
        SharedPreferences settings;
        List<Contact> contacts;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(CONTACT_CHECK)) {
            String jsonContacts = settings.getString(CONTACT_CHECK, null);
            Gson gson = new Gson();
            Contact[] favoriteItems = gson.fromJson(jsonContacts,
                    Contact[].class);

            contacts = Arrays.asList(favoriteItems);
            contacts = new ArrayList<Contact>(contacts);
        } else
            return null;
        Log.d("dataLog", String.valueOf(contacts));
        return (ArrayList<Contact>) contacts;
    }

}
