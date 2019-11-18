/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Telas;

import Cliente.Cliente;
import Cliente.TratadorAplicacao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class FXMLClienteTelaInicialController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    AnchorPane pane;
    
    @FXML
    public void criarSala(ActionEvent e){
        TratadorAplicacao.trata_status = 1;
        Cliente.inputString = "criarSala";
        Cliente.is_host=true;
        pane.getChildren().clear();
        try {
            AnchorPane tela = (AnchorPane) FXMLLoader.load(getClass()
                .getResource("FXMLIniciarJogo.fxml"));
            pane.getChildren().add(tela);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog((null), "Falha ao carregar iniciar o jogo");
        }
    }
    
    @FXML
    public void entrarSala(ActionEvent e){
        pane.getChildren().clear();
        try {
            AnchorPane tela = (AnchorPane) FXMLLoader.load(getClass()
                .getResource("FXMLIniciarJogo.fxml"));
            pane.getChildren().add(tela);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog((null), "Falha ao iniciar o jogo");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
