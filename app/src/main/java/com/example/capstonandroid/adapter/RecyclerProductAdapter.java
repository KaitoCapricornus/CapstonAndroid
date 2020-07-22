package com.example.capstonandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonandroid.MainActivity;
import com.example.capstonandroid.R;
import com.example.capstonandroid.asynctask.DownloadImageAsyncTask;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.ui.product.ProductActivity;

import java.util.List;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> products;
    public Context context;

    public RecyclerProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_listview_row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this.context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder temp = (ViewHolder) holder;
        //Log.i("temp", products.get(position).getProductName());
        temp.productID.setText(products.get(position).getProductID());
        DownloadImageAsyncTask asyncTask = new DownloadImageAsyncTask(temp.productImage);
        asyncTask.execute(products.get(position).getProductImage());
        temp.productName.setText(products.get(position).getProductName());
        temp.productPrice.setText(products.get(position).getUnitPrice() + "");
        int unitQuantity = products.get(position).getUnitInStock();
        if (unitQuantity > 0) {
            temp.productStatus.setText("Còn hàng");
            temp.productStatus.setTextColor(Color.GREEN);
        } else {
            temp.productStatus.setText("Hết hàng");
            temp.productStatus.setTextColor(Color.RED);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productStatus;
        TextView productID;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.imageViewProductImage);
            productName = (TextView) itemView.findViewById(R.id.textViewProductName);
            productPrice = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            productStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            productID = (TextView) itemView.findViewById(R.id.textViewProductID);

            itemView.setOnClickListener(new View.OnClickListener() {

                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("data", productID.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
