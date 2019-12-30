package com.badlogic.lunarproj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PauseScreen implements Screen {
	final Lunar game;
	OrthographicCamera camera;
	LunarLanding game1;
	Texture pause;
	Sprite pauseSprite;

	Music backgroundMusic;

	public PauseScreen(final Lunar gam) {
		game = gam;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("lost.mp3"));
		backgroundMusic.setLooping(true);
		pause = new Texture("pauseScreen.png");
		pauseSprite = new Sprite(pause);
		pauseSprite.setSize(800,480);
		//pauseSprite.setCenter(0, 0);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		pauseSprite.draw(game.batch);

		game.batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			//this.hide();
			//new LunarLanding(game).show();
			game.setScreen(new LunarLanding(game));
			//game.setScreen(game);
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.M)) {
			game.setScreen(new MainMenuScreen(game));
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
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