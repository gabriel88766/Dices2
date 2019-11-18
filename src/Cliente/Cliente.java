package Cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

	protected final String HOST = "172.16.32.175";
	protected final int PORTA = 1406;
        public static String inputString = null;
        public static String sala = null;
        public static int jogador_id = -1;
        public static int current_id = 0;
        public static int sua_vez;
        public static ArrayList<String> jogadores;
        public static ArrayList<Integer> ids;
        public static int contador=0;
        public static int vencedor=0;
        public static String vencedor_nome;
        public static boolean is_host = false;
        public static int current_score=0;
     

	public Cliente() {
		jogadores = new ArrayList<>();
                ids = new ArrayList<>();
	}

        @Override
	public void run()  {
		try(Socket cliente = new Socket(HOST, PORTA);
                        PrintStream saida = new PrintStream(cliente.getOutputStream())) {
                        
			RecebedorDeMensagemDoServidor r = new RecebedorDeMensagemDoServidor(cliente.getInputStream());
			new Thread(r).start();
                        
			while (true) {
                                if(inputString!=null){
                                    saida.println(inputString);
                                    inputString=null;
                                }
                                Thread.sleep(5);
			}
			
		} catch (IOException | InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } 
	}
        
        
}