package xxvii27.idareyou;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;

import xxvii27.idareyou.model.Challenge;

public class AddChallengeActivity extends Activity {

    private Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        Parse.initialize(this, "YJz5kY7KW9zxE79y3KkleC8WwP8aesJxgdMMBskq", "m6URvjQ3JbZMD3jHjbDwxwbDMA0fqrKSK1X7A2EX");

        mAdd = (Button)findViewById(R.id.add);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String description = ((EditText) findViewById(R.id.description)).getText().toString();
                int points = Integer.parseInt(((EditText) findViewById(R.id.points)).getText().toString());

                //Create Challenge
                ParseObject.registerSubclass(Challenge.class);
                new Challenge(name, description, points).saveInBackground();

                finish();


            }
        });


    }



}
