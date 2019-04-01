package com.rdavepatient.soft.meetdoctor.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.rdavepatient.soft.meetdoctor.Forgot;
import com.rdavepatient.soft.meetdoctor.RegisterActivity;

public class IncomingSMS extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		final Bundle bundle = intent.getExtras();
		try {
			if (bundle != null) 
			{
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdusObj .length; i++) 
				{
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					String senderNum = phoneNumber ;
					String newSender=senderNum.subSequence(3, 9).toString();
					String message = currentMessage .getDisplayMessageBody();
					try
					{ 
						if (newSender.equalsIgnoreCase("MDSOFT"))
						{

							RegisterActivity.newInstance().recivedSms(message);
							Forgot.newInstance().recivedSms(message);

//							ActivationOTPPage Sms1 = new ActivationOTPPage();
//							Sms1.recivedSms(message);
//
//							RenewalActivationOTPPage renew=new  RenewalActivationOTPPage();
//							renew.recivedSms(message);
//
//							BimacareActivationOTPPage BSMS=new BimacareActivationOTPPage();
//							BSMS.recivedSms(message);
//
//							BimacareRenewalActivationOTPPage BRSMS=new BimacareRenewalActivationOTPPage();
//							BRSMS.recivedSms(message);

						}
					}
					catch(Exception e){
						Log.e("Sms", e.getMessage());

					}

				}
			}

		} catch (Exception e)
		{
			Log.e("SMS Read Main Thread", e.getMessage());
		}
	}

}
