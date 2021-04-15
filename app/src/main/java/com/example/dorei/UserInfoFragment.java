package com.example.dorei;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class UserInfoFragment extends Fragment {

    private int userId ;
    private TextView mName,mEmail,mAddress;
    private View mView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button addBtn;
    private EditText mContact;

    public UserInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getActivity().getIntent().getIntExtra("userId", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user_info, container, false);

        DataBaseHelper db = new DataBaseHelper(getActivity());
        UserModel user = db.getUser(userId);

        mName = mView.findViewById(R.id.name);
        mName.setText("Hello " + user.getName() + " !");

        mEmail = mView.findViewById(R.id.email);
        mEmail.setText(user.getEmail());

        mAddress = mView.findViewById(R.id.address);
        String address = user.getHouse_number() +"/" + user.getStreet_number() + ", "+ user.getStreet_name() + ",\n" + user.getCity() + ",\n" + user.getState() + ",\n" + user.getPostal_code();
        mAddress.setText(address);

        listView = mView.findViewById(R.id.list_view);
        ArrayList<String> contacts = new ArrayList<>();
        adapter = new ArrayAdapter<String>(  getActivity(), android.R.layout.simple_list_item_1,contacts);
        listView.setAdapter(adapter);
        refreshList();

        mContact = mView.findViewById(R.id.newNum);

        addBtn = mView.findViewById(R.id.addContact);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContact.getVisibility()==View.GONE)
                {
                    mContact.setVisibility(View.VISIBLE);
                }
                else if(mContact.getText().toString().trim().equals(""))
                {
                    mContact.setVisibility(View.GONE);
                }
                else
                {
                    DataBaseHelper db = new DataBaseHelper(getActivity());
                    db.addContact(userId,mContact.getText().toString().trim());
                    refreshList();
                    mContact.setVisibility(View.GONE);

                }
            }
        });

        return mView;
    }

    public void refreshList()
    {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        //dataBaseHelper.clearDatabase();
        ArrayList<String> all = dataBaseHelper.getContactNumbers(userId);
        adapter.clear();
        if(all.size()==0) all.add("No contact numbers added yet.");
        adapter.addAll(all);
    }
}
