package Main;

import Object.Cannon;
import Object.Explosion;
import Object.Target;

/**
 * A class for storing game objects like cannon and target
 */
public class Repository {
    private Cannon cannon;
    private Target target;
    private Explosion explosion;

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
    public Explosion getExplosion() {
        return explosion;
    }

    public Repository(Cannon cannon, Target target) {
        setCannon(cannon);
        setTarget(target);
    }



}
