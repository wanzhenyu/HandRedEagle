package unicom.hand.redeagle.zhfy.bean;

public class ResultBaseBean extends BaseBean{

	private Integer code ;
	private Object update ;
	private Object delete ;
	private Object rows ;
	private String msg ;

	public Object getUpdate() {
		return update;
	}

	public void setUpdate(Object update) {
		this.update = update;
	}

	public Object getDelete() {
		return delete;
	}

	public void setDelete(Object delete) {
		this.delete = delete;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return rows;
	}

	public void setData(Object data) {
		this.rows = data;
	}
}
