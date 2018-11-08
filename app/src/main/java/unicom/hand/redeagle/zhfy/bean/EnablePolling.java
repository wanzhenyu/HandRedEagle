package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-13.
 */

public class EnablePolling {
    private String conferenceRecordId;
    private String confEntity;
    private String conferenceLayoutId;
    private String layoutId;
    private boolean enablePolling;

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

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public boolean isEnablePolling() {
        return enablePolling;
    }

    public void setEnablePolling(boolean enablePolling) {
        this.enablePolling = enablePolling;
    }
}
