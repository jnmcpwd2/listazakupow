package com.example.listazakupow;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnDeleteClick {
        void onDelete(int id);
    }

    private List<Product> products;
    private OnDeleteClick listener;

    public ProductAdapter(List<Product> products, OnDeleteClick listener) {
        this.products = products;
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, category;
        Button delete;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvProductName);
            quantity = view.findViewById(R.id.tvQuantity);
            category = view.findViewById(R.id.tvCategory);
            delete = view.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product p = products.get(position);

        holder.name.setText(p.name);
        holder.quantity.setText("Ilość: " + p.quantity);
        holder.category.setText("Kategoria: " + p.category);

        holder.delete.setOnClickListener(v -> listener.onDelete(p.id));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}