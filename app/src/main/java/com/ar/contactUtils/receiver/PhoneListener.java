package com.ar.contactUtils.receiver;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Date;

public class PhoneListener extends PhoneStateListener {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;

    public void onCallStateChanged(Context context, int state, String phoneNumber){
        if(lastState == state){
            //No change, debounce extras
            return;
        }

        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();

            //    Toast.makeText(context, "Incoming Call" + phoneNumber, Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    //Toast.makeText(context, "Outgoing Call " + phoneNumber, Toast.LENGTH_SHORT).show();
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                }

                break;
        }
        lastState = state;
    }

}
