/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Cliente.Telas.FXMLOJogoController;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class TratadorAplicacao{
    
    public static int trata_status; //o valor é mudado de acordo com o conteúdo esperado dentro da função trata.
    
    public TratadorAplicacao(){
    }
    public static void trata(String s) throws InterruptedException{
        switch(trata_status){
            case 1:{ //criar sala
                Cliente.sala = s;
                break;
            }
            case 2:{ //criar jogador
                if(s.length()<=3)
                    Cliente.jogador_id = Integer.parseInt(s);
                break;
            }
            case 3:{ //listar
                String[] strings = s.split("/");
                Cliente.jogadores= new ArrayList<>();
                Cliente.ids= new ArrayList<>();
                for(int i = 0 ; i< strings.length; i = i+2){
                    Cliente.jogadores.add(strings[i]);
                    Cliente.ids.add(Integer.parseInt(strings[i+1]));
                    if(Integer.parseInt(strings[i+1])==Cliente.jogador_id){
                        Cliente.sua_vez = i/2;
                    }
                }
                
                FXMLOJogoController.atualiza = "novo_jogador";
                break;
            }
            case 4:{ //stand by:
                if(!s.equals("iniciado")){
                    trata_status=3;
                    Cliente.inputString = "listar/"+s;
                }else{
                    trata_status=5;
                    FXMLOJogoController.iniciar=true;
                    FXMLOJogoController.atualiza="iniciado";
                }
                    
                break;
            }
            case 5:{ //jogando
                System.out.println(s);
                char first = s.charAt(0);
                if((first!='v') && (s.length()>4)){
                    
                    Cliente.current_id++;
                   
                        String[] msg = s.split(",");
                        for(int i =0;i<6;i++){
                            FXMLOJogoController.dados[i]=Integer.parseInt(msg[i]);
                        }
                        Cliente.inputString="verPontos/"+Cliente.ids.get(Cliente.current_id-1);
                    
                }else if(s.length()<=4){
                    Cliente.current_score = Integer.parseInt(s);
                    if(Cliente.vencedor<Cliente.current_score){
                        Cliente.vencedor=Cliente.current_score;
                        Cliente.vencedor_nome=Cliente.jogadores.get(Cliente.current_id-1);
                    }
                    
                    FXMLOJogoController.atualiza="dado_lançado";
                    
                    Thread.sleep(500);
                     if((Cliente.sua_vez)==Cliente.current_id){
                        FXMLOJogoController.atualiza="sua_vez";
                        FXMLOJogoController.sua_vez=true;
                    }
                }else{
                    
                }
                break;
            }
            default:{
                System.out.println("erro no trata_status");
            }
        }
    }

    
}
