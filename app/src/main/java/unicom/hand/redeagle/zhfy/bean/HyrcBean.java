package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-04-19.
 */

public class HyrcBean extends BaseBean {
    private boolean hasData;
    private boolean isIgnoreRemind;
    private boolean isNew;
    private boolean mCanJoin;
    private boolean mExpire;

    private String mAttendePin;
    private String mTitle;
    private String mOrganizer;
    private String mNumber;
    private String mScheduledId;
    private String mStartTime;
    private String mState;
    private String mMeetingId;
    private String mLastUpdateTime;
    private String mId;
    private String mFoucusUri;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public boolean isIgnoreRemind() {
        return isIgnoreRemind;
    }

    public void setIgnoreRemind(boolean ignoreRemind) {
        isIgnoreRemind = ignoreRemind;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean ismCanJoin() {
        return mCanJoin;
    }

    public void setmCanJoin(boolean mCanJoin) {
        this.mCanJoin = mCanJoin;
    }

    public boolean ismExpire() {
        return mExpire;
    }

    public void setmExpire(boolean mExpire) {
        this.mExpire = mExpire;
    }

    public String getmAttendePin() {
        return mAttendePin;
    }

    public void setmAttendePin(String mAttendePin) {
        this.mAttendePin = mAttendePin;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmOrganizer() {
        return mOrganizer;
    }

    public void setmOrganizer(String mOrganizer) {
        this.mOrganizer = mOrganizer;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmScheduledId() {
        return mScheduledId;
    }

    public void setmScheduledId(String mScheduledId) {
        this.mScheduledId = mScheduledId;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmMeetingId() {
        return mMeetingId;
    }

    public void setmMeetingId(String mMeetingId) {
        this.mMeetingId = mMeetingId;
    }

    public String getmLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setmLastUpdateTime(String mLastUpdateTime) {
        this.mLastUpdateTime = mLastUpdateTime;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmFoucusUri() {
        return mFoucusUri;
    }

    public void setmFoucusUri(String mFoucusUri) {
        this.mFoucusUri = mFoucusUri;
    }
}
