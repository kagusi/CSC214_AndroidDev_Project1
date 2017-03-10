/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by kennedy on 3/8/2017.
 */

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mPlayerOneNameEdiText;
    private EditText mPlayerTwoNameEdiText;
    private Button mLaunchButton;
    String mPlayerOneName;
    String mPlayerTwoName;
    private static final int RC_HOST = 2;
    public static final String KEY_PLAYERONE = "MAIN.PLAYERONE";
    public static final String KEY_PLAYERTWO = "MAIN.PLAYERTWO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Lock Orientation to portrat
          I locked the orientaion because all my view of this page cannot fit into screen
          if orientation is landscape
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPlayerOneNameEdiText = (EditText)findViewById(R.id.main_playerOneNameEditext);
        mPlayerTwoNameEdiText = (EditText)findViewById(R.id.main_playerTwoNameEditext);
        mLaunchButton = (Button)findViewById(R.id.main_launchButton);

        mLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlayerOneName = mPlayerOneNameEdiText.getText().toString();
                mPlayerTwoName = mPlayerTwoNameEdiText.getText().toString();

                if(TextUtils.isEmpty(mPlayerOneNameEdiText.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "PLEASE ENTER FIRST PLAYER", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(mPlayerTwoNameEdiText.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "PLEASE ENTER SECOND PLAYER", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, HostActivity.class);
                    intent.putExtra(KEY_PLAYERONE, mPlayerOneName);
                    intent.putExtra(KEY_PLAYERTWO, mPlayerTwoName);
                    startActivityForResult(intent, RC_HOST);
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        Log.d(TAG, "ononActivityResult() Called");

        if(requestCode == RC_HOST) {
            if (responseCode == RESULT_OK) {

                mPlayerOneName = data.getStringExtra(KEY_PLAYERONE);
                mPlayerTwoName = data.getStringExtra(KEY_PLAYERTWO);

                //Toast.makeText(MainActivity.this, data.getStringExtra(KEY_PLAYERONE), Toast.LENGTH_LONG).show();
                mPlayerOneNameEdiText.setText(mPlayerOneName);
                mPlayerTwoNameEdiText.setText(mPlayerTwoName);
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        Log.d(TAG, "onSaveInstanceState(Bundle) Called");
        state.putString(KEY_PLAYERONE, mPlayerOneNameEdiText.getText().toString());
        state.putString(KEY_PLAYERTWO, mPlayerTwoNameEdiText.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "nRestoreInstanceState() called");

        //Restore Players
        mPlayerOneName = savedInstanceState.getString(KEY_PLAYERONE);
        mPlayerTwoName = savedInstanceState.getString(KEY_PLAYERTWO);

        mPlayerOneNameEdiText.setText(mPlayerOneName);
        mPlayerTwoNameEdiText.setText(mPlayerTwoName);

    }
}
