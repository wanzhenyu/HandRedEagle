package unicom.hand.redeagle.zhfy.bean;

import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wzy on 17/11/7.
 */
@Table(name = "t_Directory_2018_1127")
public class FaceBean {
    private String id;
    private int areaId;
    private String areaCode;
    private String parentCode;
    private String calledName;
    private String calledNum;
    private String calledPassword;
    private String callingNum;
    private String callingPassword;
    private Integer stars;
    private Integer sort;
    private String name;
    private Integer hasChildren;
    private Integer valid;
    private Integer expanding;
    private String category;
    private Integer layer;
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCalledName() {
        return calledName;
    }

    public void setCalledName(String calledName) {
        this.calledName = calledName;
    }

    public String getCalledNum() {
        return calledNum;
    }

    public void setCalledNum(String calledNum) {
        this.calledNum = calledNum;
    }

    public String getCalledPassword() {
        return calledPassword;
    }

    public void setCalledPassword(String calledPassword) {
        this.calledPassword = calledPassword;
    }

    public String getCallingNum() {
        return callingNum;
    }

    public void setCallingNum(String callingNum) {
        this.callingNum = callingNum;
    }

    public String getCallingPassword() {
        return callingPassword;
    }

    public void setCallingPassword(String callingPassword) {
        this.callingPassword = callingPassword;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCompany() {
        return name;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getExpanding() {
        return expanding;
    }

    public void setExpanding(Integer expanding) {
        this.expanding = expanding;
    }

    public void setCompany(String name) {
        this.name = name;
    }

    public Integer getHasChildren() {
        return hasChildren;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHasChildren(Integer hasChildren) {
        this.hasChildren = hasChildren;
    }

}
