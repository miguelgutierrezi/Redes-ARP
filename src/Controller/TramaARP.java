package Controller;

import java.net.InetAddress;
import java.util.StringTokenizer;

import jpcap.packet.ARPPacket;

public class TramaARP extends ARPPacket {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static  byte[] broadcast=new byte[]{(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255};
		public void llenar(short typeH,byte[] originMAC,InetAddress originIP,InetAddress destiniIP)
		{
			this.hardtype = typeH;
			this.prototype = ARPPacket.PROTOTYPE_IP;
			this.hlen = 6;
			this.plen = 4;
			this.operation = ARPPacket.ARP_REQUEST;
			this.sender_protoaddr = originIP.getAddress();
			this.target_protoaddr = destiniIP.getAddress();
			this.target_hardaddr = broadcast;
			this.sender_hardaddr = originMAC;

		}

	  //Cuando llega en string
	  public void setSender_hardaddr(String _sender_hardaddr)
	  {
	    StringTokenizer tokens = new StringTokenizer(_sender_hardaddr, ":");
	    sender_hardaddr = new byte[this.hlen];
	    int pos = 0;
	    while( tokens.hasMoreElements() )
	    {
	      sender_hardaddr[ pos ] = convertString(tokens.nextElement().toString());;
	      pos++;
	    }
	    //System.out.println("'''''''"+_sender_hardaddr+"--------->"+sender_hardaddr);
	  }
	  private byte castChar( char a )
	  {
	    if( a >= '0' && a <= '9' )
	      return (byte) (a-'0');
	    return (byte) ((a-'a')+10);
	  }
	  private byte convertString( String hex )
	  {
	    char left = hex.charAt(0);
	    char right = hex.charAt(1);
	    //System.out.println(left+"}}}}"+right+"cascas"+castChar( left)+"cascas2"+castChar( right));
	    return (byte) ((castChar( left)<<4)|castChar(right));
	  }
	  //------------------
	  private char hexUpperChar(byte b) {
	        b = (byte) ((b >> 4) & 0xf);
	        if (b == 0) return '0';
	        else if (b < 10) return (char) ('0' + b);
	        else return (char) ('a' + b - 10);


	    }

	    private char hexLowerChar(byte b) {
	        b = (byte) (b & 0xf);

	        if (b == 0) return '0';

	        else if (b < 10) return (char) ('0' + b);

	        else return (char) ('a' + b - 10);
	    }
	  private String harToString( byte n )
	  {
	    return String.valueOf(hexUpperChar(n))+String.valueOf(hexLowerChar(n));
	  }
	  public String[] getSenderHardwareAdrresString()
	  {
	    String[] ret = new String[hlen];
	    for( int i = 0  ; i < hlen ; ++i )
	    {
	      ret[ i ] = harToString(sender_hardaddr[i]);
	    }
	    return ret;
	  }
	  public String[] getTargetHardwareAdrresString()
	  {
	    String[] ret = new String[hlen];
	    for( int i = 0  ; i < hlen ; ++i )
	    {
	      ret[ i ] = harToString(target_hardaddr[i]);
	    }
	    return ret;
	  }
}
