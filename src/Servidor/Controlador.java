/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Jogo.Jogador;
import Jogo.Sala;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author aless
 */
public class Controlador {    
   
    public ArrayList<Sala> Salas;
    public static ArrayList<Jogador> Jogadores;
    public static int contador_de_jogadores;
    
    public Controlador(){
        Salas = new ArrayList<>();
        Jogadores = new ArrayList<>();
        contador_de_jogadores=0;
    }
    
    public String criarSala(){
        Sala s = new Sala();
        Salas.add(s);
        return s.codigo;
    }
    
    public int criarJogador(String nome, String codSala, PrintStream ps){
        Jogador j = new Jogador(nome, codSala, ps,contador_de_jogadores);
        contador_de_jogadores++;
        boolean sala_existe = false;
        for(Sala sala : Salas){
            if(sala.codigo.equals(codSala)&&(!sala.iniciado)){
                sala.jogadores.add(j);
                sala_existe=true;
                break;
            }
        }
        if(sala_existe){
            Jogadores.add(j);
            avisar(codSala,codSala);
            return j.id;
        }else{
            return -1;
        }
    }
    
    public String verJogador(int id){
        for(Jogador j : Jogadores){
            if (j.id == id){
                return j.nome + "/" + j.codSala;
            }
        }
        return "Erro";
    }
    
    public String listar(String codSala){
        String jogadores_lista ="";
        for (Sala sala : Salas){
            if(sala.codigo.equals(codSala)){
                for(Jogador j : sala.jogadores)
                    jogadores_lista+=j.nome+"/"+j.id+"/";
                }
            }
        jogadores_lista=jogadores_lista.substring(0,jogadores_lista.length()-1);
        System.out.println(jogadores_lista);
        return jogadores_lista;
    }
    public boolean procurar(String codSala) {
        boolean retorno = false;
        for(Sala sala: Salas){
            if(sala.codigo.equals(codSala.toLowerCase())){
                retorno = true;
            }
        }
        return retorno;
    }
     public void avisar(String aviso, String codSala){
        for (Sala sala : Salas){
            if(sala.codigo.equals(codSala)){
                sala.jogadores.forEach((j) -> {
                    j.ps.println(aviso);
                });
            }
        }
    }
    
    public void iniciar(String codSala) throws InterruptedException{
        Thread.sleep(500);
        avisar("iniciado", codSala);

          for(Sala sala : Salas){
            if(sala.codigo.equals(codSala)){
                sala.iniciado=true;
                break;
            }
        }
    }
    
    public Boolean jogar(int id, String codSala) throws InterruptedException{
        for(Sala sala : Salas){
            if(sala.codigo.equals(codSala)){
                for(Jogador j : sala.jogadores){
                    if(j.id == id && id == sala.jogadores.get(sala.proximo).id){
                        j.jogar();
                        String msg = "";
                        for(int dado : j.resultado){
                            msg = msg + Integer.toString(dado) + ",";
                        }
                        
                        msg = msg.substring(0, msg.length() - 1);
                        avisar(msg, codSala);
                        sala.atualizaProximo();
                        Thread.sleep(600);
                        if(sala.proximo == 0){ 
                            avisar("vencedor/" + Integer.toString(sala.vencedor()), codSala);
                        }
                        
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public int verPontos(int id){
        int pontuacao = 0;
        for(Jogador j : Jogadores){
            if (j.id == id){
                for(int i = 0; i < j.resultado.length; i++){
                    pontuacao += j.resultado[i];
                }
            }
        }
        return pontuacao;
    }
}
