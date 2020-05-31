package ru.kubankredit.kubankredittest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static ru.kubankredit.kubankredittest.RequestManager.*;

public class MainActivity extends AppCompatActivity {


    private TextView log;
    private Button startButton;
    private Button stopButton;
    private Button clearButton;
    private ImageButton shareButton;
    private Timer timer;
    private TimerTask timerTask;
    private boolean timerIsActive;

    private final static String KEY_LOG = "LOG_TEXT";
    private static final String KEY_TIMER_IS_ACTIVE = "KEY_TIMER";

    @SuppressLint("StaticFieldLeak")
    class MyRequest extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;

            try {
//                response = getResponse(urls[0]);
                response = postResponse(urls[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            log.append(response);
        }
    }

    class StartRequest implements Runnable {

        @Override
        public void run() {
            if (timer != null) {
                timer.cancel();
            }

            timer = new Timer();
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    URL url = null;
                    try {
                        url = new URL("https://portal.kubankredit.ru/auth/login/backend/rest/stateful/personal/ping");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    new MainActivity.MyRequest().execute(url);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = (TextView) findViewById(R.id.log_view);

        if (savedInstanceState != null) {
            log.setText(savedInstanceState.getString(KEY_LOG, ""));
            if (savedInstanceState.getBoolean(KEY_TIMER_IS_ACTIVE, false)) {
                Thread threadRequest = new Thread(new StartRequest());
                threadRequest.start();
            }

        }

        startButton = (Button) findViewById(R.id.start_btn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new StartRequest());
                thread.start();
                timerIsActive = true;
            }
        });

        stopButton = (Button) findViewById(R.id.stop_btn);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                    timerIsActive = false;
                }
            }
        });

        clearButton = (Button) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("");
            }
        });

        shareButton = (ImageButton) findViewById(R.id.share_btn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the text message with a string
                String message = log.getText().toString();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putString(KEY_LOG, log.getText().toString());
        saveInstanceState.putBoolean(KEY_TIMER_IS_ACTIVE, timerIsActive);
    }

}


