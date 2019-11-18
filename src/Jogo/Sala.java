/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jogo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author aless
 */
public class Sala {
    public String codigo;
    public ArrayList<Jogador> jogadores;
    public int proximo;
    public boolean iniciado = false;
    
    public Sala(){
        Random r = new Random();
        codigo = Integer.toString(Math.abs(r.nextInt()), 36);  //Gera uma string de 6 letras minusculas ou numeros.
        jogadores = new ArrayList<>(); 
        proximo = 0;
    }
    
    public void atualizaProximo(){
        proximo = (proximo + 1) % jogadores.size();    
    }
    
    public int vencedor(){
        int max_id = 0, max = 0;
        for(Jogador j : jogadores){
            int pontuacao = 0;
            for(int i = 0; i < j.resultado.length; i++){
                pontuacao += j.resultado[i];
            }
            if(pontuacao > max){
                max = pontuacao;
                max_id = j.id;
            }
        }
        return max_id;
    }
}
