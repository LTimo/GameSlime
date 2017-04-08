package sk.actplus.slime.other;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import sk.actplus.slime.screens.GUI;
import sk.actplus.slime.screens.PlayScreen;

import static sk.actplus.slime.constants.Values.PPM;
import static sk.actplus.slime.constants.Values.finalPPM;

/**
 * Created by Ja on 8.4.2017.
 */

public class CollisionListener implements ContactListener {

    PlayScreen screen;
    GUI gui;

    public CollisionListener(PlayScreen screen, GUI gui) {
        this.screen = screen;
        this.gui = gui;
    }

    @Override
    public void beginContact(Contact contact) {

        if ((contact.getFixtureA().getUserData() != null) && (contact.getFixtureB().getUserData() != null)) {
            String fixAData, fixBData;
            fixAData = contact.getFixtureA().getUserData().toString();
            fixBData = contact.getFixtureB().getUserData().toString();
            if (((fixAData == "trap") && (fixBData == "player")) || ((fixAData == "player") && (fixBData == "trap"))) {
                screen.gameOver();
            }

            if (((fixAData == "block") && (fixBData == "player")) || ((fixAData == "player") && (fixBData == "block"))) {
                screen.jumped = false;
                //PPM = finalPPM;

            }

            if (((fixAData == "cookie") && (fixBData == "player")) || ((fixAData == "player") && (fixBData == "cookie"))) {
                screen.score++;

                if (fixAData.toString() == "cookie") {
                    loop:for (int i = 0; i < screen.blocks.size; i++) {
                        if (screen.blocks.get(i).getFixtureList().first() == contact.getFixtureA()) {
                            screen.blocks.removeIndex(i);
                            break loop;
                        }
                    }

                }

                if (fixBData.toString() == "cookie") {
                    loop:for (int i = 0; i < screen.blocks.size; i++) {
                        if (screen.blocks.get(i).getFixtureList().first() == contact.getFixtureB()) {
                            screen.blocks.removeIndex(i);
                            break loop;
                        }
                    }
                }

                gui.updateScore(screen.score);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
