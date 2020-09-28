package at.wirecube.additiveanimations.additive_animator.sequence;

import java.util.Arrays;

import at.wirecube.additiveanimations.additive_animator.AnimationEndListener;


public class PlayWithStaggerAnimationSequence <T extends AnimationSequence>extends AnimationSequence<T> {

    private final long stagger;
    private long delayInSequence;


    public PlayWithStaggerAnimationSequence(long stagger) {
        this.stagger = stagger;
    }

    public PlayWithStaggerAnimationSequence(long stagger, T... animations) {
        this.stagger = stagger;
        this.animations.addAll(Arrays.asList(animations));
    }




    @Override
    public void start() {
        long totalDelay = 0;
        for(AnimationSequence sequence : animations) {
            sequence.setDelayInSequence(totalDelay + this.delayInSequence);
            totalDelay += this.stagger;
            sequence.start();
        }
    }

    @Override
    public void setDelayInSequence(long delay) {
        this.delayInSequence = delay;
    }

    @Override
    public long getTotalDurationInSequence() {
        long longestDuration = 0;
        long currentStagger = 0;
        for (AnimationSequence sequence : animations) {
            long duration = sequence.getTotalDurationInSequence() + currentStagger;
            if(duration > longestDuration) {
                longestDuration = duration;
            }
            currentStagger += stagger;
        }
        return longestDuration + delayInSequence;
    }

    @Override
    public T addStartAction(Runnable r) {

        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public T addEndAction(AnimationEndListener endListener) {

        throw new IllegalStateException("Not yet implemented");
    }
}
