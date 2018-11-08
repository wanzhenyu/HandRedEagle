package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-11.
 */

public class SaveSubTitleBean {
    private String conferenceRecordId;
    private String confEntity;
    private String conferenceSubtitleId;
    private String subtitleContent;
    private String subtitleType;
    private int repeatTimes;
    private int intervalTime;
    private int durationTime;
    private List<layoutUser> userList;

    public List<layoutUser> getUserList() {
        return userList;
    }

    public void setUserList(List<layoutUser> userList) {
        this.userList = userList;
    }

    //    private String uid;
//    private String userName;
//    private String name;
//    private String userEntity;
    private boolean enable;
    private String position;
    private String rollDirection;

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

    public String getConferenceSubtitleId() {
        return conferenceSubtitleId;
    }

    public void setConferenceSubtitleId(String conferenceSubtitleId) {
        this.conferenceSubtitleId = conferenceSubtitleId;
    }

    public String getSubtitleContent() {
        return subtitleContent;
    }

    public void setSubtitleContent(String subtitleContent) {
        this.subtitleContent = subtitleContent;
    }

    public String getSubtitleType() {
        return subtitleType;
    }

    public void setSubtitleType(String subtitleType) {
        this.subtitleType = subtitleType;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRollDirection() {
        return rollDirection;
    }

    public void setRollDirection(String rollDirection) {
        this.rollDirection = rollDirection;
    }
}
