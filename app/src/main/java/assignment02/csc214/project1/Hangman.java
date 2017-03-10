/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by kennedy on 3/8/2017.
 */

import android.util.Log;

import java.io.Serializable;


public class Hangman implements Serializable{

    private static final String TAG = "HANGMANJAVA";
    private static final long serialVersionUID = -8504755932017737115L;

    private static Hangman instance;


    private Player mPlayerOne;
    private Player mPlayerTwo;
    private String[] mGeneratedWord;
    private String[] mGuessedAlphabetBank;
    private String mDisplayGuessedAlphabet;
    private final String[] mWordBank;
    private int mPlayerOneNumberOfGuess;
    private int mPlayerTwoNumberOfGuess;
    private boolean mIsGameOver;
    private boolean isRight;
    private Images[] mImageBank;
    private int mCurrentIndex;
    int mImageResID;

    public Hangman(Player playerOne, Player playerTwo) {

        //Hangman Images
        this.mImageBank = new Images[] {
                new Images(R.mipmap.blankhangman, false),
                new Images( R.mipmap.stage1, false),
                new Images( R.mipmap.stage2, false),
                new Images( R.mipmap.stage3, false),
                new Images( R.mipmap.stage4, false),
                new Images( R.mipmap.stage5, false),
                new Images( R.mipmap.stage6, false),
                new Images( R.mipmap.stage7, false),};
        this.mCurrentIndex = 0;
        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;
        //Set the current player to player one
        this.mPlayerOne.setIsPlaying(true);
        this.mWordBank = new String[]{"WERE", "COME", "WENT", "JANE", "SEND", "RUIN", "ASK", "THERE", "DISC", "LOYAL"};
        int index = (int)(Math.random() * 10);
        String word = mWordBank[index];
        this.mGeneratedWord = new String[word.length()];
        for(int i = 0; i<word.length(); i++)
            this.mGeneratedWord[i] = word.substring(i, (i+1));
        //this.mGeneratedWord = mWordBank[index].split("");
        //Log.d(TAG, "GeneratedLen: "+mGeneratedWord.length);
        this.mPlayerOneNumberOfGuess = 0;
        this.mPlayerTwoNumberOfGuess = 0;
        this.mGuessedAlphabetBank = new String[mGeneratedWord.length];
        //fillmGuessedAlphabetBank();
        this.mDisplayGuessedAlphabet = "";
        this.mIsGameOver = false;
        this.isRight = false;

    }

    public static synchronized Hangman getInstance(Player playerOne, Player playerTwo)
    {
        if(instance == null){
            instance = new Hangman(playerOne, playerTwo);
        }

        return instance;
    }

    //Generate new word
    public void generateWord()
    {
        resetGame();
        int index = (int)(Math.random() * 10);
        String word = mWordBank[index];
        this.mGeneratedWord = new String[word.length()];
        for(int i = 0; i<word.length(); i++)
            this.mGeneratedWord[i] = word.substring(i, (i+1));
        //this.mGeneratedWord = mWordBank[index].split("");
        fillmGuessedAlphabetBank();
        this.mGuessedAlphabetBank = new String[mGeneratedWord.length];
    }

