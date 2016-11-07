package com.hypherweb.www.asscky.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hypherweb.www.asscky.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView mNameText;
    TextView mEmailText;
    CircleImageView mProfileImage;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        mNameText = (TextView) rootView.findViewById(R.id.name_text_view);
        mEmailText = (TextView) rootView.findViewById(R.id.email_text_view);
        mProfileImage = (CircleImageView) rootView.findViewById(R.id.profile_image);


        return rootView;
    }

}
