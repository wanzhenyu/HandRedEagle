package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-11.
 */

public class EnableHylcBean {
    private String conferenceRecordId;
    private String confEntity;
    private String conferenceProcessId;
    private boolean enable;

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

    public String getConferenceBannerId() {
        return conferenceProcessId;
    }

    public void setConferenceBannerId(String conferenceBannerId) {
        this.conferenceProcessId = conferenceBannerId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
