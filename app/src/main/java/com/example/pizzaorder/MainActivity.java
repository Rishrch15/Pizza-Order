package com.example.pizzaorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroupPizza, radioGroupSize, radioGroupCrust;
    CheckBox checkBoxExtraCheese, checkBoxMushrooms, checkBoxOnions, checkBoxTomatoes, checkBoxPineapple;
    TextView textViewOrderDetails, textViewTotal;
    Button buttonProcessOrder, buttonNewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        radioGroupPizza = findViewById(R.id.radioGroupPizza);
        radioGroupSize = findViewById(R.id.radioGroupSize);
        radioGroupCrust = findViewById(R.id.radioGroupCrust);

        checkBoxExtraCheese = findViewById(R.id.checkBoxExtraCheese);
        checkBoxMushrooms = findViewById(R.id.checkBoxMushrooms);
        checkBoxOnions = findViewById(R.id.checkBoxOnions);
        checkBoxTomatoes = findViewById(R.id.checkBoxTomatoes);
        checkBoxPineapple = findViewById(R.id.checkBoxPineapple);

        textViewOrderDetails = findViewById(R.id.textViewOrderDetails);
        textViewTotal = findViewById(R.id.textViewTotal);

        buttonProcessOrder = findViewById(R.id.buttonProcessOrder);
        buttonNewOrder = findViewById(R.id.buttonNewOrder);

        // Process Order button logic
        buttonProcessOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder orderSummary = new StringBuilder();

                // Pizza Type
                int selectedPizzaId = radioGroupPizza.getCheckedRadioButtonId();
                RadioButton selectedPizza = findViewById(selectedPizzaId);
                orderSummary.append("Pizza: ").append(selectedPizza.getText()).append("\n");

                // Size
                int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();
                RadioButton selectedSize = findViewById(selectedSizeId);
                orderSummary.append("Size: ").append(selectedSize.getText()).append("\n");

                // Crust
                int selectedCrustId = radioGroupCrust.getCheckedRadioButtonId();
                RadioButton selectedCrust = findViewById(selectedCrustId);
                orderSummary.append("Crust: ").append(selectedCrust.getText()).append("\n");

                // Toppings
                orderSummary.append("Toppings: ");
                boolean hasToppings = false;
                if (checkBoxExtraCheese.isChecked()) {
                    orderSummary.append("Extra Cheese, ");
                    hasToppings = true;
                }
                if (checkBoxMushrooms.isChecked()) {
                    orderSummary.append("Mushrooms, ");
                    hasToppings = true;
                }
                if (checkBoxOnions.isChecked()) {
                    orderSummary.append("Onions, ");
                    hasToppings = true;
                }
                if (checkBoxTomatoes.isChecked()) {
                    orderSummary.append("Tomatoes, ");
                    hasToppings = true;
                }
                if (checkBoxPineapple.isChecked()) {
                    orderSummary.append("Pineapple, ");
                    hasToppings = true;
                }
                if (hasToppings) {
                    // Remove trailing comma
                    orderSummary.setLength(orderSummary.length() - 2);
                } else {
                    orderSummary.append("None");
                }

                textViewOrderDetails.setText(orderSummary.toString());
                textViewTotal.setText(""); // Calculation logic will be handled elsewhere
            }
        });

        // New Order button logic
        buttonNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset radio buttons
                radioGroupPizza.check(R.id.radioButtonHamAndCheese);
                radioGroupSize.check(R.id.radioButtonLarge);
                radioGroupCrust.check(R.id.radioButtonThick);

                // Uncheck all toppings
                checkBoxExtraCheese.setChecked(false);
                checkBoxMushrooms.setChecked(false);
                checkBoxOnions.setChecked(false);
                checkBoxTomatoes.setChecked(false);
                checkBoxPineapple.setChecked(false);

                // Clear order details
                textViewOrderDetails.setText("");
                textViewTotal.setText("");
            }
        });
    }
}
