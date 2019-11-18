/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Telas;

import Cliente.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Cliente.TratadorAplicacao;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class FXMLIniciarJogoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public TextField txt_user;
     
    @FXML
    public  TextField txt_sala;
    
   
    
    @FXML
    AnchorPane pane;
    
    @FXML 
    public void actionPlay(ActionEvent e) throws InterruptedException{
        TratadorAplicacao.trata_status = 2;
        if(txt_user.getText().length()>0){
            if(Cliente.sala!=null){
                String input = "criarJogador/"+Cliente.sala+"/"+txt_user.getText();
                Cliente.inputString = input;
                Thread.sleep(300);
            }else{
                //verificar se sala existe
                String input = "procurar/"+txt_sala.getText()+"/"+txt_user.getText();
                Cliente.inputString = input;
                Cliente.sala = txt_sala.getText().toLowerCase();
                Thread.sleep(300);   
            }
            if(Cliente.jogador_id!=-1){
                try {
                    TratadorAplicacao.trata_status = 3;
                    Cliente.inputString = "listar/"+Cliente.sala;
                    Thread.sleep(200);
                    pane.getChildren().clear();
                     AnchorPane tela = (AnchorPane) FXMLLoader.load(getClass()
                         .getResource("FXMLOJogo.fxml"));
                     pane.getChildren().add(tela);

                } catch (IOException exc) {
                     JOptionPane.showMessageDialog((null), "Falha ao entrar no jogo");
                }
            }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setContentText("Sala não existe ou jogo já iniciado");
                    alert.showAndWait();
            }
        }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ERRO");
                    alert.setContentText("Insira um nome");
                    alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(Cliente.sala!=null){
            txt_sala.setStyle("-fx-background-color: rgb(162,162,162)");
            txt_sala.setText(Cliente.sala);
            txt_sala.setEditable(false);
        }
    }    
    
}
