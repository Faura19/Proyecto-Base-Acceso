package com.spaceinvaders.spaceinvaders;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.Clip;

public class SpaceInvaders extends Application implements Serializable{

	private Clip clip;
	private HiloMusical hiloMusical;

	private static SpaceInvaders instancia;
	private boolean juegoPausado;
	private List<String> partidasListView;
	private int contador;
	private int botonMusica=0;

	public SpaceInvaders(){
		instancia=this;
	}

	public static SpaceInvaders getInstancia(){
		return instancia;
	}

	public void juegoParado(){
		juegoPausado=true;

	}

	public void juegoReanudado(){
		juegoPausado=false;
	}

	private boolean menuVisible=false;

	// Variables
	static final Random RAND = new Random();
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	static final int PLAYER_SIZE = 60;
	static final Image PLAYER_IMG = new Image("file:images/player.png");
	static final Image EXPLOSION_IMG = new Image("file:images/explosion.png");
	static final int EXPLOSION_W = 128;
	static final int EXPLOSION_ROWS = 3;
	static final int EXPLOSION_COL = 3;
	static final int EXPLOSION_H = 128;
	static final int EXPLOSION_STEPS = 15;
	static GraphicsContext gc;

	static final Image BOMBS_IMG[] = {
			new Image("file:images/1.png"),
			new Image("file:images/2.png"),
			new Image("file:images/3.png"),
			new Image("file:images/4.png"),
			new Image("file:images/5.png"),
			new Image("file:images/6.png"),
			new Image("file:images/7.png"),
			new Image("file:images/8.png"),
			new Image("file:images/9.png"),
			new Image("file:images/10.png"),
	};
	final int MAX_BOMBS = 10,  MAX_SHOTS = MAX_BOMBS * 2;
	boolean gameOver = false;

	static Rocket player;
	List<Shot> shots;
	List<Universe> univ;
	List<Bomb> Bombs;

	private double mouseX;
	private Stage mainStage;
	private StackPane rootPane;

	// Start
	public void start(Stage stage) throws Exception {
		this.mainStage=stage;

		String cancion1="src/clip-1-muzica-pentru-reclame-" +
				"film-calitate-24-biti-fisier" +
				"-audio-wav-viteza-124-bpm-durata-105-min-90.wav";

		String cancion2="src/clip-2-muzica-pentru-reclama-film-24-biti-wav-120-bpm-9013.wav";

		hiloMusical=new HiloMusical(cancion1,cancion2);
		hiloMusical.reproducirMusica(cancion1,cancion2);


		// Este metodo sirve para que al cerrar el javafx la musica pare
		stage.setOnCloseRequest(event -> {
			hiloMusical.detenerMusica();
			Platform.exit();
			System.exit(0);
		});

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		canvas.setCursor(Cursor.MOVE);

		canvas.setOnMouseMoved(e -> mouseX = e.getX());
		canvas.setOnMouseClicked(e -> {
			if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
			if(gameOver) {
				gameOver = false;
				setup();
			}
		});
		setup();


		Scene scene=new Scene(new StackPane(canvas));
		scene.setOnKeyPressed(event->{
			if(event.getCode()==KeyCode.X){
				hiloMusical.detenerMusica();
				Platform.exit();
			} else if(event.getCode()==KeyCode.P){
				hiloMusical.detenerMusica();
				timeline.pause();

			} else if (event.getCode()==KeyCode.O) {
				timeline.play();
				hiloMusical.reproducirMusica(cancion1,cancion2);

			} else if(event.getCode()==KeyCode.ESCAPE){

				try {
					FXMLLoader loader=new FXMLLoader(getClass().getResource("menu.fxml"));
					Stage menuRoot=new Stage();

					Scene rootPane=new Scene(loader.load());

					juegoParado();
					//hiloMusical.detenerMusica();
					menuRoot.setScene(rootPane);
					menuRoot.show();

					Menu menu=new Menu();


					//menu.continuarAction(timeline.play());

					if (menu.pararMusica==true){
						hiloMusical.detenerMusica();
					}

					Bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
						if(player.colide(e) && !player.exploding) {
							hiloMusical.detenerMusica();
						}
					});
				}catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});

