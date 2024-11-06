package com.spaceinvaders.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.List;

import static com.spaceinvaders.spaceinvaders.SpaceInvaders.gc;
import static java.awt.SystemColor.menu;

public class Menu implements Serializable {

    public boolean pararMusica;
    @FXML
    private ListView<String> listPartidas;
    private SpaceInvaders mainApp;
    private Timeline timeline;

    @FXML
    public void initialize(ActionEvent actionEvent) {


    }

    public void guardarAction(ActionEvent actionEvent) {
        SpaceInvaders.getInstancia().guardarEstadoJuego();
    }

    public void guardarAction2(ActionEvent actionEvent) {
        SpaceInvaders.getInstancia().guardarEstadoJuego2();
    }

    public void guardarAction3(ActionEvent actionEvent) {
        SpaceInvaders.getInstancia().guardarEstadoJuego3();
    }

    public void cargarAction(ActionEvent actionEvent){
        // Cargar el estado de juego correspondiente a la partida seleccionada
        SpaceInvaders.getInstancia().cargarEstadoJuego();
    }

    public void cargarAction2(ActionEvent actionEvent){
        // Cargar el estado de juego correspondiente a la partida seleccionada
        SpaceInvaders.getInstancia().cargarEstadoJuego2();
    }

    public void cargarAction3(ActionEvent actionEvent){
        // Cargar el estado de juego correspondiente a la partida seleccionada
        SpaceInvaders.getInstancia().cargarEstadoJuego3();
    }

    public void partidasAction(ActionEvent actionEvent){

    }

    public void musicaAction(ActionEvent actionEvent){
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

}
