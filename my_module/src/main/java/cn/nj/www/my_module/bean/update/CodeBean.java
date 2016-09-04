package cn.nj.www.my_module.bean.update;

import java.io.Serializable;

public class CodeBean implements Serializable {
	private String codenum;
	private String codename;
	private String message;
	private String url;

	public String getCodenum() {
		return codenum;
	}

	public void setCodenum(String codenum) {
		this.codenum = codenum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CodeBean() {

	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public CodeBean(String codenum, String codename, String message, String url) {
		this.codenum = codenum;
		this.codename = codename;
		this.message = message;
		this.url = url;
	}
}
