package com.sftelehealth.doctor.app.receiver;

/**
 * Created by Rahul on 24/01/18.
 */

public class SMSListener {

    //private SharedPreferences preferences;
    /*OTPReceiver otpReceiver;

    public SMSListener(OTPReceiver otpReceiver) {
        this.otpReceiver = otpReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            String[] msg_sender;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msg_sender = msg_from.split("-");
                        String msgBody = msgs[i].getMessageBody();
                        Timber.d("message from - " + msgs[i].getOriginatingAddress() + ", messageBody - " + msgs[i].getMessageBody());
                        //if(msgs[i].getOriginatingAddress().equals(Constants.SMS_SENDER1) || msgs[i].getOriginatingAddress().equals(Constants.SMS_SENDER2)) {
                        if(msg_sender[msg_sender.length - 1].equals("DRCALL") || msg_sender[msg_sender.length - 1].equals("DRTALK")) {
                            String[] messageSplit = msgs[i].getMessageBody().split(" ");
                            //loginWithOTP(messageSplit[0].substring(4, 8));
                            otpReceiver.otpReceived(messageSplit[0].substring(4, 10));
                        }
                    }
                } catch(Exception e) {
                    Timber.d("Exception caught: %s", e.getMessage());
                }
            }
        }
    }

    public interface OTPReceiver {
        void otpReceived(String otp);
    }*/

}
