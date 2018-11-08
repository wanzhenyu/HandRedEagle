package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-06.
 */

public class EditMeetBean {
    public String conferencePlanId;
    public String name;
    public String conferenceDate;
    public String conferenceEndDate;
    public String conferenceStartTime;
    public String conferenceEndTime;
    public String timeZoneName;
    public int utcOffset;
    public int recurrenceRange;
    public String recurrenceType;
    public String conferenceProfile;
    public List<PeopleBean> participants;
    public List<PeopleBean> daysOfWeeks;
    private String interval;
    private String dailyType;
    public String conferenceType;
    public List<PeopleBean> conferenceRoomIds;
    public List<PeopleBean> location;
    public String emailRemark;
    public int dstEnable;

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

    public List<PeopleBean> getConferenceRoomIds() {
        return conferenceRoomIds;
    }

    public void setConferenceRoomIds(List<PeopleBean> conferenceRoomIds) {
        this.conferenceRoomIds = conferenceRoomIds;
    }

    public List<PeopleBean> getLocation() {
        return location;
    }

    public void setLocation(List<PeopleBean> location) {
        this.location = location;
    }

    public String getEmailRemark() {
        return emailRemark;
    }

    public void setEmailRemark(String emailRemark) {
        this.emailRemark = emailRemark;
    }

    public List<PeopleBean> getDaysOfWeeks() {
        return daysOfWeeks;
    }

    public void setDaysOfWeeks(List<PeopleBean> daysOfWeeks) {
        this.daysOfWeeks = daysOfWeeks;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getDailyType() {
        return dailyType;
    }

    public void setDailyType(String dailyType) {
        this.dailyType = dailyType;
    }

    public String getConferencePlanId() {
        return conferencePlanId;
    }

    public void setConferencePlanId(String conferencePlanId) {
        this.conferencePlanId = conferencePlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(String conferenceDate) {
        this.conferenceDate = conferenceDate;
    }

    public String getConferenceEndDate() {
        return conferenceEndDate;
    }

    public void setConferenceEndDate(String conferenceEndDate) {
        this.conferenceEndDate = conferenceEndDate;
    }

    public String getConferenceStartTime() {
        return conferenceStartTime;
    }

    public void setConferenceStartTime(String conferenceStartTime) {
        this.conferenceStartTime = conferenceStartTime;
    }

    public String getConferenceEndTime() {
        return conferenceEndTime;
    }

    public void setConferenceEndTime(String conferenceEndTime) {
        this.conferenceEndTime = conferenceEndTime;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }

    public int getRecurrenceRange() {
        return recurrenceRange;
    }

    public void setRecurrenceRange(int recurrenceRange) {
        this.recurrenceRange = recurrenceRange;
    }

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public String getConferenceProfile() {
        return conferenceProfile;
    }

    public void setConferenceProfile(String conferenceProfile) {
        this.conferenceProfile = conferenceProfile;
    }

    public List<PeopleBean> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PeopleBean> participants) {
        this.participants = participants;
    }

    public int getDstEnable() {
        return dstEnable;
    }

    public void setDstEnable(int dstEnable) {
        this.dstEnable = dstEnable;
    }
}
