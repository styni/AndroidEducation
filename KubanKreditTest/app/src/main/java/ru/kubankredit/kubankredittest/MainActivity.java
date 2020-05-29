package ru.kubankredit.kubankredittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static ru.kubankredit.kubankredittest.RequestManager.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    private TextView log;
    private Button startButton;
    private Button stopButton;
    private Button clearButton;
    private Timer timer;
    private TimerTask timerTask;

    class MyRequest extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;

            try {
                response = getResponseFromURL(urls[0]);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = (TextView) findViewById(R.id.log_view);

        startButton = (Button) findViewById(R.id.start_btn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                            url = new URL("http://4pda.ru/");

                            url = new URL("https://portal.kubankredit.ru/auth/login/backend/rest/stateful/personal/ping");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        new MyRequest().execute(url);
                    }
                };
                timer.schedule(timerTask, 0, 1000);

            }
        });

        stopButton = (Button) findViewById(R.id.stop_btn);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
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



    }
}
