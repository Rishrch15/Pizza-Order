package com.example.pizzaorder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupPizza, radioGroupSize, radioGroupCrust;
    private CheckBox checkBoxExtraCheese, checkBoxMushrooms, checkBoxOnions, checkBoxTomatoes, checkBoxPineapple;
    private Button buttonProcessOrder, buttonNewOrder;
    private TextView textViewOrderDetails, textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all views
        radioGroupPizza = findViewById(R.id.radioGroupPizza);
        radioGroupSize = findViewById(R.id.radioGroupSize);
        radioGroupCrust = findViewById(R.id.radioGroupCrust);

        checkBoxExtraCheese = findViewById(R.id.checkBoxExtraCheese);
        checkBoxMushrooms = findViewById(R.id.checkBoxMushrooms);
        checkBoxOnions = findViewById(R.id.checkBoxOnions);
        checkBoxTomatoes = findViewById(R.id.checkBoxTomatoes);
        checkBoxPineapple = findViewById(R.id.checkBoxPineapple);

        buttonProcessOrder = findViewById(R.id.buttonProcessOrder);
        buttonNewOrder = findViewById(R.id.buttonNewOrder);

        textViewOrderDetails = findViewById(R.id.textViewOrderDetails);
        textViewTotal = findViewById(R.id.textViewTotal);

        // Set button listeners
        buttonProcessOrder.setOnClickListener(v -> processOrder());
        buttonNewOrder.setOnClickListener(v -> resetOrder());
    }

    private void processOrder() {
        int basePrice = 0;
        int total = 0;
        StringBuilder order = new StringBuilder("You ordered: ");

        // sa Pizza type
        int selectedPizzaId = radioGroupPizza.getCheckedRadioButtonId();
        RadioButton selectedPizzaButton = findViewById(selectedPizzaId);
        if (selectedPizzaButton == null) {
            Toast.makeText(this, "Please select a pizza type.", Toast.LENGTH_SHORT).show();
            return;
        }
        String pizzaType = selectedPizzaButton.getText().toString();

        // sa Pizza size
        int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();
        RadioButton selectedSizeButton = findViewById(selectedSizeId);
        if (selectedSizeButton == null) {
            Toast.makeText(this, "Please select a pizza size.", Toast.LENGTH_SHORT).show();
            return;
        }
        String size = selectedSizeButton.getText().toString();

        // Determine base price
        switch (pizzaType) {
            case "Hawaiian":
                basePrice = size.equals("Small") ? 100 : size.equals("Medium") ? 150 : 200;
                break;
            case "Ham & Cheese":
                basePrice = size.equals("Small") ? 200 : size.equals("Medium") ? 300 : 400;
                break;
        }
        total += basePrice;
        order.append(pizzaType).append(", ").append(size).append(" Size");

        // Base sa  ang price Crust type
        int selectedCrustId = radioGroupCrust.getCheckedRadioButtonId();
        RadioButton selectedCrustButton = findViewById(selectedCrustId);
        String crust = selectedCrustButton.getText().toString();
        order.append(", ").append(crust).append(" Crust");

        if (crust.equals("Thick")) {
            int crustAdd = (int) (basePrice * 0.5);
            total += crustAdd;
            order.append(" (+₱").append(crustAdd).append(")");
        }

        // Para sa Toppings Pricing
        order.append("\nToppings: ");
        int toppingTotal = 0;
        int count = 0;

        if (checkBoxExtraCheese.isChecked()) {
            toppingTotal += 20;
            order.append("Extra Cheese ");
            count++;
        }
        if (checkBoxMushrooms.isChecked()) {
            toppingTotal += 20;
            order.append("Mushrooms ");
            count++;
        }
        if (checkBoxOnions.isChecked()) {
            toppingTotal += 10;
            order.append("Onions ");
            count++;
        }
        if (checkBoxTomatoes.isChecked()) {
            toppingTotal += 10;
            order.append("Tomatoes ");
            count++;
        }
        if (checkBoxPineapple.isChecked()) {
            toppingTotal += 15;
            order.append("Pineapple ");
            count++;
        }

        if (count == 0) {
            order.append("None");
        }

        total += toppingTotal;

        // Para to sa PWD Discount tsaka vat
        double vat = total * 0.12;
        double finalTotal = total + vat;

        order.append("\nBase Price: ₱").append(total);
        order.append("\nVAT (12%): +₱").append(String.format("%.2f", vat));
        order.append("\nFinal Total: ₱").append(String.format("%.2f", finalTotal));

        textViewOrderDetails.setText(order.toString());
        textViewTotal.setText(String.format("₱%.2f", finalTotal));
    }

    private void resetOrder() {
        radioGroupPizza.clearCheck();
        radioGroupSize.clearCheck();
        radioGroupCrust.clearCheck();

        checkBoxExtraCheese.setChecked(false);
        checkBoxMushrooms.setChecked(false);
        checkBoxOnions.setChecked(false);
        checkBoxTomatoes.setChecked(false);
        checkBoxPineapple.setChecked(false);

        textViewOrderDetails.setText("");
        textViewTotal.setText("");
    }
}
