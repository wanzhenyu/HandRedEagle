package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-09.
 */

public class LockBean {
    public String confEntity;
    public String confId;
//    public List<String> userEntities;
    public boolean lock;

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
        return lock;
    }

    public void setBlock(boolean block) {
        this.lock = block;
    }
}
