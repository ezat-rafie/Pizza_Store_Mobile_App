package com.example.pizzaappassignment2er;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="pizza_order_db";
    private static final int DB_VERSION=1;
    private static final String TB_NAME="orders";
    private static final String ID_COL="id";
    private static final String PIZZA_AMOUNT_COL="pizzaAmount";
    private static final String SUB_TOTAL_COL="subTotal";
    private static final String TOTAL_COL="total";
    private static final String PIZZA_SIZE_COL="pizzaSize";
    private static final String BASE_SAUCE_COL="baseSauce";
    private static final String TOPPINGS_COL="toppings";
    private static final String SPICE_LEVEL_COL="spiceLevel";
    private static final String DIPPING_SAUCE_COL="dippingSauce";
    private static final String DELIVERY_COL="delivery";
    private static final String NAME_COL="name";
    private static final String ADDRESS_COL="address";
    private static final String PHONE_COL="phone";
    public DBHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table "+TB_NAME+" ("+ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PIZZA_AMOUNT_COL+" TEXT," +
                SUB_TOTAL_COL+" TEXT," +
                TOTAL_COL+" TEXT," +
                PIZZA_SIZE_COL+" TEXT," +
                BASE_SAUCE_COL+" TEXT," +
                TOPPINGS_COL+" TEXT," +
                SPICE_LEVEL_COL+" TEXT," +
                DIPPING_SAUCE_COL+" TEXT," +
                DELIVERY_COL+" TEXT," +
                NAME_COL+" TEXT," +
                ADDRESS_COL+" TEXT," +
                PHONE_COL+" TEXT)";
        db.execSQL(query);
    }
    //add order into database
    public void addOrder(String pizzaAmount,
                         String subTotal,
                         String total,
                         String pizzaSize,
                         String baseSauce,
                         String toppings,
                         String spiceLevel,
                         String dippingSauce,
                         String delivery,
                         String name,
                         String address,
                         String phone)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(PIZZA_AMOUNT_COL,pizzaAmount);
        values.put(SUB_TOTAL_COL,subTotal);
        values.put(TOTAL_COL,total);
        values.put(PIZZA_SIZE_COL,pizzaSize);
        values.put(BASE_SAUCE_COL,baseSauce);
        values.put(TOPPINGS_COL,toppings);
        values.put(SPICE_LEVEL_COL,spiceLevel);
        values.put(DIPPING_SAUCE_COL,dippingSauce);
        values.put(DELIVERY_COL,delivery);
        values.put(NAME_COL,name);
        values.put(ADDRESS_COL,address);
        values.put(PHONE_COL,phone);
        db.insert(TB_NAME,null,values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public String getAllOrders()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String data="";
        Cursor cr=db.rawQuery("select * from "+TB_NAME,null);
        cr.moveToFirst();
        if(cr!=null && cr.getCount()>0) {
            do {
                for (int i = 1; i < cr.getColumnCount(); i++) {
                    data=data+" "+ cr.getColumnName(i) + ": " + cr.getString(i)+"\n";
                }
                data=data+"\n\n";
            }while(cr.moveToNext());
        }
        else
        {
            data="no Data Found!";
        }
        db.close();
        return data;
    }
}