    //Start Game
    public String startGame(String guessedAlphabet)
    {
        this.isRight = false;
        //Check whether both players has completeed their turns
        if(isGameOver())
            return "Game is Over!, Please Start a New Game";

        //Increment number of guesses if current player is player one
        if(mPlayerOne.isPlaying() == true && this.mPlayerOneNumberOfGuess < 8)
        {
            if(hasGuessedAlphabet(guessedAlphabet) == true)
                return "You Already Guessed this Alpabet";

        }
        //Increment number of guesses if currentr player is player two
        else if(mPlayerTwo.isPlaying() == true && this.mPlayerTwoNumberOfGuess < 8)
        {
            if(hasGuessedAlphabet(guessedAlphabet) == true)
                return "You Already Guessed this Alpabet";
        }
        else
            return "Please Start New Game!";




        //compares guessed character with character in the generated word
        for(int i = 0; i<mGeneratedWord.length; i++)
        {
            if(guessedAlphabet.equalsIgnoreCase(mGeneratedWord[i]))
            {
                mGuessedAlphabetBank[i] = guessedAlphabet.toUpperCase();
                this.isRight = true;
            }
            else
            {
                if(mGuessedAlphabetBank[i] == null)
                    mGuessedAlphabetBank[i] = "___";
            }

        }


        if(mPlayerOne.isPlaying() == true && this.isRight == false)
            this.mPlayerOneNumberOfGuess++;
        else if(mPlayerTwo.isPlaying() == true && this.isRight == false)
            this.mPlayerTwoNumberOfGuess++;

        //Check if current player has matched the word or has exhausted the maximum number of guesses
        if(mPlayerOne.isPlaying() == true && (hasMatchedWord() || this.mPlayerOneNumberOfGuess == 8))
        {
            mPlayerOne.setDonePlayingRound(true);
            mPlayerOne.setIsPlaying(false);
            mPlayerTwo.setIsPlaying(true);
            //Resets array of guessed alphabets
            this.mGuessedAlphabetBank = new String[mGeneratedWord.length];
            fillmGuessedAlphabetBank();
            //Resets displayed guessed words
            this.mDisplayGuessedAlphabet = "";

            if(this.mPlayerOneNumberOfGuess == 8)
            {
                //Check whether both players has completeed their turns
                if(isGameOver())
                {
                    fillmGuessedAlphabetBank();
                    return computeScore();
                }

                else
                {
                    fillmGuessedAlphabetBank();
                    return "Your Time is Up "+mPlayerOne.getPlayerName();
                }

            }
            else
            {
                //Check whether both players has completeed their turns
                if(isGameOver())
                {
                    fillmGuessedAlphabetBank();
                    return "You Got it, "+computeScore();
                }

                else
                {
                    fillmGuessedAlphabetBank();
                    return "You Got it, "+mPlayerOne.getPlayerName();
                }

            }
        }
        if(mPlayerTwo.isPlaying() == true && (hasMatchedWord() || this.mPlayerTwoNumberOfGuess == 8))
        {
            mPlayerTwo.setDonePlayingRound(true);
            mPlayerTwo.setIsPlaying(false);
            mPlayerOne.setIsPlaying(true);
            //Resets array of guessed alphabets
            this.mGuessedAlphabetBank = new String[mGeneratedWord.length];
            //Resets displayed guessed words
            this.mDisplayGuessedAlphabet = "";

            if(this.mPlayerTwoNumberOfGuess == 8)
            {
                //Check whether both players has completeed their turns
                if(isGameOver())
                {
                    fillmGuessedAlphabetBank();
                    return computeScore();
                }

                else
                {
                    fillmGuessedAlphabetBank();
                    return "Your Time is Up "+mPlayerTwo.getPlayerName();
                }

            }
            else
            {
                //Check whether both players has completeed their turns
                if(isGameOver())
                {
                    fillmGuessedAlphabetBank();
                    return "You Got it, "+computeScore();
                }

                else
                {
                    fillmGuessedAlphabetBank();
                    return "You Got it, "+mPlayerTwo.getPlayerName();
                }

            }
        }

        if(this.isRight)
            return "Your Guessed Right!!";
        else
        {
            mCurrentIndex++;
            return "WRONG!";
        }

    }

    public void fillmGuessedAlphabetBank()
    {
        for(int i = 0; i<mGuessedAlphabetBank.length; i++)
        {
            mGuessedAlphabetBank[i] = "___";
        }
        mCurrentIndex = 0;
    }

    //Check if player has already guessed same alphabet
    public boolean hasGuessedAlphabet(String alpha)
    {
        boolean matched = false;
        for(int i = 0; i<mGuessedAlphabetBank.length; i++)
        {
            if(alpha.equalsIgnoreCase(mGuessedAlphabetBank[i]))
                matched = true;
        }
        return matched;
    }

    //Check whether a player has matched the correct word
    public boolean hasMatchedWord()
    {
        boolean matched = false;
        int counter = 0;
        for(int i = 0; i<mGeneratedWord.length; i++)
        {
            if(mGeneratedWord[i].equalsIgnoreCase(mGuessedAlphabetBank[i]))
            {
                counter++;
            }

        }

        if(counter == mGeneratedWord.length)
            matched = true;
        return matched;
    }

    //Calculates players scores
    public String computeScore()
    {
        if(this.mPlayerOneNumberOfGuess > this.mPlayerTwoNumberOfGuess)
        {
            this.mPlayerTwo.setCurrentScore(100*(this.mPlayerOneNumberOfGuess - this.mPlayerTwoNumberOfGuess));
            this.mPlayerTwo.setTotalScore(mPlayerTwo.getTotalScore() + mPlayerTwo.getCurrentScore());
            //System.out.println(this.mPlayerTwo.getPlayerName()+"'s score is "+this.mPlayerTwo.getCurrentScore());
            //System.out.println(this.mPlayerOne.getPlayerName()+"'s score is "+this.mPlayerOne.getCurrentScore());
            return this.mPlayerTwo.getPlayerName()+" won!";
        }
        else if(this.mPlayerOneNumberOfGuess < this.mPlayerTwoNumberOfGuess)
        {
            this.mPlayerOne.setCurrentScore(100*(this.mPlayerTwoNumberOfGuess - this.mPlayerOneNumberOfGuess));
            this.mPlayerOne.setTotalScore(mPlayerOne.getTotalScore() + mPlayerOne.getCurrentScore());
            //System.out.println(this.mPlayerTwo.getPlayerName()+"'s score is "+this.mPlayerTwo.getCurrentScore());
            //System.out.println(this.mPlayerOne.getPlayerName()+"'s score is "+this.mPlayerOne.getCurrentScore());
            return this.mPlayerOne.getPlayerName()+" won!";
        }

        return "Tied!, No Winner";
    }

