package unicom.hand.redeagle.zhfy.bean;

/**
 * Created by linana on 2018-07-19.
 */

public class ParticipantBean {
    private String uid;
    private String userEntity;
    private String displayText;
    private String username;
    private String permission;
    private String userType;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(String userEntity) {
        this.userEntity = userEntity;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
