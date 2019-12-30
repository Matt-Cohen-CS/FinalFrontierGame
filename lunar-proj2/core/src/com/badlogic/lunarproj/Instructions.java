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
	The Instructions class is used for the instructions screen in the mainmenu to direct users as to how to play the game.
 */
public class Instructions implements Screen {
	/*
		Instance variables
	 */
	final Lunar game;
	OrthographicCamera camera;
	Sprite bgSprite;
	Sprite mKeySprite;
	Sprite titleSprite;
	Sprite instructions_Sprite;
	Sprite backSprite;
	Texture title;
	Texture background;
	Texture instructions;
	Texture back;
	Texture mKey;
	Music backgroundMusic;
	/*
		Instructions constructor
	 */
	public Instructions(Lunar gam) {
		game = gam;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("instructions.mp3")); //sets backgroundmusic
		backgroundMusic.setLooping(true);
		/*
			Sprite declarations are used to create a more legible screen for the user. Each image are sprites for easy manipulation. For example,
			the "Instructions" portion of the instructions screen is actually a sprite. Breaking the screen in multiple sprites easily enables simple
			changes to the instructions screen.
		 */
		background = new Texture("splash.png"); //sets background image
		bgSprite = new Sprite(background);
		bgSprite.setSize(800,480);
		title = new Texture("instruction.png");
		titleSprite = new Sprite(title);
		titleSprite.setSize(600, 60);
		titleSprite.setCenter(410,440);
		instructions = new Texture("inst_txt.png");
		instructions_Sprite = new Sprite(instructions);
		instructions_Sprite.setSize(650, 280);
		instructions_Sprite.setCenter(410,260);
		back = new Texture("backtomenu.png");
		backSprite = new Sprite(back);
		backSprite.setSize(260, 38);
		backSprite.setCenter(410,90);
		mKey = new Texture("mkey.png");
		mKeySprite = new Sprite(mKey);
		mKeySprite.setSize(85, 27);
		mKeySprite.setCenter(410,45);
		// End creating instructions screen
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
			Drawing each sprite for instructions screen
		 */
		bgSprite.draw(game.batch);
		titleSprite.draw(game.batch);
		instructions_Sprite.draw(game.batch);
		backSprite.draw(game.batch);
		mKeySprite.draw(game.batch);

		game.batch.end();

		if (Gdx.input.isKeyPressed(Input.Keys.M)) {
			game.setScreen(new MainMenuScreen(game));
			backgroundMusic.stop();
			dispose();
		} // returns player to main menu screen

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
