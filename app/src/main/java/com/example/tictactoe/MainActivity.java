package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private Button btn_reset;
    private boolean player1Turn = true;
    private int playRounds=0;
    private int p1Points = 0;
    private int p2Points = 0;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);


        for(int i=0; i<3;i++){
            for(int j=0;j<3;j++){
                String btnId = "btn_"+i+j;
                int resID = getResources().getIdentifier(btnId,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    reset();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            ((Button)view).setText("X");
        }else {
            ((Button)view).setText("O");
        }

        playRounds++;

        if(checkWin()){
            if(player1Turn == true){
                player1Win();
            }else {
                player2Win();
            }


        } else {
            player1Turn = !player1Turn;
        }
        if(playRounds==9){
            draw();
        }


    }

    public boolean checkWin(){

        String filed[][]=new String[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                filed[i][j]=buttons[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++){
            if(filed[i][0].equals(filed[i][1])
                    &&filed[i][0].equals(filed[i][2])
                    &&!filed[i][0].equals("")){
                return true;
            }
            if(filed[0][i].equals(filed[1][i])
                    &&filed[0][i].equals(filed[2][i])
                    &&!filed[0][i].equals("")){
                return true;
            }

        }

        if(filed[0][0].equals(filed[1][1])
                &&filed[0][0].equals(filed[2][2])
                &&!filed[0][0].equals("")){
            return true;
        }
        if(filed[0][2].equals(filed[1][1])
                &&filed[0][2].equals(filed[2][0])
                &&!filed[0][2].equals("")){
            return true;
        }

        return false;
    }
    public void player1Win(){
        Toast t = Toast.makeText(this,"Player1 Win!",Toast.LENGTH_LONG);
        t.show();
        p1Points++;
        tv1.setText("Player1: "+p1Points);
        cleanButton();

    }
    public void player2Win(){
        Toast t = Toast.makeText(this,"Player2 Win!",Toast.LENGTH_LONG);
        t.show();
        p2Points++;
        tv2.setText("Player1: "+p2Points);
        cleanButton();
    }
    public void draw(){
        Toast t = Toast.makeText(this,"Draw!",Toast.LENGTH_LONG);
        t.show();
        cleanButton();
    }
    public void reset(){
        player1Turn = true;
        p1Points = 0;
        p2Points = 0;
        tv1.setText("Player1: "+p1Points);
        tv2.setText("Player2: "+p2Points);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }

    }

    public void cleanButton(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        playRounds =0;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("p1Points",p1Points);
        outState.putInt("p2Points",p2Points);
        outState.putInt("roundCount",playRounds);
        outState.putBoolean("p1Turn",player1Turn);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        playRounds = savedInstanceState.getInt("roundCount");
        p1Points = savedInstanceState.getInt("p1Points");
        p2Points = savedInstanceState.getInt("p2Points");
        player1Turn = savedInstanceState.getBoolean("p1Turn");
    }
}