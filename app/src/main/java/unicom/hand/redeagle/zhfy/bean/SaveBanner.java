package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-11.
 */

public class SaveBanner {
    private String conferenceRecordId;
    private String confEntity;
    private String conferenceBannerId;
    private String bannerContent;
    private boolean enable;
    private String position;

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
        return conferenceBannerId;
    }

    public void setConferenceBannerId(String conferenceBannerId) {
        this.conferenceBannerId = conferenceBannerId;
    }

    public String getBannerContent() {
        return bannerContent;
    }

    public void setBannerContent(String bannerContent) {
        this.bannerContent = bannerContent;
    }

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
}
