package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class game extends AppCompatActivity implements View.OnClickListener{


    private Button[][] buttons = new Button[3][3];

    String p1name,p2name;

    private boolean player1Turn = true;

    private int roundcount;

    private int player1Points;
    private int player2Points;

    private TextView textviewPlayer1;
    private TextView textviewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        p1name=intent.getStringExtra("p1name");
        p2name=intent.getStringExtra("p2name");



        textviewPlayer1 = findViewById(R.id.text_view_p1);
        textviewPlayer2 = findViewById(R.id.text_view_p2);

        textviewPlayer1.setText(p1name+" : 0 ");
        textviewPlayer2.setText(p2name+" : 0 ");

        textviewPlayer1.setPaintFlags(textviewPlayer1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        for(int i =0;i<3;i++) {
            for(int j=0;j<3;j++) {
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            textviewPlayer2.setPaintFlags(textviewPlayer2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            textviewPlayer1.setPaintFlags(View.INVISIBLE);
            ((Button) v).setText("X");
        }else{
            textviewPlayer1.setPaintFlags(textviewPlayer1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            textviewPlayer2.setPaintFlags(View.INVISIBLE);
            ((Button) v).setText("O");
        }

        roundcount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundcount == 9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }
    }
    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&& !field[i][0].equals("")){
                return true;
            }
        }

        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&& !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&& !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1])&&field[0][2].equals(field[2][0])&& !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this,p1name+" WINS !!!",Toast.LENGTH_SHORT).show();
        updatepointsText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this,p2name+" WINS !!!",Toast.LENGTH_SHORT).show();
        updatepointsText();
        resetBoard();
        textviewPlayer2.setPaintFlags(textviewPlayer2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textviewPlayer1.setPaintFlags(View.INVISIBLE);
        player1Turn = !player1Turn;
    }

    private void draw(){
        Toast.makeText(this,"!!! DRAW !!!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatepointsText(){
        textviewPlayer1.setText(p1name+" : "+player1Points);
        textviewPlayer2.setText(p2name+" : "+player2Points);
    }

    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundcount = 0;
        player1Turn = true;
        textviewPlayer1.setPaintFlags(textviewPlayer1.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textviewPlayer2.setPaintFlags(View.INVISIBLE);
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatepointsText();
        resetBoard();
    }
    protected void onSaveInstancesState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("roundcount",roundcount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundcount = savedInstanceState.getInt("roundcount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
