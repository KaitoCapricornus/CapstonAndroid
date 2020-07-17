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

import com.example.capstonandroid.R;
import com.example.capstonandroid.asynctask.DownloadImageAsyncTask;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.ui.product.ProductActivity;

import java.util.List;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> products;

    public RecyclerProductAdapter(List<Product> products) {
        this.products = products;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_listview_row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, parent.getContext());
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder temp = (ViewHolder) holder;
        //Log.i("temp", products.get(position).getProductName());
        temp.productID = products.get(position).getProductID();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productStatus;
        String productID;


        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.imageViewProductImage);
            productName = (TextView) itemView.findViewById(R.id.textViewProductName);
            productPrice = (TextView) itemView.findViewById(R.id.textViewProductPrice);
            productStatus = (TextView) itemView.findViewById(R.id.textViewStatus);

            itemView.setOnClickListener(new View.OnClickListener() {

                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("data", productID);
                    context.startActivity(intent);
                }
            });
        }
    }
}
