package com.example.dorei;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class BookRequestFragment extends Fragment {

    private Spinner mSubject;
    private int userId;
    private View myView;
    private ListView listView;
    private String subject = "Default";
    private BookAdapter adapter1;

    public BookRequestFragment() {
        // Required empty public constructor
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
        myView = inflater.inflate(R.layout.fragment_book_request, container, false);

        listView = myView.findViewById(R.id.list_view);
        ArrayList<Book> arrayOfUsers = new ArrayList<Book>();
        adapter1 = new BookAdapter(getActivity(), arrayOfUsers);
        listView.setAdapter(adapter1);

        mSubject= myView.findViewById(R.id.subject);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.subjects, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubject.setAdapter(adapter);

        mSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0) subject = "Default";
                else if(position==1) subject = "Mathematics";
                else if(position==2) subject = "English";
                else if(position==3) subject ="Physics";
                else if(position==4) subject ="Chemistry";
                else if(position==5) subject ="Biology";
                else if(position==6) subject ="Science";
                else if(position==7) subject ="SocialScience";
                else if(position==8) subject ="Hindi";
                else if(position==9) subject = "Others";

                refreshList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject="Others";
            }
        });

        return myView;
    }

    public class BookAdapter extends ArrayAdapter<Book> {

        public BookAdapter(Context context, ArrayList<Book> books) {
            super(context, 0, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Book book  = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_elem, parent, false);
            }
            // Lookup view for data population
            TextView bookName =  convertView.findViewById(R.id.name);
            TextView grade = convertView.findViewById(R.id.subject);
            Button requestBtn =  convertView.findViewById(R.id.btn);
            requestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    boolean success = dataBaseHelper.makeRequest(userId,book.getIsbn());
                    if(success)
                    {
                        refreshList();
                        Toast.makeText(getActivity(), "Made the Request!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if(book.getTitle().equals("No Book Available"))
            {
                bookName.setText(book.getTitle());
                grade.setText("");
                requestBtn.setVisibility(View.GONE);
            }
            else
            {
                bookName.setText((position+1) +". " + book.getTitle());
                grade.setText("     Class : "+ book.getGrade());
                requestBtn.setVisibility(View.VISIBLE);
                requestBtn.setText("REQUEST");
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    void refreshList()
    {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        ArrayList<Book> all = dataBaseHelper.getRequestableBooks(subject);
        adapter1.clear();

        if(all.size()==0) all.add(new Book(-1, "", "", "No Book Available", -1, -1));
        adapter1.addAll(all);
    }
}
