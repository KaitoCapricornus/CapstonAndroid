package com.example.capstonandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonandroid.R;
import com.example.capstonandroid.asynctask.DownloadImageAsyncTask;
import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.ui.catalogs.CatalogActivity;

import java.util.List;

public class RecyclerCatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<Catalog> catalogs;
    public Context context;

    public RecyclerCatalogAdapter(List<Catalog> catalogs, Context context) {
        this.catalogs = catalogs;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_catalog_cell_layout, parent, false);
        RecyclerCatalogAdapter.ViewHolder viewHolder = new RecyclerCatalogAdapter.ViewHolder(view, this.context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerCatalogAdapter.ViewHolder temp = (RecyclerCatalogAdapter.ViewHolder) holder;
        temp.catalogID.setText(catalogs.get(position).getCatalogID());
        DownloadImageAsyncTask asyncTask = new DownloadImageAsyncTask(temp.catalogImage);
        asyncTask.execute(catalogs.get(position).getCatalogImage());
        temp.catalogName.setText(catalogs.get(position).getCatalogName());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catalogImage;
        TextView catalogName;
        TextView catalogID;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            catalogImage = (ImageView) itemView.findViewById(R.id.imageViewCatalog);
            catalogName = (TextView) itemView.findViewById(R.id.textViewCatalogName);
            catalogID = (TextView) itemView.findViewById(R.id.textViewCatalogID);

            itemView.setOnClickListener(new View.OnClickListener() {

                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CatalogActivity.class);
                    intent.putExtra("data", catalogID.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

}
