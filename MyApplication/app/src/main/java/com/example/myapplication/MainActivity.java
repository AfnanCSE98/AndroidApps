package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void clearbtn(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        TextView out = (TextView) findViewById(R.id.txtSolution);
        inp.setText("");
        out.setText("Result");
    }
    public void btn0(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        TextView out = (TextView) findViewById(R.id.txtSolution);
        inp.append("0");

    }
    public void btn1(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        TextView out = (TextView) findViewById(R.id.txtSolution);
        inp.append("1");

    }
    public void btn2(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        TextView out = (TextView) findViewById(R.id.txtSolution);
        inp.append("2");

    }
    public void btn3(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("3");
    }
    public void btn4(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("4");
    }
    public void btn5(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("5");
    }
    public void btn6(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("6");
    }
    public void btn7(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("7");
    }
    public void btn8(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("8");
    }
    public void btn9(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append("9");
    }

    public void btnplus(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append(" + ");
    }
    public void btnminus(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append(" - ");
    }
    public void btnmultiply(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append(" * ");
    }
    public void btndivide(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append(" / ");

    }
    public void btndecimal(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        inp.append(".");

    }
    public void btnbs(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        //clr the text
        String text = inp.getText().toString();
        inp.setText(text.substring(0, text.length() - 1));
    }
    public void btnans(View v){
        EditText inp = (EditText)findViewById(R.id.txtInput);
        TextView out = (TextView) findViewById(R.id.txtSolution);
        String ans = out.getText().toString();
        inp.setText(ans);
    }
    public void equalButton(View v){

        EditText inp = (EditText)findViewById(R.id.txtInput);
        TextView out = (TextView) findViewById(R.id.txtSolution);

        String str = inp.getText().toString();
        double ans = calculate(str);
        String s = Double.toString(ans);

        out.setText(s);
        inp.setText("");
    }

    public double calculate(String expression){
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Double> values = new Stack<Double>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if ((tokens[i] >= '0' && tokens[i] <= '9')||(tokens[i]=='.'))
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i]=='.')))
                    sbuf.append(tokens[i++]);
                values.push(Double.parseDouble(sbuf.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static double applyOp(char op, double b, double a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    }


