package Cliente;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class RecebedorDeMensagemDoServidor implements Runnable {

	private InputStream servidor;

	public RecebedorDeMensagemDoServidor(InputStream servidor) {
		this.servidor = servidor;
	}

	public void run() {
		try(Scanner s = new Scanner(this.servidor)){
			while (s.hasNextLine()) {
				TratadorAplicacao.trata(s.next());
			}
		} catch (InterruptedException ex) {
                Logger.getLogger(RecebedorDeMensagemDoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
	}
}