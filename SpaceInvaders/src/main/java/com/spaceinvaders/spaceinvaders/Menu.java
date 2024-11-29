package com.spaceinvaders.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.w3c.dom.Node;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static com.spaceinvaders.spaceinvaders.SpaceInvaders.gc;
import static java.awt.SystemColor.menu;

public class Menu implements Serializable {

    public boolean pararMusica;
    @FXML
    private ListView<String> listPartidas=new ListView<>();

    @FXML
    private TextField nombrePartida=new TextField();

    @FXML
    private Button button1=new Button();

    @FXML
    private Button button2=new Button();

    @FXML
    private Button button3=new Button();

    @FXML
    private Button button4=new Button();

    @FXML
    private Button button5=new Button();

    private ObservableList<String> partidasObservable = FXCollections.observableArrayList();

    private Clip clip;


    public void guardarAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("guardar.fxml"));
            AnchorPane secondPage = loader.load();

            Stage stage2 = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(secondPage);
            stage2.setScene(scene);
            stage2.show();


        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarNombre(ActionEvent actionEvent){
        String nombre = nombrePartida.getText();


        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        partidasObservable.add(nombrePartida.getText());
        listPartidas.setItems(partidasObservable);
        stage.close();
        SpaceInvaders.getInstancia().guardarEstadoJuego();

        System.out.println(nombre);
    }

    @FXML
    public void initialize() {
        // Puedes configurar los botones aquí si es necesario
        // Ejemplo de hacer que todos los botones sean visibles
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        button4.setVisible(true);
        button5.setVisible(true);

        String nombre=nombrePartida.getText();

        // Cambiar el texto de button1 como ejemplo
        button1.setText(nombrePartida.getId());
    }

    public void cargarAction(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("partidas.fxml"));
            AnchorPane secondPage = loader.load();

            Stage stage2 = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(secondPage);
            stage2.setScene(scene);
            stage2.show();

            loadFileNames();

        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargarAction1(ActionEvent actionEvent){

        button1.setText(nombrePartida.getText());
        button1.setVisible(true);

        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego();
    }

    public void cargarAction2(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego2();
    }

    public void cargarAction3(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        // Cargar el estado de juego correspondiente a la partida seleccionada
        SpaceInvaders.getInstancia().cargarEstadoJuego3();
    }

    public void cargarAction4(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        // Cargar el estado de juego correspondiente a la partida seleccionada
        SpaceInvaders.getInstancia().cargarEstadoJuego4();
    }

    public void cargarAction5(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        // Cargar el estado de juego correspondiente a la partida seleccionada
        SpaceInvaders.getInstancia().cargarEstadoJuego5();
    }

    public void partidasAction(ActionEvent actionEvent){

    }

    public void musicaAction(ActionEvent actionEvent) throws InterruptedException {

        HiloMusical hiloMusical=new HiloMusical();
        String cancion1="src/clip-1-muzica-pentru-reclame-film-calitate-24-biti-fisier-audio-wav-viteza-124-bpm-durata-105-min-90 (mp3cut.net).wav";

        String cancion2="src/clip-2-muzica-pentru-reclama-film-24-biti-wav-120-bpm-9013 (mp3cut.net).wav";
        
        if (hiloMusical.getClass()==null){
            hiloMusical.reproducirMusica(cancion1,cancion2);
        }
        
        

        hiloMusical.detenerMusica();
        SpaceInvaders.getInstancia().quitarMusica();
    }

    public void continuarAction(ActionEvent actionEvent) {
        //mainApp.resumeGame();

        SpaceInvaders spaceInvaders=new SpaceInvaders();

        //timeline.play();

        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().juegoReanudado();

        //SpaceInvaders.sh

        /*
        SpaceInvaders spaceInvaders=new SpaceInvaders();
        spaceInvaders.resumeGame();
         */
    }

    public void salirAction(ActionEvent actionEvent) {
        try{
            /*
            String cancion1="src/clip-1-muzica-pentru-reclame-" +
                    "film-calitate-24-biti-fisier" +
                    "-audio-wav-viteza-124-bpm-durata-105-min-90.wav";

            String cancion2="src/clip-2-muzica-pentru-reclama-film-24-biti-wav-120-bpm-9013.wav";

            pararMusica=true;
            HiloMusical hiloMusical=new HiloMusical(cancion1,cancion2);

            if (hiloMusical!=null){
                hiloMusical.detenerMusica();
            }
             */

            //SpaceInvaders spaceInvaders=new SpaceInvaders();

            // Salir del menu

            /*
            Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
             */
            System.exit(0);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadFileNames() {
        listPartidas=new ListView<>();

        File directory = new File("src/guardados/");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                int count = 0;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".dat")) {
                        if (count < 5) {
                            listPartidas.getItems().add(file.getName());
                            count++;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

}
