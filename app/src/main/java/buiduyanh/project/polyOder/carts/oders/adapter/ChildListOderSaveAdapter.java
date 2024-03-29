package buiduyanh.project.polyOder.carts.oders.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import buiduyanh.project.polyOder.model.Product;

import com.example.polyOder.databinding.LayoutChildItemOderSaveBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ChildListOderSaveAdapter extends RecyclerView.Adapter<ChildListOderSaveAdapter.ChildViewHolder> {
    private List<Product> listItemChild;
    private Context context;



    public ChildListOderSaveAdapter(List<Product> listItemChild, Context context) {
        this.listItemChild = listItemChild;
        this.context = context;

    }



    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildViewHolder(LayoutChildItemOderSaveBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        Product product = listItemChild.get(position);
        if (product == null) {
            return;
        } else {
            holder.initData(product,context);
        }

    }


    @Override
    public int getItemCount() {
        return listItemChild.size();

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName,tvCount;

        public ChildViewHolder(LayoutChildItemOderSaveBinding binding) {
            super(binding.getRoot());
            imgProduct = binding.imgProduct;
            tvCount = binding.tvCount;
            tvName = binding.tvNameProduct;
        }

       public void initData(Product product, Context context ) {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("imgProducts");
            reference.listAll().addOnSuccessListener(listResult -> {
                for (StorageReference files: listResult.getItems()){
                    if(files.getName().equals(product.getId())){
                        files.getDownloadUrl().addOnSuccessListener(uri -> {

                            Glide.with(context).load(uri).into(imgProduct);
                        });
                    }
                }
            });
           tvName.setText(product.getNameProduct());
           tvCount.setText(product.getIsClick()+"");


        }



    }
}
