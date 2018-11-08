package unicom.hand.redeagle.zhfy.bean;

import java.io.Serializable;

/**
 * Created by linana on 2018-07-13.
 */

public class layoutUser implements Serializable{
    private String uid;
    private String userName;
    private String name;
    private String userEntity;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(String userEntity) {
        this.userEntity = userEntity;
    }
}
