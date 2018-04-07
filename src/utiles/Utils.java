package utiles;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import static java.lang.Math.pow;

public class Utils {
	private static final int BITS_DIR = 32;
	private static final int BITS_OCTETO = 8;
	private static final int BYTE_OVERFLOW = 127;
	private static final int BRDCST = 255;
	
	public static final short OCTETOS_DIR_IP = 4;
	public static final short OCTETOS_DIR_MAC = 6;
	public static final int SNAPLEN = 2000;
	public static final int TO_MS = 3000;
	
	private InetAddress direccion;
	private NetworkInterface Interface;
	private short mascaraDeRed;
	private byte[] direccionFisica;
	
	public Utils() throws UnknownHostException, SocketException{
		this.direccion = InetAddress.getLocalHost();
		this.Interface = NetworkInterface.getByInetAddress(direccion);
		this.mascaraDeRed = Interface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
		this.direccionFisica = Interface.getHardwareAddress();
	}
	
	public static byte[] dirToByte(String decimal) throws NoSuchElementException{
		byte[] dirByte = new byte[OCTETOS_DIR_IP];
		
		StringTokenizer token = new StringTokenizer(decimal, ".");
		
		for(int indx_oct = 0; indx_oct < OCTETOS_DIR_IP; indx_oct++){
			int dec_oct = Integer.parseInt(token.nextToken());
			if(dec_oct > BYTE_OVERFLOW){
				dec_oct = dec_oct - (2*BYTE_OVERFLOW) - 2;
			}
			dirByte[indx_oct] = (byte)dec_oct;
		}
		
		return dirByte;
	}
	
	public static byte[] macToByte(String hex){
		byte[] macByte = new byte[OCTETOS_DIR_MAC];
		
		StringTokenizer token = new StringTokenizer(hex, ":");
		
		for(int indx_oct = 0; indx_oct < OCTETOS_DIR_MAC; indx_oct++){
			int dec_oct = Integer.parseInt(token.nextToken(), 16);
			System.out.println(dec_oct + "%%%");
			if(dec_oct > BYTE_OVERFLOW){
				dec_oct = dec_oct - (2*BYTE_OVERFLOW) - 2;
			}
			macByte[indx_oct] = (byte)dec_oct;
			System.out.println(macByte[indx_oct]);
		}
		return macByte;
	}
	
	public String dirToBinary(String decimal){
		String dirBinary = new String(), octeto;
		
		StringTokenizer token = new StringTokenizer(decimal, ".");
		
		for(int indx_oct = 0; indx_oct < OCTETOS_DIR_IP; indx_oct++){
			octeto = Integer.toBinaryString(Integer.parseInt(token.nextToken()));
			int bitsRelleno = BITS_OCTETO - octeto.length();
			
			if(bitsRelleno > 0){
				String formato = String.format("%%1$0%dd%%2$s", bitsRelleno);
				octeto = String.format(formato, 0, octeto);
			}
			
			dirBinary += octeto;
		}
		
		return dirBinary;
	}
	
	private String dirToDec(String binary){
		String dirDecimal = new String(), octeto;
		for(int ind_Ini = 0, ind_Fnl = BITS_OCTETO; ind_Fnl <= BITS_DIR; ind_Ini += 8 , ind_Fnl += 8){
			octeto = binary.substring(ind_Ini, ind_Fnl);
			dirDecimal += Integer.parseInt(octeto, 2);
			if(ind_Fnl < BITS_DIR){
				dirDecimal += ".";
			}
		}		
		return dirDecimal;
	}
	
	public byte[] getDirFisica(){
		return direccionFisica;
	}
	
	public String getDirLocal(){
		return direccion.getHostAddress();
	}
	
	public short getDecimalMascara(){
		return mascaraDeRed;
	}
	
	public static byte[] getMacBroadcast(){
		byte[] macBoradcast = {(byte)BRDCST, (byte)BRDCST, (byte)BRDCST, (byte)BRDCST, (byte)BRDCST, (byte)BRDCST};
		return macBoradcast;
	}
	
	public String getDirBroadcast(){
		
		String direccion = dirToBinary(getDirLocal());
		
		direccion = direccion.substring(0, mascaraDeRed);
		
		int bitsRelleno = BITS_DIR - mascaraDeRed;
		for(int cont = 0; cont < bitsRelleno; cont++){
			direccion += "1";
		}
		
		return dirToDec(direccion);
	}
	
	public String getDirRed(){
		
		String dirRed = dirToBinary(getDirLocal());

		dirRed = dirRed.substring(0, mascaraDeRed);
		
		int bitsRelleno = BITS_DIR - mascaraDeRed;
		for(int cont = 0; cont < bitsRelleno; cont++){
			dirRed += "0";
		}
		
		return dirToDec(dirRed);	
	}
	
	public String getDirMascara(){
		String dirMascara = String.format("%1$d.%1$d.%1$d.%1$d", BRDCST);
		
		dirMascara = dirToBinary(dirMascara);
		
		dirMascara = dirMascara.substring(0, mascaraDeRed);
		
		int bitsRelleno = BITS_DIR - mascaraDeRed;
		for(int cont = 0; cont < bitsRelleno; cont++){
			dirMascara += "0";
		}
		
		dirMascara = dirToDec(dirMascara);
		
		return dirMascara;
	}
	
	public int cantDisp() {
		return (int) ((pow(2, 8 * (4 - (getDecimalMascara() / 8))
				- (getDecimalMascara() % 8))) - 2);
	}

	public String sumaBinaria(String n1) {
		String suma = "", n2 = "00000000000000000000000000000001";
		int carry = 0;
		n1 = this.dirToBinary(n1);
		for (int i = n1.length()-1; i >= 0; i--) {
			if ((n2.charAt(i) == '1' && n1.charAt(i) == '1') && carry == 1) {
				suma = suma.concat("1");
				carry = 1;
			} else if ((n2.charAt(i) == '1' && n1.charAt(i) == '1') && carry == 0) {
				suma = suma.concat("0");
				carry = 1;
			}

			if ((n2.charAt(i) == '0' && n1.charAt(i) == '1' && carry == 1)
					|| (n2.charAt(i) == '1' && n1.charAt(i) == '0' && carry == 1)) {
				suma = suma.concat("0");
				carry = 1;
			} else if ((n2.charAt(i) == '0' && n1.charAt(i) == '1' && carry == 0)
					|| (n2.charAt(i) == '1' && n1.charAt(i) == '0' && carry == 0)) {
				suma = suma.concat("1");
				carry = 0;
			}

			if ((n2.charAt(i) == '0' && n1.charAt(i) == '0' && carry == 1)) {
				suma = suma.concat("1");
				carry = 0;
			} else if ((n2.charAt(i) == '0' && n1.charAt(i) == '0' && carry == 0)) {
				suma = suma.concat("0");
				carry = 0;
			}
		}
		suma = this.reverse(suma);
		return suma;
	}

	public String reverse(String str) {
		if ((null == str) || (str.length() <= 1)) {
			return str;
		}
		return new StringBuffer(str).reverse().toString();
	}
	
	public List<String> obtainHosts (String dirRed, int cantDisp) {
		List<String> hosts = new ArrayList<String>();
		String suma = this.sumaBinaria(dirRed);
		for (int i=0; i<cantDisp; i++) {
			hosts.add(this.dirToDec(suma));
			suma = this.sumaBinaria(this.dirToDec(suma));
			System.out.println(hosts.get(i));
		}
		return hosts;
	}
}