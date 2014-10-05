package xxvii27.idareyou.model;

/**
 * Created by xxvii27 on 10/4/14.
 */


import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import java.util.List;
import com.parse.ParseUser;

@ParseClassName("Challenged")
public class Challenge extends ParseObject {

    public Challenge(){}

    public Challenge(String n, String d, int p){
        this.put("Name", n);
        this.put("Description", d);
        this.put("Points", p);
    }






}
