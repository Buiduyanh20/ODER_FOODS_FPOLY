package buiduyanh.project.polyOder.chatPoly.messageChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.polyOder.R;
import com.example.polyOder.databinding.FragmentChatsMessageBinding;

import buiduyanh.project.polyOder.base.BaseFragment;

public class ChatsFragment extends BaseFragment   {
    private FragmentChatsMessageBinding binding = null;


    public ChatsFragment(){

    }
    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = FragmentChatsMessageBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setColorStatusBar(getActivity().getColor(R.color.white));

        hideBottomBar();
        listening();
        initObSever();
    }

    @Override
    public void loadData() {


    }

    @Override
    public void listening() {


    }

    @Override
    public void initObSever() {

    }

    @Override
    public void initView() {

    }



}
