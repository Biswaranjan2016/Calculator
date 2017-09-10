package com.example.happy.calculator;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
    private boolean afterResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generallayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options,menu);
        return true;
    }

    public void setNumberInEditText(View view){
        button = (Button) view;

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Znikomit.otf");

        textView = (TextView) findViewById(R.id.display);
        textView.setTypeface(typeface);
        enteredString = button.getText();

        if (!afterResult){
            isNull();
        }else {
            afterPrintResultSetTextview();
        }
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
            stringNumbers = string.split("[+/*-]");
            //Obtain the array of operators
            operator = string.split("(\\w+[.]\\w+)|(\\w+)");

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
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
        for (int i = getIndex(operator);i<sOperator.length;i++){

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
            int iRes = (int)res;
            float resultant = res - iRes;
            if (resultant != 0){
                textView.setText(string+"\n= "+Float.toString(res));
            }else{
                textView.setText(string+"\n= "+Integer.toString(iRes));
            }
            afterResult = true;
            string = Float.toString(res);
        }catch (NullPointerException npe){
            textView = (TextView) findViewById(R.id.display);
        }
    }
    public void afterPrintResultSetTextview(){
        if (enteredString.equals("+")||enteredString.equals("-")||enteredString.equals("/")
                ||enteredString.equals("*")){
            string+=enteredString.toString();
            textView.setText(string);
            afterResult = false;
        }else {
            string = enteredString.toString();
            afterResult = false;
        }
    }
    public int getIndex(String operator[]){
        int i = 1;
        for (i = 1;i < operator.length;i++){
            if (operator[i]!=""){
                return i;
            }
        }
        return i-1;
    }
    public void backspace(View view){
        try{
            int length = string.length();
            string = string.substring(0,length-1);
            textView.setText(string);
        }catch (Exception e){

        }
    }

}