		stage.setScene(scene);
		stage.setTitle("Space Invaders");
		stage.show();
	}

	public void quitarMusica(){
		hiloMusical.detenerMusica();
	}

	public void guardarEstadoJuego() {
		String[] rutas= {"src/savegame.dat","src/savedgame2.dat","src/savedgame3.dat",
				"src/savedgame4.dat","savedgame5.dat"};


		if (contador<rutas.length){
			try (FileOutputStream ruta = new FileOutputStream(rutas[contador]);
				 ObjectOutputStream object = new ObjectOutputStream(ruta)) {

				object.writeObject(player);
				object.writeObject(shots);
				object.writeObject(Bombs);
				object.writeObject(univ);

				contador++;
				System.out.println("Partida" + contador);


			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("No se pueden guardar mas partidas");
		}
		juegoParado();
	}

	public void cargarEstadoJuego() {
		try (FileInputStream ruta = new FileInputStream("src/savegame.dat");
			 ObjectInputStream fichero = new ObjectInputStream(ruta)) {

			player = (Rocket) fichero.readObject();
			shots = (List<Shot>) fichero.readObject();
			Bombs = (List<Bomb>) fichero.readObject();
			univ= (List<Universe>) fichero.readObject();

			System.out.println("El estado del juego ha sido cargado desde " + "src/savegame.dat");

		} catch (EOFException ee){
			System.out.println("No tienes esa partida guardada");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		juegoReanudado();
	}

	public void cargarEstadoJuego2() {
		try (FileInputStream ruta = new FileInputStream("src/savedgame2.dat");
			 ObjectInputStream fichero = new ObjectInputStream(ruta)) {

			player = (Rocket) fichero.readObject();
			shots = (List<Shot>) fichero.readObject();
			Bombs = (List<Bomb>) fichero.readObject();
			univ= (List<Universe>) fichero.readObject();

			System.out.println("El estado del juego ha sido cargado desde " + "src/savegame.dat");

		} catch (EOFException ee){
			System.out.println("No tienes esa partida guardada");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		juegoReanudado();
	}

	public void cargarEstadoJuego3() {
		try (FileInputStream ruta = new FileInputStream("src/savedgame3.dat");
			 ObjectInputStream fichero = new ObjectInputStream(ruta)) {

			player = (Rocket) fichero.readObject();
			shots = (List<Shot>) fichero.readObject();
			Bombs = (List<Bomb>) fichero.readObject();
			univ= (List<Universe>) fichero.readObject();

			System.out.println("El estado del juego ha sido cargado desde " + "src/savegame.dat");

		} catch (EOFException ee){
			System.out.println("No tienes esa partida guardada");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		juegoReanudado();
	}

	public void cargarEstadoJuego4() {
		try (FileInputStream ruta = new FileInputStream("src/savedgame4.dat");
			 ObjectInputStream fichero = new ObjectInputStream(ruta)) {

			player = (Rocket) fichero.readObject();
			shots = (List<Shot>) fichero.readObject();
			Bombs = (List<Bomb>) fichero.readObject();
			univ= (List<Universe>) fichero.readObject();

			System.out.println("El estado del juego ha sido cargado desde " + "src/savegame.dat");

		}catch (EOFException ee){
			System.out.println("No tienes esa partida guardada");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		juegoReanudado();
	}

	public void cargarEstadoJuego5() {
		try (FileInputStream ruta = new FileInputStream("src/savedgame5.dat");
			 ObjectInputStream fichero = new ObjectInputStream(ruta)) {

			player = (Rocket) fichero.readObject();
			shots = (List<Shot>) fichero.readObject();
			Bombs = (List<Bomb>) fichero.readObject();
			univ= (List<Universe>) fichero.readObject();

			System.out.println("El estado del juego ha sido cargado desde " + "src/savegame.dat");

		}catch (EOFException ee){
			System.out.println("No tienes esa partida guardada");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		juegoReanudado();
	}


	public void resumeGame() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));

		if (timeline != null) {
			timeline.play();
		}

	}

	// Setup the game, only executed once
	private void setup() {
		univ = new ArrayList<>();
		shots = new ArrayList<>();
		Bombs = new ArrayList<>();
		player = new Rocket(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE);
		player.score = 0;
		IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
	}

	private void playMusic(){

		/*
		try {
			File archivoSonido=new File("src/clip-1-muzica-pentru-reclame-film-" +
					"calitate-24-biti-fisier-audio-wav-viteza-124-bpm-durata-105-min-90.wav");
			AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(archivoSonido);
			clip= AudioSystem.getClip();
			clip.open(audioInputStream);

			clip.start();

			Scene scene=new Scene();
			scene.setOnKeyPressed(event->{
				if(event.getCode()==KeyCode.X){
					clip.stop();
				}

			});


		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		}catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
		 */

	}


	// Run graphics
	private void run(GraphicsContext gc) {
		gc.setFill(Color.grayRgb(20));
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(20));
		gc.setFill(Color.WHITE);
		gc.fillText("Score: " + player.score, 60,  20);

		String explosion="door-bang-1wav-14449.wav";

		if(gameOver) {
			hiloMusical.explosionFinalizar(explosion);
			gc.setFont(Font.font(35));
			gc.setFill(Color.YELLOW);
			gc.fillText("Game Over \n Your Score is: " + player.score + " \n Click to play again", WIDTH / 2, HEIGHT /2.5);
			//	return;
		}
		univ.forEach(Universe::draw);
		player.update();
		player.draw();
		player.posX = (int) mouseX;

		Bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
			if(player.colide(e) && !player.exploding) {
				player.explode();
				gameOver=true;

				Rocket rocket=new Rocket();
				rocket.muerteNave();
			}
		});

		for (int i = shots.size() - 1; i >=0 ; i--) {
			Shot shot = shots.get(i);
			if(shot.posY < 0 || shot.toRemove)  {
				shots.remove(i);
				continue;
			}
			shot.update();
			shot.draw();
			for (Bomb bomb : Bombs) {
				if(shot.colide(bomb) && !bomb.exploding) {
					player.score++;
					bomb.explode();
					shot.toRemove = true;
				}
			}
		}

		for (int i = Bombs.size() - 1; i >= 0; i--){
			if(Bombs.get(i).destroyed)  {
				Bombs.set(i, newBomb());
			}
		}

		gameOver = player.destroyed;
		if(RAND.nextInt(10) > 2) {
			univ.add(new Universe());
		}
		for (int i = 0; i < univ.size(); i++) {
			if(univ.get(i).posY > HEIGHT)
				univ.remove(i);
		}
	}

	private Bomb newBomb() {
		return new Bomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, RAND.nextInt(BOMBS_IMG.length));
	}

	static int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	public static void main(String[] args) {
		launch();
	}
}