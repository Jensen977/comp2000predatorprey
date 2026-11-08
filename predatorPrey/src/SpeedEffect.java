@FunctionalInterface 
public interface SpeedEffect {
    int apply(Creature creature, int speed);
}

abstract class SpeedEffectDecorator implements SpeedEffect {
    protected SpeedEffect wrappedEffect;

    public SpeedEffectDecorator(SpeedEffect decoratedEffect) {
        this.wrappedEffect = decoratedEffect;
    }
}

// High Starvation halves speed (minimum speed of 1)
final class StarvationSlowDown extends SpeedEffectDecorator {
    private static final int STARVATION_THRESHOLD = 70;
}


// After eight consecutive moving ticks, reduce speed by 1 (minimum speed of 1)
final class FatigueSlowDown extends SpeedEffectDecorator {
}


// Temporary speed boost applied after the two possible slowdowns
final class SpeedBoost extends SpeedEffectDecorator {
    private static final int BONUS_SPEED = 2;
}