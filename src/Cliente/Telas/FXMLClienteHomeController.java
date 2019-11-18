/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Telas;

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
public class FXMLClienteHomeController implements Initializable {

    
    /**
     * Initializes the controller class.
     */
    @FXML
    AnchorPane pane;
    
    @FXML
    public void actionButton(ActionEvent e){
        pane.getChildren().clear();
        try {
            AnchorPane tela = (AnchorPane) FXMLLoader.load(getClass()
                .getResource("FXMLClienteTelaInicial.fxml"));
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
