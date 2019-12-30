/**
 * Part 3a of box2d physics demo from gamefromscratch.com - http://bit.ly/2eZWuSl
 * Introduces collisions with a static body (floor).
 *
 * Also uses libgdx InputProcessor interface - http://bit.ly/2eXUqv7
 *
 * To run, change appropriate line in Physics1-desktop -> DesktopLauncher.java.
 */

package com.badlogic.lunarproj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import static com.badlogic.lunarproj.State.FINISHED;

public class LunarLanding2 implements Screen {
     Lunar game;

   public  SpriteBatch batch;

    State state = State.RUN;
    long lastDropTime;
    boolean gameWon = false;
    float torque = 0.0f;
    boolean drawSprite = true;
    final float PIXELS_TO_METERS = 100f;
    float health = 1; //0 = dead, 1 = full
    OrthographicCamera camera;
    BitmapFont font;
    // The world, body, texture, rectangle, and sprite variables
    World world;
    Body landerBody;
    Body body;
    Body protecter;
    Body protecter1;
    Body protecter2;
    Body bodyEdgeScreen;
    Texture img;
    Texture asteroid;
    Texture landingpad;
    Texture background;
    Texture prot;
    Texture pause;
    Texture healthTxt;
    Texture controlBar;
    Texture laser;
    Texture blank;
    Texture blankG;
    Texture blankY;
    Texture blankR;
    Rectangle landerRect;
    Rectangle protRect;
    Rectangle protRect1;
    Rectangle protRect2;
    Rectangle rocketRect;
    Rectangle asteroidRect;
    Rectangle laserRect;
    Sprite sprite;
    Sprite backgroundSprite;
    Sprite landingPadSprite;
    Sprite asteroidSprite;
    Sprite protectorSprite;
    Sprite protectorSprite1;
    Sprite protectorSprite2;
    Sprite pauseSprite;
    Sprite healthTxtSprite;
    Sprite controlBarSprite;
    Sprite laserSprite;
    Array<Rectangle> asteroids;
    Array<Rectangle> lasers;
    //For Music/Sounds
    Music backgroundMusic;
    Sound crashSound;
    Sound successSound;
    Sound gameOverSound;
    Music thrust;
    //For contact
    boolean isContact;
    int contactCount;
    boolean crashed;





