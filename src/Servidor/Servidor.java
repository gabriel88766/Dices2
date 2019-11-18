package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

        public static Controlador controlador;
	private final int PORTA = 1406;

	public Servidor() {
                controlador = new Controlador();
	}

	public void executa() throws IOException  {
		try(ServerSocket servidor = new ServerSocket(PORTA)){
			System.out.println("Porta 1406 aberta!");
	
			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Nova conexão com o cliente " + 
						cliente.getInetAddress().getHostAddress());			
      
				IntermediadorDeMensagem tc = new IntermediadorDeMensagem(cliente,this);
				new Thread(tc).start();
                                
			}
		}
	}
        
        public static void main(String[] args) 
			throws IOException {
		new Servidor().executa();
	}
}
