package com.badlogic.lunarproj;
/*
	The GameOverScreen class is the last screen the user will visit when they run out of health. This screen is just for the user to either quit the game
	or return to the mainmenu.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameOverScreen implements Screen {
	/*
		Instance variables
	 */
	final Lunar game;
	OrthographicCamera camera;
	Texture gameOver;
	Sprite gameOverSprite;
	Music backgroundMusic;
	Sound gameOverSound;
	/*
		Constructor for GameOverScreen
	 */
	public GameOverScreen(Lunar gam) {
		game = gam;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("lost.mp3")); // Sets backgroundmusic
		backgroundMusic.setLooping(true);

		gameOver = new Texture("gameOverScreen.png");
		gameOverSprite = new Sprite(gameOver);
		gameOverSprite.setSize(800,480); // creates gameoverSprite

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}
	/*
		GameOverScreen render for batch
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		gameOverSprite.draw(game.batch);
		//gameOverSound.play();

		game.batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.M)) {
			game.setScreen(new MainMenuScreen(game));
			backgroundMusic.stop();
			dispose();
		} // Returns user to mainmenu

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			System.exit(1);
		} // Quits the game

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		backgroundMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	    backgroundMusic.dispose();
	}
}