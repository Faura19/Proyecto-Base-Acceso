package com.spaceinvaders.spaceinvaders;

import javafx.application.Platform;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.Serializable;

import static com.spaceinvaders.spaceinvaders.SpaceInvaders.*;

// Player
public class Rocket implements Serializable {
    int score;
    int posX, posY, size;
    boolean exploding, destroyed;
    int imgIndex;
    int explosionStep = 0;
    private HiloMusical hiloMusical;
    private boolean pararMusica=true;

    public Rocket(int posX, int posY, int size) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
    }

    public Rocket(){

    }

    public void stop(){
        pararMusica=false;
    }


    public void explosionDisparos() {

        if (!pararMusica){
            return;
        }

        try {
            File explosionSoundFile = new File("src/door-bang-1wav-14449.wav");  // Ajusta la ruta según la ubicación de tu archivo
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(explosionSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();  // Reproducir el sonido de explosión
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void explosionCohete() {

        if (!pararMusica){
            return;
        }

        try {
            File explosionSoundFile = new File("src/081895_impact_wav-43951.wav");  // Ajusta la ruta según la ubicación de tu archivo
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(explosionSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();  // Reproducir el sonido de explosión
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void muerteNave() {
        if (pararMusica){
            try {
                File explosionSoundFile = new File("src/mixkit-arcade-retro-game-over-213.wav");  // Ajusta la ruta según la ubicación de tu archivo
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(explosionSoundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();  // Reproducir el sonido de explosión
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Shot shoot() {
        if (pararMusica){
            explosionDisparos();
        }

        return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
    }

    public void update() {
        if(exploding) explosionStep++;
        destroyed = explosionStep > EXPLOSION_STEPS;
    }



    public void draw() {
        if(exploding) {
            gc.drawImage(EXPLOSION_IMG, explosionStep % EXPLOSION_COL * EXPLOSION_W, (explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1,
                    EXPLOSION_W, EXPLOSION_H,
                    posX, posY, size, size);

            muerteNave();

        }
        else {
            gc.drawImage(PLAYER_IMG, posX, posY, size, size);
        }
    }

    public boolean colide(Rocket other) {
        int d = distance(this.posX + size / 2, this.posY + size /2,
                other.posX + other.size / 2, other.posY + other.size / 2);
        return d < other.size / 2 + this.size / 2 ;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;

        explosionCohete();

        /*
        String cancion1="src/clip-1-muzica-pentru-reclame-" +
                "film-calitate-24-biti-fisier" +
                "-audio-wav-viteza-124-bpm-durata-105-min-90.wav";

        String cancion2="src/clip-2-muzica-pentru-reclama-film-24-biti-wav-120-bpm-9013.wav";

        HiloMusical hiloMusical=new HiloMusical(cancion1,cancion2);

        if (hiloMusical!=null){
            hiloMusical.detenerMusica();
        }
         */
    }
}