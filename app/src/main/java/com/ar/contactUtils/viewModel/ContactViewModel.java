package com.ar.contactUtils.viewModel;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableArrayList;

import com.ar.contactUtils.model.Contact;
import com.ar.contactUtils.repository.ContactRepository;

import java.util.List;


public class ContactViewModel extends BaseObservable {

    private ObservableArrayList<Contact> contacts;
    private ContactRepository repository;

    public ContactViewModel(Context context) {
        contacts = new ObservableArrayList<>();
        repository = new ContactRepository(context);
    }

    public List<Contact> getContacts() {
        contacts.addAll(repository.getContacts());
        return contacts;
    }
}
