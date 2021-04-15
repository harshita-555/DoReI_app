package com.example.dorei;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ManagerInfoFragment extends Fragment {

    private TextView mUsers,mDonations,mRequests,mPendingDon,mPendingReq;
    private View mView;

    public ManagerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_manager_info, container, false);

        mUsers = mView.findViewById(R.id.totalusers);
        mDonations = mView.findViewById(R.id.numDonations);
        mRequests= mView.findViewById(R.id.numRequests);
        mPendingDon= mView.findViewById(R.id.pendingDonations);
        mPendingReq= mView.findViewById(R.id.pendingRequests);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        ArrayList<Integer> myList = dataBaseHelper.getSummary();
        mUsers.setText(String.valueOf(myList.get(0)));
        mDonations.setText(String.valueOf(myList.get(1)));
        mRequests.setText(String.valueOf(myList.get(2)));
        mPendingDon.setText(String.valueOf(myList.get(3)));
        mPendingReq.setText(String.valueOf(myList.get(4)));;

        return mView;
    }
}
