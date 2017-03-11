/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by kennedy on 3/8/2017.
 */

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class HostActivity extends AppCompatActivity implements MenuFragment.menuUpdateAvailable, HotterColderFragment.HoterColderUpdateAvailable,
        HangmanFragment.HangmanUpdateAvailable, Connect4Fragment.Connect4UpdateAvailable{

    private static final String TAG = "HostActivity";
    private TextView mPlayerOneEditText;
    private TextView mPlayerTwoEditText;
    private TextView mPlayerOneScoreEditText;
    private TextView mPlayerTwoScoreEditText;
    private Player mPlayerOne;
    private Player mPlayerTwo;
    public static final String KEY_PLAYERONE = "HOST.PLAYERONE";
    public static final String KEY_PLAYERTWO = "HOST.PLAYERTWO";
    public static final String KEY_WHICHFRAG = "HOST.WHICH";
    public static final String KEY_HOTFRAG = "HOST.HOTFRAG";
    //public static final String KEY_MENUFRAG = "HOST.MENUFRAG";


    private String mWhichFragg = "A";

    //Fragments hosted by this activity
    private MenuFragment mMenuFragment;
    private HotterColderFragment mHotterColderFragment;
    private HangmanFragment mHangmanFragment;
    private Connect4Fragment mConnect4Fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_host);


        mHotterColderFragment = new HotterColderFragment();
        //mMenuFragment = new MenuFragment();

        if(savedInstanceState != null)
        {
            //Restore Players
            this.mPlayerOne = (Player)savedInstanceState.getSerializable(KEY_PLAYERONE);
            this.mPlayerTwo = (Player)savedInstanceState.getSerializable(KEY_PLAYERTWO);
            this.mWhichFragg = savedInstanceState.getString(KEY_WHICHFRAG);

            //this.mHotterColderFragment = (HotterColderFragment)savedInstanceState.getSerializable(KEY_HOTFRAG);

        }
        else
        {
            Intent intent = getIntent();
            //Get Players names from main activity
            String playerOneName = intent.getStringExtra(MainActivity.KEY_PLAYERONE);
            String playerTwoName = intent.getStringExtra(MainActivity.KEY_PLAYERTWO);

            //Create New Players
            this.mPlayerOne = new Player(playerOneName);
            this.mPlayerTwo = new Player(playerTwoName);

            //Display Menu Fragment
            mMenuFragment = new MenuFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_hostRelativeLayout, mMenuFragment)
                    .commit();
            //mWhichFragg = "MENU";
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }



        mPlayerOneEditText = (TextView)findViewById(R.id.PlayerOneName);
        mPlayerTwoEditText = (TextView)findViewById(R.id.PlayerTwoName);
        mPlayerOneScoreEditText = (TextView)findViewById(R.id.PlayerOneScore);
        mPlayerTwoScoreEditText = (TextView)findViewById(R.id.PlayerTwoScore);

        //Display Players Names
        mPlayerOneEditText.setText(mPlayerOne.getPlayerName());
        mPlayerTwoEditText.setText(mPlayerTwo.getPlayerName());

        //Display Players Scores
        String playerOneScore = Integer.toString(mPlayerOne.getTotalScore());
        String playerTwoScore = Integer.toString(mPlayerTwo.getTotalScore());
        mPlayerOneScoreEditText.setText(playerOneScore);
        mPlayerTwoScoreEditText.setText(playerTwoScore);





    }

    //Check which fragment is added and Determine Orientation
    public void determinOrientaion()
    {



    }

    public void restorMenu()
    {
        MenuFragment fragment = new MenuFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_hostRelativeLayout, fragment)
                .commit();

    }

    @Override
    public void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        Log.d(TAG, "onSaveInstanceState(Bundle) Called");

        state.putSerializable(KEY_PLAYERONE, this.mPlayerOne);
        state.putSerializable(KEY_PLAYERTWO, this.mPlayerTwo);
        state.putString(KEY_WHICHFRAG, mWhichFragg);
        state.putSerializable(KEY_HOTFRAG, mHotterColderFragment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "nRestoreInstanceState() called");

        //Restore Players
        this.mPlayerOne = (Player)savedInstanceState.getSerializable(KEY_PLAYERONE);
        this.mPlayerTwo = (Player)savedInstanceState.getSerializable(KEY_PLAYERTWO);
        this.mWhichFragg = savedInstanceState.getString(KEY_WHICHFRAG);
        //this.mHotterColderFragment = (HotterColderFragment)savedInstanceState.getSerializable(KEY_HOTFRAG);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called.");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called.");


        //Toast.makeText(HostActivity.this, tt, Toast.LENGTH_LONG).show();
    }

    @Override
    public void lauchAGame(String gameName) {
        if(gameName.equals("Hot"))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                Bundle hoterArguments = new Bundle();
                hoterArguments.putSerializable(KEY_PLAYERONE, this.mPlayerOne);
                hoterArguments.putSerializable(KEY_PLAYERTWO, this.mPlayerTwo);

            removeAllFragments(getFragmentManager());

              mHotterColderFragment = new HotterColderFragment();
                //Assign player to play that will play the game
                mHotterColderFragment.setArguments(hoterArguments);

                getFragmentManager()
                        .beginTransaction()
                        .detach(mMenuFragment)
                        .add(R.id.activity_hostRelativeLayout, mHotterColderFragment)
                        .commit();

            //Toast.makeText(HostActivity.this, "Hot", Toast.LENGTH_LONG).show();
        }
        else if(gameName.equals("Hang"))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //Toast.makeText(HostActivity.this, "Hangman", Toast.LENGTH_LONG).show();
            Bundle hangmanArguments = new Bundle();
            hangmanArguments.putSerializable(KEY_PLAYERONE, this.mPlayerOne);
            hangmanArguments.putSerializable(KEY_PLAYERTWO, this.mPlayerTwo);

            removeAllFragments(getFragmentManager());

            mHangmanFragment = new HangmanFragment();
            //Assign player to play that will play the game
            mHangmanFragment.setArguments(hangmanArguments);

            getFragmentManager()
                    .beginTransaction()
                    .detach(mMenuFragment)
                    .add(R.id.activity_hostRelativeLayout, mHangmanFragment)
                    .commit();

            mWhichFragg = "HANG";
        }
        else if(gameName.equals("Connect"))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //Toast.makeText(HostActivity.this, "Hangman", Toast.LENGTH_LONG).show();
            Bundle connect4Arguments = new Bundle();
            connect4Arguments.putSerializable(KEY_PLAYERONE, this.mPlayerOne);
            connect4Arguments.putSerializable(KEY_PLAYERTWO, this.mPlayerTwo);

            removeAllFragments(getFragmentManager());

            mConnect4Fragment = new Connect4Fragment();
            //Assign player to play that will play the game
            mConnect4Fragment.setArguments(connect4Arguments);

            getFragmentManager()
                    .beginTransaction()
                    .detach(mMenuFragment)
                    .add(R.id.activity_hostRelativeLayout, mConnect4Fragment)
                    .commit();

            mWhichFragg = "HANG";

        }

    }

    @Override
    public void exitGame() {

        Intent data = new Intent();
        data.putExtra(MainActivity.KEY_PLAYERONE, mPlayerOne.getPlayerName());
        data.putExtra(MainActivity.KEY_PLAYERTWO, mPlayerTwo.getPlayerName());
        //Toast.makeText(HostActivity.this, mPlayerOne.getPlayerName(), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public void hoterUpdateScore(Player playerOne, Player playerTwo) {

        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;

        //Display Players Scores
        String playerOneScore = Integer.toString(mPlayerOne.getTotalScore());
        String playerTwoScore = Integer.toString(mPlayerTwo.getTotalScore());
        mPlayerOneScoreEditText.setText(playerOneScore);
        mPlayerTwoScoreEditText.setText(playerTwoScore);


    }

    @Override
    public void hoterEnd(Player playerOne, Player playerTwo) {

        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;

        removeAllFragments(getFragmentManager());

        mMenuFragment = new MenuFragment();

        getFragmentManager()
                .beginTransaction()
                .detach(mHotterColderFragment)
                .add(R.id.activity_hostRelativeLayout, mMenuFragment)
                .commit();
    }

    @Override
    public void displayToast(String message) {

        Toast.makeText(HostActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    private static void removeAllFragments(FragmentManager fragmentManager) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public void hangmanUpdateScore(Player playerOne, Player playerTwo) {

        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;

        //Display Players Scores
        String playerOneScore = Integer.toString(mPlayerOne.getTotalScore());
        String playerTwoScore = Integer.toString(mPlayerTwo.getTotalScore());
        mPlayerOneScoreEditText.setText(playerOneScore);
        mPlayerTwoScoreEditText.setText(playerTwoScore);

    }

    @Override
    public void hangmanEnd(Player playerOne, Player playerTwo) {

        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;

        removeAllFragments(getFragmentManager());

        mMenuFragment = new MenuFragment();

        getFragmentManager()
                .beginTransaction()
                .detach(mHangmanFragment)
                .add(R.id.activity_hostRelativeLayout, mMenuFragment)
                .commit();

    }

    @Override
    public void hangmanDisplayToast(String message) {

        Toast.makeText(HostActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void connect4UpdateScore(Player playerOne, Player playerTwo) {

        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;

        //Display Players Scores
        String playerOneScore = Integer.toString(mPlayerOne.getTotalScore());
        String playerTwoScore = Integer.toString(mPlayerTwo.getTotalScore());
        mPlayerOneScoreEditText.setText(playerOneScore);
        mPlayerTwoScoreEditText.setText(playerTwoScore);

    }

    @Override
    public void connect4End(Player playerOne, Player playerTwo) {

        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;

        removeAllFragments(getFragmentManager());

        mMenuFragment = new MenuFragment();

        getFragmentManager()
                .beginTransaction()
                .remove(mConnect4Fragment)
                .add(R.id.activity_hostRelativeLayout, mMenuFragment)
                .commit();

    }

    @Override
    public void connect4DisplayToast(String message) {

        Toast.makeText(HostActivity.this, message, Toast.LENGTH_SHORT).show();

    }
}
