package com.example.happy.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int enteredNumber;
    private CharSequence enteredString;
    private String string;

    private Button button;
    private TextView textView;

    private String stringNumbers[];
    private float floatNumbers[];
    private String operator[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generallayout);
    }
    public void setNumberInEditText(View view){
        button = (Button) view;
        textView = (TextView) findViewById(R.id.display);
        enteredString = button.getText();
        isNull();
        textView.setText(string);
    }
    private void isNull(){
        if (string == null){
            string = enteredString.toString();
        }else {
            string+=enteredString;
        }
    }
    public void clearField(View view){
        try{
            textView.setText("");
            string = "";
        }catch (IllegalStateException e){
            Toast.makeText(this,"Nothing to clear",Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){
            textView = (TextView)findViewById(R.id.display);
        }
    }
    public void calculate(View view){
        try{
            //Get the text from the textView
            enteredString = textView.getText();
            //Convert the obtained charsequence to string
            string = enteredString.toString();

            //Obtain the array Of Digits
            stringNumbers = string.split("[+*-/]");
            //Obtain the array of operators
            operator = string.split("\\w+");

        }catch (NullPointerException npe){
            textView = (TextView)findViewById(R.id.display);
        }

        try{
            floatNumbers = new float[stringNumbers.length];
            for (int i = 0; i<stringNumbers.length; i++){
                floatNumbers[i] = Float.parseFloat(stringNumbers[i]);
            }
            calculateAndPrint(floatNumbers,operator);
        }catch (Exception e){

        }
    }
    private void calculateAndPrint(float[] fNumbers,String[] sOperator){
        String returnedString[];

        int index = 0;
        do {
            returnedString = hasOperator(sOperator);

            if (returnedString[1] != "")
                index = Integer.parseInt(returnedString[0]);

            switch (returnedString[1]){
                case "/": try{
                    fNumbers[index] = fNumbers[index-1]/fNumbers[index];
                }catch (ArithmeticException ae){
                    textView.setText(string+"\nerror");
                }
                    fNumbers[index-1] = fNumbers[index];
                    break;
                case "*": fNumbers[index] = fNumbers[index]*fNumbers[index-1];
                    fNumbers[index-1] = fNumbers[index];
                    break;
                case "+": fNumbers[index] = fNumbers[index]+fNumbers[index-1];
                    fNumbers[index-1] = fNumbers[index];
                    break;
                case "-": fNumbers[index] = fNumbers[index-1]-fNumbers[index];
                    fNumbers[index-1] = fNumbers[index];
                    break;
                default:
            }
        }while (returnedString[1]!= "");
        printResult(fNumbers[index]);
    }
    private String[] hasOperator(String[] sOperator){
        String returnString[] = new String[2];
        for (int i = 1;i<sOperator.length;i++){

            if (sOperator[i].equals("/")){
                returnString[0] = Integer.toString(i);
                returnString[1] = "/";
                sOperator[i] = "";
                return returnString;

            }else if (sOperator[i].equals("*")){
                returnString[0] = Integer.toString(i);
                returnString[1] = "*";
                sOperator[i] = "";
                return returnString;
            }else if (sOperator[i].equals("+")){
                returnString[0] = Integer.toString(i);
                returnString[1] = "+";
                sOperator[i] = "";
                return returnString;
            }else if (sOperator[i].equals("-")){
                returnString[0] = Integer.toString(i);
                returnString[1] = "-";
                sOperator[i] = "";
                return returnString;
            }else {
                returnString[0] = Integer.toString(i);
                returnString[1] = "";
                return returnString;
            }
        }
        return returnString;
    }

    public void printResult(float res){
        try{
            textView.setText(Float.toString(res));
//            string = "";
        }catch (NullPointerException npe){
            textView = (TextView) findViewById(R.id.display);
        }
    }

}
