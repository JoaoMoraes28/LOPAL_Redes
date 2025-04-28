package br.com.joao.iot.model;

public class Ip {
	
	private String ip;
	private String mask;
	private String maskBinary;
	private double ipQuantd;
	private String cidr;
	private String clas;

	
	public String getIp() {
		return ip;
	}
	
	public String setIp(String ip) {
		this.ip = ip;
		return ip;
	}
	
	public String getMask() {
		return mask;
	}
	
	public void setMask(String mask) {
		this.mask = mask;
	}
	
	public String getMaskBinary() {
		return maskBinary;
	}
	
	public void setMaskBinary(String maskBinary) {
		this.maskBinary = maskBinary;
	}
	
	public double getIpQuantd() {
		return ipQuantd;
	}
	
	public void setIpQuantd(double ipQuantd) {
		this.ipQuantd = ipQuantd;
	}
	
	public String getCidr() {
		return cidr;
	}
	
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public String getClas() {
		return clas;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}
	
	public String extractCidr() {
		cidr = ip.substring(ip.length() - 2);
		return cidr;
	}
	
	public String extractClas() {
		clas = ip.substring(0 , 3);
		return clas;
	}
	
}
