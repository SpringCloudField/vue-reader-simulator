package net.opentrends.vue.simulator.dto;

public class WifiStationTO {
	
	private Boolean dhcp;
	private String gateway;
	private String ip;
	private String netmask;
	private String pwd;
	private String ssid;
	
	public Boolean getDhcp() {
		return dhcp;
	}
	public void setDhcp(Boolean dhcp) {
		this.dhcp = dhcp;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
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
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

}
