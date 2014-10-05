package xxvii27.idareyou;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.parse.Parse;
import com.parse.ParseUser;
import android.content.Intent;
import android.app.ProgressDialog;
import com.parse.ParseException;
import com.parse.LogInCallback;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity {

    private AutoCompleteTextView mUserName;
    private EditText mPassword;
    private Button mSignUp;
    private Button mSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Intitalize Layout
        mUserName = (AutoCompleteTextView) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignUp = (Button) findViewById(R.id.email_sign_up_button);
        mSignIn = (Button) findViewById(R.id.email_sign_in_button);


        Parse.initialize(this, "YJz5kY7KW9zxE79y3KkleC8WwP8aesJxgdMMBskq", "m6URvjQ3JbZMD3jHjbDwxwbDMA0fqrKSK1X7A2EX");


        //If Buttons Clicked
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(goToSignUp);
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();

                String username = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Intent intent = new Intent(LoginActivity.this, DashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });


            }
        });


    }


}