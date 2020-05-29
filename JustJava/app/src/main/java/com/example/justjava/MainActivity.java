package com.example.justjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    int priceCoffee = 5;
    int priceWhippedCream = 1;
    int priceChocolate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void plusCount (View view) {
        display(++count);
    }

    public void minusCount (View view) {
        if (count<= 0) {
            count=1;
            Toast toast = Toast.makeText(getApplicationContext(),"Вы не можете заказать меньше 0 чашек кофе!", Toast.LENGTH_SHORT);
            toast.show();
        }
        display(--count);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePrice(int quantity){
        int totalPrice = 0;
        int priceTopping = 0;
        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate);

        if (hasWhippedCream.isChecked())
            priceTopping += priceWhippedCream;

        if (hasChocolate.isChecked())
            priceTopping+=priceChocolate;

        totalPrice=quantity*(priceTopping+priceCoffee);
        return totalPrice;
    }

    public void displayPrice(View view) {
        EditText editName = (EditText) findViewById(R.id.edit_name);

        TextView priceText = (TextView) findViewById(R.id.price);
        priceText.setText("Total price: ");
        priceText.append(NumberFormat.getCurrencyInstance().format(calculatePrice(count)));
        priceText.append("\nThank you, " + editName.getText());
    }
}