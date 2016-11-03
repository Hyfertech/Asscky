package com.hypherweb.www.asscky.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hypherweb.www.asscky.Adaptor.DividerItemDecoration;
import com.hypherweb.www.asscky.Adaptor.MessageViewHolder;
import com.hypherweb.www.asscky.Model.Constants;
import com.hypherweb.www.asscky.Model.Messages;
import com.hypherweb.www.asscky.R;

import java.util.Date;

public class BoardActivity extends AppCompatActivity {

    final static String TAG = BoardActivity.class.getSimpleName();

    String mBoardNum;
    TextView mBoardNumber;
    Button mSendButton;
    EditText mMessageText;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private RecyclerView mMessageRecyclerView;

    private FirebaseRecyclerAdapter<Messages, MessageViewHolder>
            mFirebaseAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private DataSnapshot mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mBoardNumber = (TextView) findViewById(R.id.board_number);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.message_recycler_view);
        mSendButton = (Button) findViewById(R.id.send_button);
        mMessageText = (EditText) findViewById(R.id.message_text);


        Intent intent = getIntent();
        mBoardNum = intent.getStringExtra(Constants.BOARD_NUMBER);
        mBoardNumber.setText(mBoardNum);


        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, null));


        myRef = database.getReference(getString(R.string.board_reference) + "/" + mBoardNum + "/" + Constants.BOARD_MESSAGES + "/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                Messages messages = dataSnapshot.getValue(Messages.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMessageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(BoardActivity.this,
                        getString(R.string.progress_bar_sending_text),
                        getString(R.string.progress_bar_will_take_a_second));
                progressDialog.show();
                String date = getFormattedDate(new Date());
                Messages message = new
                        Messages(mMessageText.getText().toString().trim(),
                        mFirebaseUser.getEmail(), date);
                myRef.push().setValue(message, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mMessageText.setText("");
                        progressDialog.dismiss();
                    }
                });

            }
        });

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Messages,
                MessageViewHolder>(
                Messages.class,
                R.layout.message_item,
                MessageViewHolder.class,
                myRef) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Messages model, int position) {
                viewHolder.mMessageText.setText(model.getMessage());
                viewHolder.mDateText.setText(model.getDate());
            }

        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_board_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.signOut) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(BoardActivity.this, R.string.signed_out_toast, Toast.LENGTH_LONG).show();
            Intent signOut = new Intent(BoardActivity.this, MainActivity.class);
            startActivity(signOut);
        } else if (id == R.id.new_board_quite_board) {
            Toast.makeText(BoardActivity.this, R.string.quit_board_successfully, Toast.LENGTH_LONG).show();
            Intent quiteBoard = new Intent(BoardActivity.this, HomePageActivity.class);
            startActivity(quiteBoard);
        }
        return true;
    }

    public String getFormattedDate(Date date) {
        String returnVal = String.format(date.toString());

        return returnVal;

    }

}
