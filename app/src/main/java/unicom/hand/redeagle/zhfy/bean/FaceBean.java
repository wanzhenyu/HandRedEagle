package unicom.hand.redeagle.zhfy.bean;

import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wzy on 17/11/7.
 */
@Table(name = "t_Directory")
public class FaceBean {
    private String id;
    private String Code;
    private String ParentCode;
    private String Remarks;
    private String Valid;
    private String Company;
    private String CalledNum;
    private String CallingNum;
    private Integer Stars;
    private Integer Sort;
    private String Category;
    private String CalledName;
    private String CalledPassword;
    private String CallingPassword;
    private Integer Position;
    private String CreateId;
    private String CreateTime;
    private String Creator;
    private String Detail;
    private Integer HasChildren;//是否有下级
    private Integer Expanding;//是否展开


    public Integer getPosition() {
        return Position;
    }

    public void setPosition(Integer position) {
        Position = position;
    }

    public Integer getHasChildren() {
        return HasChildren;
    }

    public void setHasChildren(Integer hasChildren) {
        HasChildren = hasChildren;
    }

    public Integer getExpanding() {
        return Expanding;
    }

    public void setExpanding(Integer expanding) {
        Expanding = expanding;
    }

    public Integer getStars() {
        return Stars;
    }

    public void setStars(Integer stars) {
        Stars = stars;
    }

    public Integer getSort() {
        return Sort;
    }

    public void setSort(Integer sort) {
        Sort = sort;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCalledName() {
        return CalledName;
    }

    public void setCalledName(String calledName) {
        CalledName = calledName;
    }

    public String getCalledPassword() {
        return CalledPassword;
    }

    public void setCalledPassword(String calledPassword) {
        CalledPassword = calledPassword;
    }

    public String getCallingPassword() {
        return CallingPassword;
    }

    public void setCallingPassword(String callingPassword) {
        CallingPassword = callingPassword;
    }


    public String getCreateId() {
        return CreateId;
    }

    public void setCreateId(String createId) {
        CreateId = createId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }


    public String getCalledNum() {
        return CalledNum;
    }

    public void setCalledNum(String calledNum) {
        this.CalledNum = calledNum;
    }

    public String getCallingNum() {
        return CallingNum;
    }

    public void setCallingNum(String callingNum) {
        this.CallingNum = callingNum;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }


    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getValid() {
        return Valid;
    }

    public void setValid(String valid) {
        Valid = valid;
    }


}
