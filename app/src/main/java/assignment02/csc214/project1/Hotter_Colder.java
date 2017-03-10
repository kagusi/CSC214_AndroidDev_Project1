/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

import java.io.Serializable;

/**
 * Created by Kennedy on 3/8/2017.
 */

public class Hotter_Colder implements Serializable {

    private static final long serialVersionUID = -7604766932016737115L;

    private static Hotter_Colder instance;

    private Player mPlayerOne;
    private Player mPlayerTwo;
    private int mGeneratedNumber;
    private int mPlayerOneNumberOfGuess;
    private int mPlayerTwoNumberOfGuess;
    private boolean mIsGameOver;


    private Hotter_Colder(Player playerOne, Player playerTwo)
    {
        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;
        //Set the current player to player one
        this.mPlayerOne.setIsPlaying(true);
        this.mPlayerOneNumberOfGuess = 0;
        this.mPlayerOneNumberOfGuess = 0;
        this.mIsGameOver = false;
        this.mGeneratedNumber = (int)(Math.random() * 20);
    }

    public static synchronized Hotter_Colder getInstance(Player playerOne, Player playerTwo)
    {
        if(instance == null){
            instance = new Hotter_Colder(playerOne, playerTwo);
        }

        return instance;
    }


    //Return generated number
    public int getGeneratedNumber() {
        return mGeneratedNumber;
    }

    //Generates new number
    public void generateNumber() {
        resetGame();
        this.mGeneratedNumber = (int)(Math.random() * 20);
    }

    //Start Game, Compares guessed number with generated number
    public String startGame(int guessedNumber)
    {
        //Check whether both players has completeed their turns
        if(isGameOver())
            return "Game is Over!, Please Start a New Game";

        //int close = how closer  guessedNumber is to mGeneratedNumber
        //int far = how far  guessedNumber is to mGeneratedNumber
        int close = this.mGeneratedNumber - guessedNumber;
        int far = guessedNumber - this.mGeneratedNumber;

        //If current player is player one
        if(mPlayerOne.isPlaying())
            this.mPlayerOneNumberOfGuess++;
            //If currentr player is player two
        else if(mPlayerTwo.isPlaying())
            this.mPlayerTwoNumberOfGuess++;
        else
            return "Please Start New Game!";


        if(close == 0)
        {
            /*Each player completes a round once he/she get the correct number
            */

            //Player one completed round
            if(mPlayerOne.isPlaying())
            {
                mPlayerOne.setDonePlayingRound(true);
                mPlayerOne.setIsPlaying(false);
                mPlayerTwo.setIsPlaying(true);
            }
            //Player two completed round
            else
            {
                mPlayerTwo.setDonePlayingRound(true);
                mPlayerTwo.setIsPlaying(false);
                mPlayerOne.setIsPlaying(true);
            }


            //Check whether both players has completeed their turns
            if(isGameOver())
                return computeScore();
            return "Correct!!";
        }

        else if(close >= 0 && close <= 5)
            return "On Fire!";
        else if(close >= 0 && close <= 10)
            return "Hot!";
        else if(close >= 0 && close <= 15)
            return "Warmer!";
        else if(close >= 0 && close > 15)
            return "Warm";
        else if(far <= 5)
            return "Cold";
        else if(far <= 10)
            return "Colder!";
        else if(far <= 15)
            return "Freezing!";
        else
            return "Absolute Zero";
    }

    //Calculates players scores
    private String computeScore()
    {
        if(this.mPlayerOneNumberOfGuess > this.mPlayerTwoNumberOfGuess)
        {
            this.mPlayerTwo.setCurrentScore(100*(this.mPlayerOneNumberOfGuess - this.mPlayerTwoNumberOfGuess));
            this.mPlayerTwo.setTotalScore(mPlayerTwo.getTotalScore() + mPlayerTwo.getCurrentScore());
            //System.out.println(this.mPlayerTwo.getPlayerName()+"'s score is "+this.mPlayerTwo.getCurrentScore());
            //System.out.println(this.mPlayerOne.getPlayerName()+"'s score is "+this.mPlayerOne.getCurrentScore());
            return "Correct!!," +" "+this.mPlayerTwo.getPlayerName()+" won!";
        }
        else if(this.mPlayerOneNumberOfGuess < this.mPlayerTwoNumberOfGuess)
        {
            this.mPlayerOne.setCurrentScore(100*(this.mPlayerTwoNumberOfGuess - this.mPlayerOneNumberOfGuess));
            this.mPlayerOne.setTotalScore(mPlayerOne.getTotalScore() + mPlayerOne.getCurrentScore());
            //System.out.println(this.mPlayerTwo.getPlayerName()+"'s score is "+this.mPlayerTwo.getCurrentScore());
            //System.out.println(this.mPlayerOne.getPlayerName()+"'s score is "+this.mPlayerOne.getCurrentScore());
            return "Correct!!," +" "+this.mPlayerOne.getPlayerName()+" won!";
        }

        return "Tied!, No Winner";
    }

    //Reset Game whenever "New Game Button is pressed"
    public void resetGame()
    {
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
        setIsGameOver(false);
    }


    public String getWhosTurn()
    {
        if(mPlayerOne.isPlaying())
            return mPlayerOne.getPlayerName()+"'s"+" turn";
        else if(mPlayerTwo.isPlaying())
            return mPlayerTwo.getPlayerName()+"'s"+" turn";
        else
            return "Please Start New Game";
    }

    public String getCurrentPlayer()
    {
        if(mPlayerOne.isPlaying())
            return mPlayerOne.getPlayerName();
        else if(mPlayerTwo.isPlaying())
            return mPlayerTwo.getPlayerName();
        else
            return "No Active Player";
    }

    private boolean isGameOver() {
        if(this.mPlayerOne.isDonePlayingRound() && this.mPlayerTwo.isDonePlayingRound())
        {
            setIsGameOver(true);
            this.mPlayerOne.setIsPlaying(false);
            this.mPlayerOne.setCurrentScore(0);
            this.mPlayerTwo.setIsPlaying(false);
            this.mPlayerTwo.setCurrentScore(0);
        }

        return mIsGameOver;
    }

    private void setIsGameOver(boolean isGameOver) {
        this.mIsGameOver = isGameOver;
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

    public String getNumberOfGuesses()
    {
        if(mPlayerOne.isPlaying())
            return "Number of Guesses: "+Integer.toString(this.mPlayerOneNumberOfGuess);
        else if(mPlayerTwo.isPlaying())
            return "Number of Guesses: "+Integer.toString(this.mPlayerTwoNumberOfGuess);
        else
            return "Number of Guesses: 0";

    }
}

