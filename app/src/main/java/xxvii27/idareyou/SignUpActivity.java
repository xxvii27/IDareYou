package xxvii27.idareyou;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.parse.Parse;
import com.parse.ParseUser;
import android.app.ProgressDialog;
import com.parse.ParseException;
import com.parse.SignUpCallback;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;


public class SignUpActivity extends Activity {

    //Wire UI
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Parse.initialize(this, "YJz5kY7KW9zxE79y3KkleC8WwP8aesJxgdMMBskq", "m6URvjQ3JbZMD3jHjbDwxwbDMA0fqrKSK1X7A2EX");

        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword =  (EditText) findViewById(R.id.password);

        //Signup
        findViewById(R.id.email_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(SignUpActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();

                //Get data from UI
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                // Set up a new Parse user
                ParseUser user = new ParseUser();
                user.setUsername(email);
                user.setPassword(password);
                user.setEmail(email);
                user.put("Name", name);
                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            //Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
                            Intent intent = new Intent(SignUpActivity.this, DashActivity.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });


            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
