package com.hypherweb.www.asscky.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hypherweb.www.asscky.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignInSignUp extends AppCompatActivity {

    @Bind(R.id.signUpButton) Button mSignupButton;

    @Bind(R.id.signInButton) Button mSignInButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);
        ButterKnife.bind(this);

        if (!isNetworkAvailable(SignInSignUp.this)) {
            noNetworkAvailableDialog();
        }

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignInSignUp.this, LoginActivty.class);
                SignInSignUp.this.startActivity(myIntent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignInSignUp.this, SignUpActivity.class);
                SignInSignUp.this.startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!isNetworkAvailable(SignInSignUp.this)) {
            noNetworkAvailableDialog();
        }
    }

    private void noNetworkAvailableDialog() {
        String message = getResources().getString(R.string.dialog_no_network_message);
        noPermissionCreateDialog(message, SignInSignUp.this);
    }


    /**
     * Creates an alert dialog with the passed title and message.
     *
     * @param title   Title for the Alert Dialog
     * @param message Message on the Alert Dialog
     * @return
     */
    public static android.app.AlertDialog.Builder createAlertDialog(String title, String message, Context context) {
        //Creating an alert window.
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }
//    public android.app.AlertDialog.Builder


    /**
     * Created dialog if no permission is available.
     */
    public void noPermissionCreateDialog(String message, Context context) {

        android.app.AlertDialog.Builder builder = createAlertDialog(getResources().getString(R.string.dialog_error_title), message, context);
        builder.setPositiveButton(getResources().getString(R.string.dialog_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS), 0);
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
        android.app.AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Checks if phone is connected to the internet.
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isConnectionAvail = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null)
                return netInfo.isConnected();
            else
                return isConnectionAvail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnectionAvail;
    }
}
