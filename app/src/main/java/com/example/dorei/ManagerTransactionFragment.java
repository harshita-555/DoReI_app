package com.example.dorei;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ManagerTransactionFragment extends Fragment {

    private int flag;
    private View myView;
    private ListView listView;
    private TextView tv;
    private BookTransactionAdapter adapter1;

    public ManagerTransactionFragment(int i) {
        // Required empty public constructor
        flag = i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_user_transaction, container, false);

        listView = myView.findViewById(R.id.list_view);
        tv = myView.findViewById(R.id.textView);
        if(flag == 0) tv.setText("Donations");
        else tv.setText("Requests");

        ArrayList<BookTransaction> arrayOfUsers = new ArrayList<>();
        adapter1 = new BookTransactionAdapter(getActivity(), arrayOfUsers);
        listView.setAdapter(adapter1);
        refreshList();
        return myView;
    }

    public class BookTransactionAdapter extends ArrayAdapter<BookTransaction> {

        public BookTransactionAdapter(Context context, ArrayList<BookTransaction> books) {
            super(context, 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final BookTransaction trans  = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.manager_transaction_elem, parent, false);
            }

            TextView bookName =  convertView.findViewById(R.id.name);
            TextView sbjName = convertView.findViewById(R.id.subject);
            TextView user_id = convertView.findViewById(R.id.donorId);
            TextView isbn = convertView.findViewById(R.id.isbn);
            Button requestBtn =  convertView.findViewById(R.id.btn);
            // Populate the data into the template view using the data object

            bookName.setText((position+1) + ". "+trans.getTitle());
            sbjName.setText("    Subject : " + trans.getSubject());

            if(flag==0) user_id.setText("    Donor Id : " + trans.getUser_id());
            else user_id.setText("    User Id : " + trans.getUser_id());
            isbn.setText("ISBN : "+ trans.getIsbn());

            if(trans.getCompleted()==1)
            {
                if(flag == 0) requestBtn.setText("COLLECTED");
                else requestBtn.setText("DELIVERED");

                requestBtn.setEnabled(false);
            }
            else
            {
                requestBtn.setText("Approve");
                requestBtn.setEnabled(true);
                requestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                        boolean success = dataBaseHelper.completeTransaction(trans.getIsbn(),trans.getUser_id(),flag);
                        if(success)
                        {
                            Toast.makeText(getActivity(), "Approved", Toast.LENGTH_SHORT).show();
                            refreshList();
                        }
                        else Toast.makeText(getActivity(), "Error! Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    void refreshList()
    {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        ArrayList<BookTransaction> all = dataBaseHelper.getManagerTransaction(flag);
        adapter1.clear();
        adapter1.addAll(all);
    }

}
