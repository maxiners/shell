import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shell extends JFrame{
	
	//elementos gráficos
	JTextField tComando;
	JButton bEjecutar, bClear;
	JTextArea tResultado;
	JScrollPane sPane;

	//oyente de click de botón
	ActionListener alEjecutar;
	KeyListener alAccion;
	
	//String que define el tipo de ejecución según el OS
	String comando = "";

	public Shell(){
		setSize(700,600);
		setTitle(System.getProperty("os.name"));
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			comando = "cmd /c";
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void graficos(){
		getContentPane().setLayout(null);
		//cuadro de texto
		tComando = new JTextField();
		tComando.setBounds(50,50,250,30);
		tComando.addKeyListener(alAccion);
		tComando.setBackground(Color.BLACK);
		tComando.setForeground(Color.WHITE);
		add(tComando);
		//botón para ejecutar comando
		bEjecutar = new JButton("Ejecutar");
		bEjecutar.setBounds(350,50,150,30);
		add(bEjecutar);
		bEjecutar.addActionListener(alEjecutar);
		
		//botón para limpiar shell
		bClear = new JButton("Limpiar");
		bClear.setBounds(500,50,150,30);
		add(bClear);
		bClear.addActionListener(alEjecutar);

		//área de texto
		tResultado = new JTextArea();
		tResultado.setBounds(50,130,600,370);
		tResultado.setBackground(Color.BLACK);
		tResultado.setForeground(Color.RED);
		
		//scroll pane
		sPane = new JScrollPane(tResultado);
		sPane.setBounds(50,120,600,400);
		add(sPane);
		//
		setVisible(true);
	}

	private void acciones(){
		alEjecutar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("a  "+e.getActionCommand());
					switch (e.getActionCommand()){
						case "Ejecutar":
							ejecutar();
							break;
						case "Limpiar":
							limpiar();
							break;
					}
				}
		};
		alAccion = new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					ejecutar();
				}
			}
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					tComando.setText("");
				}
			}
			};
		
	}

	private void ejecutar(){

		Process proc; 
		InputStream is_in;
		String s_aux;
		BufferedReader br;

		try
		{
			if(tComando.getText().equals("clear")){
				tResultado.setText("");
			}
			else if(tComando.getText().equals("exit()")){
				System.exit(0);
			}
			else{
			proc = Runtime.getRuntime().exec(comando+tComando.getText().toLowerCase());
			is_in=proc.getInputStream();
			br=new BufferedReader (new InputStreamReader (is_in));
			s_aux = br.readLine();
			tResultado.setText(tResultado.getText()+"$ "+tComando.getText()+"\n");
            while (s_aux!=null)
            {
            	tResultado.setText(tResultado.getText()+s_aux+"\n");
                s_aux = br.readLine();
            }
			} 
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	    }
	
	private void limpiar(){
		tResultado.setText("");
	}

	public static void main(String args[]){
		Shell ventana = new Shell();
		ventana.acciones();	
		ventana.graficos();	
	}

}
