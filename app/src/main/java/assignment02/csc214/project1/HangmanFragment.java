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
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class HangmanFragment extends Fragment {


    public interface HangmanUpdateAvailable{
        public void hangmanUpdateScore(Player playerOne, Player playerTwo);
        //This will exit Hotter/Colder and return back players to host activity
        public void hangmanEnd(Player playerOne, Player playerTwo);
        public void hangmanDisplayToast(String message);
    }

    private static final String TAG = "HangmanActivity";
    private HangmanUpdateAvailable mHangmanUpdateAvailable;
    private TextView mCurrentPlayerTextView;
    private TextView mNumberOfGuessTextView;
    private ImageView mHangmanImageView;
    private TextView mDisplayGuessesTextView;
    private EditText mEnterATextEditText;
    private Button mGuessButton;
    private Button mNewGameButton;
    private Button mExitButton;
    private int mComfirmExit;


    private Player mPlayerOne;
    private Player mPlayerTwo;
    private Hangman mGame;

    public static final String KEY_HANGMANPLAYERONE = "HANG.P1";
    public static final String KEY_HANGMANPLAYERTWO = "HANG.P2";
    public static final String KEY_HANGMANGAME = "HANG.GAME";
    private static final String KEY_IMAGE = "IMAGEVIEW";




    public HangmanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mHangmanUpdateAvailable = (HangmanUpdateAvailable)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mHangmanUpdateAvailable = (HangmanUpdateAvailable)activity;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hangman, container, false);
        Log.d(TAG, "onCreateView(Bundle) Called");


        mCurrentPlayerTextView = (TextView)view.findViewById(R.id.Hangman_CurrentPlayerTextView);
        mNumberOfGuessTextView = (TextView)view.findViewById(R.id.Hangman_NumberOfGuessTextView);
        mHangmanImageView = (ImageView)view.findViewById(R.id.Hangman_ImageView);
        mDisplayGuessesTextView = (TextView)view.findViewById(R.id.Hangman_DisplayGuessesdTextView);
        mEnterATextEditText = (EditText)view.findViewById(R.id.Hangman_enterATextEditText);
        mGuessButton = (Button)view.findViewById(R.id.Hangman_GuessButton);
        mNewGameButton = (Button)view.findViewById(R.id.Hangman_NewGameButton);
        mExitButton = (Button)view.findViewById(R.id.Hangman_ExitButton);
        mComfirmExit = 0;
        //Get Players from Host Activity
        this.mPlayerOne = (Player)getArguments().getSerializable(HostActivity.KEY_PLAYERONE);
        this.mPlayerTwo = (Player)getArguments().getSerializable(HostActivity.KEY_PLAYERTWO);

        //Initialize Game and assign players to it
        this.mGame = Hangman.getInstance(mPlayerOne, mPlayerTwo);
        mCurrentPlayerTextView.setText(mGame.getWhosTurn());
        mNumberOfGuessTextView.setText(mGame.getNumberOfGuesses());


        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mEnterATextEditText.getText().toString()))
                {
                    mHangmanUpdateAvailable.hangmanDisplayToast("PLEASE ENTER A LETTER");
                    return;
                }


                String guessedLetter = mEnterATextEditText.getText().toString();
                String outcome = mGame.startGame(guessedLetter);
                //String numberOfGuess = mGame.getNumberOfGuesses();

                Toast.makeText(getActivity().getApplicationContext(), outcome, Toast.LENGTH_SHORT).show();
                //mHangmanUpdateAvailable.hangmanDisplayToast(outcome);
                mCurrentPlayerTextView.setText(mGame.getWhosTurn());

                mNumberOfGuessTextView.setText(mGame.getNumberOfGuesses());
                mDisplayGuessesTextView.setText(mGame.getDisplayGuessedAlphabet());
                //Display Hangman Image
                mHangmanImageView.setImageResource(mGame.getImageResID());
                //Display players current Cummulative score
                mHangmanUpdateAvailable.hangmanUpdateScore(mGame.getPlayerOne(), mGame.getPlayerTwo());
            }
        });

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGame.generateWord();
                mCurrentPlayerTextView.setText(mGame.getWhosTurn());
                //mNumberOfGuessTextView.setText(mGame.getNumberOfGuesses());
            }
        });

        mExitButton.setOnClickListener(new View.OnClickListener() {
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
                    mHangmanUpdateAvailable.hangmanEnd(mPlayerOne, mPlayerTwo);

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

}
