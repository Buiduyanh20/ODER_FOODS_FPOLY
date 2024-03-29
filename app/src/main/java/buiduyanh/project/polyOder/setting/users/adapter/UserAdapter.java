package buiduyanh.project.polyOder.setting.users.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import buiduyanh.project.polyOder.interfaces.OnTouchTheUser;
import buiduyanh.project.polyOder.model.User;

import com.example.polyOder.databinding.LayoutItemEmployeeBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolderEmployee> {
    private ArrayList<User> listUser;
    private Context context;
    private OnTouchTheUser onClickItemUser;


    public UserAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    public UserAdapter(ArrayList<User> listUser, OnTouchTheUser onClickItemUser , Context context) {
        this.listUser = listUser;
        this.onClickItemUser = onClickItemUser;
        this.context = context;

    }

    public void setFilterList(ArrayList<User> filterList) {
        this.listUser = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolderEmployee onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderEmployee(LayoutItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEmployee holder, int position) {
        User user = listUser.get(position);
        if (user == null) {
            return;
        } else {
            holder.initData(user,context);
        }

    }


    @Override
    public int getItemCount() {
        return listUser.size();

    }

    public class ViewHolderEmployee extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvBirth;
        RelativeLayout layoutItem;
        CardView cavMale, cavFemale;


        public ViewHolderEmployee(LayoutItemEmployeeBinding binding) {
            super(binding.getRoot());
            imgAvatar = binding.imgAvatar;
            tvName = binding.tvName;
            tvBirth = binding.tvBirth;
            cavMale = binding.cavGenderMale;
            cavFemale = binding.cavGenderFemale;
            layoutItem = binding.layoutItem;
        }

       public void initData(User user, Context context ) {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("avatars");
            reference.listAll().addOnSuccessListener(listResult -> {
                for (StorageReference files: listResult.getItems()){
                    if(files.getName().equals(user.getId())){
                        files.getDownloadUrl().addOnSuccessListener(uri -> {
                            Glide.with(context).load(uri).into(imgAvatar);
                        });
                    }
                }
            });
            tvName.setText(user.getName_user());
           tvBirth.setText(user.getBirthday());
            if(user.getSex()){
                cavMale.setVisibility(View.VISIBLE);
                cavFemale.setVisibility(View.GONE);
            }else{
                cavMale.setVisibility(View.GONE);
                cavFemale.setVisibility(View.VISIBLE);
            }

            layoutItem.setOnLongClickListener(v ->{
                onClickItemUser.onLongClick(user);
                return true;
            });

        }

    }
}
