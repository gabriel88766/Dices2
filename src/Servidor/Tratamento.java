/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import static Servidor.Servidor.controlador;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author aless
 */
public class Tratamento{
    
    public InputStream input;
    public PrintStream output;
    public Socket cliente;
    public Servidor servidor;
    
    public Tratamento(Socket cliente,Servidor servidor,InputStream input,PrintStream ps) throws InterruptedException{
        this.cliente = cliente;
        this.servidor = servidor;
        this.input = input;
        this.output = ps;
        run();
    }
    
    
    public final void run() throws InterruptedException{
        
        try (Scanner s = new Scanner(input)) {
            String msg;
            while(s.hasNextLine()){
                msg = s.nextLine();
                System.out.println(msg);
                if(msg.contains("criarSala")){
                    String codigo = controlador.criarSala();
                    output.println(codigo);
                    System.out.println(codigo);
                }
                if(msg.contains("procurar")){
                    String[] args = msg.split("/");
                    boolean procurar = controlador.procurar(args[1]);
                    if(procurar){
                        output.println(controlador.criarJogador(args[2], args[1], output));
                    }
                }
                if(msg.contains("criarJogador")){
                    String[] args = msg.split("/");
                    String codSala = args[1];
                    String nome = args[2];
                    output.println(controlador.criarJogador(nome, codSala, output));
                }
                if(msg.contains("verJogador")){
                    String[] args = msg.split("/");
                    int id = Integer.valueOf(args[1]);
                    output.println(controlador.verJogador(id));
                }
                if(msg.contains("listar")){
                    String[] args = msg.split("/");
                    String lista = controlador.listar(args[1]);
                    output.println(lista);
                }
                if(msg.contains("avisar")){
                    String[] args = msg.split("/");
                    String aviso = args[1];
                    String codSala = args[2];
                    controlador.avisar(aviso, codSala);
                }
                if(msg.contains("iniciar")){
                    String[] args = msg.split("/");
                    String codSala = args[1];
                    controlador.iniciar(codSala);
                }
                if(msg.contains("jogar")){
                    String[] args = msg.split("/");
                    int id = Integer.valueOf(args[1]);
                    String codSala = args[2];
                    if(!controlador.jogar(id, codSala)){
                        output.println("Aguarde sua vez!");
                    }
                }
                if(msg.contains("verPontos")){
                    String[] args = msg.split("/");
                    int id = Integer.valueOf(args[1]);
                    output.println(controlador.verPontos(id));
                }
                
            }
        }
    }  
     
}

