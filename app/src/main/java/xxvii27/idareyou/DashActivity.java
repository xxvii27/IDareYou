package xxvii27.idareyou;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.LayoutInflater;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.net.Uri;
import android.widget.ListView;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.MediaController;
import android.widget.TextView;
import android.database.Cursor;
import android.provider.MediaStore;
import android.media.MediaPlayer;
import android.content.Context;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseObject;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import android.util.Log;
import android.widget.VideoView;


import xxvii27.idareyou.model.User;


public class DashActivity extends FragmentActivity {

    FragmentPagerAdapter adapterViewPager;
    public static final int SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        Parse.initialize(this, "YJz5kY7KW9zxE79y3KkleC8WwP8aesJxgdMMBskq", "m6URvjQ3JbZMD3jHjbDwxwbDMA0fqrKSK1X7A2EX");

        //Set up tabs
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        final ActionBar actionBar = getActionBar();

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(1);

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };


        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
        });

        // Add 3 tabs, specifying the tab's text and TabListener
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Profile")
                            .setTabListener(tabListener));

            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Dashboard")
                            .setTabListener(tabListener));
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Challenges")
                            .setTabListener(tabListener));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash, menu);
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



    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance("Profile");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return SecondFragment.newInstance("Dashboard");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ThirdFragment.newInstance("Challenges");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }



    public static class FirstFragment extends Fragment {
        // Store instance variables
        private String title;

        // newInstance constructor for creating fragment with arguments
        public static FirstFragment newInstance(String title) {
            FirstFragment fragmentFirst = new FirstFragment();
            Bundle args = new Bundle();
            args.putString("someTitle", title);
            fragmentFirst.setArguments(args);
            return fragmentFirst;
        }

        // Store instance variables based on arguments passed
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            title = getArguments().getString("someTitle");
        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile, container, false);
            TextView pageTitle = (TextView) view.findViewById(R.id.pageTitle);
            TextView userName = (TextView) view.findViewById(R.id.userName);
            pageTitle.setText(title);
            userName.setText(User.getName());
            return view;
        }
    }

    public static class SecondFragment extends Fragment {
        // Store instance variables
        private String title;
        private List<ParseObject> userChallenge;
        private ArrayAdapter<String> listAdapter ;
        private ArrayList<String> challenges;
        ListView mainListView;

        // newInstance constructor for creating fragment with arguments
        public static SecondFragment newInstance(String title) {
            SecondFragment fragmentFirst = new SecondFragment();
            Bundle args = new Bundle();
            args.putString("someTitle", title);
            fragmentFirst.setArguments(args);
            return fragmentFirst;
        }

        // Store instance variables based on arguments passed
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            title = getArguments().getString("someTitle");
        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_challenge, container, false);
            TextView pageTitle = (TextView) view.findViewById(R.id.pageTitle);
            mainListView = (ListView) view.findViewById( R.id.mainListView );
            pageTitle.setText(title);
            //Try create list
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenged");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> challengeList, ParseException e) {
                    if (e == null) {
                        userChallenge = challengeList;
                        if(userChallenge != null)
                            buildListView(mainListView);
                    } else {
                        Log.d("Challenge", "Error: " + e.getMessage());
                    }
                }
            });
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            //Try create list
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenged");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> challengeList, ParseException e) {
                    if (e == null) {
                        userChallenge = challengeList;
                        if(userChallenge != null)
                            buildListView(mainListView);
                    } else {
                        Log.d("Challenge", "Error: " + e.getMessage());
                    }
                }
            });
        }

        private void buildListView(ListView m){

            challenges = new ArrayList<String>();

            for(int i = 0; i < userChallenge.size(); i++){
                challenges.add(userChallenge.get(i).getString("Name"));
            }

            listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row, challenges);

            m.setAdapter(listAdapter);

        }
    }


    public static class ThirdFragment extends Fragment {
        // Store instance variables
        private String title;
        private List<ParseObject> allChallenge;
        private ArrayAdapter<String> listAdapter ;
        private ArrayList<String> challenges;
        private VideoView mMedia;
        ListView mainListView;

        // newInstance constructor for creating fragment with arguments
        public static ThirdFragment newInstance(String title) {
            ThirdFragment fragmentFirst = new ThirdFragment();
            Bundle args = new Bundle();
            args.putString("someTitle", title);
            fragmentFirst.setArguments(args);
            return fragmentFirst;
        }

        // Store instance variables based on arguments passed
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            title = getArguments().getString("someTitle");
        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            TextView pageTitle = (TextView) view.findViewById(R.id.pageTitle);
            mainListView = (ListView) view.findViewById( R.id.mainListView );
            pageTitle.setText(title);
            //Individual items listener
            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String value = (String)parent.getItemAtPosition(position);
                    AlertDialog.Builder challenge = new AlertDialog.Builder(getActivity());
                    challenge.setTitle(value);
                    final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.activity_show_challenge, null);
                    challenge.setView(dialogView);
                    mMedia = (VideoView) dialogView.findViewById(R.id.media);

                    dialogView.findViewById(R.id.postVideo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent goToGallery = new Intent();
                            goToGallery.setType("video/*");
                            goToGallery.setAction(Intent.ACTION_GET_CONTENT);//
                            startActivityForResult(Intent.createChooser(goToGallery, "Select Video"), SELECT_IMAGE);

                        }
                    });

                    dialogView.findViewById(R.id.recVideo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
                    query.whereEqualTo("Name", value);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (object == null) {
                                Log.d("score", "The getFirst request failed.");
                            } else {

                            }
                        }
                    });




                    challenge.setPositiveButton("Challenge",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}  });


                    challenge.create().show();


                }
            });
            //Try create list
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> challengeList, ParseException e) {
                    if (e == null) {
                        allChallenge = challengeList;
                        if(allChallenge != null)
                            buildListView(mainListView);
                    } else {
                        Log.d("Challenge", "Error: " + e.getMessage());
                    }
                }
            });

            return view;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode != getActivity().RESULT_CANCELED) {
                if (requestCode == SELECT_IMAGE) {
                    Uri selectedImageUri = data.getData();
                    String selectedImagePath = getRealPathFromURI(getActivity().getApplicationContext(), selectedImageUri);
                    MediaPlayer mp = new MediaPlayer();
                    MediaController mc = new MediaController(getActivity());
                    try {
                        mp.setDataSource(selectedImagePath);
                        mp.prepare();
                        mp.start();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mMedia.setVideoPath(selectedImagePath);
                    mMedia.setMediaController(mc);
                    mMedia.start();


                }
            }

        }
        public String getRealPathFromURI(Context context, Uri contentUri) {
            Cursor cursor = null;
            try {
                String[] proj = { MediaStore.Video.Media.DATA };
                cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            //Try create list
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> challengeList, ParseException e) {
                    if (e == null) {
                        allChallenge = challengeList;
                        if(allChallenge != null)
                            buildListView(mainListView);
                    } else {
                        Log.d("Challenge", "Error: " + e.getMessage());
                    }
                }
            });
        }

        private void buildListView(ListView m){

            challenges = new ArrayList<String>();

            for(int i = 0; i < allChallenge.size(); i++){
                challenges.add(allChallenge.get(i).getString("Name"));
            }

            listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row, challenges);

            m.setAdapter(listAdapter);

        }
    }



}






