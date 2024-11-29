package com.spaceinvaders.spaceinvaders;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class HiloMusical implements Runnable, Serializable {

    private Clip clip1;
    private Clip clip2;
    private String rutaCancion1;
    private String rutaCancion2;
    private String efectoEspecial;
    private final Object monitor=new Object();
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

    public void reproducirMusica(String rutaCancion1, String rutaCancion2) throws InterruptedException {
        // Objeto sincronizador que usaremos para los hilos
        Object semaforo = new Object();
        musica = true;

        // Hilo para la primera canción
        Thread hiloCancion1 = new Thread(() -> {
            while (musica) {
                try {
                    // Reproducir la primera canción
                    File archivoSonido1 = new File(rutaCancion1);
                    AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(archivoSonido1);
                    clip1 = AudioSystem.getClip();
                    clip1.open(audioInputStream1);
                    clip1.start();

                    synchronized (semaforo) {
                        // Esperar a que termine la canción
                        while (clip1.isRunning() && musica) {
                            semaforo.wait();
                        }
                        clip1.stop();
                        clip1.close();

                        // Notificar al otro hilo que puede iniciar
                        semaforo.notify();
                    }
                    if (!musica){
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Hilo para la segunda canción
        Thread hiloCancion2 = new Thread(() -> {
            while (musica) {
                try {
                    // Reproducir la segunda canción
                    File archivoSonido2 = new File(rutaCancion2);
                    AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(archivoSonido2);
                    clip2 = AudioSystem.getClip();
                    clip2.open(audioInputStream2);
                    clip2.start();

                    synchronized (semaforo) {
                        // Notificar al primer hilo que puede iniciar
                        semaforo.notify();

                        // Esperar a que termine la canción
                        while (clip2.isRunning() && musica) {
                            semaforo.wait();
                        }
                        clip2.stop();
                        clip2.close();
                    }
                    if (!musica){
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Iniciar los hilos
        hiloCancion1.start();
        hiloCancion2.start();
    }

    public void explosionFinalizar(String efectoEspecial){

        Thread musicThread=new Thread(()->{

            try {
                File archivoSonido3 = new File(efectoEspecial);
                AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(archivoSonido3);
                clip1 = AudioSystem.getClip();
                clip1.open(audioInputStream1);
                clip1.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

        });


    }

    public void detenerMusica(){
        musica=false;
        if ((clip1.isRunning() && clip1!=null) || (clip2.isRunning() && clip2!=null)){
            clip1.stop();
            clip1.close();
            clip2.stop();
            clip2.close();
        }
    }
}
