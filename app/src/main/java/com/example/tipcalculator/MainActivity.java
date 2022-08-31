package com.example.tipcalculator;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;

import java.text.NumberFormat;

public class MainActivity extends Activity implements TextView.OnEditorActionListener {
    // define the instance variables for the widgets that the class needs to work with
    private EditText billAmountEditText;
    private EditText percentageEditText;
    private TextView tipTextView;
    private TextView totalTextView;

    private SharedPreferences savedValues;
    private float billAmount = 0.0f;
    private float tipPercent = 15f;

   //The onCreate method begins by calling the onCreate method of the super- class,
   // which is necessary for the superclass to work correctly. Then, it uses the
   // setContentView method that’s available from the superclass to display the user interface that’s defined in the XML file for the activity.
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        billAmountEditText = (EditText) findViewById(R.id.billAmountInput);
        percentageEditText = findViewById(R.id.percentageInput);
        tipTextView = findViewById(R.id.tipInput);
        totalTextView = findViewById(R.id.totalInput);

        //After getting references to the widgets, this code sets the listeners.
        // First, it sets the current class as the listener for the EditorAction
        // event on the editable text view for the bill amount.
        percentageEditText.setOnEditorActionListener(this);
        savedValues = getPreferences(MODE_PRIVATE);

    }

    //The onPause method saves both of this activity’s instance variables:
    // the bill amount string and the tip percent. These instance variables need
    // to be saved for the app to work correctly when orientation changes and when the user navigates away from and back to this activity.
    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putFloat("billAmount", billAmount);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();
        super.onPause();
    }

    //The onResume method restores both of the activity’s instance variables.
    // Then, it sets the bill amount string as the display text on the bill
    // amount EditText widget. This is necessary because Android sets the display
    // text to its default value if the user navigates away from and back to this activity.
    // Finally, this code calls the calculateAndDisplay method.
    @Override
    public void onResume() {
        super.onResume();
        billAmount = savedValues.getFloat("billAmount", 0.0f);
        tipPercent = savedValues.getFloat("tipPercent", 15f);
        billAmountEditText.setText(String.valueOf(billAmount));
        percentageEditText.setText(String.valueOf(tipPercent));
        tipTextView.setText(String.valueOf(tipPercent));
        calculateAndDisplay();
    }

    //The calculateAndDisplay method calculates the tip and total amounts for the bill
    // and displays all current data on the user interface. First, this method gets the
    // bill amount from the EditText widget and converts this string to a float value.
    // Then, it calculates the tip and total amounts and stores them as float values.
    // Finally, it formats these float values and displays them on their corresponding
    // widgets.
    private void calculateAndDisplay() {
        billAmount = Float.parseFloat(billAmountEditText.getText().toString());
        tipPercent = Float.parseFloat(percentageEditText.getText().toString());
        float tipAmount = billAmount * (tipPercent / 100);
        float totalAmount = billAmount + tipAmount;

        tipTextView.setText(String.valueOf(tipAmount));
        totalTextView.setText(String.valueOf(totalAmount));
    }

    //The onEditorAction method is executed whenever the user presses an action key
    // on a soft keyboard such as the Done key. This method begins by using an if
    // statement to check whether the action key is the Done key. If so, it calls the
    // calculateAndDisplay method to perform the calculation and display the results
    // on the user interface.
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            calculateAndDisplay();
        }
        return false;
    }
}