package com.mc2022.template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ListAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class DescriptionFragment extends Fragment {

    TextView fTitle;
    TextView fBody;
    ImageView fImage;


    int clickCounter=0;

    EditText inputComm;
    Button addComm;

    ArrayList<String> comments = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    ListView l;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_description, container, false);

        fTitle =(TextView) v.findViewById(R.id.nTitle);
        fBody =(TextView) v.findViewById(R.id.nBody);
        fImage =(ImageView) v.findViewById(R.id.nImage);
        addComm =(Button) v.findViewById(R.id.addComm);
        inputComm =(EditText) v.findViewById(R.id.inputCom);



        Bundle bundle = this.getArguments();
        String dataTitle = bundle.getString("title");
        fTitle.setText(dataTitle);
        String dataBody = bundle.getString("body");
        fBody.setText(dataBody);
        String urlImagedata = bundle.getString("image");
        Picasso.with(getContext()).load(urlImagedata).into(fImage);
       // l = (ListView) v.findViewById(R.id.list_item);

        if (l == null) {
           l = (ListView) v.findViewById(R.id.commList);
        }
        Collections.reverse(comments);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, comments);
        l.setAdapter(adapter);

        // Here, you set the data in your ListView


        addComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = inputComm.getText().toString();
                if(comment == null || comment.length()==0){
                    Toast.makeText(getContext(), "Comment is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    addItem(comment);
                    inputComm.setText("");
                    Toast.makeText(getContext(), "Comment Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Comment removed", Toast.LENGTH_SHORT).show();
                removeComment(i);
                return false;
            }
        });

        return v;
    }

    private void removeComment(int i) {
        comments.remove(i);
        adapter.notifyDataSetChanged();
    }

    private void addItem(String comment) {
        comments.add(comment);
        l.setAdapter(adapter);
    }
}