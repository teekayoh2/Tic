package com.example.olayinka.tictac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Five extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons_five = new Button[5][5];
    //Player 1 would start as soon as the game starts
    private boolean player1Turn_five = true;

    private int roundCount_five;
    private int Player1points_five;
    private int Player2points_five;
    Button buttonthree;

    private TextView textViewPlayer1_five;
    private TextView textViewPlayer2_five;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        textViewPlayer1_five = (TextView) findViewById(R.id.textPlayer1_five);
        textViewPlayer2_five = (TextView) findViewById(R.id.textPlayer2_five);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button5_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons_five[i][j] = (Button) findViewById(resID);
                buttons_five[i][j].setOnClickListener(Five.this);

                //buttons_five[i][j].setOnClickListener(this);
            }
        }
        buttonthree = (Button) findViewById(R.id.button3);
        Button buttonReset = (Button) findViewById(R.id.button_reset);
        buttonthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Five.this, MainActivity.class);
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

        if (player1Turn_five) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");

        }
        roundCount_five++;

        if (checkForWin()){
            if (player1Turn_five){
                player1wins();
            }
            else {
                player2wins();
            }
        } else if (roundCount_five == 9){
            draw();
        }else {
            player1Turn_five = !player1Turn_five;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons_five[i][j].getText().toString();

            }
        }
        for (int i = 0; i < 5; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && field[i][0].equals(field[i][3])
                    && !field[i][0].equals("")) {
                return true;

            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && field[0][i].equals(field[3][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && field[0][0].equals(field[3][3])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                & field[0][2].equals(field[3][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void player1wins(){
        Player1points_five++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT ).show();
        updatePointsText();
        resetBoard();
    }

    private void player2wins(){
        Player2points_five++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT ).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT ).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1_five.setText("Player 1:" + Player1points_five);
        textViewPlayer2_five.setText("Player 2:" + Player2points_five);


    }

    private void resetBoard() {
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                buttons_five[i][j].setText("");
            }
        }

        roundCount_five = 0;
        player1Turn_five = true;


    }
    private void resetGame() {
        Player1points_five = 0;
        Player2points_five = 0;
        updatePointsText();
        resetBoard();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount_five);
        outState.putInt("Player1Points", Player1points_five);
        outState.putInt("Player2Points", Player2points_five);
        outState.putBoolean("Player1Turn", player1Turn_five);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount_five = savedInstanceState.getInt("roundCount");
        Player1points_five = savedInstanceState.getInt("Player1points");
        Player2points_five = savedInstanceState.getInt("Player2points");
        player1Turn_five = savedInstanceState.getBoolean("Player1Turn");

    }
}