    public String getWhosTurn()
    {
        if(mPlayerOne.isPlaying() == true)
            return mPlayerOne.getPlayerName()+"'s"+" turn";
        else if(mPlayerTwo.isPlaying() == true)
            return mPlayerTwo.getPlayerName()+"'s"+" turn";
        else
            return "Please Start New Game";
    }

    public String getCurrentPlayer()
    {
        if(mPlayerOne.isPlaying() == true)
            return mPlayerOne.getPlayerName();
        else if(mPlayerTwo.isPlaying() == true)
            return mPlayerTwo.getPlayerName();
        else
            return "No Active Player";
    }

    //Reset Game whenever "New Game Button is pressed"
    public void resetGame()
    {
        mCurrentIndex = 0;
        //Reset player one as current player
        this.mPlayerOne.setIsPlaying(true);

        this.mPlayerTwo.setIsPlaying(false);
        this.mPlayerOneNumberOfGuess = 0;
        this.mPlayerTwoNumberOfGuess = 0;
        this.mPlayerOne.setCurrentScore(0);
        this.mPlayerTwo.setCurrentScore(0);
        this.mPlayerOne.setDonePlayingRound(false);
        this.mPlayerTwo.setDonePlayingRound(false);
        this.mIsGameOver = false;
        this.mDisplayGuessedAlphabet = "";
        setIsGameOver(false);

    }

    public Player getPlayerOne() {
        return mPlayerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.mPlayerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return mPlayerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.mPlayerTwo = playerTwo;
    }

    public String[] getGeneratedWord() {
        return mGeneratedWord;
    }

    public void setGeneratedWord(String[] generatedWord) {
        this.mGeneratedWord = generatedWord;
    }

    public String[] getWordBank() {
        return mWordBank;
    }

    public int getPlayerOneNumberOfGuess() {
        return mPlayerOneNumberOfGuess;
    }

    public void setPlayerOneNumberOfGuess(int playerOneNumberOfGuess) {
        this.mPlayerOneNumberOfGuess = playerOneNumberOfGuess;
    }

    public int getPlayerTwoNumberOfGuess() {
        return mPlayerTwoNumberOfGuess;
    }

    public void setPlayerTwoNumberOfGuess(int playerTwoNumberOfGuess) {
        this.mPlayerTwoNumberOfGuess = playerTwoNumberOfGuess;
    }

    public boolean IsGameOver() {
        return mIsGameOver;
    }

    public void setIsGameOver(boolean mIsGameOver) {
        this.mIsGameOver = mIsGameOver;
    }

    public boolean isGameOver() {
        if(this.mPlayerOne.isDonePlayingRound() == true && this.mPlayerTwo.isDonePlayingRound() == true)
        {
            setIsGameOver(true);
            this.mPlayerOne.setIsPlaying(false);
            this.mPlayerTwo.setIsPlaying(false);
        }

        return mIsGameOver;
    }

    public String[] getGuessedAlphabetBank() {
        return mGuessedAlphabetBank;
    }

    public void setGuessedAlphabetBank(String[] guessedAlphabetBank) {
        this.mGuessedAlphabetBank = guessedAlphabetBank;
    }

    public String getDisplayGuessedAlphabet() {

        mDisplayGuessedAlphabet = "";
        for(int i = 0; i<mGuessedAlphabetBank.length; i++)
        {
            mDisplayGuessedAlphabet += " "+mGuessedAlphabetBank[i]+" ";
        }
        return mDisplayGuessedAlphabet;
    }

    public void setDisplayGuessedAlphabet(String displayGuessedAlphabet) {
        this.mDisplayGuessedAlphabet = displayGuessedAlphabet;
    }

    public String getNumberOfGuesses()
    {
        if(mPlayerOne.isPlaying() == true)
            return "Number of Guesses: "+Integer.toString(this.mPlayerOneNumberOfGuess);
        else if(mPlayerTwo.isPlaying() == true)
            return "Number of Guesses: "+Integer.toString(this.mPlayerTwoNumberOfGuess);
        else
            return "Number of Guesses: 0";

    }

    public int getImageResID()
    {
        return mImageBank[mCurrentIndex].getImageResId();
    }

}



