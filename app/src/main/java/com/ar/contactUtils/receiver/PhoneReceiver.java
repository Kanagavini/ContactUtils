package com.ar.contactUtils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.ar.contactUtils.model.Contact;
import com.ar.contactUtils.ui.CustomDialog;
import com.ar.contactUtils.utils.SharedPreference;
import java.util.List;


public class PhoneReceiver extends BroadcastReceiver {
    SharedPreference sharedPreference;
    List<Contact> contacts;
    Context mContext;
    private String contactName;
    private String contactUri;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        //Log.w("intent " , intent.getAction().toString());
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneListener customPhoneListener = new PhoneListener();
        sharedPreference = new SharedPreference();
        contacts = sharedPreference.getContacts(context);
        Log.d("dataLog", String.valueOf(contacts));

        TelephonyManager telephonys = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonys.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

               if (checkItem(incomingNumber)) {
                    //start activity which has dialog
                    final Intent intent = new Intent(context, CustomDialog.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("phone_no", incomingNumber);
                    intent.putExtra("phone_name", contactName);
                    intent.putExtra("phone_uri", contactUri);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(intent);
                        }
                    }, 1500);
                }
            }

        }, PhoneStateListener.LISTEN_CALL_STATE);


        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        String phone_number = bundle.getString("incoming_number");
        //Toast.makeText(context, "Phone Number " + phone_number, Toast.LENGTH_SHORT).show();
        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        // String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        int state = 0;
        if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            state = TelephonyManager.CALL_STATE_IDLE;
        } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            state = TelephonyManager.CALL_STATE_RINGING;
        }
        if (phone_number == null || "".equals(phone_number)) {
            return;
        }
        customPhoneListener.onCallStateChanged(context, state, phone_number);

        //Toast.makeText(context, "DataPhone Number " + phone_number, Toast.LENGTH_SHORT).show();
    }

    public boolean checkItem(String phoneNumber) {
        boolean check = false;
        List<Contact> contacts = sharedPreference.getContacts(mContext);
        if (contacts != null) {
            for (Contact contact : contacts) {
                Log.d("dataChec000",contact.getContactNumber() + " "+contact.getContactName());
                contactName = contact.getContactName();
                contactUri = contact.getContactUri();
                String str = phoneNumber.substring(3);
                if (contact.getContactNumber().equals(str)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

}