package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-04.
 */

public class HyrcBeanMd5 extends BaseBean {
    private String conferenceId;
    private String resourceId;
    private String start;
    private String end;
    private String title;
    private String confType;
    private String isPresenter;
    private Integer state;
    private String conferencePlanId;

    public String getId() {
        return conferenceId;
    }

    public void setId(String id) {
        this.conferenceId = conferenceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConfType() {
        return confType;
    }

    public void setConfType(String confType) {
        this.confType = confType;
    }

    public String getIsPresenter() {
        return isPresenter;
    }

    public void setIsPresenter(String isPresenter) {
        this.isPresenter = isPresenter;
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
}
