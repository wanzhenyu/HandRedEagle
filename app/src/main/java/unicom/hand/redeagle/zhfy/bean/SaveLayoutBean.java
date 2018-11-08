package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-10.
 */

public class SaveLayoutBean {

    private String conferenceRecordId;
    private String confEntity;
    private String conferenceLayoutId;
    private String layoutType;
    private int layoutMaxView;
//    private String uid;
//    private String userName;
//    private String name;
//    private String userEntity;
    private boolean enablePolling;
    private String pollingType;
    private int pollingNumber;
    private int pollingTimeInterval;
    private boolean enableSpeechExcitation;
    private int speechExcitationTime;
    private boolean osdEnable;
    private layoutUser layoutUser;

//    public List<unicom.hand.redeagle.zhfy.bean.layoutUser> getLayoutUser() {
//        return layoutUser;
//    }
//
//    public void setLayoutUser(List<unicom.hand.redeagle.zhfy.bean.layoutUser> layoutUser) {
//        this.layoutUser = layoutUser;
//    }
    //
    public unicom.hand.redeagle.zhfy.bean.layoutUser getLayoutUser() {
        return layoutUser;
    }

    public void setLayoutUser(unicom.hand.redeagle.zhfy.bean.layoutUser layoutUser) {
        this.layoutUser = layoutUser;
    }

    public String getConferenceRecordId() {
        return conferenceRecordId;
    }

    public void setConferenceRecordId(String conferenceRecordId) {
        this.conferenceRecordId = conferenceRecordId;
    }

    public String getConfEntity() {
        return confEntity;
    }

    public void setConfEntity(String confEntity) {
        this.confEntity = confEntity;
    }

    public String getConferenceLayoutId() {
        return conferenceLayoutId;
    }

    public void setConferenceLayoutId(String conferenceLayoutId) {
        this.conferenceLayoutId = conferenceLayoutId;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public int getLayoutMaxView() {
        return layoutMaxView;
    }

    public void setLayoutMaxView(int layoutMaxView) {
        this.layoutMaxView = layoutMaxView;
    }

//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getUserEntity() {
//        return userEntity;
//    }
//
//    public void setUserEntity(String userEntity) {
//        this.userEntity = userEntity;
//    }

    public boolean isEnablePolling() {
        return enablePolling;
    }

    public void setEnablePolling(boolean enablePolling) {
        this.enablePolling = enablePolling;
    }

    public String getPollingType() {
        return pollingType;
    }

    public void setPollingType(String pollingType) {
        this.pollingType = pollingType;
    }

    public int getPollingNumber() {
        return pollingNumber;
    }

    public void setPollingNumber(int pollingNumber) {
        this.pollingNumber = pollingNumber;
    }

    public int getPollingTimeInterval() {
        return pollingTimeInterval;
    }

    public void setPollingTimeInterval(int pollingTimeInterval) {
        this.pollingTimeInterval = pollingTimeInterval;
    }

    public boolean isEnableSpeechExcitation() {
        return enableSpeechExcitation;
    }

    public void setEnableSpeechExcitation(boolean enableSpeechExcitation) {
        this.enableSpeechExcitation = enableSpeechExcitation;
    }

    public int getSpeechExcitationTime() {
        return speechExcitationTime;
    }

    public void setSpeechExcitationTime(int speechExcitationTime) {
        this.speechExcitationTime = speechExcitationTime;
    }

    public boolean isOsdEnable() {
        return osdEnable;
    }

    public void setOsdEnable(boolean osdEnable) {
        this.osdEnable = osdEnable;
    }
}
