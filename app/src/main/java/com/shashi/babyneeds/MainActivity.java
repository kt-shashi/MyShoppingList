package com.shashi.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shashi.babyneeds.data.DatabaseHandler;
import com.shashi.babyneeds.model.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemPrice;

    private DatabaseHandler databaseHandler;


    //Sa
    private void saveItem(View view) {
        //Save Each item to DB

        Item item = new Item();


        //Get data from Popup
        String newitem = babyItem.getText().toString().trim();
        String newColor = itemColor.getText().toString().trim();
        int quantity = Integer.parseInt(itemQuantity.getText().toString().trim());
        int price = Integer.parseInt(itemPrice.getText().toString().trim());

        //Set data
        item.setItemName(newitem);
        item.setItemColor(newColor);
        item.setItemQuantity(quantity);
        item.setItemPrice(price);

        //Add to DB
        databaseHandler.addItem(item);

        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                //Move to new Screen
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);
        byPassActivity();

        //Check if Item was saved
        List<Item> items = databaseHandler.getAllItems();
        for (Item item : items) {
            Log.d("Main", "Shashidb:    " + item.getItemName()
                    + "\nDate: " + item.getDateItemAdded());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
    }


    //Popup Dialog
    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        babyItem = view.findViewById(R.id.babyItem);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        itemColor = view.findViewById(R.id.itemColor);
        itemPrice = view.findViewById(R.id.itemPrice);


        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!babyItem.getText().toString().trim().isEmpty()
                        && !itemColor.getText().toString().trim().isEmpty()
                        && !itemQuantity.getText().toString().trim().isEmpty()
                        && !itemPrice.getText().toString().trim().isEmpty()) {

                    saveItem(v);

                } else {
                    Snackbar.make(v, "Empty fields not allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        builder.setView(view);
        dialog = builder.create();        //Create Dialog obj
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.developer_menu) {
            Toast.makeText(this, "Developer: coding.shashi@gamil.com", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void byPassActivity() {

        //If records are present, go to new activity
        if (databaseHandler.getitemCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
