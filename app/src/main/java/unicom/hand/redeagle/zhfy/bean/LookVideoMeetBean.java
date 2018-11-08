package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-14.
 */

public class LookVideoMeetBean {
    private String conferenceRecordId;
    private String conferenceType;
    private String subject;
    private String enterpriseId;
    private String conferenceId;
    private String conferenceNumber;
    private String organizerId;
    private String organizerName;
    private int state;
    private boolean isPresenter;
    private String conferencePlanId;
    private String pinCode;
    private boolean active;
    private boolean isIndependent;
    private String conferenceDate;
    private String conferenceEndDate;
    private String conferenceStartTime;
    private String conferenceEndTime;
    private String profile;
    private int dstEnable;

    private boolean autoInvite;
    public List<Oneparticipants> participants;

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

    public boolean isPresenter() {
        return isPresenter;
    }

    public void setPresenter(boolean presenter) {
        isPresenter = presenter;
    }

    public String getConferencePlanId() {
        return conferencePlanId;
    }

    public void setConferencePlanId(String conferencePlanId) {
        this.conferencePlanId = conferencePlanId;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isIndependent() {
        return isIndependent;
    }

    public void setIndependent(boolean independent) {
        isIndependent = independent;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getDstEnable() {
        return dstEnable;
    }

    public void setDstEnable(int dstEnable) {
        this.dstEnable = dstEnable;
    }

    public boolean isAutoInvite() {
        return autoInvite;
    }

    public void setAutoInvite(boolean autoInvite) {
        this.autoInvite = autoInvite;
    }

    public List<Oneparticipants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Oneparticipants> participants) {
        this.participants = participants;
    }
}
