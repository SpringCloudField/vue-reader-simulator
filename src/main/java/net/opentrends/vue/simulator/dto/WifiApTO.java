package net.opentrends.vue.simulator.dto;

public class WifiApTO {
	
	private String ip;
	private String netmask;
	private String pwd;
	private String Ssid;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSsid() {
		return Ssid;
	}

	public void setSsid(String ssid) {
		Ssid = ssid;
	}

}
