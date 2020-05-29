package com.example.pricticeset2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0, scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addOnePoint (View view) {
        if (view.getId()==R.id.addOneForTeamA) {
            scoreTeamA++;
            displayScoreA(scoreTeamA);
        } else {
            scoreTeamB++;
            displayScoreB(scoreTeamB);
        }
    }

    public void addTwoPoint (View view) {
        if (view.getId()==R.id.addTwoForTeamA) {
            scoreTeamA+=2;
            displayScoreA(scoreTeamA);
        } else {
            scoreTeamB+=2;
            displayScoreB(scoreTeamB);
        }
    }

    public void addThreePoint (View view) {
        if (view.getId()==R.id.addThreeForTeamA) {
            scoreTeamA+=3;
            displayScoreA(scoreTeamA);
        } else {
            scoreTeamB+=3;
            displayScoreB(scoreTeamB);
        }
    }

    private void displayScoreA (int score) {
        TextView scoreView = (TextView) findViewById(R.id.score_team_a);
        scoreView.setText(String.valueOf(score));

    }

    private void displayScoreB (int score) {
        TextView scoreView = (TextView) findViewById(R.id.score_team_b);
        scoreView.setText(String.valueOf(score));
    }

    public void resetScore (View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayScoreA(scoreTeamA);
        displayScoreB(scoreTeamB);
    }
}
