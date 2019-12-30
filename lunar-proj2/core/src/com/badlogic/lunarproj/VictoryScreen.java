package com.badlogic.lunarproj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
/*
	Final screen to indicate the user "beat" the game
 */
public class VictoryScreen implements Screen {
	final Lunar game;
	OrthographicCamera camera;
	Texture victory;
	Sprite victorySprite;
	Music backgroundMusic;

	public VictoryScreen(Lunar gam) {
		game = gam;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("victory.mp3"));//sets background music
		backgroundMusic.setLooping(true);
		/*
			Creating sprites, textures, and images
		 */
		victory = new Texture("victoryScreen.png");
		victorySprite = new Sprite(victory);
		victorySprite.setSize(800,480);
		// End creating sprites/textures/images
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		//Changing the GameWin boolean values to ensure the player must beat three levels before the victory screen will show
		GameWin.gameWon1 = false;
		GameWin.gameWon2 = false;
		GameWin.gameWon3 = false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		victorySprite.draw(game.batch);

		game.batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.M)) { // sets the game to mainmenu
			game.setScreen(new MainMenuScreen(game));
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) { // quits the game
			System.exit(1);
		}

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