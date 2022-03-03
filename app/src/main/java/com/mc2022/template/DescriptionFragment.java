package com.mc2022.template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DescriptionFragment extends Fragment {

    TextView fTitle;
    TextView fBody;
    ImageView fImage;
    public DescriptionFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DescriptionFragment.
//     */
//
//    public static DescriptionFragment newInstance(String param1, String param2) {
//        DescriptionFragment fragment = new DescriptionFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_description, container, false);

        fTitle =(TextView) v.findViewById(R.id.nTitle);
        fBody =(TextView) v.findViewById(R.id.nBody);
        fImage =(ImageView) v.findViewById(R.id.nImage);

        Bundle bundle = this.getArguments();
        String dataTitle = bundle.getString("title");
        fTitle.setText(dataTitle);
        String dataBody = bundle.getString("body");
        fBody.setText(dataBody);
        String urlImagedata = bundle.getString("image");
        Picasso.with(getContext()).load(urlImagedata).into(fImage);


        return v;
    }
}