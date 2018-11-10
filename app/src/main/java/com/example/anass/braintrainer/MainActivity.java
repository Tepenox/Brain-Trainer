package com.example.anass.braintrainer;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    TextView scoreTextView;
    TextView questionTextView;
    TextView resultTextView;
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    String correctChoiceTag;
    List<Button> choices;
    Random rand;
    int gameState;
    int randomNum1;
    int randomNum2;
    int correctAnswer;
    int correctAnswerCounter;
    int answersCounter;
    CountDownTimer gameTime;

    public void onChoiceClick(View view) {
        resultTextView.setVisibility(View.VISIBLE);
        if (view.getTag().toString().equals(correctChoiceTag)) {
            // if correct
            answersCounter++;
            correctAnswerCounter++;
            scoreTextView.setText(correctAnswerCounter+"/"+answersCounter);
            generateNewQuestion();
            resultTextView.setTextColor(Color.rgb(0, 165, 16));
            resultTextView.setText("True :)");
            //TODO add correct sound effect

        } else {
            // if no
            answersCounter++;
            scoreTextView.setText(correctAnswerCounter+"/"+answersCounter);
            resultTextView.setTextColor(Color.RED);
            resultTextView.setText("False :(");
            generateNewQuestion();
            //TODO add wrong sound effect

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        questionTextView = findViewById(R.id.questionTextView);
        resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setVisibility(View.INVISIBLE);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        choices = new ArrayList<>(Arrays.asList(choice1, choice2, choice3,
                choice4));
        rand = new Random();


        generateNewQuestion();


        gameTime = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // update the timer
                String timerText = millisUntilFinished / 1000 + "s";
                timerTextView.setText(timerText);


            }

            @Override
            public void onFinish() {
                //endGame()
            }
        }.start();


    }

    private int generateTwoRandomNumber() {
        //generating two number between 1 and  20 both inclusive
        //                               max  min      min
        randomNum1 = rand.nextInt((20 - 1) + 1) + 1;
        randomNum2 = rand.nextInt((20 - 1) + 1) + 1;
        return randomNum1 + randomNum2;
    }

    private void populateChoices() {
        //choosing randomly one choice to be the correct answer
        //and others  to be close answers in range of 10;
        ArrayList<Button> tmpChoices = new ArrayList<>(choices);
        int randomChoice = rand.nextInt(4);
        correctChoiceTag = tmpChoices.get(randomChoice).getTag().toString();
        String correctChoiceStr = "" + correctAnswer;
        tmpChoices.get(randomChoice).setText(correctChoiceStr); //just to avoid some warnings
        tmpChoices.remove(randomChoice);
        //iterate thought  the remaining choices and populate them with close values to the correct
        //answer
        int min = correctAnswer - 5;
        int max = correctAnswer + 5;

        for (Button btn : tmpChoices) {
            int closeAnswer = rand.nextInt((max - min) + 1) + min;
            //checking if the randomly generated answer is the same as the correct answer
            //if yes generate a new one until it s not the same
            while (true) {
                if (closeAnswer == correctAnswer) {
                    closeAnswer = rand.nextInt((max - min) + 1) + min;
                } else {
                    break;
                }
            }
            String str = "" + closeAnswer;
            btn.setText(str);
        }


    }

    private void generateNewQuestion() {
        correctAnswer = generateTwoRandomNumber();
        String questionString = randomNum1 + " + " + randomNum2 + " = " + "?";
        questionTextView.setText(questionString);
        populateChoices();
    }


}
