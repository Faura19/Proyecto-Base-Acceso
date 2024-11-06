package com.spaceinvaders.spaceinvaders;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

public class HiloMusical implements Runnable{

    private Clip clip;
    private String rutaCancion1;
    private String rutaCancion2;
    private String efectoEspecial;
    private boolean musica=true;

    public HiloMusical(String rutaCancion2, String rutaCancion1) {
        this.rutaCancion2 = rutaCancion2;
        this.rutaCancion1 = rutaCancion1;
    }

    public HiloMusical(String efectoEspecial){
        this.efectoEspecial=efectoEspecial;
    }

    public HiloMusical(){

    }

    @Override
    public void run() {



    }

    public void reproducirMusica(String rutaCancion1,String rutaCancion2) {

        musica=true;
        Thread musicThread = new Thread(() -> {
            while (musica) {

                try {
                    // Reproduce la primera canción por 15 segundos
                    File archivoSonido1 = new File(rutaCancion1);
                    AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(archivoSonido1);
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream1);
                    clip.start();

                    // Espera 15 segundos antes de detener la canción
                    Thread.sleep(15000);
                    if (clip.isRunning()) {
                        clip.stop();
                        clip.close();
                    }

                    if (!musica){
                        break;
                    }

                    // Reproduce la segunda canción por 15 segundos
                    File archivoSonido2 = new File(rutaCancion2);
                    AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(archivoSonido2);
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream2);
                    clip.start();

                    // Espera 15 segundos antes de detener la canción
                    Thread.sleep(15000);
                    if (clip.isRunning()) {
                        clip.stop();
                        clip.close();
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException |
                         InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        musicThread.start();

    }

    public void explosionFinalizar(String efectoEspecial){

        Thread musicThread=new Thread(()->{

            try {
                File archivoSonido3 = new File(efectoEspecial);
                AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(archivoSonido3);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream1);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

        });
    }

    public void detenerMusica(){
        musica=false;
        if (clip.isRunning()){
            clip.stop();
            clip.close();
        }
    }
}
