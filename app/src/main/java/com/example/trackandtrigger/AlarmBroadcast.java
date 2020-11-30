package com.example.trackandtrigger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.security.AccessController;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AlarmBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String text = bundle.getString("event");
        String date = bundle.getString("date") + " " + bundle.getString("time");
        String msg = bundle.getString("msg");
        String emailid = bundle.getString("emailid");
        //String phone=bundle.getString("phone");
        //String msg=bundle.getString("msg");

        //Click on Notification

        Intent intent1 = new Intent(context, Todo_Activity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("message", text);
        //Notification Builder
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001")
                .setSmallIcon(R.mipmap.ic_launcher);

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        //PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        //contentView.setOnClickPendingIntent(R.id.snooze, pendingSwitchIntent);
        contentView.setTextViewText(R.id.message, text);
        contentView.setTextViewText(R.id.date, date);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(android.app.Notification.PRIORITY_HIGH);
        //mBuilder.setOnlyAlertOnce(true);
        mBuilder.build().flags = android.app.Notification.FLAG_NO_CLEAR | android.app.Notification.PRIORITY_HIGH;
        mBuilder.setContent(contentView);
        mBuilder.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Notification notification = mBuilder.build();
        notificationManager.notify(1,notification);
        //sendSMSMessage(phone,msg);
        final GMailSender sender = new GMailSender("manasabollavaram2000@gmail.com", "Bits@1234");
        new AsyncTask<Void, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override public Void doInBackground(Void... arg) {
                try {
                    sender.sendMail(text,
                            msg,
                            "TrackAndTrigger",
                            emailid);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                return null;}
        }.execute();
    }

//    private void sendSMSMessage(String phone, String msg) {
//        final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
//        SmsManager smsManager = SmsManager.getDefault();
//
//        smsManager.sendTextMessage(phone, null, msg, null, null);
//        //Toast.makeText(null, Toast.LENGTH_LONG).show();
//
//    }

}
class GMailSender extends javax.mail.Authenticator {
    /*
        private String mailhost = "smtp.gmail.com";
    */
    private final String username;
    private final String password;
    private final Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.username = user;
        this.password = password;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //session = Session.getDefaultInstance(props, this);
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("TrackAndTrigger"));
            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            //Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_SHORT).show();
            //System.out.println("Done");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            //Toast.makeText(getApplicationContext(), "Unable to send mail", Toast.LENGTH_SHORT).show();
        }

    }
}

final class JSSEProvider extends Provider {

    public JSSEProvider() {
        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
        AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {
            public Void run() {
                put("SSLContext.TLS",
                        "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                put("Alg.Alias.SSLContext.TLSv1", "TLS");
                put("KeyManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                put("TrustManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                return null;
            }
        });
    }
}
