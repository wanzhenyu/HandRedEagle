package unicom.hand.redeagle.zhfy.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linana on 2018-07-05.
 */

public class MeetDetailListBean {
    public String currentTimeMills;
    public String confId;
    public Integer confType;
    public String confEntity;
    public String subject;
    public String confNumber;
    public String pinCode;
    public long startTime;
    public long expiryTime;
    public String organizerId;
    public CurrentOperator currentOperator;
    public List<Participants> participants;

    public class CurrentOperator{
        public String uid;
        public String userEntity;
        public String name;
        public String role;
        public String userName;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class Participants implements Serializable {
        public String _id;
        public String name;
        public String permission;
        public String type;
        public String userType;
        public String entity;
        public String phone;
        public String accountType;

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }

    public String getCurrentTimeMills() {
        return currentTimeMills;
    }

    public void setCurrentTimeMills(String currentTimeMills) {
        this.currentTimeMills = currentTimeMills;
    }

    public String getConfId() {
        return confId;
    }

    public void setConfId(String confId) {
        this.confId = confId;
    }

    public Integer getConfType() {
        return confType;
    }

    public void setConfType(Integer confType) {
        this.confType = confType;
    }

    public String getConfEntity() {
        return confEntity;
    }

    public void setConfEntity(String confEntity) {
        this.confEntity = confEntity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getConfNumber() {
        return confNumber;
    }

    public void setConfNumber(String confNumber) {
        this.confNumber = confNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public CurrentOperator getCurrentOperator() {
        return currentOperator;
    }

    public void setCurrentOperator(CurrentOperator currentOperator) {
        this.currentOperator = currentOperator;
    }

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }
}
