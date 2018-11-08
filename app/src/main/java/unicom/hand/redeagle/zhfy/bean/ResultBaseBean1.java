package unicom.hand.redeagle.zhfy.bean;

public class ResultBaseBean1 extends BaseBean{
	
	private Integer code ;
	private Object data ;
	private String msg ;

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
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
