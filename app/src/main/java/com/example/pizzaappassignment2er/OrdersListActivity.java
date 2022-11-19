package com.example.pizzaappassignment2er;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrdersListActivity extends AppCompatActivity implements View.OnClickListener {
    TextView orderListTextView;
    Button returnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        getSupportActionBar().hide();

        orderListTextView = (TextView) findViewById(R.id.orderListTextView);
        returnBtn = (Button) findViewById(R.id.returnBtn);

        returnBtn.setOnClickListener(this);

        DBHelper dbHelp=new DBHelper(this);
        orderListTextView.setText(dbHelp.getAllOrders());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.returnBtn:
                finish();
                break;
        }
    }
}