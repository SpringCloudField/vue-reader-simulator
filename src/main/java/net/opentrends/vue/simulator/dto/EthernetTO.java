package net.opentrends.vue.simulator.dto;

public class EthernetTO {
		private int dhcp;
		private String gateway;
		private String ip;
		private String netmask;
	
		public int getDhcp() {
			return dhcp;
		}
		public void setDhcp(int dhcp) {
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


}
