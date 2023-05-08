package id.ac.ub.risite.mibi.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.ub.risite.mibi.Data;
import id.ac.ub.risite.mibi.R;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    int x;
    String kondisi;
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBaseRef;

    public TextView mTextView;
    public TextView mTextView2;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextView = (TextView) findViewById(R.id.textView6);
        mTextView2 = (TextView) findViewById(R.id.textView3);

        mDataBase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDataBase.getReference();

        mDataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data mdata = new Data();
                mdata.setPpm(dataSnapshot.child("Data_realtime").getValue(Data.class).getPpm());
                x = mdata.getPpm();
                if (x<600){
                    kondisi = "Aman";
                }
                else {
                    kondisi = "Bahaya";
                }
                mTextView.setText(x+" PPM");
                mTextView2.setText(kondisi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mTextView.setText("Not Connected");
                mTextView2.setText("Not Connected");
            }
        });

        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    private View findViewById(int textView) {
        return getView().findViewById(textView);
    }

}