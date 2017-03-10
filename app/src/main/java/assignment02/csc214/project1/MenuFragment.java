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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements Serializable {

    public interface menuUpdateAvailable {
        public void lauchAGame(String gameName);
        public void exitGame(); //Game Ended
    }

    private menuUpdateAvailable mMenuUpdateAvailable;
    private static final long serialVersionUID = -7604711932016737115L;
    private int mComfirmExit;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mMenuUpdateAvailable = (menuUpdateAvailable)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mMenuUpdateAvailable = (menuUpdateAvailable)activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        final Button hotter_colder = (Button)view.findViewById(R.id.Menu_Hotter_Colder);
        final Button hangman = (Button)view.findViewById(R.id.Menu_Hangman);
        final Button connect4 = (Button)view.findViewById(R.id.Menu_Connect4);
        final Button exit = (Button)view.findViewById(R.id.Menu_ExitButton);
        mComfirmExit = 0;

        hotter_colder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMenuUpdateAvailable.lauchAGame("Hot");

            }
        });

        hangman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMenuUpdateAvailable.lauchAGame("Hang");
            }
        });

        connect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMenuUpdateAvailable.lauchAGame("Connect");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mComfirmExit == 1)
                {
                    mComfirmExit = 0;
                    mMenuUpdateAvailable.exitGame();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "PLEASE CLICK EXIT AGAIN TO COMFIRM!", Toast.LENGTH_LONG).show();
                    mComfirmExit++;
                }

            }
        });


        return view;
    }

}
