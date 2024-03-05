package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Constants.PPM;

public class Player extends GameEntity {

    private int jumpCounter; // e5

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;   // e5
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        // e5
        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    // e5
    private void checkUserInput() {
        velX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            velX = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            velX = -1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2) {
            float force = body.getMass() * 18;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);  // double jump
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true);
            jumpCounter++;
        }

        // reset jumpcounter
        if (body.getLinearVelocity().y == 0) {   // hit floor after jump
            jumpCounter = 0;
        }

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25 );
    }
}
