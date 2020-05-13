package com.shashi.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shashi.babyneeds.R;
import com.shashi.babyneeds.data.DatabaseHandler;
import com.shashi.babyneeds.model.Item;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.itemName.setText("Item: " + item.getItemName());
        holder.itemColor.setText("Color: " + item.getItemColor());
        holder.quantity.setText("Quantity: " + item.getItemQuantity() + "");
        holder.price.setText("Price: " + item.getItemPrice() + "");
        holder.dateAdder.setText("Added on: " + item.getDateItemAdded());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemName;
        public TextView itemColor;
        public TextView quantity;
        public TextView price;
        public TextView dateAdder;
        public int id;

        public Button editButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;


            itemName = itemView.findViewById(R.id.item_name);
            itemColor = itemView.findViewById(R.id.item_color);
            quantity = itemView.findViewById(R.id.item_quantity);
            price = itemView.findViewById(R.id.item_price);
            dateAdder = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Item item = itemList.get(position);

            switch (v.getId()) {
                case R.id.editButton:
                    editItem(item);
                    break;
                case R.id.deleteButton:
                    deleteItem(item.getId());
                    break;
            }
        }


        //Delete a record
        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.confirmation_pop, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();


            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

        }


        //Edit a record
        private void editItem(final Item newItem) {


            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            Button saveButton;
            final EditText babyItem;
            final EditText itemQuantity;
            final EditText itemColor;
            final EditText itemPrice;

            TextView title;

            babyItem = view.findViewById(R.id.babyItem);
            itemQuantity = view.findViewById(R.id.itemQuantity);
            itemColor = view.findViewById(R.id.itemColor);
            itemPrice = view.findViewById(R.id.itemPrice);
            saveButton = view.findViewById(R.id.saveButton);
            title = view.findViewById(R.id.title);

            title.setText(R.string.edit_time);
            saveButton.setText(R.string.update_text);
            babyItem.setText(newItem.getItemName());
            itemQuantity.setText(String.valueOf(newItem.getItemQuantity()));
            itemColor.setText(newItem.getItemColor());
            itemPrice.setText(String.valueOf(newItem.getItemPrice()));


            builder.setView(view);
            dialog = builder.create();
            dialog.show();


            //update
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);


                    newItem.setItemName(babyItem.getText().toString());
                    newItem.setItemColor(itemColor.getText().toString());
                    newItem.setItemQuantity(Integer.parseInt(itemQuantity.getText().toString()));
                    newItem.setItemPrice(Integer.parseInt(itemPrice.getText().toString()));

                    if (!babyItem.getText().toString().trim().isEmpty()
                            && !itemColor.getText().toString().trim().isEmpty()
                            && !itemQuantity.getText().toString().trim().isEmpty()
                            && !itemPrice.getText().toString().trim().isEmpty()) {
                        databaseHandler.updateItem(newItem);
                        notifyItemChanged(getAdapterPosition(), newItem);

                    } else {
                        Snackbar.make(view, "Fields are Empty",
                                Snackbar.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }
            });
        }
    }
}
