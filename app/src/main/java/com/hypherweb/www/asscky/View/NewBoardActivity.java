package com.hypherweb.www.asscky.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.hypherweb.www.asscky.R;
import com.hypherweb.www.asscky.View.Fragments.CreateBoardFragment;

public class NewBoardActivity extends AppCompatActivity {

    String mBoardNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_board);

        Intent intent = getIntent();
        mBoardNum = intent.getStringExtra(CreateBoardFragment.BOARD_NUM);

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
            Toast.makeText(NewBoardActivity.this, R.string.signed_out_toast, Toast.LENGTH_LONG).show();
            Intent signOut = new Intent(NewBoardActivity.this, MainActivity.class);
            startActivity(signOut);
        } else if ( id == R.id.new_board_quite_board){
            Toast.makeText(NewBoardActivity.this, R.string.quit_board_successfully, Toast.LENGTH_LONG).show();
            Intent quiteBoard = new Intent(NewBoardActivity.this, HomePageActivity.class);
            startActivity(quiteBoard);
        }
        return true;
    }
}
