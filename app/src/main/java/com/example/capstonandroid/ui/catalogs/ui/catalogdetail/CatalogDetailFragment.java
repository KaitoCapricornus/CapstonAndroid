package com.example.capstonandroid.ui.catalogs.ui.catalogdetail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstonandroid.R;
import com.example.capstonandroid.adapter.RecyclerCatalogAdapter;
import com.example.capstonandroid.adapter.RecyclerProductAdapter;
import com.example.capstonandroid.asynctask.DownloadImageAsyncTask;
import com.example.capstonandroid.entity.Catalog;
import com.example.capstonandroid.entity.Product;
import com.example.capstonandroid.ui.catalogs.CatalogActivity;

import java.util.List;

public class CatalogDetailFragment extends Fragment {

    private CatalogDetailViewModel mViewModel;

    public static CatalogDetailFragment newInstance() {
        return new CatalogDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(CatalogDetailViewModel.class);
        View root = inflater.inflate(R.layout.catalog_detail_fragment, container, false);
        CatalogActivity activity = (CatalogActivity) getActivity();
        final String catalogID = activity.getCatalogID();
        final ImageView catalogImage = (ImageView) root.findViewById(R.id.imageViewCatalogDetailImage);
        final TextView catalogName = (TextView) root.findViewById(R.id.textViewCatalogName);
        final TextView catalogDescription = (TextView) root.findViewById(R.id.textViewCatalogDescription);
        final RecyclerView recyclerViewProduct = (RecyclerView) root.findViewById(R.id.recyclerCatalogProduct);

        mViewModel.getCatalog(catalogID).observe(getViewLifecycleOwner(), new Observer<Catalog>() {
            @Override
            public void onChanged(Catalog catalog) {
                DownloadImageAsyncTask imageAsyncTask = new DownloadImageAsyncTask(catalogImage);
                imageAsyncTask.execute(catalog.getCatalogImage());
                catalogName.setText(catalog.getCatalogName());
                catalogDescription.setText(catalog.getCatalogDescription());
            }
        });

        mViewModel.getData(catalogID).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> s) {
                RecyclerProductAdapter adapter = new RecyclerProductAdapter(s, container.getContext());
                recyclerViewProduct.setAdapter(adapter);
                recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });


        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}