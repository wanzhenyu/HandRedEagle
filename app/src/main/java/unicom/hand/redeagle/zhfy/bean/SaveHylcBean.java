package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-11.
 */

public class SaveHylcBean {

    private String conferenceRecordId;
    private String confEntity;
    private String conferenceProcessId;
    private boolean enable;
    private List<ProcedureInfos> procedureInfos;


//    private String id;
//    private String displayText;
//    private boolean active;

    public List<ProcedureInfos> getProcedureInfos() {
        return procedureInfos;
    }

    public void setProcedureInfos(List<ProcedureInfos> procedureInfos) {
        this.procedureInfos = procedureInfos;
    }

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

    public String getConferenceProcessId() {
        return conferenceProcessId;
    }

    public void setConferenceProcessId(String conferenceProcessId) {
        this.conferenceProcessId = conferenceProcessId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getDisplayText() {
//        return displayText;
//    }
//
//    public void setDisplayText(String displayText) {
//        this.displayText = displayText;
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
}
