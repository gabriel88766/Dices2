/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.Telas;

import Cliente.TratadorAplicacao;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author Gabriel
 */
public class FXMLOJogoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private final static String[] CSS_STYLE = {" -fx-background-color : rgb(139,195,74); -fx-border-color : black", " -fx-background-color : rgb(76,175,80); -fx-border-color : black"};
    public static boolean sua_vez=false;
    public static boolean iniciar=false;
    public static String atualiza = "stand";
    public static boolean jogando = false;
    public static int[] dados={0,0,0,0,0,0};
    public static int current=0;
    public static Image img_dados[];
    
    @FXML
    Label label1;
    
    @FXML 
    Button button;
    
    @FXML
    Button button2;
    
    @FXML
    Label dado1;
    
    @FXML
    Label dado2;
    
    @FXML
    Label dado3;
    
    @FXML
    Label dado4;
    
    @FXML
    Label dado5;
    
    @FXML
    Label dado6;
    
    @FXML
    VBox box1;
    
    @FXML
    VBox box2;
    
    @FXML 
    Label status;
    
    @FXML
    Label button_text;
    
    Label[] players = new Label[20];
    
    @FXML
    public void actionButton(ActionEvent evt) throws InterruptedException{
        if(sua_vez){
            //jogar.
            sua_vez = false;
            Thread.sleep(2500);
            Cliente.Cliente.inputString="jogar/"+Cliente.Cliente.jogador_id+"/"+Cliente.Cliente.sala;
            button_text.setText("JÁ JOGOU!");
            button.setStyle("-fx-background-color : rgb(192,192,192); -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px");
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("NÃO É SUA VEZ");
                alert.setContentText("Aguarde a sua vez");
                alert.show();
                Thread.sleep(1500);
                alert.close();
        }
    }
    
    public void start(ActionEvent evt) throws InterruptedException{
        if(Cliente.Cliente.is_host){
            iniciar=true;
            Cliente.Cliente.inputString="iniciar/"+Cliente.Cliente.sala;
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("VOCÊ NÃO É O HOST");
                alert.setContentText("Apenas o host pode começar o jogo");
                alert.show();
                Thread.sleep(3000);
                alert.close();
        }
    }
  
  
    // verPontos/id a cada jogada.  
    public void atualiza(){
            Platform.runLater(() -> {
                // Update UI here.
                    if(atualiza.equals("novo_jogador")&& iniciar==false){
                        int tam = Cliente.Cliente.jogadores.size();
                        for(int i = Cliente.Cliente.contador;i<tam;i++){
                            players[i].setStyle(CSS_STYLE[i%2]);
                            players[i+10].setStyle(CSS_STYLE[i%2]);
                            players[i].setText(Cliente.Cliente.jogadores.get(i));
                            players[i+10].setText("0");
                        }
                        Cliente.Cliente.contador=tam;
                        TratadorAplicacao.trata_status=4; // standy-by
                        atualiza="stand";
                    }
                    if(atualiza.equals("iniciado")){
                        status.setText("JOGO INICIADO");
                        status.setTextFill(Color.BLUE);
                        iniciar=true;
                        atualiza="stand";
                        button2.setVisible(false);
                        if(Cliente.Cliente.is_host){
                            atualiza="sua_vez";
                            sua_vez=true;
                        }
                    }
                    if(sua_vez){
                        button_text.setText("JOGAR!");
                        atualiza="stand";
                    }
                    if(atualiza.equals("dado_lançado")){
                        players[current+10].setText(String.valueOf(Cliente.Cliente.current_score));
                        current++;
                        if(current-1==Cliente.Cliente.sua_vez){
                            dado1.setGraphic(new ImageView(img_dados[dados[0]-1]));
                            dado2.setGraphic(new ImageView(img_dados[dados[1]-1]));
                            dado3.setGraphic(new ImageView(img_dados[dados[2]-1]));
                            dado4.setGraphic(new ImageView(img_dados[dados[3]-1]));
                            dado5.setGraphic(new ImageView(img_dados[dados[4]-1]));
                            dado6.setGraphic(new ImageView(img_dados[dados[5]-1]));
                            sua_vez=false;
                        }
                        if(current==Cliente.Cliente.jogadores.size()){
                            atualiza="fim";
                            jogando=false;
                        }else{
                            atualiza="stand";
                        }
                       
                    }
                    if(!jogando&&!atualiza.equals("fim")){
                        jogando=true;
                        begin();
                    }
                    if(atualiza.equals("fim")){
                        acabou();
                    }
            });
    }
    public void begin(){
        new Thread(){
            @Override
            public void run(){
                while(jogando){
                    if(atualiza.equals("novo_jogador")||atualiza.equals("dado_lançado")||atualiza.equals("sua_vez")||atualiza.equals("iniciado")||atualiza.equals("fim")){
                        atualiza();
                    }
                    try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLOJogoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                atualiza();
            }
        }.start();
    }
  
    public void acabou(){
        Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("O JOGO ACABOU!!!");
                alert.setContentText("VENCEDOR : "+ Cliente.Cliente.vencedor_nome+ " com "+Cliente.Cliente.vencedor+" pontos");
                alert.show();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLOJogoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                alert.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        label1.setText("room : "+Cliente.Cliente.sala);
        img_dados = new Image[6];
        img_dados[0] = new Image("Cliente/Telas/Images/dado_1.png");
        img_dados[1] = new Image("Cliente/Telas/Images/dado_2.png");
        img_dados[2] = new Image("Cliente/Telas/Images/dado_3.png");
        img_dados[3] = new Image("Cliente/Telas/Images/dado_4.png");
        img_dados[4] = new Image("Cliente/Telas/Images/dado_5.png");
        img_dados[5] = new Image("Cliente/Telas/Images/dado_6.png");
        
        // INICIALIZA COM DADOS ALEATORIOS
        dado1.setGraphic(new ImageView(img_dados[0]));
        dado2.setGraphic(new ImageView(img_dados[1]));
        dado3.setGraphic(new ImageView(img_dados[2]));
        dado4.setGraphic(new ImageView(img_dados[3]));
        dado5.setGraphic(new ImageView(img_dados[4]));
        dado6.setGraphic(new ImageView(img_dados[5]));
        
        // INICIALIZA OS LABELS DOS PLAYERS. (até 10)
        for(int i=0 ; i<10 ; i++){
            players[i] = new Label();
            players[i].setPrefWidth(85);
            players[i].setPrefHeight(30);
            players[i+10] = new Label();
            players[i+10].setPrefWidth(85);
            players[i+10].setPrefHeight(30);
            players[i].setAlignment(Pos.CENTER);
            players[i+10].setAlignment(Pos.CENTER);
            players[i].setFont(Font.font ("Comic Sans",FontWeight.BOLD, 14));
            players[i+10].setFont(Font.font ("Comic Sans",FontWeight.BOLD, 14));
            box1.getChildren().add(i,players[i]);
            box2.getChildren().add(i,players[10+i]);
        }
        if(!Cliente.Cliente.is_host){
            button2.isDisable();
        }
        atualiza="novo_jogador";
        atualiza();
        
    }
}


