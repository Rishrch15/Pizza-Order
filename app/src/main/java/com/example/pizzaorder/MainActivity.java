package com.example.pizzaorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final double THICK_CRUST_MULTIPLIER = 1.5;

    private static final double TOMATOES_PRICE = 10.0;
    private static final double ONIONS_PRICE = 10.0;
    private static final double PINEAPPLE_PRICE = 15.0;
    private static final double EXTRA_CHEESE_PRICE = 20.0;
    private static final double MUSHROOMS_PRICE = 20.0;

    private static final double PWD_DISCOUNT_RATE = 0.20; // 20% discount
    private static final double VAT_RATE = 0.12;          // 12% VAT


    private RadioGroup radioGroupPizza;
    private RadioButton radioButtonHawaiian;
    private RadioButton radioButtonHamAndCheese;

    private RadioGroup radioGroupSize;
    private RadioButton radioButtonSmall;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonLarge;

    private RadioGroup radioGroupCrust;
    private RadioButton radioButtonThin;
    private RadioButton radioButtonThick;

    private CheckBox checkBoxExtraCheese;
    private CheckBox checkBoxMushrooms;
    private CheckBox checkBoxOnions;
    private CheckBox checkBoxTomatoes;
    private CheckBox checkBoxPineapple;

    private Button buttonProcessOrder;
    private Button buttonNewOrder;
    private Button buttonIsPwd;
    private boolean isPwd = false;

    private TextView textViewOrderDetails;
    private TextView textViewTotal;

    private double pizzaPrice = 0.0;
    private double crustPriceModifier = 1.0;
    private double toppingsPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupPizza = findViewById(R.id.radioGroupPizza);
        radioButtonHawaiian = findViewById(R.id.radioButtonHawaiian);
        radioButtonHamAndCheese = findViewById(R.id.radioButtonHamAndCheese);

        radioGroupSize = findViewById(R.id.radioGroupSize);
        radioButtonSmall = findViewById(R.id.radioButtonSmall);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonLarge = findViewById(R.id.radioButtonLarge);

        radioGroupCrust = findViewById(R.id.radioGroupCrust);
        radioButtonThin = findViewById(R.id.radioButtonThin);
        radioButtonThick = findViewById(R.id.radioButtonThick);

        checkBoxExtraCheese = findViewById(R.id.checkBoxExtraCheese);
        checkBoxMushrooms = findViewById(R.id.checkBoxMushrooms);
        checkBoxOnions = findViewById(R.id.checkBoxOnions);
        checkBoxTomatoes = findViewById(R.id.checkBoxTomatoes);
        checkBoxPineapple = findViewById(R.id.checkBoxPineapple);

        buttonProcessOrder = findViewById(R.id.buttonProcessOrder);
        buttonNewOrder = findViewById(R.id.buttonNewOrder);
        buttonIsPwd = findViewById(R.id.buttonIsPwd);

        textViewOrderDetails = findViewById(R.id.textViewOrderDetails);
        textViewTotal = findViewById(R.id.textViewTotal);

        buttonProcessOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!radioButtonHawaiian.isChecked() && !radioButtonHamAndCheese.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please select a pizza first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!radioButtonSmall.isChecked() && !radioButtonMedium.isChecked() && !radioButtonLarge.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please select a pizza size.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!radioButtonThin.isChecked() && !radioButtonThick.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please select a crust type.", Toast.LENGTH_SHORT).show();
                    return;
                }


                calculateOrder();
                displayOrder();
            }
        });

        buttonNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOrder();
            }
        });

        buttonIsPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePwdStatus();
            }
        });

        radioGroupPizza.clearCheck();
        radioGroupSize.clearCheck();
        radioGroupCrust.clearCheck();
    }

    private void togglePwdStatus() {
        isPwd = !isPwd;
        Toast.makeText(this, "PWD Status: " + (isPwd ? "Yes" : "No"), Toast.LENGTH_SHORT).show();
    }

    private void calculateOrder() {
        pizzaPrice = 0.0;
        crustPriceModifier = 1.0;
        toppingsPrice = 0.0;

        // Set base pizza price depending on type and size
        if (radioButtonSmall.isChecked()) {
            pizzaPrice = radioButtonHawaiian.isChecked() ? 100.0 : 200.0;
        } else if (radioButtonMedium.isChecked()) {
            pizzaPrice = radioButtonHawaiian.isChecked() ? 150.0 : 300.0;
        } else if (radioButtonLarge.isChecked()) {
            pizzaPrice = radioButtonHawaiian.isChecked() ? 200.0 : 400.0;
        }

        // Crust modifier
        if (radioButtonThick.isChecked()) {
            crustPriceModifier = THICK_CRUST_MULTIPLIER;
        }

        // Toppings pricing (individual)
        if (checkBoxTomatoes.isChecked()) {
            toppingsPrice += TOMATOES_PRICE;
        }
        if (checkBoxOnions.isChecked()) {
            toppingsPrice += ONIONS_PRICE;
        }
        if (checkBoxPineapple.isChecked()) {
            toppingsPrice += PINEAPPLE_PRICE;
        }
        if (checkBoxExtraCheese.isChecked()) {
            toppingsPrice += EXTRA_CHEESE_PRICE;
        }
        if (checkBoxMushrooms.isChecked()) {
            toppingsPrice += MUSHROOMS_PRICE;
        }
    }

    private void displayOrder() {
        StringBuilder orderDetails = new StringBuilder();
        String pizzaType = "";
        String pizzaSize = "";
        String crustType = "";
        StringBuilder extraToppings = new StringBuilder();
        double subtotal = (pizzaPrice * crustPriceModifier) + toppingsPrice;
        double finalTotal = subtotal;
        double discount = 0;
        double vat = 0;

        if (radioButtonHawaiian.isChecked()) {
            pizzaType = "Hawaiian Pizza";
        } else if (radioButtonHamAndCheese.isChecked()) {
            pizzaType = "Ham & Cheese Pizza";
        }

        if (radioButtonSmall.isChecked()) {
            pizzaSize = "Small";
        } else if (radioButtonMedium.isChecked()) {
            pizzaSize = "Medium";
        } else if (radioButtonLarge.isChecked()) {
            pizzaSize = "Large";
        }

        if (radioButtonThin.isChecked()) {
            crustType = "Thin Crust";
        } else if (radioButtonThick.isChecked()) {
            crustType = "Thick Crust";
        }

        // Collect extra toppings
        if (checkBoxExtraCheese.isChecked()) {
            if (extraToppings.length() > 0) extraToppings.append(", ");
            extraToppings.append("Extra Cheese");
        }
        if (checkBoxMushrooms.isChecked()) {
            if (extraToppings.length() > 0) extraToppings.append(", ");
            extraToppings.append("Mushrooms");
        }
        if (checkBoxOnions.isChecked()) {
            if (extraToppings.length() > 0) extraToppings.append(", ");
            extraToppings.append("Onions");
        }
        if (checkBoxTomatoes.isChecked()) {
            if (extraToppings.length() > 0) extraToppings.append(", ");
            extraToppings.append("Tomatoes");
        }
        if (checkBoxPineapple.isChecked()) {
            if (extraToppings.length() > 0) extraToppings.append(", ");
            extraToppings.append("Pineapple");
        }


        orderDetails.append(String.format("%s\n", pizzaType));
        orderDetails.append(String.format("    (%s & %s) %s%.2f\n", pizzaSize, crustType, "", pizzaPrice * crustPriceModifier));

        if (extraToppings.length() > 0) {
            orderDetails.append("Extra Toppings\n");
            orderDetails.append(String.format("    (%s) %s%.2f\n", extraToppings.toString(), "", toppingsPrice));
        }

        if (isPwd) {
            discount = subtotal * PWD_DISCOUNT_RATE;
            finalTotal -= discount;
            orderDetails.append(String.format("PWD Discount (%.0f%%): %s-%.2f\n", PWD_DISCOUNT_RATE * 100, "", discount));
        }

        vat = finalTotal * VAT_RATE;
        finalTotal += vat;
        orderDetails.append(String.format("VAT (%.0f%%): %s+%.2f\n", VAT_RATE * 100, "", vat));

        orderDetails.append(String.format("Subtotal: %s%.2f\n", "", subtotal));

        textViewOrderDetails.setText(orderDetails.toString());
        textViewTotal.setText(String.format("TOTAL = %.2f", finalTotal));
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
        textViewTotal.setText("TOTAL: $0.00");

        pizzaPrice = 0.0;
        crustPriceModifier = 1.0;
        toppingsPrice = 0.0;
        isPwd = false;

        Toast.makeText(this, "PWD Status: No", Toast.LENGTH_SHORT).show();
    }

}
