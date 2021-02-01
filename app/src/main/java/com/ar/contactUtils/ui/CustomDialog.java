package com.ar.contactUtils.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ar.contactUtils.R;
import com.ar.contactUtils.databinding.ActivityDialogBinding;
import com.ar.contactUtils.databinding.ActivityMainBinding;


public class CustomDialog extends Activity {

    private String contactNo;
    private String contactName;
    private String contactUri;
    ActivityDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setFinishOnTouchOutside(false);
            super.onCreate(savedInstanceState);
            binding = ActivityDialogBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.x = -100;
            params.height = 300;
            params.width = 1000;
            params.y = -50;

            this.getWindow().setAttributes(params);
            contactNo = getIntent().getExtras().getString("phone_no");
            contactName = getIntent().getExtras().getString("phone_name");
            contactUri = getIntent().getExtras().getString("phone_uri");

            binding.txtContactName.setText(contactName);
            binding.txtContactNumber.setText(contactNo);


        } catch (Exception e) {
            Log.d("Exception", e.toString());
            e.printStackTrace();
        }
    }


}