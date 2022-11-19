package com.example.pizzaappassignment2er;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener{
    TextView pizzaSizeTextView,
            pizzaAmountTextView,
            baseSauceTextView,
            toppingsTextView,
            spiceLevelTextView,
            dippingSauceTextView,
            deliveryTextView,
            nameTextView,
            addressTextView,
            phoneTextView,
            subTotalTextView,
            totalTextView;
    Button placeOrderBtn,
            editOrderBtn;

    Integer pizzaAmount,
            subTotal,
            total = 0;
    String pizzaSize,
            baseSauce,
            toppings,
            spiceLevel,
            dippingSauce,
            delivery,
            name,
            address,
            phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        getSupportActionBar().hide();

        pizzaSizeTextView = (TextView) findViewById(R.id.pizzaSizeTextView);
        pizzaAmountTextView = (TextView) findViewById(R.id.pizzaAmountTextView);
        baseSauceTextView = (TextView) findViewById(R.id.baseSauceTextView);
        toppingsTextView = (TextView) findViewById(R.id.toppingsTextView);
        spiceLevelTextView = (TextView) findViewById(R.id.spiceLevelTextView);
        dippingSauceTextView = (TextView) findViewById(R.id.dippingSauceTextView);
        deliveryTextView = (TextView) findViewById(R.id.deliveryTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        subTotalTextView = (TextView) findViewById(R.id.subTotalTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        placeOrderBtn = (Button) findViewById(R.id.placeOrderBtn);
        editOrderBtn = (Button) findViewById(R.id.editOrderBtn);

        Intent intent = getIntent();

        List<String> toppingsList = new ArrayList<String>();
        if (intent.getBooleanExtra("pepperoni", false))
            toppingsList.add("Pepperoni");
        if (intent.getBooleanExtra("mushroom", false))
            toppingsList.add("Mushroom");
        if (intent.getBooleanExtra("chicken", false))
            toppingsList.add("Chicken");
        if (intent.getBooleanExtra("feta", false))
            toppingsList.add("Feta");
        if (intent.getBooleanExtra("garlic", false))
            toppingsList.add("Garlic");
        if (intent.getBooleanExtra("tomato", false))
            toppingsList.add("Tomato");

        List<String> dips = new ArrayList<String>();

        if (intent.getIntExtra("garlicDip", 0) > 0)
            dips.add("Garlic Dip x" + intent.getIntExtra("garlicDip", 0));
        if (intent.getIntExtra("ranchDip", 0) > 0)
            dips.add("Ranch Dip x" + intent.getIntExtra("ranchDip", 0));
        if (intent.getIntExtra("bbqDip", 0) > 0)
            dips.add("BBQ Dip x" + intent.getIntExtra("bbqDip", 0));
        if (intent.getIntExtra("hotDip", 0) > 0)
            dips.add("Hot Dip x" + intent.getIntExtra("hotDip", 0));

        pizzaAmount = intent.getIntExtra("pizzaAmount", 1);
        subTotal = intent.getIntExtra("subTotal", 0);
        total = Math.toIntExact(Math.round(intent.getIntExtra("subTotal", 0) * 1.13));
        pizzaSize = intent.getStringExtra("pizzaSize");
        baseSauce = intent.getStringExtra("baseSauce");
        toppings = String.join("\n", toppingsList);
        spiceLevel = intent.getStringExtra("spiceLevel");
        dippingSauce = String.join("\n", dips);
        delivery= intent.getBooleanExtra("delivery", false) == false ? "Pick Up" : "Delivery";
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        phone = intent.getStringExtra("phone");

        pizzaSizeTextView.setText(pizzaSize);
        pizzaAmountTextView.setText(String.valueOf(pizzaAmount));
        baseSauceTextView.setText(baseSauce);
        toppingsTextView.setText(toppings);
        spiceLevelTextView.setText(spiceLevel);
        dippingSauceTextView.setText(dippingSauce);
        deliveryTextView.setText(delivery);
        nameTextView.setText(name);
        addressTextView.setText(address);
        phoneTextView.setText(phone);
        subTotalTextView.setText(String.valueOf(subTotal) + " $");
        totalTextView.setText(String.valueOf(total) + " $");

        placeOrderBtn.setOnClickListener(this);
        editOrderBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DBHelper dbHelp=new DBHelper(this);
        switch (view.getId()){
            case R.id.placeOrderBtn:
                dbHelp.addOrder(String.valueOf(pizzaAmount),
                        String.valueOf(subTotal),
                        String.valueOf(total),
                        pizzaSize,
                        baseSauce,
                        toppings,
                        spiceLevel,
                        dippingSauce,
                        delivery,
                        name,
                        address,
                        phone);
                Toast.makeText(this,"Order is placed successfully", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.editOrderBtn:
                super.onBackPressed();
                break;
        }
    }
}