package com.spaceinvaders.spaceinvaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static com.spaceinvaders.spaceinvaders.SpaceInvaders.*;

// Enemy
public class Bomb extends Rocket {
    int SPEED = (score/5)+2;

    public Bomb(int posX, int posY, int size, int imgIndex) {
        super(posX, posY, size);
        this.imgIndex = imgIndex;
    }

    public void explosion() {
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

    public void update() {
        super.update();
        if(!exploding && !destroyed) posY += SPEED;
        if(posY > HEIGHT){
            destroyed = true;
            explosion();
        }
    }

    public void draw() {
        if(exploding) {
            gc.drawImage(EXPLOSION_IMG, explosionStep % EXPLOSION_COL * EXPLOSION_W, (explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1,
                    EXPLOSION_W, EXPLOSION_H,
                    posX, posY, size, size);
        }
        else {
            gc.drawImage(BOMBS_IMG[imgIndex], posX, posY, size, size);
        }
    }
}