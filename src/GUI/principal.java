package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.deploy.uitoolkit.impl.fx.Utils;

import Controller.ARP;
import jpcap.packet.ARPPacket;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Vector;
import java.awt.event.ItemEvent;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;

public class principal {

	private JFrame frmArp;
	private JScrollPane scrollPane;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField hardtype;
	private JTextField macori;
	private JTextField iporigi;
	private JTextField textField_16;
	private JLabel lblOpercacion;
	private JLabel lblHardware;
	//private  ARP arp= new ARP();
	private JTextField operacion;
	private JComboBox<String> selecmacori;
	private JComboBox<String> selectipori;
	private JLabel lblArp;
	private JLabel lblHlen;
	private JLabel lblPlen;
	private JLabel lblPtype;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					principal window = new principal();
					window.frmArp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public principal() {
		initialize();
	}




	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmArp = new JFrame();

		frmArp.setTitle("ARP");
		frmArp.setIconImage(Toolkit.getDefaultToolkit().getImage(".\\Categories-applications-internet-icon (1).png"));
		frmArp.setBounds(100, 100, 1056, 556);
		frmArp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmArp.getContentPane().setLayout(null);

		lblArp = new JLabel("Address Resolution Protocol (ARP)");
		lblArp.setForeground(Color.BLACK);
		lblArp.setFont(new Font("SansSerif", Font.BOLD, 21));
		lblArp.setBounds(292, 6, 508, 32);
		frmArp.getContentPane().add(lblArp);

		JLabel lblMacOrigen = new JLabel("MAC origen");
		lblMacOrigen.setBackground(Color.WHITE);
		lblMacOrigen.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblMacOrigen.setForeground(Color.WHITE);
		lblMacOrigen.setBounds(401, 370, 74, 26);
		frmArp.getContentPane().add(lblMacOrigen);

		JLabel lblIpOri = new JLabel("IP origen");
		lblIpOri.setBackground(Color.WHITE);
		lblIpOri.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblIpOri.setForeground(Color.WHITE);
		lblIpOri.setBounds(583, 370, 74, 26);
		frmArp.getContentPane().add(lblIpOri);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(104, 49, 854, 308);
		frmArp.getContentPane().add(scrollPane);

