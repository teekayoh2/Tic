package com.example.olayinka.tictac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaring a 2 dimensional array
    private Button[][] buttons = new Button[3][3];
    //Player 1 would start as soon as the game starts
    private boolean player1Turn = true;

    private int roundCount;
    private Spinner spinner;
    private int Player1points;
    private int Player2points;
    Button buttonfive;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewPlayer1 = (TextView) findViewById(R.id.textPlayer1);
        textViewPlayer2 = (TextView) findViewById(R.id.textPlayer2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }

        }
        buttonfive = (Button) findViewById(R.id.button5);
        Button buttonReset = (Button) findViewById(R.id.button_reset);
        buttonfive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, Five.class);
                startActivity(myIntent);
            }
        });



        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");

        }
        roundCount++;

        if (checkForWin()){
            if (player1Turn){
                player1wins();
            }
            else {
                player2wins();
            }
        } else if (roundCount == 9){
            draw();
        }else {
            player1Turn = !player1Turn;
        }

    }


    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();

            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;

            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void player1wins(){
        Player1points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT ).show();
        updatePointsText();
        resetBoard();
    }

    private void player2wins(){
        Player2points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT ).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT ).show();
        resetBoard();
    }

    private void updatePointsText() {
       textViewPlayer1.setText("Player 1:" + Player1points);
        textViewPlayer2.setText("Player 2:" + Player2points);


    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;


    }
    private void resetGame() {
        Player1points = 0;
        Player2points = 0;
        updatePointsText();
        resetBoard();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("Player1Points", Player1points);
        outState.putInt("Player2Points", Player2points);
        outState.putBoolean("Player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        Player1points = savedInstanceState.getInt("Player1points");
        Player2points = savedInstanceState.getInt("Player2points");
        player1Turn = savedInstanceState.getBoolean("Player1Turn");

    }
}
