package com.example.tom.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bsh.EvalError;
import bsh.Interpreter;

public class MainActivity extends AppCompatActivity {

    TextView result, tempResult;
    Button one,two, three, four, five, six, seven, eight, nine, zero,
            dot, equal, ce, plus, minus, multiple, divide, bracketOne, bracketTwo, root;

    String line=""; //for save all action untli press =
    String numberOne=""; // first number entered
    String numberTwo=""; //number entered after pressing sign
    int BracketsCount =0; //how many brackets writen
    boolean BracketPressed =false; //to know if brackets are pressed
    boolean ActionPressed =false;  //to know if is pressed even one action button
    boolean isEqualPressed =false; //to know if = is pressed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         result = (TextView) findViewById(R.id.result);
        tempResult = (TextView) findViewById(R.id.line);

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        dot = (Button) findViewById(R.id.dot);
        equal = (Button) findViewById(R.id.equal);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiple = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);
        bracketOne = (Button) findViewById(R.id.bracket1);
        bracketTwo = (Button) findViewById(R.id.bracket2);
        ce = (Button) findViewById(R.id.ce);
        root = (Button) findViewById(R.id.root);

        //to turn of buttons at start, as they cannot be used before firs number
        bracketTwo.setEnabled(false);
        root.setEnabled(false);
        plus.setEnabled(false);
        divide.setEnabled(false);
        multiple.setEnabled(false);


        //all buttons clicks actions
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("1");
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("2");
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              PressedNumber("3");

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("4");

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("5");

            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("6");

            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("7");

            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("8");

            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("9");

            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber("0");

            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedNumber(".");

            }
        });
        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearEverything();
                EnableNumbers();

            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedAction("/");
            }
        });

        multiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedAction("*");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedAction("-");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PressedAction("+");
            }
        });

        //what happens when press root button
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what to do if press root after pressing =
                if(isEqualPressed){
                    numberOne=result.getText().toString();
                    line+=numberOne;
                    isEqualPressed=false;
                }
                int tempLenght;
                if (numberTwo!=""){ //if want to calculate root for first number
                     tempLenght = numberTwo.length();
                   line = line.substring(0,line.length()-tempLenght);
                    line+="Math.sqrt(";
                    line+=numberTwo+")";
                }
                else{
                     tempLenght = numberOne.length();
                     line = line.substring(0,line.length()-tempLenght);
                    line+="Math.sqrt(";
                    line+=numberOne+")";
                }
                //if number is too long that throw exception and clear everything
                if(tempLenght>6){
                    Toast.makeText(MainActivity.this,"too long Root",Toast.LENGTH_LONG ).show();
                    ClearEverything();
                }
                tempResult.setText(line);                 //to add root to line and show it in calculating line
                DisableNumbers();                         // after root cannot be pressed numbers
                root.setEnabled(false);
            }
        });

        bracketOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BracketsCount+=1;
                BracketPressed= true;
                line +="(";
                minus.setEnabled(true);
                tempResult.setText(line);
            }
        });

        bracketTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line +=")";
                BracketsCount-=1;
                if (BracketsCount==0){
                    bracketTwo.setEnabled(false);
                }
                root.setEnabled(false); //that after ( cannot be pressed root
                tempResult.setText(line);
            }
        });



        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculate();
                numberTwo="";
                line="";
                isEqualPressed=true;
                EnableNumbers();
            }
        });
    }
    // what to do when is pressed number
    private void PressedNumber(String number){
        isEqualPressed=false;
        if(!ActionPressed){
            line+=number;
            numberOne+=number;
        }else{
            numberTwo+=number;
            line+=number;
        }
        if (BracketsCount!=0) {
            bracketTwo.setEnabled(true);
        }
            bracketOne.setEnabled(false);
        tempResult.setText(line);
        EnableButtons();
    }
    // what to do when press =
    private double Calculate(){
        for (int i=0; i<BracketsCount; i++){ // to add brackets to end of line if some is missing
            line+=")";
        }
        BracketsCount=0;
        tempResult.setText(line);
        Object obj= new Object();
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.eval("result = "+line);
            obj =interpreter.get("result");
        } catch (EvalError evalError) {
            Toast.makeText(MainActivity.this,"wrong input",Toast.LENGTH_LONG ).show();
            ClearEverything();
            return 0;

        }
        result.setText(String.valueOf(obj));
        return Double.parseDouble(obj.toString());
    }
    //pressed +-*/ and etc.
    private void PressedAction(String action){
        if(isEqualPressed){
            numberOne=result.getText().toString();
            line+=numberOne;
            isEqualPressed=false;
        }

        if(!ActionPressed){
            ActionPressed=true;
            //if devide then one number have to be with point
            int index=0;
            if(!numberOne.contains(".") && action=="/"){
                if(line.charAt(line.length()-1)==')'){
                    for (int i=line.length()-2;i>0; i--) {
                       if (Character.isDigit(line.charAt(i))){
                           index = i+1;
                           break;
                       }
                    }
                    line = line.substring(0,index)+".0)"+line.substring(index, line.length()-1);
                }else {
                    line += ".0";
                }
            }
            line+=action;
        }else{
            //if devide then one number have to be with point
            if( numberTwo!="" &&!numberTwo.contains(".") && action=="/"){
                int index=0;
                if(line.charAt(line.length()-1)==')'){
                    for (int i=line.length()-2;i>0; i--) {
                        if (Character.isDigit(line.charAt(i))){
                            index = i+1;
                            break;
                        }
                    }
                    line = line.substring(0,index)+".0)"+line.substring(index, line.length()-1);
                }else {
                    line += ".0";
                }
            }
            line+=action;
            numberTwo="";
        }
        tempResult.setText(line);
        DistableButtons();
        bracketTwo.setEnabled(false);
        bracketOne.setEnabled(true);
        EnableNumbers();
    }
    //clear all data, using after pressing CE or = and if after it press number
    private void ClearEverything(){
        line="";
        BracketsCount=0;
        BracketPressed=false;
        bracketTwo.setEnabled(false);
        bracketOne.setEnabled(true);
        result.setText(" ");
        tempResult.setText(" ");
        numberOne="";
        numberTwo="";
        DistableButtons();

    }
    //disable action buttons
    private void DistableButtons(){
        plus.setEnabled(false);
        minus.setEnabled(false);
        divide.setEnabled(false);
        multiple.setEnabled(false);
        root.setEnabled(false);

    }
    //enable action buttons
    private void EnableButtons(){
        plus.setEnabled(true);
        minus.setEnabled(true);
        divide.setEnabled(true);
        multiple.setEnabled(true);
        root.setEnabled(true);

    }

    private void DisableNumbers(){
        one.setEnabled(false);
        two.setEnabled(false);
        three.setEnabled(false);
        four.setEnabled(false);
        five.setEnabled(false);
        six.setEnabled(false);
        seven.setEnabled(false);
        eight.setEnabled(false);
        nine.setEnabled(false);
        zero.setEnabled(false);
        dot.setEnabled(false);
    }

    private void EnableNumbers(){
        one.setEnabled(true);
        two.setEnabled(true);
        three.setEnabled(true);
        four.setEnabled(true);
        five.setEnabled(true);
        six.setEnabled(true);
        seven.setEnabled(true);
        eight.setEnabled(true);
        nine.setEnabled(true);
        zero.setEnabled(true);
        dot.setEnabled(true);
    }



}
