package com.example.listazakupow;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.*;
import android.app.AlertDialog;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText nameInput, quantityInput;
    Spinner spinner;
    DatabaseHelper db;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.etProductName);
        quantityInput = findViewById(R.id.etProductQuantity);
        spinner = findViewById(R.id.spinnerCategory);

        Button addBtn = findViewById(R.id.btnAdd);
        Button clearBtn = findViewById(R.id.btnClear);
        RecyclerView rv = findViewById(R.id.rvProducts);

        db = new DatabaseHelper(this);

        adapter = new ProductAdapter(db.getAllProducts(), id -> {
            db.deleteProduct(id);
            adapter.setProducts(db.getAllProducts());
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        addBtn.setOnClickListener(v -> addProduct());
        clearBtn.setOnClickListener(v -> clearList());
    }

    private void addProduct() {
        String name = nameInput.getText().toString();
        String qtyStr = quantityInput.getText().toString();
        String category = spinner.getSelectedItem().toString();

        if(name.isEmpty()) {
            Toast.makeText(this, "Podaj nazwę", Toast.LENGTH_SHORT).show();
            return;
        }

        if(qtyStr.isEmpty() || Integer.parseInt(qtyStr) <= 0) {
            Toast.makeText(this, "Zła ilość", Toast.LENGTH_SHORT).show();
            return;
        }

        db.addProduct(name, Integer.parseInt(qtyStr), category);
        adapter.setProducts(db.getAllProducts());

        nameInput.setText("");
        quantityInput.setText("");
    }

    private void clearList() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirm)
                .setPositiveButton(R.string.yes, (d,w)->{
                    db.clearAll();
                    adapter.setProducts(new ArrayList<>());
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}