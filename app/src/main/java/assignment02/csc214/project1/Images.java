/*
KENNEDY AGUSI
ID: 30113350
CSC 214 PROJECT 1
*/

package assignment02.csc214.project1;

/**
 * Created by kennedy on 3/8/2017.
 */
import java.io.Serializable;

public class Images implements Serializable {

    private static final long serialVersionUID = -7504755932017737115L;
    private int mImageResId;
    private boolean mIsDisplaying;

    Images(int imageResId, boolean isDisplaying){

        mImageResId = imageResId;
        mIsDisplaying = isDisplaying;
    }

    int getImageResId() {
        return mImageResId;
    }

    public void setImageResId(int imageResId) {

        mImageResId = imageResId;
    }

    public void setDisplaying(boolean displaying) {
        mIsDisplaying = displaying;
    }

    boolean isDisplaying() {
        return mIsDisplaying;
    }

    void isDisplaying(boolean answerTrue) {
        mIsDisplaying = answerTrue;
    }
}