    public LunarLanding2(Lunar gam) {
        this.game = gam;

        // Create sounds, this includes background music and both crashing and landing sounds
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("cautious-path-01.mp3"));
        backgroundMusic.setLooping(true);
        crashSound = Gdx.audio.newSound(Gdx.files.internal("crash.wav"));
        successSound = Gdx.audio.newSound(Gdx.files.internal("Ta Da.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        thrust = Gdx.audio.newMusic(Gdx.files.internal("thrust.wav"));
        //End create sound

        /*
        Create texture
         */
        background = new Texture("bg2.jpg");
        img = new Texture("rocket.png");
        asteroid = new Texture("asteroid.png");
        laser = new Texture("laser.png");
        landingpad = new Texture(Gdx.files.internal("landingpad.png"));
        prot = new Texture(Gdx.files.internal("protectorPad.jpg"));
        pause = new Texture("pauseScreen.png");
        healthTxt = new Texture("health.png");
        blank = new Texture("blank.png");
        blankG = new Texture("blankG.png");
        blankY = new Texture("blankY.png");
        blankR = new Texture("blankR.png");
        batch = new SpriteBatch();
         /*
            Initializing most sprites used in game
         */
        // background sprite
        backgroundSprite = new Sprite(background);
        backgroundSprite.setBounds(0f, 0f, 1000f, 700f);
        backgroundSprite.setCenter(0, 0);
        //Sprite for Rocket
        sprite = new Sprite(img);
        sprite.setSize(65,65);
        sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);
        //Sprite for Landing Pad
        landingPadSprite = new Sprite (landingpad);
        landingPadSprite.setSize(80,10);
        //Sprite for protector pads
        protectorSprite = new Sprite(prot);
        protectorSprite.setSize(80,10);
        protectorSprite.setPosition(-protectorSprite.getWidth()/2,-protectorSprite.getHeight()/2);
        protectorSprite1 = new Sprite(prot);
        protectorSprite1.setSize(80,10);
        protectorSprite1.setPosition(-protectorSprite.getWidth()/2,-protectorSprite.getHeight()/2);
        protectorSprite2 = new Sprite(prot);
        protectorSprite2.setSize(80,10);
        protectorSprite2.setPosition(-protectorSprite.getWidth()/2,-protectorSprite.getHeight()/2);
        //Sprite for Asteroid
        asteroidSprite = new Sprite (asteroid);
        asteroidSprite.setSize(50,50);
        // Sprite for lasers
        laserSprite = new Sprite(laser);
        laserSprite.setSize(10,20);
        // Sprite for Pause DEPRACATED
        pauseSprite = new Sprite(pause);
        pauseSprite.setSize(300,250);
        pauseSprite.setCenter(0,0);
        // health bar sprites
        healthTxtSprite = new Sprite(healthTxt);
        healthTxtSprite.setSize(70,24);
        healthTxtSprite.setCenter(-280,-215);
        controlBar = new Texture("controlBar.png");
        controlBarSprite = new Sprite(controlBar);
        controlBarSprite.setSize(Gdx.graphics.getWidth(),22);
        controlBarSprite.setCenter(0,230);
        // End initializing sprites

        // This world has a force acting downward in the y direction, i.e., some kind of gravity.
        //Velocity detection will be based off of this.
        world = new World(new Vector2(0, -1f),true);
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,2); // Added
        //Create World Body
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()/2 / PIXELS_TO_METERS);
        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 5000f;
        body.createFixture(fixtureDef);
        shape.dispose();
        //********* Creating the Landing Pad **********
        BodyDef landerDef = new BodyDef();
        landerDef.type = BodyDef.BodyType.StaticBody;
        landerDef.position.set(MathUtils.random(-2,2), -2);
        //Create a landing pad body.
        landerBody = world.createBody(landerDef);
        //Create a polygon shape with sprite
        PolygonShape landerShape = new PolygonShape();
        landerShape.setAsBox(landingPadSprite.getWidth()/2 / PIXELS_TO_METERS, landingPadSprite.getHeight()/2 / PIXELS_TO_METERS);
        //Create fixture definitions for lander.
        final FixtureDef landerFixtureDef = new FixtureDef();
        landerFixtureDef.shape = landerShape;
        landerBody.createFixture(landerFixtureDef);
        //Creating more "landing pads" for better protection
        BodyDef protDef = new BodyDef();
        BodyDef protDef1 = new BodyDef();
        BodyDef protDef2 = new BodyDef();
        protDef.type = BodyDef.BodyType.StaticBody;
        protDef.position.set(0,1);
        protDef1.type = BodyDef.BodyType.StaticBody;
        protDef1.position.set(-2, 0);
        protDef2.type = BodyDef.BodyType.StaticBody;
        protDef2.position.set(MathUtils.random(-2,1), 1);
        protecter = world.createBody(protDef);
        protecter1= world.createBody(protDef1);
        protecter2= world.createBody(protDef2);
        PolygonShape protecterShape = new PolygonShape();
        protecterShape.setAsBox(landingPadSprite.getWidth()/2 / PIXELS_TO_METERS, landingPadSprite.getHeight()/2 / PIXELS_TO_METERS);
        FixtureDef protFixDef = new FixtureDef();
        protFixDef.shape = protecterShape;
        protecter.createFixture(protFixDef);
        protecter1.createFixture(protFixDef);
        protecter2.createFixture(protFixDef);
        landerBody.createFixture(landerFixtureDef);
        landerShape.dispose();
        protecterShape.dispose();
        //End creating protectors

        // create Rectangle Asteroids
        asteroids = new Array<Rectangle>();
        spawnAsteroid();
        // create Rectangle lasers
        lasers = new Array<Rectangle>();
        spawnLaser();
        spawnLaser();

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer.
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 20/PIXELS_TO_METERS;
        bodyDef2.position.set(0, 0);
        //Create fixtures for all 4 edges of the screen so rocket won't go out of bounds.
        FixtureDef fixtureDef2 = new FixtureDef();
        FixtureDef fixtureDef3 = new FixtureDef();
        FixtureDef fixtureDef4 = new FixtureDef();
        FixtureDef fixtureDef5 = new FixtureDef();
        //Set up EdgeShapes, positions for each edge, and assign fixtures to shapes.
        //*********** BOTTOM EDGE ***********
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2, -h/2, w/2, -h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();

        //*********** RIGHT EDGE ***********
        EdgeShape edgeShapeRight = new EdgeShape();
        edgeShapeRight.set(w/2, -h/2, w/2, h/2+5);
        fixtureDef3.shape = edgeShapeRight;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef3);
        edgeShapeRight.dispose();

        //*********** LEFT EDGE ***********
        EdgeShape edgeShapeLeft = new EdgeShape();
        edgeShapeLeft.set(-w/2, -h/2, -w/2, h/2+5);
        fixtureDef4.shape = edgeShapeLeft;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef4);
        edgeShapeLeft.dispose();

        //*********** TOP EDGE ***********
        EdgeShape edgeShapeTop = new EdgeShape();
        edgeShapeTop.set(w/2, h/2+1, -w/2, h/2+1);
        fixtureDef5.shape = edgeShapeTop;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef5);
        edgeShapeTop.dispose();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //Set up Contact Listener to detect velocity impact of rocket and landingPad.
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //Intiialize fixtures (won't be used, for practice purposes only).
               // Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
                //If fixtureA is the rocket and Fixture B is the landerBody, print Landed statement upon contact.
                if(contact.getFixtureA().getBody() == body && contact.getFixtureB().getBody()==landerBody && !crashed)
                {
                    if(body.getLinearVelocity().y>=-1){ //If linear velocity is less than amount, then they landed safely
                        isContact = true;
                        contactCount = 75;
                        successSound.play(); // Ta Da will play if the user lands safely
                    }
                    else{
                        crashed = true; //  This ensures the playe
                        // r cannot "win" off a bounce
                        contactCount = -250;
                        crashSound.play(); // Crashing car will play if the user "crashes"
                        health -= 0.1;  //Health drops when crash occurs
                    }
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) { }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) { }
        });

    }
    /*
           creates asteroids and adds them to an array
        */
    private void spawnAsteroid() {
        Rectangle asteroidRect = new Rectangle();
        asteroidRect.x = MathUtils.random(-500, 800-64);
        asteroidRect.y = 480;
        asteroidRect.width = 50;
        asteroidRect.height = 50;
        asteroids.add(asteroidRect);
        lastDropTime = TimeUtils.nanoTime();
    }
    /*
              creates lasers and adds them to an array
    */
    private void spawnLaser() {
        Rectangle laserRect = new Rectangle();
        laserRect.x = MathUtils.random(-500, 800 - 64);
        laserRect.y = -500;
        laserRect.width = 10;
        laserRect.height = 20;
        lasers.add(laserRect);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        camera.update();
        /*
            Setting sprites to be set to the position of their bodies
         */
        sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth() / 2,
                (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight() / 2);
        protectorSprite.setPosition((protecter.getPosition().x * PIXELS_TO_METERS) - protectorSprite.getWidth() / 2,
                (protecter.getPosition().y * PIXELS_TO_METERS) - protectorSprite.getHeight() / 2);
        protectorSprite1.setPosition((protecter1.getPosition().x * PIXELS_TO_METERS) - protectorSprite1.getWidth() / 2,
                (protecter1.getPosition().y * PIXELS_TO_METERS) - protectorSprite1.getHeight() / 2);
        protectorSprite2.setPosition((protecter2.getPosition().x * PIXELS_TO_METERS) - protectorSprite2.getWidth() / 2,
                (protecter2.getPosition().y * PIXELS_TO_METERS) - protectorSprite2.getHeight() / 2);
        landingPadSprite.setPosition((landerBody.getPosition().x * PIXELS_TO_METERS) - landingPadSprite.getWidth() / 2,
                (landerBody.getPosition().y * PIXELS_TO_METERS) - landingPadSprite.getHeight() / 2);
        // End aligning sprites

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        body.setFixedRotation(true);
        batch.setProjectionMatrix(camera.combined);
        //BEGIN
        batch.begin();
        /*
            Creates the rectangle to fit over the sprite, this allows for easier collision detection
         */
        backgroundSprite.draw(batch);
        controlBarSprite.draw(batch);
        rocketRect = sprite.getBoundingRectangle();
        landerRect = landingPadSprite.getBoundingRectangle();
        protRect = protectorSprite.getBoundingRectangle();
        protRect1 = protectorSprite1.getBoundingRectangle();
        protRect2 = protectorSprite2.getBoundingRectangle();
        landingPadSprite.draw(batch);
        //Draw background of Health Bar to indicate full value
        batch.draw(blank, -320, -240, Gdx.graphics.getWidth(), 10);
         /*
            HEALTH BAR
         */
        //Add Green when Health is full, yellow when average, red when low.
        if (health >= 0.7) {
            //Draw decreasable Health Bar - multiply width by health
            batch.draw(blankG, -320, -240, Gdx.graphics.getWidth() * health, 10);
        } else if (health >= 0.3) {
            batch.draw(blankY, -320, -240, Gdx.graphics.getWidth() * health, 10);
        } else {
            batch.draw(blankR, -320, -240, Gdx.graphics.getWidth() * health, 10);
        }

        if (health <= 0) {
            //End Game
            backgroundMusic.stop();
            gameOverSound.play();
            game.setScreen(new GameOverScreen(game));
            this.state = FINISHED;
        }

        //Display Health Bar Title
        healthTxtSprite.draw(batch);

        //Spawns asteroids
        for (Rectangle asteroidRect : asteroids) {
            asteroidSprite.setPosition(asteroidRect.x, asteroidRect.y);
            asteroidSprite.draw(batch);
        }//End
        //Spawns lasers
        for (Rectangle lasetRect : lasers) {
            laserSprite.setPosition(lasetRect.x, lasetRect.y);
            laserSprite.draw(batch);
        }//End
        if (drawSprite)
             /*
                Drawing rocket sprite, and protector pad sprites
             */
            sprite.draw(batch);
            protectorSprite.draw(batch);
            protectorSprite1.draw(batch);
            protectorSprite2.draw(batch);
        //For contact
        /*
            COLLISION DETECTION
         */
        if (gameWon) {
            font.draw(batch, "You Landed The Craft Safely Please Press C To Continue! ",
                    -Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 12); // DEPRACATED
            font.setColor(Color.GREEN);
            sprite.setColor(0, 1, 0, 1);
            GameWin.gameWon2= true;
            lastDropTime += delta;
            //Will only set to victory if and only if all other levels are passed
            if(GameWin.gameWon1 && GameWin.gameWon2 && GameWin.gameWon3){
                game.setScreen(new VictoryScreen(game));
                backgroundMusic.stop();
                this.state = State.FINISHED;
            }
            else{
                backgroundMusic.stop();
                game.setScreen(new MainMenuScreen(game));
            }

        }
        if (contactCount == 0) {
            crashed = false;
        }
        if (contactCount > 0 && !crashed) {
            sprite.setColor(0, 1, 0, 1);
            isContact = false;
            contactCount--;
            gameWon = true;
        }
        /*
            These if statements allow for a blinking red color to indicate damage
         */
        else if (contactCount < 0) {
            if (contactCount < -200) {

                sprite.setColor(1, 0, 0, 1);

            } else if (contactCount < -150 && contactCount > -175) {

                sprite.setColor(1, 0, 0, 1);
            } else if (contactCount < -100 && contactCount > -125) {

                sprite.setColor(1, 0, 0, 1);
            } else if (contactCount < -50 && contactCount > -75) {

                sprite.setColor(1, 0, 0, 1);
            } else {
                sprite.setColor(1, 1, 1, 1);
            }
            contactCount++;
            font.draw(batch, "You Crashed, try again once the craft is normal! ",
                    -Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 9);
        } else {
            sprite.setColor(1, 1, 1, 1);

        }
        //End collision detection
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) { // Pauses the game
            if (state == State.RUN) {
                setGameState(State.PAUSE);
                backgroundMusic.stop();
            }
        }
         /*
            Check condition to pause the game
         */
        if (state == State.PAUSE) {
            camera.update();
            background = new Texture("pauseScreen.png");
            backgroundSprite = new Sprite(background);
            backgroundSprite.setBounds(0f, 0f, 700, 500f);
            backgroundSprite.setCenter(0, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) { // resumes the game
                background = new Texture("bg1.jpg");
                backgroundSprite = new Sprite(background);
                backgroundSprite.setBounds(0f, 0f, 1000f, 700f);
                backgroundSprite.setCenter(0, 0);
                setGameState(State.RUN);
                backgroundMusic.play();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.M)) { // sets screen to mainmenu
                game.setScreen(new MainMenuScreen(game));
                backgroundMusic.stop();
                dispose();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                System.exit(1);
            }
        }
        //Switch case to check if game is paused or running
        switch (state) {
            //Start
            case RUN:
                //Start
                world.step(1f / 60f, 6, 2);
                /*
                    Spawning of the asteroids/lasers
                 */
                if (TimeUtils.nanoTime() - lastDropTime > (1000000000 / 4)) {
                    spawnAsteroid();
                    spawnLaser();
                }
                Iterator<Rectangle> iter = asteroids.iterator();
                while (iter.hasNext()) {
                    Rectangle asteroid = iter.next();
                    asteroid.y -= 200 * Gdx.graphics.getDeltaTime();

                    if (asteroid.y + 255 < 0)
                        iter.remove();
                    if (asteroid.overlaps(rocketRect)) {
                        //Decrease Health
                        health -= 0.1;
                        iter.remove();
                        sprite.setColor(1, 0, 0, 1);

                    }
                    if(asteroid.overlaps(protRect) || asteroid.overlaps(protRect1) || asteroid.overlaps(protRect2)){
                        iter.remove();
                    }
                }
                Iterator<Rectangle> iter1 = lasers.iterator();

                while (iter1.hasNext()) {
                    Rectangle lasers = iter1.next();
                    lasers.y += 100 * Gdx.graphics.getDeltaTime();

                    if (lasers.y -255 > 1000)
                        iter1.remove();
                    if (lasers.overlaps(rocketRect)) {
                        //Decrease Health
                        health -= 0.1;
                        iter1.remove();
                        sprite.setColor(1, 0, 0, 1);

                    }
                } //End of spawning of lasers/asteroids
                if (Gdx.input.isKeyPressed(Input.Keys.C) && gameWon) {  //Returns to mainmenu if the game is won
                    backgroundMusic.stop();
                    game.setScreen(new MainMenuScreen(game));
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) { // Moves player character up
                    body.applyForceToCenter(0f, .1f, true);
                    thrust.play();
                }

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { // Moves player character to the right
                    body.applyForceToCenter(.05f, 0f, true);
                }

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { // Moves player character to the left
                    body.applyForceToCenter(-.05f, 0f, true);
                }

                if (Gdx.input.isKeyPressed(Input.Keys.M)) { // returns to mainmenu
                    game.setScreen(new MainMenuScreen(game));
                    backgroundMusic.stop();
                    dispose();
                }

                if (Gdx.input.isKeyPressed(Input.Keys.Q)) {// quits the game
                    System.exit(1);
                }


            case PAUSE:
                break;
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
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RUN;
    }

    @Override
    public void dispose() {
        img.dispose();
        world.dispose();
        backgroundMusic.dispose();
    }
    public void setGameState(State s) {
        this.state = s;
    }
}
