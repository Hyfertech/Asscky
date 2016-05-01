package com.hypherweb.www.asscky.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.hypherweb.www.asscky.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.loginEmailText)
    EditText mEmailText;

    @Bind(R.id.loginPasswordText)
    EditText mPasswordText;

    @Bind(R.id.loginSigninButton)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String firebaseUrl = getResources().getString(R.string.firebase_url);
        final Firebase myFirebase = new Firebase(firebaseUrl);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( validate()) {
                    String email = mEmailText.getText().toString();
                    String password = mPasswordText.getText().toString();
                    myFirebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            Log.d(TAG, "WORKED");
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            String message = firebaseError.getMessage();
                            signinFailedAlertDialog(message);
                        }
                    });
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String emailError = getResources().getString(R.string.error_validator_email);
        String passwordError = getResources().getString(R.string.error_validator_password);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError(emailError);
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError(passwordError);
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }


    public void signinFailedAlertDialog(String message) {
        validationErrorAlertDialog(message, LoginActivity.this);
    }

    /**
     * Created dialog if no permission is available.
     */
    public void validationErrorAlertDialog(String message, Context context) {

        android.app.AlertDialog.Builder builder = SignUpActivity.createAlertDialog(getResources().getString(R.string.dialog_error_title),
                message, context);

        builder.setPositiveButton(getResources().getString(R.string.dialog_okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        builder.setNegativeButton(getResources().getString(R.string.dialog_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setCancelable(false);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
