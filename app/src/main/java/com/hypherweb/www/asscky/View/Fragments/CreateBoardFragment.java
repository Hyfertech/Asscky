package com.hypherweb.www.asscky.View.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hypherweb.www.asscky.Model.Board;
import com.hypherweb.www.asscky.Model.Constants;
import com.hypherweb.www.asscky.R;
import com.hypherweb.www.asscky.View.BoardActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateBoardFragment extends Fragment {

    final String TAG = CreateBoardFragment.class.getSimpleName();
    public static final String BOARD_NUM = "board_num";
    public static final String BOARD = "board";

    EditText mBoardTitle;
    EditText mBoardDescription;
    EditText mBoardNotesOptional;
    Button mCreateBoardButton;
    String mBoardsReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;


    public CreateBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_create_board, container, false);
        mBoardsReference = getString(R.string.board_reference);

        myRef = database.getReference(mBoardsReference);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mBoardTitle = (EditText) rootView.findViewById(R.id.board_title);
        mBoardDescription = (EditText) rootView.findViewById(R.id.board_description);
        mBoardNotesOptional = (EditText) rootView.findViewById(R.id.board_notes_optional);
        mCreateBoardButton = (Button) rootView.findViewById(R.id.create_board_button);


        mCreateBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateBoardInfo()) {
                    final String boardNum = getRandomString(6);
                    String boardTitle = mBoardTitle.getText().toString();
                    String boardAdditionalNotes = mBoardNotesOptional.getText().toString();
                    String boardDescription = mBoardDescription.getText().toString();
                    final Board board = new Board(boardTitle, boardDescription, mFirebaseUser.getEmail(), boardNum ,boardAdditionalNotes);
                    Map<String, Object> childUpdates = board.toMap();
                    Map<String, Object> currentBoard = new HashMap();
                    currentBoard.put(boardNum, childUpdates);

                    final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
                            getString(R.string.progress_bar_creating_your_board),
                            getString(R.string.progress_bar_will_take_a_second));
                    progressDialog.show();

                    myRef.updateChildren(currentBoard, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(rootView.getContext(),
                                        getString(R.string.error_unable_to_create_board), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            } else {
                                Intent i = new Intent(getActivity(), BoardActivity.class);
                                i.putExtra(Constants.BOARD_NUMBER, boardNum);
                                startActivity(i);
                                progressDialog.dismiss();
                                getActivity().finish();
                            }
                        }
                    });
                }
            }
        });


        return rootView;
    }

    private boolean validateBoardInfo() {
        boolean returnVal = true;
        String boardTitle = mBoardTitle.getText().toString();
        String boardDescription = mBoardDescription.getText().toString();

        if (boardDescription.isEmpty() || boardDescription == "") {
            mBoardDescription.setError(getString(R.string.error_board_description_empty));
            returnVal = false;
        }

        if (boardTitle.isEmpty() || boardTitle == "") {
            mBoardTitle.setError(getString(R.string.error_board_title_empty));
            returnVal = false;
        }

        return returnVal;
    }

    public String getRandomString(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

}
