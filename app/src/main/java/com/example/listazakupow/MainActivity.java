package com.example.listazakupow;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.*;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    EditText nameInput, quantityInput;
    DatabaseHelper db;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.productNameInput);
        quantityInput = findViewById(R.id.productQuantityInput);
        Button addBtn = findViewById(R.id.addButton);
        Button clearBtn = findViewById(R.id.clearButton);
        RecyclerView rv = findViewById(R.id.recyclerView);

        db = new DatabaseHelper(this);

        adapter = new ProductAdapter(db.getAllProducts());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        addBtn.setOnClickListener(v -> addProduct());
        clearBtn.setOnClickListener(v -> clearList());
    }

    private void addProduct() {
        String name = nameInput.getText().toString();
        String qtyStr = quantityInput.getText().toString();

        if(name.isEmpty()) {
            Toast.makeText(this, R.string.error_name, Toast.LENGTH_SHORT).show();
            return;
        }

        if(qtyStr.isEmpty() || Integer.parseInt(qtyStr) <= 0) {
            Toast.makeText(this, R.string.error_quantity, Toast.LENGTH_SHORT).show();
            return;
        }

        db.addProduct(name, Integer.parseInt(qtyStr));
        adapter.setProducts(db.getAllProducts());

        nameInput.setText("");
        quantityInput.setText("");
    }

    private void clearList() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_text)
                .setPositiveButton("TAK", (d, w) -> {
                    db.clearAll();
                    adapter.setProducts(db.getAllProducts());
                })
                .setNegativeButton("ANULUJ", null)
                .show();
    }
}