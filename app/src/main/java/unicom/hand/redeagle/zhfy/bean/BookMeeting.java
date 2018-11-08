package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-01.
 */

public class BookMeeting extends BaseBean {

    public String recurrenceType;
    public String conferenceType;
    public String emailRemark;
    public String utcOffset;
    public String patternStartDate;
    public boolean canSaveRecurrencePattern;
    public Integer dstEnable;
    public boolean autoInvite;
    public String conferenceProfile;
    public String name;
    public String conferenceDate;

    public String conferenceStartTime;
    public String conferenceEndDate;
    public String conferenceEndTime;
    public String timeZoneName;
    public String dailyType;
    public String interval;
    public String recurrenceRange;
    public List<PeopleBean> conferenceRoomIds;
    public List<PeopleBean> location;
    public List<PeopleBean> daysOfWeeks;
    public List<PeopleBean> participants;
    public String getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

    public String getEmailRemark() {
        return emailRemark;
    }

    public void setEmailRemark(String emailRemark) {
        this.emailRemark = emailRemark;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getPatternStartDate() {
        return patternStartDate;
    }

    public void setPatternStartDate(String patternStartDate) {
        this.patternStartDate = patternStartDate;
    }

    public boolean isCanSaveRecurrencePattern() {
        return canSaveRecurrencePattern;
    }

    public void setCanSaveRecurrencePattern(boolean canSaveRecurrencePattern) {
        this.canSaveRecurrencePattern = canSaveRecurrencePattern;
    }

    public Integer getDstEnable() {
        return dstEnable;
    }

    public void setDstEnable(Integer dstEnable) {
        this.dstEnable = dstEnable;
    }

    public boolean isAutoInvite() {
        return autoInvite;
    }

    public void setAutoInvite(boolean autoInvite) {
        this.autoInvite = autoInvite;
    }

    public String getConferenceProfile() {
        return conferenceProfile;
    }

    public void setConferenceProfile(String conferenceProfile) {
        this.conferenceProfile = conferenceProfile;
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

    public String getConferenceStartTime() {
        return conferenceStartTime;
    }

    public void setConferenceStartTime(String conferenceStartTime) {
        this.conferenceStartTime = conferenceStartTime;
    }

    public String getConferenceEndDate() {
        return conferenceEndDate;
    }

    public void setConferenceEndDate(String conferenceEndDate) {
        this.conferenceEndDate = conferenceEndDate;
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

    public String getDailyType() {
        return dailyType;
    }

    public void setDailyType(String dailyType) {
        this.dailyType = dailyType;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getRecurrenceRange() {
        return recurrenceRange;
    }

    public void setRecurrenceRange(String recurrenceRange) {
        this.recurrenceRange = recurrenceRange;
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

    public List<PeopleBean> getDaysOfWeeks() {
        return daysOfWeeks;
    }

    public void setDaysOfWeeks(List<PeopleBean> daysOfWeeks) {
        this.daysOfWeeks = daysOfWeeks;
    }

    public List<PeopleBean> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PeopleBean> participants) {
        this.participants = participants;
    }
}
