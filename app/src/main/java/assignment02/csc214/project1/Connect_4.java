/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/


package assignment02.csc214.project1;

/**
 * Created by Kennedy on 3/9/2017.
 */

import java.io.Serializable;

public class Connect_4 implements Serializable{
    private static final long serialVersionUID = -7504755932014437115L;
    private final Player mPlayerOne;
    private final Player mPlayerTwo;
    private String[][] mBoard;
    private int mBoardCapacity;
    private int mNumberOfFilledSlots;
    private boolean mIsGameOver;
    //Slot where player's ball was dropped
    private int droppedPosition;

    //Players las known position
    private int playerOneLastKnowPosition;
    private int playerTwoLastKnowPosition;


    private Images[] mImageBank;
    private int mImageResID;

    public Connect_4(Player mPlayerOne, Player mPlayerTwo) {

        //Players Images/colors
        this.mImageBank = new Images[] {
                new Images(R.mipmap.connect4redicon, false),
                new Images( R.mipmap.connect4yellowicon, false),
                new Images( R.mipmap.connect4whiteicon, false)};

        this.mPlayerOne = mPlayerOne;
        this.mPlayerTwo = mPlayerTwo;
        //Set the current player to player one
        this.mPlayerOne.setIsPlaying(true);
        this.mBoard = new String[6][6];
        this.mBoardCapacity = 36;
        this.mNumberOfFilledSlots = 0;

        this.mPlayerOne.setColor(-65536);
        this.mPlayerTwo.setColor(-256);
        //Initialize all slots to Empty
        fillBoard();
    }

    //Start Game
    public String startGame(int position)
    {

        //Compute column number from clicked position in GridView
        int playedColumn = position%6;
        //Check whether both players has completeed their turns
        if(isGameOver())
            return "Game is Over!, Please Start a New Game";

        int insertedRow = insert(playedColumn);
        if(insertedRow == -1)
        {
            if(mPlayerOne.isPlaying() == true)
                this.droppedPosition = playerOneLastKnowPosition;
            else if(mPlayerTwo.isPlaying() == true)
                this.droppedPosition  = playerTwoLastKnowPosition;

            return "Column filled, Choose Another";
        }

        else
        {
            boolean filledVertical = checkVerticalSlots(playedColumn);
            boolean filledHorizontal = checkHorizontalSlots(insertedRow);
            boolean filledRightDiag = checkRightDiagonal(insertedRow, playedColumn);
            boolean filledLeftDiag = checkLeftDiagonal(insertedRow, playedColumn);

            if(filledVertical || filledHorizontal || filledRightDiag || filledLeftDiag)
            {
                String outcome = computeScore();

                if(mPlayerOne.isPlaying() == true)
                {
                    mPlayerTwo.setIsPlaying(true);
                    mPlayerOne.setIsPlaying(false);
                    //this.droppedPosition = playerOneLastKnowPosition;
                }

                else if(mPlayerTwo.isPlaying() == true)
                {
                    mPlayerOne.setIsPlaying(true);
                    mPlayerTwo.setIsPlaying(false);
                    //this.droppedPosition  = playerTwoLastKnowPosition;
                }


                return outcome;
            }
            else if(!isSlotsFilled())
            {
                if(mPlayerOne.isPlaying() == true)
                {
                    mPlayerOne.setIsPlaying(false);
                    mPlayerTwo.setIsPlaying(true);
                }
                else if(mPlayerTwo.isPlaying() == true)
                {
                    mPlayerTwo.setIsPlaying(false);
                    mPlayerOne.setIsPlaying(true);
                }
            }
            else if(isSlotsFilled())
            {
                mPlayerOne.setDonePlayingRound(true);
                mPlayerTwo.setDonePlayingRound(true);
                return "No Available Slot, Game Tied!";
            }

        }

        return "";
    }

    //Check whether there is any available slots on board
    public boolean isSlotsFilled()
    {
        return mNumberOfFilledSlots == mBoardCapacity;
    }

    /*Insert a player in an open slot and the row index number
      If no available slot it will return -1
    */
    public int insert(int column)
    {
        String player = getCurrentPlayer();
        for(int i = this.mBoard.length-1; i >= 0; i--)
        {
            if(this.mBoard[i][column].equals("E"))
            {
                this.mBoard[i][column] = player;
                mNumberOfFilledSlots++;
                //Slot where player's ball is dropped
                this.droppedPosition = 6*i+column;

                if(mPlayerOne.isPlaying() == true)
                    playerOneLastKnowPosition = this.droppedPosition;
                else if(mPlayerTwo.isPlaying() == true)
                    playerTwoLastKnowPosition = this.droppedPosition;

                return i;
            }
        }
        return -1;
    }

    //Check if a current player has filled four(4) consecutive slots vertically
    public boolean checkVerticalSlots(int column)
    {
        int count = 0;
        String player = getCurrentPlayer();
        for(int i = 0; i< this.mBoard.length; i++)
        {
            if(this.mBoard[i][column].equals(player))
            {
                count++;
                if(count == 4)
                    return true;
            }
            else
                count = 0;
        }
        return false;
    }

    //Check if a current player has filled four(4) consecutive slots horizontally
    public boolean checkHorizontalSlots(int row)
    {
        int count = 0;
        String player = getCurrentPlayer();
        for(int i = 0; i< this.mBoard.length; i++)
        {
            if(this.mBoard[row][i].equals(player))
            {
                count++;
                if(count == 4)
                    return true;
            }
            else
                count = 0;
        }
        return false;
    }

