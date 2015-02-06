package app.monikoik.com.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * Created by Monika Maytri on 04/02/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Received", Toast.LENGTH_LONG).show();
        mediaPlayer = MediaPlayer.create(context,R.raw.medsmartsound1);
        mediaPlayer.start();

    }
}
