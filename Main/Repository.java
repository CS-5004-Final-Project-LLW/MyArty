package Main;

import Object.Cannon;
import Object.Target;

/**
 * A class for storing game objects like cannon and target
 */
public class Repository {
    private Cannon cannon;
    private Target target;

    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Repository(Cannon cannon, Target target) {
        setCannon(cannon);
        setTarget(target);
    }



}
