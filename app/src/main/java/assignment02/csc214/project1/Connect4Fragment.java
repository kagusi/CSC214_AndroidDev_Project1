/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by Kennedy on 3/9/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Connect4Fragment extends Fragment {


    public interface Connect4UpdateAvailable{
        public void connect4UpdateScore(Player playerOne, Player playerTwo);
        //This will exit Hotter/Colder and return back players to host activity
        public void connect4End(Player playerOne, Player playerTwo);
        public void connect4DisplayToast(String message);
    }

    private Connect4UpdateAvailable mConnect4UpdateAvailable;
    GridView grid;
    int[] imageId = new int[36];

    private Player mPlayerOne;
    private Player mPlayerTwo;
    private Connect_4 mGame;
    private Button mNewGameButton;
    private Button mExitButton;
    private TextView mCurrentPlayerTextView;
    private int mComfirmExit;



    public Connect4Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mConnect4UpdateAvailable = (Connect4UpdateAvailable)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mConnect4UpdateAvailable = (Connect4UpdateAvailable)activity;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connect4, container, false);
        fillImage();

        mComfirmExit = 0;

        //Get Players from Host Activity
        this.mPlayerOne = (Player)getArguments().getSerializable(HostActivity.KEY_PLAYERONE);
        this.mPlayerTwo = (Player)getArguments().getSerializable(HostActivity.KEY_PLAYERTWO);
        //Initialize Game and assign players to it
        this.mGame = new Connect_4(mPlayerOne, mPlayerTwo);
        this.mNewGameButton = (Button)view.findViewById(R.id.connect4_newGameButton);
        this.mExitButton = (Button)view.findViewById(R.id.connect4_exitbutton);
        this.mCurrentPlayerTextView = (TextView)view.findViewById(R.id.connect4_currentPlayerTextView);
        mCurrentPlayerTextView.setText(mGame.getWhosTurn());
        mCurrentPlayerTextView.setTextColor(mGame.getPlayer().getColor());

        GridClass adapter = new GridClass(view.getContext(), imageId);
        grid=(GridView)view.findViewById(R.id.connect4_gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                int image = mGame.getImageResID();

                String outcome = mGame.startGame(position);
                int playedSlot = mGame.getDroppedPosition();


                ImageView gridImage = (ImageView)grid.getChildAt(playedSlot).findViewById(R.id.grid_image);
                gridImage.setImageResource(image);
                mCurrentPlayerTextView.setText(mGame.getWhosTurn());
                mCurrentPlayerTextView.setTextColor(mGame.getPlayer().getColor());


                //String pos = Integer.toString(position);
                Toast.makeText(getActivity().getApplicationContext(), outcome, Toast.LENGTH_SHORT).show();
                //mConnect4UpdateAvailable.connect4DisplayToast(outcome);
                mConnect4UpdateAvailable.connect4UpdateScore(mGame.getPlayerOne(), mGame.getPlayerTwo());
                //Toast.makeText(MainActivity.this, "You Clicked at ", Toast.LENGTH_SHORT).show();

            }
        });

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGame.resetGame();
                mCurrentPlayerTextView.setText(mGame.getWhosTurn());
                setEmptyImage();

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
                    setEmptyImage();
                    mComfirmExit = 0;

                    //Return players back to Host Activity
                    mConnect4UpdateAvailable.connect4End(mPlayerOne, mPlayerTwo);

                }else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "PLEASE CLICK EXIT AGAIN TO COMFIRM!", Toast.LENGTH_LONG).show();
                    //mConnect4UpdateAvailable.connect4DisplayToast("PLEASE CLICK EXIT AGAIN TO COMFIRM");
                    mComfirmExit++;
                }





            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void fillImage()
    {

        for(int i =0; i<36; i++)
        {
            imageId[i] = R.mipmap.connect4whiteicon;
        }

    }

    public void setEmptyImage()
    {

        for(int i =0; i<36; i++)
        {
            ImageView gridImage = (ImageView)grid.getChildAt(i).findViewById(R.id.grid_image);
            gridImage.setImageResource(mGame.getImageBank()[2].getImageResId());
        }

    }

}
