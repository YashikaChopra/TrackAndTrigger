package com.example.trackandtrigger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.trackandtrigger.Database.EntityClass;

import java.security.AccessController;
import java.security.Provider;
import java.security.Security;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Message extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    Button btn_time, btn_date, btn_done;
    EditText title;
    EditText emailid;
    //EditText txtphoneNo;
    EditText msg;
    String phoneNo;
    String message;
    String timeTonotify;
    String notify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Reminder");
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        btn_done = findViewById(R.id.btn_done);
        title = findViewById(R.id.title);
        //txtphoneNo = (EditText) findViewById(R.id.editText);
        msg = (EditText) findViewById(R.id.email_msg);
        emailid = findViewById(R.id.todoemail);


        btn_time.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_time) {
            selectTime();
        } else if (v == btn_date) {
            selectDate();
        } else {
            //sendSMSMessage();
            submit();

            Intent intent=new Intent(Message.this,Todo_Activity.class);
            startActivity(intent);
        }
    }
    /*protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        message = msg.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }*/

    private void submit() {
        String text = title.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please Give the Title", Toast.LENGTH_SHORT).show();
        } else {
            if (btn_time.getText().toString().equals("Select Time") || btn_date.getText().toString().equals("Select date")) {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show();
            } else {
                EntityClass entityClass = new EntityClass();
                String value = ((title.getText().toString()+" is due 1 hr").trim());
                String date = (btn_date.getText().toString().trim());
                String time = (notify.trim());
                entityClass.setEventdate(date);
                entityClass.setEventname(value);
                entityClass.setEventtime(time);
                setAlarm(value, date, time, msg.getText().toString(), emailid.getText().toString());
                Toast.makeText(this, "Will notify you 1hr before time ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                btn_time.setText(FormatTime(i, i1));
                if(i==0)
                    i=23;
                else
                    --i;
                timeTonotify = i + ":" + i1;
            }
        }, hour, minute, false);
        timePickerDialog.show();

    }
    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;
        notify = "";

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
            notify="11" + ":" + formattedMinute + "PM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
            if ((hour-1)==0)
                notify = "12" + ":" + formattedMinute + " AM";
            else
                notify = (hour-1) + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
            notify = "11" + ":" + formattedMinute + " AM";
        } else {
            int temp = hour - 12;
            if ((temp-1)==0)
                notify = "12" + ":" + formattedMinute + " PM";
            else
                notify = (temp-1) + ":" + formattedMinute + " PM";
            time = temp + ":" + formattedMinute + " PM";
        }
        return time;
    }
    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btn_date.setText(day + "-" + (month + 1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                title.setText(text.get(0));
            }
        }

    }

    private void setAlarm(String text, String date, String time, String msg, String emailid) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        intent.putExtra("msg", msg);
        intent.putExtra("emailid", emailid);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }
};