		JTable tablaipmac = new JTable();
		tablaipmac.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"IP", "MAC", "Estado", "Dispositivo"}
		));
		scrollPane.setViewportView(tablaipmac);

		textField_2 = new JTextField();
		textField_2.setText("6");
		textField_2.setToolTipText("");
		textField_2.setColumns(10);
		textField_2.setBounds(183, 401, 46, 32);
		frmArp.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.setText("2048");
		textField_3.setColumns(10);
		textField_3.setBounds(121, 401, 56, 32);
		frmArp.getContentPane().add(textField_3);

		hardtype = new JTextField();
		hardtype.setText("1");
		hardtype.setColumns(10);
		hardtype.setBounds(12, 401, 105, 32);
		frmArp.getContentPane().add(hardtype);

		selecmacori = new JComboBox();
		selecmacori.setModel(new DefaultComboBoxModel(new String[] {"", "Otro"}));
		selecmacori.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(selecmacori.getSelectedItem().equals("Otro"))
				{
					macori.setEnabled(true);
				}
				else
				{
					macori.setEnabled(false);
				}
			}
		});
		selecmacori.setBounds(400, 401, 179, 32);
		frmArp.getContentPane().add(selecmacori);

		macori = new JTextField();
		macori.setEnabled(false);
		macori.setColumns(10);
		macori.setBounds(400, 401, 179, 32);
		frmArp.getContentPane().add(macori);

		selectipori = new JComboBox();
		selectipori.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(selectipori.getSelectedItem().equals("Otro"))
				{
					iporigi.setEnabled(true);
				}
				else
				{
					iporigi.setEnabled(false);
				}
			}
		});
		selectipori.setModel(new DefaultComboBoxModel(new String[] {"", "Otro"}));
		selectipori.setBounds(583, 401, 118, 32);
		frmArp.getContentPane().add(selectipori);

		iporigi = new JTextField();
		iporigi.setEnabled(false);
		iporigi.setColumns(10);
		iporigi.setBounds(583, 401, 118, 32);
		frmArp.getContentPane().add(iporigi);

		textField_16 = new JTextField();
		textField_16.setToolTipText("");
		textField_16.setText("4");
		textField_16.setColumns(10);
		textField_16.setBounds(233, 401, 46, 32);
		frmArp.getContentPane().add(textField_16);

		JButton btnNewButton = new JButton("ENVIAR");
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 24));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					boolean encontro = false;
					int cantDisp;


				ARPPacket prueba = null;
				/**ENVIA LA TRAMA **/
				try {
					String macorig="";
					String iporig="";

					if(!selecmacori.getSelectedItem().toString().equals("Otro"))
					{
						macorig=selecmacori.getSelectedItem().toString();
					}
					else
					{
						macorig="";
						macorig+=macori.getText().toString();
					}

					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					if(!selectipori.getSelectedItem().toString().equals("Otro"))
					{
						iporig=selectipori.getSelectedItem().toString();
					}
					else
					{
						iporig="/";
						iporig+=iporigi.getText().toString();
					}
					utiles.Utils u = new utiles.Utils();
					List<String> hosts;
					String dirRed = u.getDirRed();
					cantDisp = u.cantDisp();
					hosts = u.obtainHosts(dirRed, cantDisp);
					ARP.ips.clear();ARP.macs.clear();ARP.estados.clear();ARP.dispositivos.clear();
					for(int i =0;i<cantDisp;i++) {
						System.out.println(i);
						prueba =ARP.request(Short.parseShort(hardtype.getText()), macorig, InetAddress.getLocalHost().getHostAddress(), hosts.get(i));
					}
					
					/*if(tablaipmac.getModel()getClass().get) {
						
					}*/
					//prueba =ARP.request(Short.parseShort(hardtype.getText()), "", "", ipdesti.getText());

					//progressBar43.
				} catch(IllegalArgumentException e) {


					JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR!!!!!!",JOptionPane.ERROR_MESSAGE); //Tipo de mensaje
					//e.printStackTrace();

				}catch (IOException e) {

					JOptionPane.showMessageDialog(null,"Ooops, algo salio mal.","ERROR!!!!!!",JOptionPane.ERROR_MESSAGE); //Tipo de mensaje
					//e.printStackTrace();
				}
				//ARP.request(Short.parseShort(hardtype.getText()), selecmacori.getName(), selectipori.getName(), ipdesti.getText());
				/**ACTUALIZA LOS CAMPO DE ABAJO**/
				for (int a=0; a<ARP.ips.size();a++) {
					//System.out.println(ARP.ips.get(a).toString());

				}
				
				/**ORDEN TABLA**/
				Vector<List<String>> columnaip = new Vector<List<String>>();
				/*Vector<String> fila2=new Vector<String>();
				fila2.add("192.168.0.1");
				fila2.add("A6.B6.00.56");
				//columnaip.add(fila);
				//fila.clear();*/
				for (int i = 0; i < ARP.ips.size(); i++) {
					Vector<String> fila=new Vector<String>();
						fila.add(ARP.ips.get(i).toString());
						fila.add(ARP.macs.get(i).toString());
						fila.add(ARP.estados.get(i).toString());
						fila.add(ARP.dispositivos.get(i).toString());
					columnaip.add(fila);
				}
				//fila.clear();
				/*fila2.add("192.168.0.3");
				fila2.add("A6.B6.00.5B");
				//columnaip.add(fila);
				//fila.clear();
				fila2.add("192.168.0.4");
				fila2.add("A6.B6.00.AA");
				//columnaip.add(fila);
				//fila.clear();*/

				Vector<String> name = new Vector<String>();
				name.add("IP");name.add("MAC");name.add("ESTADO");name.add("TIPO");
				TableModel juliantia=new DefaultTableModel( columnaip,name);
				tablaipmac.setModel(juliantia);

				/** ----ACTUALIZA LOS SELECTBOX---
				 *  PDT: AL PULSAR REPEDIDAS VECES EVIAR SE GENERAN VARIOS OTROS REVISAR
				 *  MOTIVO: NO VACIA LA LISTA DE ITEMS
				 *  SOLUCION: VACIARLA CON CUIDADO DE LOS ERRORES
				 *  SOLUCIONADO!!!!
				**/
				Vector<String> selemaor = new Vector<String>();
				Vector<String> seleipor = new Vector<String>();
				//selecmacori.addItem("Otro2");
				//selecmacori.addItem("Otro3");
				//selecmacori.addItem("Otro");
				//selectipori.removeAllItems();
				////System.out.println(selecmacori.getComponentCount());
				//selecmacori.remove(1);
				//selecmacori.removeAll();
				//selecmacori.updateUI();
				////System.out.println(selecmacori.getComponentCount());

				//selecmacori.remove(0);

				//selecmacori.removeAllItems();
				//selecmacori.removeAll();
				for (int i = 0; i < columnaip.size(); i++) {
					selemaor.add(columnaip.get(i).get(1));
					seleipor.add(columnaip.get(i).get(0));
					}

				

				selecmacori.setModel(new DefaultComboBoxModel(selemaor));
				selecmacori.addItem("Otro");

				selectipori.setModel(new DefaultComboBoxModel(seleipor));
				selectipori.addItem("Otro");
			//	cargando.setText("");
				macori.setText("");
				iporigi.setText("");



			}
		});
		btnNewButton.setBounds(712, 412, 246, 54);
		frmArp.getContentPane().add(btnNewButton);

		lblOpercacion = new JLabel("Operacion");
		lblOpercacion.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblOpercacion.setForeground(Color.RED);
		lblOpercacion.setBounds(296, 370, 74, 26);
		frmArp.getContentPane().add(lblOpercacion);

		lblHardware = new JLabel("Hardware \r\ntype");
		lblHardware.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblHardware.setForeground(Color.RED);
		lblHardware.setBounds(12, 367, 95, 32);
		frmArp.getContentPane().add(lblHardware);

		operacion = new JTextField();
		operacion.setText("Request");
		operacion.setBounds(283, 401, 105, 32);
		frmArp.getContentPane().add(operacion);
		operacion.setColumns(10);
		
		lblHlen = new JLabel("HLEN");
		lblHlen.setForeground(Color.RED);
		lblHlen.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblHlen.setBounds(183, 367, 46, 32);
		frmArp.getContentPane().add(lblHlen);
		
		lblPlen = new JLabel("PLEN");
		lblPlen.setForeground(Color.RED);
		lblPlen.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblPlen.setBounds(233, 367, 46, 32);
		frmArp.getContentPane().add(lblPlen);
		
		lblPtype = new JLabel("PTYPE");
		lblPtype.setForeground(Color.RED);
		lblPtype.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblPtype.setBounds(121, 367, 56, 32);
		frmArp.getContentPane().add(lblPtype);
	}
}
