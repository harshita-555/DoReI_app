package com.example.dorei;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class UserTransactionFragment extends Fragment {

    private int flag;
    private int userId;
    private View myView;
    private ListView listView;
    private TextView tv;
    private BookAdapter adapter1;


    public UserTransactionFragment(int i) {
        // Required empty public constructor
        flag = i;
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
        myView = inflater.inflate(R.layout.fragment_user_transaction, container, false);

        listView = myView.findViewById(R.id.list_view);
        tv = myView.findViewById(R.id.textView);
        if(flag == 0) tv.setText("Your Donations");
        ArrayList<Pair<Book,Integer>> arrayOfUsers = new ArrayList<>();
        adapter1 = new BookAdapter(getActivity(), arrayOfUsers);
        listView.setAdapter(adapter1);
        refreshList();
        return myView;
    }

    public class BookAdapter extends ArrayAdapter<Pair<Book, Integer>> {

        public BookAdapter(Context context, ArrayList<Pair<Book, Integer>> books) {
            super(context, 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Pair<Book, Integer> book  = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_elem, parent, false);
            }

            TextView bookName =  convertView.findViewById(R.id.name);
            TextView sbjName = convertView.findViewById(R.id.subject);
            Button requestBtn =  convertView.findViewById(R.id.btn);
            requestBtn.setEnabled(false);

            Book b = book.first;
            Integer completed = book.second;

            if(b.getTitle().equals("No Book Available"))
            {
                bookName.setText(b.getTitle());
                sbjName.setText("");
                requestBtn.setVisibility(View.GONE);
            }
            else
            {
                bookName.setText((position+1) +". " + b.getTitle());
                sbjName.setText("      Subject : "+ b.getSubject());
                requestBtn.setVisibility(View.VISIBLE);
                if(completed == 0) requestBtn.setText("PENDING");
                else if(completed == 1 && flag == 0)  requestBtn.setText("COLLECTED");
                else requestBtn.setText("DELIVERED");
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    void refreshList()
    {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        ArrayList<Pair<Book,Integer>> all = dataBaseHelper.getUserTransaction(userId,flag);
        adapter1.clear();

       // ArrayList<Pair<Book,Integer>> all = new ArrayList<>();

        Book b = new Book(-1, "", "", "No Book Available", -1, -1);
        Pair<Book,Integer> p = new Pair<>(b,0);

        if(all.size()==0) all.add(p);
        adapter1.addAll(all);
    }
}
