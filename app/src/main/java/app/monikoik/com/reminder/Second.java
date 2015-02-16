package app.monikoik.com.reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;


public class Second extends Activity {

    Button buttonStartSetDialog;
    TextView textAlarmPrompt;
    ArrayList<PendingIntent> intentArray;
    int i;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textAlarmPrompt = (TextView)findViewById(R.id.promptAlarm);
        buttonStartSetDialog = (Button)findViewById(R.id.setAlarm);
        buttonStartSetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }
        });

    }

    private void openTimePickerDialog(boolean b) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(Second.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
                Log.i("hasil"," =<0");
            } else if(calSet.compareTo(calNow)>0){
                Log.i("hasil"," >0");
            } else {
                Log.i("hasil", "else");
            }

            intentArray = new ArrayList<PendingIntent>();
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

            for(i=0;i<10;i++){
                textAlarmPrompt.setText("***\n" + "Alarm set on " + calSet.getTime() + "***" );
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),i, intent, 0);
                alarmManager.set(alarmManager.ELAPSED_REALTIME_WAKEUP,calSet.getTimeInMillis() + 60000 * i,pendingIntent);
                intentArray.add(pendingIntent);
            }

            /*for(i = 0; i< 10; ++i)
            {
                setAlarm(calSet,i,intentArray);
            }*/
        }
    };

    /*private void setAlarm(Calendar targetCal,int requestAlarm,ArrayList<PendingIntent> list) {
        textAlarmPrompt.setText("***\n" + "Alarm set on " + targetCal.getTime() + "***" );
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),requestAlarm, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(alarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),pendingIntent);
        list.add(pendingIntent);
    }*/

}
