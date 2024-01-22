package buiduyanh.project.polyOder.setting.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import buiduyanh.project.polyOder.base.Helpers;
import buiduyanh.project.polyOder.carts.oders.adapter.ListOderAdapter;
import buiduyanh.project.polyOder.carts.oders.DetailReceiptFragment;
import com.example.polyOder.R;
import com.example.polyOder.databinding.FragmentSalesReportBinding;

import buiduyanh.project.polyOder.interfaces.OnTouchTheOder;
import buiduyanh.project.polyOder.viewModel.ReceiptViewModel;
import buiduyanh.project.polyOder.base.BaseFragment;
import buiduyanh.project.polyOder.model.Receipt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailySalesReportFragment extends BaseFragment implements OnTouchTheOder {

    private FragmentSalesReportBinding binding = null;
    private ReceiptViewModel viewModel;
    private ListOderAdapter adapter;
    private Helpers helpers = new Helpers();


    public DailySalesReportFragment() {
        // Required empty public constructor
    }

    public static DailySalesReportFragment newInstance() {
        DailySalesReportFragment fragment = new DailySalesReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSalesReportBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ReceiptViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setColorStatusBar(getActivity().getColor(R.color.white));
        hideBottomBar();
        listening();
        initObSever();
        loadData();
    }

    @Override
    public void loadData() {

        String strToday = helpers.isFormatTime(Calendar.getInstance().getTime(),"yyyy-MM-dd");
        String strToday2 = helpers.isFormatTime(Calendar.getInstance().getTime(),"dd-MM-yyyy");

        binding.tvDate.setText(strToday2);

        viewModel.getReceiptByToDay(strToday);
        viewModel.liveDateGetReceiptToDay.observe(getViewLifecycleOwner(), new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                if(receipts.size() == 0){
                    binding.layoutNotificationNullData.setVisibility(View.VISIBLE);
                    binding.tvOrderNumber.setText("0");
                    binding.tvTotalOderValue.setText("0");
                    binding.viewHeader.setVisibility(View.GONE);
                    binding.recVListOder.setVisibility(View.GONE);
                }else {
                    binding.layoutNotificationNullData.setVisibility(View.GONE);
                    binding.viewHeader.setVisibility(View.VISIBLE);
                    binding.recVListOder.setVisibility(View.VISIBLE);
                    binding.tvOrderNumber.setText(receipts.size() + "");
                    Double money = 0.0;
                    for (Receipt receipt : receipts) {
                        money += receipt.getMoney();
                    }

                    String strMoney = helpers.isFormatMoney(money);

                    binding.tvTotalOderValue.setText(strMoney);
                    adapter = new ListOderAdapter((ArrayList<Receipt>) receipts, DailySalesReportFragment.this, 0);
                    helpers.setReverseItemRecycleView(getContext(),binding.recVListOder);
                    binding.recVListOder.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public void listening() {
        binding.icBack.setOnClickListener(ic ->{
            backStack();
        });
    }

    @Override
    public void initObSever() {
    }

    @Override
    public void initView() {


    }


    @Override
    public void onClickOder(Receipt receipt) {
        replaceFragment(new DetailReceiptFragment(receipt));
    }
}