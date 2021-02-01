package com.ar.contactUtils.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import com.ar.contactUtils.model.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    private Context context;
    private List<Contact> contacts;
    private static ContactRepository contactRepository;

    public static ContactRepository getInstance() {
        if (contactRepository == null) {
            contactRepository = new ContactRepository();
        }
        return contactRepository;
    }

    public ContactRepository() {

    }

    public ContactRepository(Context context){
        this.context = context;
        contacts = new ArrayList<>();
    }


    public List<Contact>  getContacts(){

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                Log.d("contact",  name + " " + phoneNo + " " + photoUri);

                contact.setId(Integer.parseInt(contactId));
                contact.setContactName(name);
                contact.setContactNumber(phoneNo.trim());
                contact.setContactUri(photoUri);

                contacts.add(contact);

            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return contacts;



    }

}
