/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by kennedy on 3/8/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotterColderFragment extends Fragment implements Serializable {

    public interface HoterColderUpdateAvailable{
        public void hoterUpdateScore(Player playerOne, Player playerTwo);
        //This will exit Hotter/Colder and return back players to host activity
        public void hoterEnd(Player playerOne, Player playerTwo);
        public void displayToast(String message);
    }

    private static final String TAG = "HHoterActivity";
    private static final long serialVersionUID = -7604722932016737115L;
    private HoterColderUpdateAvailable mHoterColderUpdateAvailable;
    private TextView mCurrentPlayerTextView;
    private TextView mNumberOfGuessesTextView;
    private EditText mEnterANumberEditText;
    private Button mGuessButton;
    private Button mNewGameButton;
    private Button mExitGameButton;
    private int mComfirmExit;

    private Player mPlayerOne;
    private Player mPlayerTwo;
    private Hotter_Colder mGame;

    public static final String KEY_HOTPLAYERONE = "HOST.P1";
    public static final String KEY_HOTPLAYERTWO = "HOST.P2";
    public static final String KEY_GAME = "HOST.GAME";


    public HotterColderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mHoterColderUpdateAvailable = (HoterColderUpdateAvailable)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mHoterColderUpdateAvailable = (HoterColderUpdateAvailable)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotter_colder, container, false);
        Log.d(TAG, "onCreateView(Bundle) Called");
        mCurrentPlayerTextView = (TextView)view.findViewById(R.id.HotterColder_currentPlayerTextView);
        mNumberOfGuessesTextView = (TextView)view.findViewById(R.id.HotterColder_numberOfGuessesTextView);
        mEnterANumberEditText = (EditText)view.findViewById(R.id.HotterColder_guessANumEditText);
        mGuessButton = (Button)view.findViewById(R.id.HotterColder_GuessButton);
        mNewGameButton = (Button)view.findViewById(R.id.HotterColder_NewGameButton);
        mExitGameButton = (Button)view.findViewById(R.id.HotterColder_ExitButton);
        mComfirmExit = 0;

            //Get Players from Host Activity
            this.mPlayerOne = (Player)getArguments().getSerializable(HostActivity.KEY_PLAYERONE);
            this.mPlayerTwo = (Player)getArguments().getSerializable(HostActivity.KEY_PLAYERTWO);

            //Initialize Game and assign players to it
            mGame = Hotter_Colder.getInstance(mPlayerOne, mPlayerTwo);
            mCurrentPlayerTextView.setText(mGame.getWhosTurn());
            mNumberOfGuessesTextView.setText(mGame.getNumberOfGuesses());




        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mEnterANumberEditText.getText().toString()))
                {
                    mHoterColderUpdateAvailable.displayToast("PLEASE ENTER A NUMBER");
                    return;
                }

                int guessedNum = Integer.parseInt(mEnterANumberEditText.getText().toString());
                String outcome = mGame.startGame(guessedNum);
                String numberOfGuess = mGame.getNumberOfGuesses();

                Toast.makeText(getActivity().getApplicationContext(), outcome, Toast.LENGTH_SHORT).show();
                //mHoterColderUpdateAvailable.displayToast(outcome);
                mCurrentPlayerTextView.setText(mGame.getWhosTurn());
                mNumberOfGuessesTextView.setText(mGame.getNumberOfGuesses());
                //Display players current Cummulative score
                mHoterColderUpdateAvailable.hoterUpdateScore(mGame.getPlayerOne(), mGame.getPlayerTwo());

            }
        });

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGame.generateNumber();
                mCurrentPlayerTextView.setText(mGame.getWhosTurn());
                mNumberOfGuessesTextView.setText(mGame.getNumberOfGuesses());
            }
        });

        mExitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mComfirmExit == 1)
                {
                    mGame.resetGame();

                    mPlayerOne = mGame.getPlayerOne();
                    mPlayerTwo = mGame.getPlayerTwo();

                    mPlayerOne.setIsPlaying(false);
                    mPlayerOne.setDonePlayingRound(false);
                    mPlayerOne.setCurrentScore(0);
                    mPlayerTwo.setIsPlaying(false);
                    mPlayerTwo.setDonePlayingRound(false);
                    mPlayerTwo.setCurrentScore(0);
                    mComfirmExit = 0;

                    //Return players back to Host Activity
                    mHoterColderUpdateAvailable.hoterEnd(mPlayerOne, mPlayerTwo);

                }else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "PLEASE CLICK EXIT AGAIN TO COMFIRM!", Toast.LENGTH_LONG).show();
                    //mConnect4UpdateAvailable.connect4DisplayToast("PLEASE CLICK EXIT AGAIN TO COMFIRM");
                    mComfirmExit++;
                }

            }
        });

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        Log.d(TAG, "onSaveInstanceState(Bundle) Called");
        state.putSerializable(KEY_HOTPLAYERONE, mPlayerOne);
        state.putSerializable(KEY_HOTPLAYERTWO, mPlayerTwo);
        //state.putSerializable(KEY_GAME, mGame);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated(Bundle) called.");



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called.");
    }

}
