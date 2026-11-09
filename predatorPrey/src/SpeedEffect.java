@FunctionalInterface 
public interface SpeedEffect {
    int apply(Creature creature, int speed);
}

abstract class SpeedEffectDecorator implements SpeedEffect {
    protected SpeedEffect wrappedEffect;

    public SpeedEffectDecorator(SpeedEffect wrappedEffect) {
        this.wrappedEffect = wrappedEffect;
    }
}

// High Starvation halves speed (minimum speed of 1)
final class StarvationSlowDown extends SpeedEffectDecorator {
    private static final int STARVATION_LIMIT = 70;

    StarvationSlowDown(SpeedEffect wrappedEffect) {
        super(wrappedEffect);
    }

    @Override
    public int apply(Creature creature, int speed) {
        int current = wrappedEffect.apply(creature, speed);
        return creature.getStarvation() >= STARVATION_LIMIT 
            ? Math.max(1, current / 2) : current;
    }
}


// After eight consecutive moving ticks, reduce speed by 1 (minimum speed of 1)
final class FatigueSlowDown extends SpeedEffectDecorator {
    private static final int FATIGUE_LIMIT = 8;

    FatigueSlowDown(SpeedEffect wrappedEffect) {
        super(wrappedEffect);
    }

    @Override
    public int apply(Creature creature, int speed) {
        int current = wrappedEffect.apply(creature, speed);
        return creature.getConsecutiveMovingTicks() >= FATIGUE_LIMIT 
            ? Math.max(1, current - 1) : current;
    }
}


// Temporary speed boost applied after the two possible slowdowns
final class SpeedBoost extends SpeedEffectDecorator {
    private static final int BONUS_SPEED = 2;

    SpeedBoost(SpeedEffect wrappedEffect) {
        super(wrappedEffect);
    }

    @Override
    public int apply(Creature creature, int speed) {
        int current = wrappedEffect.apply(creature, speed);
        return creature.hasSpeedBoost() ? current + BONUS_SPEED : current;
    }
}