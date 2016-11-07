package com.hypherweb.www.asscky.View.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hypherweb.www.asscky.Model.Constants;
import com.hypherweb.www.asscky.R;
import com.hypherweb.www.asscky.View.BoardActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinBoardFragment extends Fragment {

    final String TAG = JoinBoardFragment.class.getSimpleName();

    EditText mNumOne;
    EditText mNumTwo;
    EditText mNumThree;
    EditText mNumFour;
    EditText mNumFive;
    EditText mNumSix;
    Button mJoinButton;
    String mBoardsReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public JoinBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_join_board, container, false);

        mBoardsReference = getString(R.string.board_reference);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //num_six_text
        mNumOne = (EditText) rootView.findViewById(R.id.num_one_text);
        mNumTwo = (EditText) rootView.findViewById(R.id.num_two_text);
        mNumThree = (EditText) rootView.findViewById(R.id.num_three_text);
        mNumFour = (EditText) rootView.findViewById(R.id.num_four_text);
        mNumFive = (EditText) rootView.findViewById(R.id.num_five_text);
        mNumSix = (EditText) rootView.findViewById(R.id.num_six_text);
        mJoinButton = (Button) rootView.findViewById(R.id.join_board_button);

        mNumOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    mNumTwo.requestFocus();
                }

            }
        });

        mNumTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    mNumThree.requestFocus();
                }
            }
        });

        mNumThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    mNumFour.requestFocus();
                }
            }
        });

        mNumFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    mNumFive.requestFocus();
                }
            }
        });

        mNumFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    mNumSix.requestFocus();
                }
            }
        });

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
                        getString(R.string.progress_bar_joining_board),
                        getString(R.string.progress_bar_will_take_a_second));
                progressDialog.show();
                if (validateBoardNumberEdit()) {
                    final String boardNum = mNumOne.getText().toString() + mNumTwo.getText().toString() +
                            mNumThree.getText().toString() + mNumFour.getText().toString() +
                            mNumFive.getText().toString() + mNumSix.getText().toString();
                    myRef = database.getReference(mBoardsReference + "/" + boardNum);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                Toast.makeText(rootView.getContext(), getString(R.string.error_board_doesnt_exist),
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                Intent i = new Intent(getActivity(), BoardActivity.class);
                                i.putExtra(Constants.BOARD_NUMBER, boardNum);
                                startActivity(i);
                                progressDialog.dismiss();
                                getActivity().finish();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            FirebaseCrash.log(TAG + databaseError.toString());
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(rootView.getContext(), getString(R.string.error_incomplete_number),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    public boolean validateBoardNumberEdit() {
        boolean returnVal = true;

        if (mNumOne.getText().length() < 1 ||
                mNumTwo.getText().length() < 1 ||
                mNumThree.getText().length() < 1 ||
                mNumFour.getText().length() < 1 ||
                mNumFive.getText().length() < 1 ||
                mNumSix.getText().length() < 1) {
            returnVal = false;
        }

        return returnVal;
    }

}
