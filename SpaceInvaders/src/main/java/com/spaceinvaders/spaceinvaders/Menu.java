package com.spaceinvaders.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Node;

import static com.spaceinvaders.spaceinvaders.SpaceInvaders.gc;
import static java.awt.SystemColor.menu;

public class Menu {

    private HiloMusical hiloMusical;
    public boolean pararMusica;
    private SpaceInvaders mainApp;
    private Timeline timeline;

    public void guardarAction(ActionEvent actionEvent) {

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

    private void run(GraphicsContext gc) {
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
