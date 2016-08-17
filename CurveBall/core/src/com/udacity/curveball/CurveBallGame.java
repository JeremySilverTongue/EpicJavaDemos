package com.udacity.curveball;

import com.badlogic.gdx.Game;

public class CurveBallGame extends Game {

    @Override
    public void create() {

        setScreen(new CurveBallScreen());
    }
}
