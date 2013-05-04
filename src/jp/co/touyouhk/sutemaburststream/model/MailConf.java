package jp.co.touyouhk.sutemaburststream.model;

public class MailConf {

	public String getSmtp_server() {
		return smtp_server;
	}
	public void setSmtp_server(String smtp_server) {
		this.smtp_server = smtp_server;
	}
	public int getSmtp_port() {
		return smtp_port;
	}
	public void setSmtp_port(int smtp_port) {
		this.smtp_port = smtp_port;
	}
	public boolean isSmtp_ssl_flag() {
		return smtp_ssl_flag;
	}
	public void setSmtp_ssl_flag(boolean smtp_ssl_flag) {
		this.smtp_ssl_flag = smtp_ssl_flag;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSrc_mail_adress() {
		return src_mail_adress;
	}
	public void setSrc_mail_adress(String src_mail_adress) {
		this.src_mail_adress = src_mail_adress;
	}
	public String getDest_mail_adress() {
		return dest_mail_adress;
	}
	public void setDest_mail_adress(String dest_mail_adress) {
		this.dest_mail_adress = dest_mail_adress;
	}
	private String smtp_server;
	private int smtp_port;
	private boolean smtp_ssl_flag;
	private String user_name;
	private String password;
	private String src_mail_adress;
	private String dest_mail_adress;
}
