package unicom.hand.redeagle.zhfy.bean;

import java.io.Serializable;


/**
 * Created by linana on 2018-03-15.
 */

public   class MyNodeBean implements Serializable {

    public String name;
    public String nodeId;
    public String serialNumber;
    public boolean isCheck;
    public Integer type;
    public String uid;
    public String userentity;
//    public String userName;
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserentity() {
        return userentity;
    }

    public void setUserentity(String userentity) {
        this.userentity = userentity;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }



}
