package com.example.capstonandroid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonandroid.R;
import com.example.capstonandroid.entity.Cart;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.entity.Quantity;
import com.example.capstonandroid.firebaseinterface.Auxiliary;
import com.example.capstonandroid.firebaseinterface.QuantityChangeListener;

import java.util.List;

public class RecyclerCartAdapter extends RecyclerView.Adapter<RecyclerCartAdapter.MyViewHolder>{
    private Context context;
    private List<Cart> carts;
    private String username;
    QuantityChangeListener mQcl;
    private static ClickListener clickListener;

    @NonNull
    @Override
    public RecyclerCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cart_cell_layout, parent, false);
        return new RecyclerCartAdapter.MyViewHolder(itemView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView productName, catalogs, unitPrice, unitInStock, count, priceOfAllQuantities;
        public ImageView plus, minus;
        public ImageView productImage;
        public CheckBox chk_cart;
        public CardView delete;

        public MyViewHolder(View view) {
            super(view);
            bindItem(view);

            //set event for object
            productImage.setOnClickListener(this);
            productImage.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }

        public void bindItem(View view){
            //bind id
            productImage = view.findViewById(R.id.img_dialog);
            productName = view.findViewById(R.id.tv_product_name);
            catalogs = view.findViewById(R.id.tv_catalogs);
            unitPrice = view.findViewById(R.id.tv_unit_price);
            unitInStock = view.findViewById(R.id.tv_unit_in_stock);
            count = view.findViewById(R.id.tv_count);
            plus = (ImageView) view.findViewById(R.id.add);
            minus = (ImageView) view.findViewById(R.id.minus);
            chk_cart = view.findViewById(R.id.chkbox_cart);
            priceOfAllQuantities = view.findViewById(R.id.priceOfAllQuantities);
            delete = view.findViewById(R.id.cv_delete);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public RecyclerCartAdapter(Context context, List<Cart> carts, String username, QuantityChangeListener qcl) {
        this.context = context;
        this.carts = carts;
        this.username = username;
        this.mQcl = qcl;
    }


    public int checkUserPosition(String UserName){
        for(int i = 0; i < carts.size(); i++){
            if(carts.get(i).getUserName().equalsIgnoreCase(UserName)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerCartAdapter.MyViewHolder holder, final int position) {
        if(checkUserPosition(username) != -1){
            final Cart positionCart = carts.get(checkUserPosition(username));
            final Product product = positionCart.getProducts().get(position);
            final Quantity quanityProduct = positionCart.getQuantities().get(position);

            holder.productName.setText(product.getProductName());
            holder.catalogs.setText("Type: " + product.getCatalogs());
            holder.unitPrice.setText("Price: " + product.getUnitPrice() + " $");
            holder.unitInStock.setText(""+product.getUnitInStock());
            holder.count.setText("" + quanityProduct.getQuantity());
            holder.priceOfAllQuantities.setText("" + quanityProduct.getQuantity() * product.getUnitPrice());
            if(quanityProduct.isChecked()){
                holder.chk_cart.setChecked(true);
            } else {
                holder.chk_cart.setChecked(false);
            }

            holder.chk_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.chk_cart.isChecked()){
                        quanityProduct.setChecked(true);
                        mQcl.onQuantityChange();
                    } else {
                        quanityProduct.setChecked(false);
                        mQcl.onQuantityChange();
                    }
                }
            });

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = quanityProduct.getQuantity();
                    if (count < product.getUnitInStock()) {
                        quanityProduct.setQuantity(count + 1);
                        mQcl.onQuantityChange();
                        notifyDataSetChanged();
                    }}
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = quanityProduct.getQuantity();
                    if (count > 0) {
                        quanityProduct.setQuantity(count - 1);
                        mQcl.onQuantityChange();
                        notifyDataSetChanged();
                    }
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                    adb.setTitle(Html.fromHtml("<font color='#000000'>Delete?</font>"));
                    adb.setMessage(Html.fromHtml("<font color='#000000'>Are you sure you want to delete " + product.getProductName() + " </font>"));
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            carts.get(checkUserPosition(username)).getProducts().remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,carts.get(checkUserPosition(username)).getProducts().size());
                            mQcl.onQuantityChange();
                            notifyDataSetChanged();
                            Auxiliary.dialogStatus(context, true, "Đã từ bỏ sản phẩm! Đéo mua hối hận đó!");
                        }
                    });

                    AlertDialog ad = adb.create();
                    ad.show();
                    ad.getButton(DialogInterface.BUTTON_POSITIVE)
                            .setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                    ad.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                }
            });

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.fragment_cart_cell_layout_detail, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                alertDialogBuilder.setView(promptsView);

                ImageView productImageDetail = promptsView.findViewById(R.id.img_dialog_detail);
                TextView productNameDetail = promptsView.findViewById(R.id.tv_product_name_detail);
                TextView catalogsDetail = promptsView.findViewById(R.id.tv_catalogs_detail);
                TextView unitPriceDetail = promptsView.findViewById(R.id.tv_unit_price_detail);
                TextView unitInStockDetail = promptsView.findViewById(R.id.tv_unit_in_stock_detail);

                productNameDetail.setText(product.getProductName());
                catalogsDetail.setText("Type: " + product.getCatalogs());
                unitPriceDetail.setText("Price: " + product.getUnitPrice() + " $");
                unitInStockDetail.setText("Amount: " + product.getUnitInStock());
                Glide.with(context).load(product.getProductImage()).into(productImageDetail);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            }
        });

            Glide.with(context).load(product.getProductImage()).into(holder.productImage);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        if(checkUserPosition(username) != -1) {
            return carts.get(checkUserPosition(username)).getProducts().size();
        } else {
            return 0;
        }
    }
}