    //Check if a current player has filled four(4) consecutive slots right diagonally
    public boolean checkRightDiagonal(int row, int column)
    {
        String player = getCurrentPlayer();
        int count = 1;
        int sumUpDiag = 0;
        int sumDownDiag = 0;

        boolean UpDiagIsDone = false;
        int upDiagRow = row-1;
        int upDiagColumn = column+1;

        boolean downDiagIsDone = false;
        int downDiagRow = row+1;
        int downDiagColumn = column-1;

        for(int i = 0; i< this.mBoard.length; i++)
        {
            if(UpDiagIsDone == false && upDiagRow >= 0 && upDiagColumn < 6 && this.mBoard[upDiagRow][upDiagColumn].equals(player))
            {
                sumUpDiag++;
                upDiagRow--;
                upDiagColumn++;
            }
            else
                UpDiagIsDone = true;

            if(downDiagIsDone == false && downDiagRow < 6 && downDiagColumn >= 0 && this.mBoard[downDiagRow][downDiagColumn].equals(player))
            {
                sumDownDiag++;
                downDiagRow++;
                downDiagColumn--;
            }
            else
                downDiagIsDone = true;
        }
        count += sumUpDiag + sumDownDiag;
        return count >= 4;
    }

    //Check if a current player has filled four(4) consecutive slots left diagonally
    public boolean checkLeftDiagonal(int row, int column)
    {
        String player = getCurrentPlayer();
        int count = 1;
        int sumUpDiag = 0;
        int sumDownDiag = 0;

        boolean UpDiagIsDone = false;
        int upDiagRow = row-1;
        int upDiagColumn = column-1;

        boolean downDiagIsDone = false;
        int downDiagRow = row+1;
        int downDiagColumn = column+1;

        for(int i = 0; i< this.mBoard.length; i++)
        {
            if(UpDiagIsDone == false && upDiagRow >= 0 && upDiagColumn >= 0 && this.mBoard[upDiagRow][upDiagColumn].equals(player))
            {
                sumUpDiag++;
                upDiagRow--;
                upDiagColumn--;
            }
            else
                UpDiagIsDone = true;

            if(downDiagIsDone == false && downDiagRow < 6 && downDiagColumn < 6 && this.mBoard[downDiagRow][downDiagColumn].equals(player))
            {
                sumDownDiag++;
                downDiagRow++;
                downDiagColumn++;
            }
            else
                downDiagIsDone = true;
        }
        count += sumUpDiag + sumDownDiag;
        return count >= 4;
    }

    //Initialize all slots in board to "E" (E means empty)
    public void fillBoard()
    {
        for(int i = 0; i<this.mBoard.length; i++)
        {
            for(int j = 0; j<this.mBoard.length; j++)
            {
                this.mBoard[i][j] = "E";
            }
        }
    }

    //Reset Game
    public void resetGame()
    {
        fillBoard();
        setIsGameOver(false);
        mPlayerOne.setDonePlayingRound(false);
        mPlayerTwo.setDonePlayingRound(false);

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

    public boolean isGameOver() {
        if(this.mPlayerOne.isDonePlayingRound() == true && this.mPlayerTwo.isDonePlayingRound() == true)
        {
            setIsGameOver(true);
            //this.mPlayerOne.setIsPlaying(false);
            //this.mPlayerTwo.setIsPlaying(false);
        }

        return mIsGameOver;
    }

    //Calculates players scores
    public String computeScore()
    {
        if(mPlayerOne.isPlaying() == true)
        {
            this.mPlayerOne.setTotalScore(mPlayerOne.getTotalScore() + 100);
            mPlayerOne.setDonePlayingRound(true);
            mPlayerTwo.setDonePlayingRound(true);
            mPlayerOne.setIsPlaying(false);
            mPlayerTwo.setIsPlaying(true);
            return this.mPlayerOne.getPlayerName()+" won!";
        }
        else if(mPlayerTwo.isPlaying() == true)
        {
            this.mPlayerTwo.setTotalScore(mPlayerTwo.getTotalScore() + 100);
            mPlayerTwo.setDonePlayingRound(true);
            mPlayerOne.setDonePlayingRound(true);
            mPlayerTwo.setIsPlaying(false);
            mPlayerOne.setIsPlaying(true);
            return this.mPlayerTwo.getPlayerName()+" won!";
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

    public void setIsGameOver(boolean isGameOver) {
        this.mIsGameOver = isGameOver;
    }

    public Player getPlayerOne() {
        return mPlayerOne;
    }

    public Player getPlayerTwo() {
        return mPlayerTwo;
    }

    public String[][] getmBoard() {
        return mBoard;
    }

    public void setBoard(String[][] board) {
        this.mBoard = board;
    }

    public int getBoardCapacity() {
        return mBoardCapacity;
    }

    public void setBoardCapacity(int boardCapacity) {
        this.mBoardCapacity = boardCapacity;
    }

    public int getNumberOfFilledSlots() {
        return mNumberOfFilledSlots;
    }

    public void setNumberOfFilledSlots(int numberOfFilledSlots) {
        this.mNumberOfFilledSlots = numberOfFilledSlots;
    }

    public int getDroppedPosition() {
        return droppedPosition;
    }

    public void setDroppedPosition(int droppedPosition) {
        this.droppedPosition = droppedPosition;
    }

    public int getImageResID() {

        if(mPlayerOne.isPlaying() == true)
            this.mImageResID = this.mImageBank[0].getImageResId();
        else if(mPlayerTwo.isPlaying() == true)
            this.mImageResID = this.mImageBank[1].getImageResId();
        return mImageResID;
    }

    public void setImageResID(int imageResID) {
        this.mImageResID = imageResID;
    }

    public Images[] getImageBank() {
        return mImageBank;
    }

    public Player getPlayer()
    {
        if(mPlayerOne.isPlaying() == true)
            return this.mPlayerOne;
        else if(mPlayerTwo.isPlaying() == true)
            return this.mPlayerTwo;

        return null;

    }


}
