package br.com.joao.iot.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ip {

	// Variables of class
	private String ip;
	private int secondOctet;
	private int thirdOctet;
	private String mask;
	private String maskBinary;
	private int ipQuantd;
	private String cidr;
	private String clas;
	private String[] networkIp;
	private String[] broadcastingIp;
	private String[] resultNetworkBroadcasting;
	private String[] allIpHostNetwork;
	private String[] allIpHostBroadcasting;
	private int[] ipHostNetwork;
	private int[] ipHostBroadcasting;
	private int restCidr;
	// Variable used to activate the error message, if there is one
	private String error = "";

	public int getRestCidr() {
		return restCidr;
	}

	// Method set of IP
	public String setIp(String ip) {

		// Regex for finder values of IP
		String firstRegex = "\\.(.*?)\\.";
		String secondRegex = "^(?:[^.]*\\.){2}([^.]*)";
		String thirdRegex = "^(?:[^.]*\\.){3}([^/]*)";

		// Pattern's and Matcher's
		Pattern firstPattern = Pattern.compile(firstRegex);
		Matcher firstMatcher = firstPattern.matcher(ip);

		Pattern secondPattern = Pattern.compile(secondRegex);
		Matcher secondMatcher = secondPattern.matcher(ip);

		Pattern thirdPattern = Pattern.compile(thirdRegex);
		Matcher thirdMatcher = thirdPattern.matcher(ip);

		// Try and catch for validation of values of matcher's
		try {

			if (firstMatcher.find()) {
				int firstMatcherInt = Integer.parseInt(firstMatcher.group(1));
				secondOctet = firstMatcherInt;

				if (firstMatcherInt < 0 || firstMatcherInt > 255) {
					error = "x";
				}
			}

			if (secondMatcher.find()) {
				int secondMatcherInt = Integer.parseInt(secondMatcher.group(1));
				thirdOctet = secondMatcherInt;

				if (secondMatcherInt < 0 || secondMatcherInt > 255) {
					error = "x";
				}
			}

			if (thirdMatcher.find()) {
				int thirdMatcherInt = Integer.parseInt(thirdMatcher.group(1));
				if (thirdMatcherInt < 0 || thirdMatcherInt > 255) {
					error = "x";
				}
			}

		} catch (Exception e) {
			error = "x";
		}

		this.ip = ip;
		return ip;
	}

	public String getError() {
		return error;
	}

	public int getCidr() {
		int cidrInt = 0;

		try {
			cidrInt = Integer.parseInt(cidr);
		} catch (Exception e) {
			error = "x";
		}

		return cidrInt;
	}

	public String[] getResultNetworkBroadcasting() {
		return resultNetworkBroadcasting;
	}

	// Function for get the CIDR of IP
	public String extractCidr() {
		cidr = ip.substring(ip.length() - 2);
		return cidr;
	}

	// Function for get the three first character of IP and get the class
	public String extractClas() {
		clas = ip.substring(0, 3);
		String clasOctet = clas;
		return clasOctet;
	}

	public void defineIpClass() {

		// Variable contend the three first digits without "."
		clas = clas.replace(".", "");

		// Block utilized for determine if entrance of data this correct
		try {

			// Variable responsible for transform of String for int
			int clasInt = Integer.parseInt(clas);

			// If and Else utilized for defined the class of IP
			if (clasInt >= 1 && clasInt <= 127) {
				clas = "A";

			} else if (clasInt >= 128 && clasInt <= 191) {
				clas = "B";

			} else if (clasInt >= 192 && clasInt <= 223) {
				clas = "C";

			} else if (clasInt >= 224 && clasInt <= 239) {
				clas = "D";

			} else if (clasInt >= 240 && clasInt <= 255) {
				clas = "E";

			} else {
				error = "x";
			}

			defineMaskAndMaskBinary();

		} catch (Exception e) {

			error = "x";
		}

	}

	public void defineMaskAndMaskBinary() {

		// Variable responsible for transform of String for int
		restCidr = getCidr();
		;

		if (getCidr() < 0 || getCidr() > 32) {
			error = "x";
		}

		// Variable contend the keys binary for determine the sub-net
		int[] binary = { 128, 64, 32, 16, 8, 4, 2, 1 };

		// While utilized for extract the number multiplier of sub-net
		while (restCidr >= 8) {
			restCidr -= 8;
		}

		int cidrDec = 0;
		int i = 0;

		// If and Else for define if the mask will 255 or another number
		if (restCidr > 0) {

			// While responsible for calculation of sub-mask
			while (i < restCidr) {
				cidrDec += binary[i];
				i++;
			}
		} else {
			cidrDec = 255;

		}

		int mask255 = 255;
		String mask255Binary = Integer.toBinaryString(mask255);
		String cidrBinary = Integer.toBinaryString(cidrDec);

		// If's and Else's that will assemble the mask in decimal and binary completely
		if (getCidr() <= 8) {
			mask = cidrDec + "." + "0" + "." + "0" + "." + "0";

		} else if (getCidr() > 8 && getCidr() <= 16) {
			mask = mask255 + "." + cidrDec + "." + "0" + "." + "0";

		} else if (getCidr() > 16 && getCidr() <= 24) {
			mask = mask255 + "." + mask255 + "." + cidrDec + "." + "0";

		} else if (getCidr() > 24 && getCidr() <= 32) {
			mask = mask255 + "." + mask255 + "." + mask255 + "." + cidrDec;

		}

		if (getCidr() <= 8) {
			maskBinary = cidrBinary + " . " + "00000000" + " . " + "00000000" + " . " + "00000000";

		} else if (getCidr() > 8 && getCidr() <= 16) {
			maskBinary = mask255Binary + " . " + cidrBinary + " . " + "00000000" + " . " + "00000000";

		} else if (getCidr() > 16 && getCidr() <= 24) {
			maskBinary = mask255Binary + " . " + mask255Binary + " . " + cidrBinary + " . " + "00000000";

		} else if (getCidr() > 24 && getCidr() <= 32) {
			maskBinary = mask255Binary + " . " + mask255Binary + " . " + mask255Binary + " . " + cidrBinary;

		}

		calculateIp();
	}

	public void calculateIp() {

		// Calculation for get the quantity of IP's available and conversation of Double for int
		double quantdDouble = (Math.pow(2, 32 - getCidr()) - 2);
		int quantdInt = (int) quantdDouble;

		if (quantdInt < 0) {
			ipQuantd = 0;
		} else {
			ipQuantd = quantdInt;
		}

	}

	public String[] assembleResultVector() {

		// Vector that will store all result and print in JList
		String[] result = null;

		if (error.equals("")) {
			result = new String[] {

					"A classe do IP " + ip + " é: " + clas, "", "A máscara é: " + mask, "",
					"A quantidade de hosts disponíveis por rede é igual a: " + ipQuantd, "",
					"A máscara deste IP em binário é: " + maskBinary };

		} else {
			result = new String[] {

					""

			};
		}

		return result;
	}

	public void calculateNetwork() {

		// Variable contend the keys binary for determine the sub-net
		int[] binary = { 2, 4, 8, 16, 32, 64, 128 };

		// If and Else for made the list of IP's of network of Sub-net or not
		if (restCidr == 0) {

		} else {
			// Int to obtain the divisor of 256
			int divisor = +binary[restCidr - 1];
			networkIp = new String[divisor];

			// Int containing the quantity of IP's by sub-net
			int quantIpNetwork = 256 / divisor;
			int i = 0;
			ipHostNetwork = new int[divisor];

			// While to put the IPs in the array
			while (i < divisor) {
				int result = quantIpNetwork * i;
				networkIp[i] = String.format("%s.%s.%s.%s", extractClas(), secondOctet, thirdOctet, result);
				ipHostNetwork[i] = result;

				i++;
			}
		}

	}

	public void calculateBroadcasting() {

		// Variable contend the keys binary for determine the sub-net
		int[] binary = { 2, 4, 8, 16, 32, 64, 128 };

		// If and Else for made the list of IP's of broadcasting of Sub-net or not
		if (restCidr == 0) {

		} else {
			// Int to obtain the divisor of 256
			int divisor = +binary[restCidr - 1];
			broadcastingIp = new String[divisor];

			// Int containing the quantity of IP's by sub-net
			int quantIpBroadcasting = 256 / divisor;
			int i = 0;
			int im = 1;
			ipHostBroadcasting = new int[divisor];

			// While to put the IPs in the array
			while (i < divisor) {
				int result = quantIpBroadcasting * im - 1;
				broadcastingIp[i] = String.format("%s.%s.%s.%s", extractClas(), secondOctet, thirdOctet, result);
				ipHostBroadcasting[i] = result;

				i++;
				im++;
			}
		}

	}

	public void calculateIpAvaliableHostNetwork() {
		int i = 0;

		while (i < networkIp.length) {
			ipHostNetwork[i]+=1;

			i++;
		}

		allIpHostNetwork = new String[i];

		i = 0;
		while (i < allIpHostNetwork.length) {
			allIpHostNetwork[i] = String.format("%s.%s.%s.%s", extractClas(), secondOctet, thirdOctet, ipHostNetwork[i]);

			i++;
		}

	}
	
	public void calculateIpAvaliableHostBroacasting() {
		int i = 0;

		while (i < broadcastingIp.length) {
			ipHostBroadcasting[i]--;

			i++;
		}

		allIpHostBroadcasting = new String[i];

		i = 0;
		while (i < allIpHostBroadcasting.length) {
			allIpHostBroadcasting[i] = String.format("%s.%s.%s.%s", extractClas(), secondOctet, thirdOctet, ipHostBroadcasting[i]);

			i++;
		}

	}

	public void assembleSubnetResultVector() {

		if (restCidr == 0) {

		} else {
			int vector = 0;
			int i = 0;
			int numberIp = 1;

			resultNetworkBroadcasting = new String[networkIp.length * 5];

			// While to put the sub-net, IP of network and IP of broadcasting in array
			while (i < networkIp.length * 5) {
				resultNetworkBroadcasting[i] = String.format("Subnet número %s", numberIp);
				resultNetworkBroadcasting[i + 1] = String.format("IP de rede: %s", networkIp[vector]);
				resultNetworkBroadcasting[i + 2] = String.format("IP de broadcasting: %s", broadcastingIp[vector]);
				resultNetworkBroadcasting[i + 3] = String.format("IP's disponíveis para host: %s - %s", allIpHostNetwork[vector], allIpHostBroadcasting[vector]);
				resultNetworkBroadcasting[i + 4] = String.format("\n");

				vector++;
				i += 5;
				numberIp++;
			}
		}
	}

}
