package com.hypherweb.www.asscky.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hypherweb.www.asscky.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInSignUp extends AppCompatActivity {

    @BindView(R.id.signInButton)
    Button mSignInButton;

    @BindView(R.id.signUpButton)
    Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);
        ButterKnife.bind(this);

        if( isNetworkAvailable() ){
            String message = getResources().getString(R.string.dialog_no_network_message);
            noPermissionCreateDialog(message, getApplicationContext());
        }

    }



    /**
     * Creates an alert dialog with the passed title and message.
     *
     * @param title   Title for the Alert Dialog
     * @param message Message on the Alert Dialog
     * @return
     */
    public AlertDialog.Builder createAlertDialog(String title, String message, Context context) {
        //Creating an alert window.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }


    /**
     * Created dialog if no permission is available.
     */
    public void noPermissionCreateDialog(String message, Context context) {

        AlertDialog.Builder builder = createAlertDialog(getResources().getString(R.string.dialog_error_title), message, context);
        builder.setPositiveButton(getResources().getString(R.string.dialog_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Settings.ACTION_APPLICATION_SETTINGS), 0);
            }

        });
        builder.setNegativeButton(getResources().getString(R.string.dialog_close)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private boolean isNetworkAvailable() {
//        boolean returnVAl = false;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConntect = activeNetworkInfo.isConnected();
        boolean isActive = activeNetworkInfo != null;
        return  isConntect && isActive;
    }
}
