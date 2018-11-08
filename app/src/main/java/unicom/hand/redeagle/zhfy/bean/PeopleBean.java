package unicom.hand.redeagle.zhfy.bean;

import java.io.Serializable;

/**
 * Created by linana on 2018-07-01.
 */

public class PeopleBean implements Serializable{
    public String type;
    public String uid;
    public String role;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
