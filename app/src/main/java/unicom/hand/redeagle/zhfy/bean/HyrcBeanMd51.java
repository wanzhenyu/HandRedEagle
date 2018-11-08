package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-04.
 */

public class HyrcBeanMd51 extends BaseBean {
    private String subject;
    private String conferenceId;
    private String organizerName;
    private String organizerId;
    private int confType;
    private Integer state;
    private String conferencePlanId;
    private String conferenceRecordId;
    private String conferenceNumber;
    private String startTime;
    private String expiryTime;
    private String pinCode;

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getConferenceNumber() {
        return conferenceNumber;
    }

    public void setConferenceNumber(String conferenceNumber) {
        this.conferenceNumber = conferenceNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public int getConfType() {
        return confType;
    }

    public void setConfType(int confType) {
        this.confType = confType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getConferencePlanId() {
        return conferencePlanId;
    }

    public void setConferencePlanId(String conferencePlanId) {
        this.conferencePlanId = conferencePlanId;
    }

    public String getConferenceRecordId() {
        return conferenceRecordId;
    }

    public void setConferenceRecordId(String conferenceRecordId) {
        this.conferenceRecordId = conferenceRecordId;
    }

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
}
