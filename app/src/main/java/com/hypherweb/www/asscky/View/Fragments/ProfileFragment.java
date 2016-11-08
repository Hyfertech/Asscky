package com.hypherweb.www.asscky.View.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hypherweb.www.asscky.Adaptor.ProfileBoardsListAdaptor;
import com.hypherweb.www.asscky.Model.Board;
import com.hypherweb.www.asscky.Model.Constants;
import com.hypherweb.www.asscky.R;
import com.hypherweb.www.asscky.View.BoardActivity;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public String TAG = ProfileFragment.class.getSimpleName();
    TextView mNameText;
    TextView mEmailText;
    TextView mBoardLable;
    CircleImageView mProfileImage;
    ListView mBoardListView;
    ProfileBoardsListAdaptor mProfileBoardsListAdaptor;

    String mName;
    String mEmail;
    Uri mPhotoUrl;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mUsersDBRef;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mNameText = (TextView) rootView.findViewById(R.id.name_text_view);
        mEmailText = (TextView) rootView.findViewById(R.id.email_text_view);
        mProfileImage = (CircleImageView) rootView.findViewById(R.id.profile_image);
        mBoardListView = (ListView) rootView.findViewById(R.id.boards_list_view);
        mBoardLable = (TextView) rootView.findViewById(R.id.board_label);
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
                getString(R.string.progress_bar_please_wait),
                getString(R.string.progress_bar_will_take_a_second));


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        progressDialog.show();
        String userRef = getString(R.string.user_reference) + "/" + mFirebaseUser.getUid() + "/" + getString(R.string.board_reference) + "/";
        mUsersDBRef = mFirebaseDatabase.getReference(userRef);

        mUsersDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> boardMap = (HashMap<String, Object>) dataSnapshot.getValue();
                if (boardMap != null) {

                    mProfileBoardsListAdaptor = new ProfileBoardsListAdaptor(boardMap, getContext());
                    mBoardListView.setAdapter(mProfileBoardsListAdaptor);
                } else {
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mName = mFirebaseUser.getDisplayName();
        mEmail = mFirebaseUser.getEmail();
        mPhotoUrl = mFirebaseUser.getPhotoUrl();


        if (mName != null) {
            mNameText.setText(mName);
        } else {
            mNameText.setText(getString(R.string.placeholder_name));
        }

        if (mEmail != null) {
            mEmailText.setText(mEmail);
        } else {
            mEmailText.setText(getString(R.string.placeholder_email));
        }

        if (mPhotoUrl != null) {
            Glide.with(getContext())
                    .load(mPhotoUrl)
                    .into(mProfileImage);

        }

        progressDialog.dismiss();


        mBoardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "onItemClick: " + mProfileBoardsListAdaptor.getItem(position));
                Board board = new Board((HashMap<String, Object>) mProfileBoardsListAdaptor.getItem(position));
                Intent intent = new Intent(getContext(), BoardActivity.class);
                intent.putExtra(Constants.BOARD_NUMBER, board.getBoardNumber());
                startActivity(intent);
                getActivity().finish();

            }
        });
        return rootView;
    }

}
