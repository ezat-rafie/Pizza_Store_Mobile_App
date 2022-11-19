package com.example.pizzaappassignment2er;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity
        extends
            AppCompatActivity
        implements
            View.OnClickListener,
            CompoundButton.OnCheckedChangeListener,
            AdapterView.OnItemSelectedListener  {
    EditText pizzaNumberEditText,
            nameEditText,
            addressEditText,
            phoneEditText,
            garlicSauceEditText,
            ranchSauceEditText,
            bbqSauceEditText,
            hotSauceEditText;
    CheckBox pepperoniCheckBox,
            mushroomCheckBox,
            fetaCheckBox,
            garlicCheckBox,
            tomatoCheckBox,
            chickenCheckBox;
    RadioButton marinaraRadioBtn,
            bbqRadioBtn,
            noBaseRadioBtn,
            mildRadioBtn,
            noSpiceRadioBtn,
            hotRadioBtn,
            pickupRadioBtn,
            deliveryRadioBtn;
    Spinner pizzaSizeDropdown;
    TextView addressTextView,
            nameTextView,
            phoneTextView,
            errorTextView;
    Button orderBtn,
            resetBtn,
            ordersListBtn;
    String pizzaSize, name, address, phone = "";
    String spiceLevel = "No Spice";
    String baseSauce = "No Sauce";
    SharedPreferences saveData;
    int pizzaAmount, garlicDip, ranchDip, bbqDip, hotDip, subTotal = 0;
    boolean pepperoni, mushroom, chicken, feta, garlic, tomato, delivery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        pizzaNumberEditText = (EditText) findViewById(R.id.pizzaNumberEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        garlicSauceEditText = (EditText) findViewById(R.id.garlicSauceEditText);
        ranchSauceEditText = (EditText) findViewById(R.id.ranchSauceEditText);
        bbqSauceEditText = (EditText) findViewById(R.id.bbqSauceEditText);
        hotSauceEditText = (EditText) findViewById(R.id.hotSauceEditText);

        pepperoniCheckBox = (CheckBox) findViewById(R.id.pepperoniCheckBox);
        mushroomCheckBox = (CheckBox) findViewById(R.id.mushroomCheckBox);
        fetaCheckBox = (CheckBox) findViewById(R.id.fetaCheckBox);
        garlicCheckBox = (CheckBox) findViewById(R.id.garlicCheckBox);
        tomatoCheckBox = (CheckBox) findViewById(R.id.tomatoCheckBox);
        chickenCheckBox = (CheckBox) findViewById(R.id.chickenCheckBox);

        marinaraRadioBtn = (RadioButton) findViewById(R.id.marinaraRadioBtn);
        bbqRadioBtn = (RadioButton) findViewById(R.id.bbqRadioBtn);
        noBaseRadioBtn = (RadioButton) findViewById(R.id.noBaseRadioBtn);
        mildRadioBtn = (RadioButton) findViewById(R.id.mildRadioBtn);
        noSpiceRadioBtn = (RadioButton) findViewById(R.id.noSpiceRadioBtn);
        hotRadioBtn = (RadioButton) findViewById(R.id.hotRadioBtn);
        pickupRadioBtn = (RadioButton) findViewById(R.id.pickupRadioBtn);
        deliveryRadioBtn = (RadioButton) findViewById(R.id.deliveryRadioBtn);

        addressTextView = (TextView) findViewById(R.id.addressTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        errorTextView = (TextView) findViewById(R.id.errorTextView);

        pizzaSizeDropdown = (Spinner) findViewById(R.id.pizzaSizeDropdown);

        orderBtn = (Button) findViewById(R.id.orderBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        ordersListBtn = (Button) findViewById(R.id.ordersListBtn);

        pepperoniCheckBox.setOnCheckedChangeListener(this);
        mushroomCheckBox.setOnCheckedChangeListener(this);
        fetaCheckBox.setOnCheckedChangeListener(this);
        garlicCheckBox.setOnCheckedChangeListener(this);
        tomatoCheckBox.setOnCheckedChangeListener(this);
        chickenCheckBox.setOnCheckedChangeListener(this);

        marinaraRadioBtn.setOnClickListener(this);
        bbqRadioBtn.setOnClickListener(this);
        noBaseRadioBtn.setOnClickListener(this);
        mildRadioBtn.setOnClickListener(this);
        noSpiceRadioBtn.setOnClickListener(this);
        hotRadioBtn.setOnClickListener(this);
        pickupRadioBtn.setOnClickListener(this);
        deliveryRadioBtn.setOnClickListener(this);

        orderBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        ordersListBtn.setOnClickListener(this);

        pizzaSizeDropdown.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pizzaSize,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaSizeDropdown.setAdapter(adapter);

        addressEditText.setVisibility(View.GONE);
        addressTextView.setVisibility(View.GONE);

        errorTextView.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.marinaraRadioBtn:
                baseSauce = "Marinara";
                break;
            case R.id.bbqRadioBtn:
                baseSauce = "BBQ";
                break;
            case R.id.noBaseRadioBtn:
                baseSauce = "No Sauce";
                break;
            case R.id.mildRadioBtn:
                spiceLevel = "Mild";
                break;
            case R.id.noSpiceRadioBtn:
                spiceLevel = "No Spice";
                break;
            case R.id.hotRadioBtn:
                spiceLevel = "Hot";
                break;
            case R.id.pickupRadioBtn:
                addressEditText.setVisibility(View.GONE);
                addressTextView.setVisibility(View.GONE);
                delivery = false;
                break;
            case R.id.deliveryRadioBtn:
                delivery = true;
                addressEditText.setVisibility(View.VISIBLE);
                addressTextView.setVisibility(View.VISIBLE);
                break;

            case R.id.orderBtn:
                errorTextView.setText("");
                subTotal = 0;
                if (ValidateForm()){
                    name = nameEditText.getText().toString();
                    address = addressEditText.getText().toString();
                    phone = phoneEditText.getText().toString();
                    pizzaAmount = Integer.parseInt(pizzaNumberEditText.getText().toString());
                    garlicDip = Integer.parseInt(garlicSauceEditText.getText().toString());
                    ranchDip = Integer.parseInt(ranchSauceEditText.getText().toString());
                    bbqDip = Integer.parseInt(bbqSauceEditText.getText().toString());
                    hotDip = Integer.parseInt(hotSauceEditText.getText().toString());

                    int selectedSize = 10;
                    switch (pizzaSize){
                        case "Small 10$":
                            selectedSize = 10;
                            break;
                        case "Medium 12$":
                            selectedSize = 12;
                            break;
                        case "Large 14$":
                            selectedSize = 14;
                            break;
                    }
                    subTotal += pizzaAmount * selectedSize;

                    if (pepperoni)
                        subTotal += 2;
                    if (mushroom)
                        subTotal += 2;
                    if (chicken)
                        subTotal += 2;
                    if (feta)
                        subTotal += 2;
                    if (garlic)
                        subTotal += 2;
                    if (tomato)
                        subTotal += 2;

                    if (garlicDip > 0)
                        subTotal += garlicDip * 3;
                    if (ranchDip > 0)
                        subTotal += ranchDip * 3;
                    if (bbqDip > 0)
                        subTotal += bbqDip * 3;
                    if (hotDip > 0)
                        subTotal += hotDip *3;

                    if (delivery)
                        subTotal += 10;

                    Intent i = new Intent(getApplicationContext(), OrderSummaryActivity.class);
                    i.putExtra("pizzaSize", pizzaSize);
                    i.putExtra("baseSauce", baseSauce);
                    i.putExtra("pizzaAmount", pizzaAmount);
                    i.putExtra("spiceLevel", spiceLevel);
                    i.putExtra("pepperoni", pepperoni);
                    i.putExtra("mushroom", mushroom);
                    i.putExtra("chicken", chicken);
                    i.putExtra("feta", feta);
                    i.putExtra("garlic", garlic);
                    i.putExtra("tomato", tomato);
                    i.putExtra("garlicDip", garlicDip);
                    i.putExtra("ranchDip", ranchDip);
                    i.putExtra("bbqDip", bbqDip);
                    i.putExtra("hotDip", hotDip);
                    i.putExtra("delivery", delivery);
                    i.putExtra("name", name);
                    i.putExtra("address", address);
                    i.putExtra("phone", phone);
                    i.putExtra("subTotal", subTotal);
                    startActivity(i);
                }
                break;
            case R.id.resetBtn:
                clearForm();
                break;
            case R.id.ordersListBtn:
                Intent orders = new Intent(getApplicationContext(), OrdersListActivity.class);
                startActivity(orders);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        pizzaSize = String.valueOf(adapterView.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Spinner
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.pepperoniCheckBox:
                if (pepperoniCheckBox.isChecked())
                    pepperoni = true;
                else
                    pepperoni = false;
                break;
            case R.id.chickenCheckBox:
                if (chickenCheckBox.isChecked())
                    chicken = true;
                else
                    chicken = false;
                break;
            case R.id.tomatoCheckBox:
                if (tomatoCheckBox.isChecked())
                    tomato = true;
                else
                    tomato = false;
                break;
            case R.id.garlicCheckBox:
                if (garlicCheckBox.isChecked())
                    garlic = true;
                else
                    garlic = false;
                break;
            case R.id.fetaCheckBox:
                if (fetaCheckBox.isChecked())
                    feta = true;
                else
                    feta = false;
                break;
            case R.id.mushroomCheckBox:
                if (mushroomCheckBox.isChecked())
                    mushroom = true;
                else
                    mushroom = false;
                break;
        }
    }

    private void clearForm(){
        pizzaNumberEditText.setText("");
        nameEditText.setText("");
        addressEditText.setText("");
        phoneEditText.setText("");
        garlicSauceEditText.setText("0");
        ranchSauceEditText.setText("0");
        bbqSauceEditText.setText("0");
        hotSauceEditText.setText("0");
        errorTextView.setText("");

        pepperoniCheckBox.setChecked(false);
        mushroomCheckBox.setChecked(false);
        fetaCheckBox.setChecked(false);
        garlicCheckBox.setChecked(false);
        tomatoCheckBox.setChecked(false);
        chickenCheckBox.setChecked(false);

        marinaraRadioBtn.setChecked(true);
        bbqRadioBtn.setChecked(false);
        noBaseRadioBtn.setChecked(false);
        mildRadioBtn.setChecked(false);
        noSpiceRadioBtn.setChecked(true);
        hotRadioBtn.setChecked(false);
        pickupRadioBtn.setChecked(true);
        deliveryRadioBtn.setChecked(false);
        pizzaSizeDropdown.setSelection(0);
    }

    public boolean ValidateForm(){
        boolean isValid = true;
        String errorMsg = "";
        if (pizzaNumberEditText.getText().toString().trim().length() < 1 ||
                Integer.valueOf(pizzaNumberEditText.getText().toString()) < 1){
            errorMsg += "At least 1 pizza should be added. \n";
            isValid = false;
        }
        if (phoneEditText.getText().toString().trim().length() < 1 ||
                !isPhoneValid(phoneEditText.getText().toString())) {
            errorMsg += "Phone number is not valid. \n";
            isValid = false;
        }
        if(delivery && (addressEditText.getText().toString().trim().length() < 1 ||
                addressEditText.getText().toString().isEmpty())){
            errorMsg += "Address is required. \n";
            isValid = false;
        }
        if(nameEditText.getText().toString().trim().length() < 1 ||
                nameEditText.getText().toString().isEmpty()){
            errorMsg += "Name is required. \n";
            isValid = false;
        }
        errorTextView.setText(errorMsg);
        return isValid;
    }

    public boolean isPhoneValid(String phone) {
        /*
        * matches
        * 123-456-7890
        * (123) 456-7890
        * 123 456 7890
        * 123.456.7890
        * +91 (123) 456-7890
        * 1234567890
        * */
        String regex = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        return phone.matches(regex);
    }
}