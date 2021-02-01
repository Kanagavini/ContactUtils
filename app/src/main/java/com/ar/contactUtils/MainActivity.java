package com.ar.contactUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.ar.contactUtils.databinding.ActivityMainBinding;
import com.ar.contactUtils.model.Contact;
import com.ar.contactUtils.ui.ContactAdapter;
import com.ar.contactUtils.utils.SharedPreference;
import com.ar.contactUtils.viewModel.ContactViewModel;

public class MainActivity extends AppCompatActivity implements ContactAdapter.onItemClickListener {

    ActivityMainBinding binding;
    private ContactViewModel contactViewModel;
    public final int REQUEST_CODE = 1;
    public final int REQUEST_CODE_OVER = 2;
    ContactAdapter adapter;
    SharedPreference sharedPreference;
    public final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;
    public final static int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactViewModel = new ContactViewModel(getApplicationContext());
        binding.setViewModel(contactViewModel);

        sharedPreference = new SharedPreference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPhoneContactsPermission(Manifest.permission.READ_CONTACTS))
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
        } else {

        }



        checkCallLogPermission();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }

        initRecyclerView();
        checkDrawOverlayPermission();
    }


    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, REQUEST_CODE_OVER);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE:
                initRecyclerView();

            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
            case MY_PERMISSIONS_REQUEST_READ_CALL_LOG:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // continue here - permission was granted
                }
            }
            return;

        }
    }

    private boolean hasPhoneContactsPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            return hasPermission == PackageManager.PERMISSION_GRANTED;
        } else
            return true;
    }


    @Override
    public void onCheckClicked(Contact contact, int pos, boolean checkStatus) {
        Log.d("ContactDetails", String.valueOf(contact));
        if (checkStatus) {
            sharedPreference.addContact(getApplicationContext(), contact);
        } else {
            sharedPreference.removeContact(getApplicationContext(), contact);
        }

    }

    private void initRecyclerView() {
        binding.contactRecycleView.setLayoutManager(new LinearLayoutManager(binding.contactRecycleView.getContext()));
        adapter = new ContactAdapter(getApplicationContext());
        adapter.setContacts(contactViewModel.getContacts());
        adapter.setOnItemClickListener(this);
        binding.contactRecycleView.setAdapter(adapter);
    }

    public void checkCallLogPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CALL_LOG)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG},
                        MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
            }
        }
    }
}