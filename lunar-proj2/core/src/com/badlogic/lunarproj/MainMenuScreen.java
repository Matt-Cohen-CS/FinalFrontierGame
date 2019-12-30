package com.badlogic.lunarproj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.geom.Rectangle2D;

public class MainMenuScreen implements Screen {
	final Lunar game;
	OrthographicCamera camera;

	Texture background;
	Sprite bgSprite;

	Texture title;
	Sprite titleSprite;

	Texture select;
	Sprite selectSprite;

	Texture input;
	Sprite inputSprite;

    Texture instructions;
    Sprite instructions_Sprite;

	Texture iKey;
	Sprite iKeySprite;

	Texture qKey;
	Sprite qKeySprite;

	Texture quit_txt;
	Sprite quit_txtSprite;

	Texture screen1;
	Sprite screen1Sprite;

	Texture screen2;
	Sprite screen2Sprite;

	Texture screen3;
	Sprite screen3Sprite;

	Music backgroundMusic;
	boolean gamewon;
	boolean gamewon1;
	boolean gamewon2;
	public MainMenuScreen(Lunar gam) {
		game = gam;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainmenuscreen.mp3")); // Background music
		backgroundMusic.setLooping(true);
		/*
			Initializing texture, sprites and images
		 */
		background = new Texture("screen3.jpg");
		bgSprite = new Sprite(background);
		bgSprite.setSize(800,480);
        title = new Texture("title.png");
        titleSprite = new Sprite(title);
        titleSprite.setSize(630, 80);
        titleSprite.setCenter(410,415);
		select = new Texture("txt_select.png");
		selectSprite = new Sprite(select);
		selectSprite.setSize(340, 50);
		selectSprite.setCenter(410,350);
		input = new Texture("input.png");
		inputSprite = new Sprite(input);
		inputSprite.setSize(250, 28);
		inputSprite.setCenter(410,300);
		screen1 = new Texture("menuscreen1.png");
		Image image = new Image();
		screen1Sprite = new Sprite(screen1);
		screen1Sprite.setSize(240, 125);
		screen1Sprite.setCenter(135,215);
		screen2 = new Texture("menuscreen2.png");
		screen2Sprite = new Sprite(screen2);
		screen2Sprite.setSize(240, 125);
		screen2Sprite.setCenter(400,215);
		screen3 = new Texture("menuscreen3.png");
		screen3Sprite = new Sprite(screen3);
		screen3Sprite.setSize(240, 125);
		screen3Sprite.setCenter(665,215);
		instructions = new Texture("instructions.png");
		instructions_Sprite = new Sprite(instructions);
		instructions_Sprite.setSize(230, 40);
		instructions_Sprite.setCenter(380,115);
		iKey = new Texture("ikey.png");
		iKeySprite = new Sprite(iKey);
		iKeySprite.setSize(110, 30);
		iKeySprite.setCenter(560,112);
		quit_txt = new Texture("txt_quit.png");
		quit_txtSprite = new Sprite(quit_txt);
		quit_txtSprite.setSize(80, 40);
		quit_txtSprite.setCenter(380,50);
		qKey = new Texture("qkey.png");
		qKeySprite = new Sprite(qKey);
		qKeySprite.setSize(110, 30);
		qKeySprite.setCenter(485,50);
		// End initializing texture, sprites and images
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
		/*
			Drawing all sprites
		 */
		bgSprite.draw(game.batch);
		titleSprite.draw(game.batch);
		selectSprite.draw(game.batch);
		inputSprite.draw(game.batch);
		screen1Sprite.draw(game.batch);
		screen2Sprite.draw(game.batch);
		screen3Sprite.draw(game.batch);
		instructions_Sprite.draw(game.batch);
		iKeySprite.draw(game.batch);
		quit_txtSprite.draw(game.batch);
		qKeySprite.draw(game.batch);
		// end drawing sprites
        game.batch.end();
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) { // sets the game to lunarlanding
			game.setScreen(new LunarLanding(game));
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {// sets the game to lunarlanding2
			game.setScreen(new LunarLanding2(game));
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {// sets the game to lunarlanding3
			game.setScreen(new LunarLanding3(game));
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.I)) {// sets the game to Instructions
			game.setScreen(new Instructions(game));
			backgroundMusic.stop();
			dispose();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {// quits the game
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