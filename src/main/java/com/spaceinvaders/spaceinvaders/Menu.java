package com.spaceinvaders.spaceinvaders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static com.spaceinvaders.spaceinvaders.SpaceInvaders.gc;
import static java.awt.SystemColor.menu;

public class Menu implements Serializable {

    public boolean pararMusica;
    @FXML
    public TextField nombrePartida;
    @FXML
    public Checkbox musicaBoton;

    @FXML
    private ListView<String> listPartidas=new ListView<String>((ObservableList<String>) nombrePartida);

    private ObservableList<String> partidasObservable = FXCollections.observableArrayList();

    @FXML
    public void initialize(ActionEvent actionEvent) {
        listPartidas.setItems(partidasObservable);
    }


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

        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().guardarEstadoJuego();



        partidasObservable = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        listPartidas= new ListView<String>(partidasObservable);


        if (!nombrePartida.getText().isEmpty()) {
            // Añadir el nombre a la lista observable
            partidasObservable.add(nombrePartida.getText());
        }


        listPartidas.getItems().add(String.valueOf(partidasObservable));

        System.out.println(nombrePartida.getText());
    }

    public void partida1(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego();
    }

    public void partida2(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego2();
    }

    public void partida3(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego3();
    }

    public void partida4(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego4();
    }

    public void partida5(ActionEvent actionEvent){
        Stage stage=(Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        SpaceInvaders.getInstancia().cargarEstadoJuego5();
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

    public void partidasAction(ActionEvent actionEvent){

    }

    public void musicaAction(ActionEvent actionEvent){

        if (musicaBoton.getState()){
            SpaceInvaders.getInstancia().quitarMusica();
        }else{
            System.out.println("Jorge");
        }


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
