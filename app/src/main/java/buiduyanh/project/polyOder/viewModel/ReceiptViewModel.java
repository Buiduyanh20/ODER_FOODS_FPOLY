package buiduyanh.project.polyOder.viewModel;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import buiduyanh.project.polyOder.model.Receipt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiptViewModel extends ViewModel {
    public MutableLiveData<List<Receipt>> liveDateGetReceipt = new MutableLiveData<>();
    public MutableLiveData<List<Receipt>> liveDateGetAllReceipt = new MutableLiveData<>();
    public MutableLiveData<List<Receipt>> liveDateGetReceiptToDay = new MutableLiveData<>();
    public MutableLiveData<List<Receipt>> liveDateGetSaveReceiptToDay = new MutableLiveData<>();

    public MutableLiveData<List<Receipt>> liveDateGetCancelReceipt = new MutableLiveData<>();
    public MutableLiveData<List<Receipt>> liveDateGetAllCancelReceipt = new MutableLiveData<>();
    public MutableLiveData<List<Receipt>> liveDateGetCancelReceiptToDay = new MutableLiveData<>();


    public void getDataFromFirebase(String path, ValueEventListener valueEventListener ) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.addValueEventListener(valueEventListener);
    }


    public LiveData<List<Receipt>> getReceiptByDate(String startDate, String endDate) {
        getDataFromFirebase("PayReceipt", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    String day = receipt.getTimeOder().substring(0, receipt.getTimeOder().lastIndexOf(""));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dayOder = sdf.parse(day);
                        Date start = sdf.parse(startDate);
                        Date end = sdf.parse(endDate);
                        if (start.getTime() <= dayOder.getTime() && end.getTime() >= dayOder.getTime()) {
                            listData.add(receipt);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                liveDateGetReceipt.postValue(listData);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetReceipt;
    }

    public LiveData<List<Receipt>> getReceiptCancelByDate(String startDate, String endDate) {

        getDataFromFirebase("CancelReceipt",new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    String day = receipt.getTimeOder().substring(0, receipt.getTimeOder().lastIndexOf(" "));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dayOder = sdf.parse(day);
                        Date start = sdf.parse(startDate);
                        Date end = sdf.parse(endDate);
                        if (start.getTime() <= dayOder.getTime() && end.getTime() >= dayOder.getTime()) {
                            listData.add(receipt);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                liveDateGetCancelReceipt.postValue(listData);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetCancelReceipt;
    }


    public LiveData<List<Receipt>> getReceiptSavedByToDay (String date) {

        getDataFromFirebase("OderSave",new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    String strDay = receipt.getTimeOder().substring(0, receipt.getTimeOder().lastIndexOf(" "));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dayOder = dateFormat.parse(strDay);
                        Date toDay = dateFormat.parse(date);

                        if (toDay.getTime() == dayOder.getTime()) {
                            listData.add(receipt);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                liveDateGetSaveReceiptToDay.postValue(listData);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetSaveReceiptToDay;
    }


    public LiveData<List<Receipt>> getReceiptByToDay(String date) {
        getDataFromFirebase("PayReceipt",new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    String strDay = receipt.getTimeOder().substring(0, receipt.getTimeOder().lastIndexOf(" "));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dayOder = dateFormat.parse(strDay);
                        Date toDay = dateFormat.parse(date);

                        if (toDay.getTime() == dayOder.getTime()) {
                            listData.add(receipt);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                liveDateGetReceiptToDay.postValue(listData);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetReceiptToDay;
    }


    public LiveData<List<Receipt>> getReceiptCancelByToDay(String date) {
        getDataFromFirebase("CancelReceipt",new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    String strDay = receipt.getTimeOder().substring(0, receipt.getTimeOder().lastIndexOf(" "));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dayOder = dateFormat.parse(strDay);
                        Date toDay = dateFormat.parse(date);
                        if (toDay.getTime() == dayOder.getTime()) {
                            listData.add(receipt);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                liveDateGetCancelReceiptToDay.postValue(listData);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetCancelReceiptToDay;
    }


    public LiveData<List<Receipt>> getAllReceipt() {
        getDataFromFirebase("PayReceipt",new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    listData.add(receipt);
                }
                liveDateGetAllReceipt.postValue(listData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetAllReceipt;
    }


    public LiveData<List<Receipt>> getAllCancelReceipt() {
        getDataFromFirebase("CancelReceipt",new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Receipt> listData = new ArrayList<>();
                listData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Receipt receipt = dataSnapshot.getValue(Receipt.class);
                    listData.add(receipt);
                }
                liveDateGetAllCancelReceipt.postValue(listData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return liveDateGetAllCancelReceipt;
    }


}
