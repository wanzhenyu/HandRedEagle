package unicom.hand.redeagle.zhfy.bean;


public class ItemBean extends BaseBean {
	private String name;
	private int icon;
	private Boolean isSelect;//是否选择显示
	private Boolean isMain;//是否在主界面
	private String sqlString;//查询标识
	private Boolean isLongBoolean = false;//是否在主界面
	private String code="";//查询码
	private Boolean isAdd = false;//是否是新添加
	private Integer HasChildren;//是否有下级
	private String Order="";//是否有下级

	private String parentCode = "";

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getOrder() {
		return Order;
	}

	public void setOrder(String order) {
		Order = order;
	}

	public Integer getHasChildren() {
		return HasChildren;
	}

	public void setHasChildren(Integer hasChildren) {
		HasChildren = hasChildren;
	}

	public String getCode() {
		return code;
	}

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsLongBoolean() {
		return isLongBoolean;
	}

	public void setIsLongBoolean(Boolean isLongBoolean) {
		this.isLongBoolean = isLongBoolean;
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int itemFwmdm) {
		this.icon = itemFwmdm;
	}

	public Boolean getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}



}
