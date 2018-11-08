package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-09.
 */

public class MuteBean {
    public String confEntity;
    public String confId;
//    public List<String> userEntities;
    public boolean block;

    public String getConfEntity() {
        return confEntity;
    }

    public void setConfEntity(String confEntity) {
        this.confEntity = confEntity;
    }

    public String getConfId() {
        return confId;
    }

    public void setConfId(String confId) {
        this.confId = confId;
    }

//    public List<String> getUserEntities() {
//        return userEntities;
//    }
//
//    public void setUserEntities(List<String> userEntities) {
//        this.userEntities = userEntities;
//    }

    public boolean getBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
