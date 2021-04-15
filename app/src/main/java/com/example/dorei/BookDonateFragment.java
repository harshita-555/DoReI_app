package com.example.dorei;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class BookDonateFragment extends Fragment {


    private EditText mTitle, mAuthor, mClass, mEdition;
    private Spinner mSubject;
    private int userId;
    private Button donateBtn;
    private View myView;
    private String subject = "Default";

    public BookDonateFragment() {
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
       myView = inflater.inflate(R.layout.fragment_book_donate, container, false);

        mTitle = myView.findViewById(R.id.title);
        mAuthor= myView.findViewById(R.id.author);
        mClass= myView.findViewById(R.id.grade);
        mEdition= myView.findViewById(R.id.edition);
        mSubject= myView.findViewById(R.id.subject);
        donateBtn = myView.findViewById(R.id.donateBtn);

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subject="Others";
            }
        });

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDataEntered())
                {
                    String title = mTitle.getText().toString().trim();
                    String author = mAuthor.getText().toString().trim();
                    int edition = Integer.valueOf(mEdition.getText().toString().trim());
                    int grade = Integer.valueOf(mClass.getText().toString().trim());

                    Book book = new Book(-1,author,subject,title,edition,grade);

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    boolean success = dataBaseHelper.addBookDonation(book,userId);
                    if(success)
                    {
                        mTitle.setText("");
                        mAuthor.setText("");
                        mEdition.setText("");
                        mClass.setText("");
                        Toast.makeText(getActivity(), "Successful!",Toast.LENGTH_SHORT).show();
                    }

                    else Toast.makeText(getActivity(), "Failed :(",Toast.LENGTH_SHORT).show();

                }
            }
        });

        return myView;
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isValidClass(EditText e1){
        int grade = Integer.valueOf(e1.getText().toString().trim());
        if(grade > 0 && grade <=12) return true;
        return false;
    }
    boolean isValidEdition(EditText e1){
        if(isEmpty(e1)) return false;
        int grade = Integer.valueOf(e1.getText().toString().trim());
        if(grade > 0 ) return true;
        return false;
    }

    boolean checkDataEntered() {
        if (isEmpty(mTitle)){
            mTitle.setError("Required");
            return false;
        }
        else if (isEmpty(mAuthor)) {
            mAuthor.setError("Required");
            return false;
        }
        else if(subject.equals("Default"))
        {
            Toast.makeText(getActivity(), "Select a subject!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!isValidClass(mClass)) {
            mClass.setError("Should be between 1 to 12");
            return false;
        }
        else if (!isValidEdition(mEdition)) {
            mEdition.setError("Require a number");
            return false;
        }
        return true;
    }
}
