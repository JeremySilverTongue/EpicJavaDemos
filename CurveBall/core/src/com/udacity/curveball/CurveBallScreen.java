package com.udacity.curveball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class CurveBallScreen extends ScreenAdapter {


    ScreenViewport viewport;

    ShapeRenderer renderer;


    Array<Ball> balls;


    @Override
    public void show() {
        viewport = new ScreenViewport();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        balls = new Array<>();


        Timer.schedule(
                new Timer.Task() {
                    @Override
                    public void run() {
                        balls.add(new Ball(10, 10, new Vector2(0, 150), new Vector2(50, 0), MathUtils.random(-1.0f, 1.0f)));
                    }
                },
                0, 1, 1000000);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin();


        for (Ball ball : balls) {
            ball.render(delta, renderer);
            if (ball.position.y + ball.radius < 0) {
                balls.removeValue(ball, true);
            }
        }

        renderer.end();

    }

    @Override
    public void hide() {
        renderer.dispose();
    }
}
