package Servidor;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

class IntermediadorDeMensagem implements Runnable {

	public Socket cliente;
	public Servidor servidor;

	public IntermediadorDeMensagem (Socket cliente, Servidor servidor) {
		this.cliente = cliente;
		this.servidor = servidor;
	}

        @Override
	public void run() {
		try(InputStream input = this.cliente.getInputStream()){
                    PrintStream ps = new PrintStream(cliente.getOutputStream());
                    Tratamento tratamento = new Tratamento(cliente,servidor,input,ps);
		} catch (IOException e) {
                    System.out.println(e);
		} catch (InterruptedException ex) {
                Logger.getLogger(IntermediadorDeMensagem.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
