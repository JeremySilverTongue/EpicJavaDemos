package com.udacity.curveball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball {

    public static final String TAG = Ball.class.getSimpleName();

    public static final Color COLOR = Color.RED;
    private static final float GRAVITY = 10;
    public static final float DENSITY = .01f;
    public static final float DRAG = .05f;

    public float radius;
    public float mass;
    public Vector2 position;
    public Vector2 velocity;
    public float rotation;
    public float angularPosition;

    Ball(float radius, float mass, Vector2 position, Vector2 velocity, float rotation) {
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.rotation = rotation;
        angularPosition = 0;
    }

    void render(float deltaTime, ShapeRenderer renderer) {

        float magnusForce = DENSITY * velocity.len() * MathUtils.PI2 * radius * radius * rotation;

//        Gdx.app.log(TAG,
//                String.format(
//                        Locale.getDefault(),
//                        "Magnus: %.2f Old rotation: %.2f",
//                        magnusForce,
//                        rotation
//                ));

        Vector2 acceleration = velocity.cpy();
        if (rotation >= 0) {
            acceleration.rotate90(1);
        } else {
            acceleration.rotate90(-1);
        }

        acceleration.setLength(magnusForce / mass - DRAG * velocity.len());
        acceleration.add(0, -GRAVITY);
        velocity.mulAdd(acceleration, deltaTime);
        position.mulAdd(velocity, deltaTime);


        float oldRotation = rotation;
        float currentRotationalEnergy = mass * radius * radius * rotation * rotation / 4;
        float energyLoss = magnusForce / mass * deltaTime;
        float newRotationalEnergy = Math.max(currentRotationalEnergy - energyLoss, 0);
        rotation = Math.signum(rotation) * (float) Math.sqrt(newRotationalEnergy * 4 / (mass * radius * radius));

        angularPosition += rotation * MathUtils.PI2 * deltaTime;

        Vector2 angleLineEnd = position.cpy();
        angleLineEnd.add(radius * MathUtils.cos(angularPosition), radius * MathUtils.sin(angularPosition));

//        Gdx.app.log(TAG,
//                String.format(
//                        Locale.getDefault(),
//                        "Magnus: %.2f Old rotation: %.2f Old e: %.2f loss: %.2f new e: %.2f new rotation: %.2f",
//                        magnusForce,
//                        oldRotation,
//                        currentRotationalEnergy,
//                        energyLoss,
//                        newRotationalEnergy,
//                        rotation));


        renderer.circle(position.x, position.y, radius);


        renderer.line(position, angleLineEnd);

    }


}
