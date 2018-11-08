package unicom.hand.redeagle.zhfy.bean;

import java.io.Serializable;

/**
 * Created by linana on 2018-07-12.
 */

public class PeopleOnLineBean implements Serializable{
    private String id;
    private String name;
    private String namePinyin;
    private String namePinyinAlia;
    private String pId;
    private String type;
    private String email;
    private int index;
    private String phone;
    private long modifyTime;
    private String entity;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getNamePinyinAlia() {
        return namePinyinAlia;
    }

    public void setNamePinyinAlia(String namePinyinAlia) {
        this.namePinyinAlia = namePinyinAlia;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
