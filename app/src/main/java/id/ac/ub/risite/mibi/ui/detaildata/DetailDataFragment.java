package id.ac.ub.risite.mibi.ui.detaildata;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.ac.ub.risite.mibi.R;

public class DetailDataFragment extends Fragment {

    private DetailDataViewModel mViewModel;
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBaseRef;
    private DatabaseReference mDataBaseRef2;

    public TextView mTextView;
    public TextView mTextView2;
    public ListView mListView;

    private ArrayList<String> mco2 = new ArrayList<>();
    private ArrayList<String> mKeys = new ArrayList<>();

    public static DetailDataFragment newInstance() {
        return new DetailDataFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_data_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTextView = (TextView) findViewById(R.id.textView5);
        mTextView2 = (TextView) findViewById(R.id.textView8);
        mListView = (ListView) findViewById(R.id.listView);
        mDataBase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDataBase.getReference().child("Data_perjam");
        mDataBaseRef2 = mDataBase.getReference();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mco2){
            public View getView(int position, View convertView, ViewGroup parent){
                TextView tv = (TextView) super.getView(position,convertView,parent);
                tv.setGravity(Gravity.RIGHT|Gravity.END|Gravity.CENTER_VERTICAL);
                return tv;
            }
        };
        mListView.setAdapter(arrayAdapter);

        mDataBaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                int value = dataSnapshot.getValue(Integer.class);
                mco2.add(String.valueOf(value)+" PPM");

                String key = dataSnapshot.getKey();
                mKeys.add(key);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                int value = dataSnapshot.getValue(Integer.class);
                String key = dataSnapshot.getKey();

                int index = mKeys.indexOf(key);

                mco2.set(index, String.valueOf(value));

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                mco2.clear();
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDataBaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Max_Min mMax_Min = new Max_Min();
                mMax_Min.setMin(dataSnapshot.getValue(Max_Min.class).getMin());
                mMax_Min.setMax(dataSnapshot.getValue(Max_Min.class).getMax());
                mTextView.setText(mMax_Min.getMax()+"\nPPM");
                mTextView2.setText(mMax_Min.getMin()+"\nPPM");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mTextView.setText("Error");
                mTextView2.setText("Error");
            }
        });

        mViewModel = ViewModelProviders.of(this).get(DetailDataViewModel.class);
        // TODO: Use the ViewModel
    }

    private Object findViewById(int listView) {
        return getView().findViewById( listView);
    }

}