package jp.co.touyouhk.sutemaburststream.model;

import java.util.List;

public class ZweiConf {

	private String itaName;
	private String url;
	private List <String> keyWords;

	public ZweiConf() {
	}
	public String getItaName() {
		return itaName;
	}
	public void setItaName(String itaName) {
		this.itaName = itaName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}


}
