package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-19.
 */

public class OneMeetDetailBean {
    private String conferenceRecordId;
    private String conferenceType;
    private String subject;
    private String enterpriseId;
    private String conferenceId;
    private String conferenceNumber;
    private String organizerId;
    private String organizerName;
    private int state;
    private String conferencePwd;
    private List<ParticipantBean> participants;
    private String conferencePlanId;
    private String conferenceDate;
    private String conferenceEndDate;


    private String conferenceStartTime;
    private String conferenceEndTime;
    private String emailRemark;
    private String profile;

    private String startTime;
    private String expiryTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getConferenceRecordId() {
        return conferenceRecordId;
    }

    public void setConferenceRecordId(String conferenceRecordId) {
        this.conferenceRecordId = conferenceRecordId;
    }

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getConferenceNumber() {
        return conferenceNumber;
    }

    public void setConferenceNumber(String conferenceNumber) {
        this.conferenceNumber = conferenceNumber;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getConferencePwd() {
        return conferencePwd;
    }

    public void setConferencePwd(String conferencePwd) {
        this.conferencePwd = conferencePwd;
    }

    public List<ParticipantBean> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantBean> participants) {
        this.participants = participants;
    }

    public String getConferencePlanId() {
        return conferencePlanId;
    }

    public void setConferencePlanId(String conferencePlanId) {
        this.conferencePlanId = conferencePlanId;
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

    public String getEmailRemark() {
        return emailRemark;
    }

    public void setEmailRemark(String emailRemark) {
        this.emailRemark = emailRemark;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
