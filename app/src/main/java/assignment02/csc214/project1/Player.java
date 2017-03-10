/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by Kennedy on 3/7/2017.
 */
import java.io.Serializable;


//Player Class
class Player implements Serializable{

    private static final long serialVersionUID = -7504755932017737115L;
    private String mPlayerName;
    private int mTotalScore;
    private int mCurrentGameScore;
    private boolean mIsPlaying;
    private boolean mDonePlayingRound;
    private int mColor;

    public Player(String playerName) {
        this.mPlayerName = playerName;
        this.mTotalScore = 0;
        this.mColor = 0;
        this.mCurrentGameScore = 0;
        this.mIsPlaying = false;
        this.mDonePlayingRound = false;
    }


    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String playerName) {
        this.mPlayerName = playerName;
    }

    public int getCurrentScore() {
        return mCurrentGameScore;
    }

    public void setCurrentScore(int currentScore) {
        this.mCurrentGameScore = currentScore;
    }

    public int getTotalScore() {
        return mTotalScore;
    }

    public void setTotalScore(int totalScore) {
        this.mTotalScore = totalScore;
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public void setIsPlaying(boolean mIsPlaying) {
        this.mIsPlaying = mIsPlaying;
    }

    public boolean isDonePlayingRound() {
        return mDonePlayingRound;
    }

    public void setDonePlayingRound(boolean donePlayingRound) {
        this.mDonePlayingRound = donePlayingRound;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

}
