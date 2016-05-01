package com.hypherweb.www.asscky.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.hypherweb.www.asscky.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {


    public static String TAG = SignUpActivity.class.getSimpleName();
    @Bind(R.id.signupEmailText)
    EditText mEmailText;

    @Bind(R.id.signupPasswordText)
    EditText mPasswordText;

    @Bind(R.id.signupSignupButton)
    Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        String firebase_url = getResources().getString(R.string.firebase_url);
        Log.d(TAG, firebase_url);
        final Firebase myFirebaseRef = new Firebase(firebase_url);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String email = mEmailText.getText().toString();
                    String password = mPasswordText.getText().toString();
                    myFirebaseRef.createUser(email, password, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "It worked");

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            signupFailedAlertDialog(firebaseError.getMessage());
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


    public void signupFailedAlertDialog(String message) {
        validationErrorAlertDialog(message, SignUpActivity.this);
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

    /**
     * Created dialog if no permission is available.
     */
    public void validationErrorAlertDialog(String message, Context context) {

        android.app.AlertDialog.Builder builder = createAlertDialog(getResources().getString(R.string.dialog_error_title), message, context);

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
