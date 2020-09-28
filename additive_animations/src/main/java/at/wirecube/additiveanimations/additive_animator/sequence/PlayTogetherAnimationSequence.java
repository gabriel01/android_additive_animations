package at.wirecube.additiveanimations.additive_animator.sequence;

import java.util.List;

import at.wirecube.additiveanimations.additive_animator.AnimationEndListener;


public class PlayTogetherAnimationSequence<T extends AnimationSequence> extends AnimationSequence<T> {

	private long delayInSequence;

	public PlayTogetherAnimationSequence() {

	}

	public PlayTogetherAnimationSequence(List<T> animations) {

		this.animations.addAll(animations);
	}


	@Override
	public void start() {

		for (AnimationSequence sequence : animations) {
			sequence.setDelayInSequence(delayInSequence);
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


		for (AnimationSequence sequence : this.animations) {
			if (sequence.getTotalDurationInSequence() > longestDuration) {
				longestDuration = sequence.getTotalDurationInSequence();
			}
		}
		return longestDuration + delayInSequence;
	}



	@Override
	public T addEndAction(AnimationEndListener endListener) {

		if (animations.size() > 0) {
			AnimationSequence longestLastingAnimation = getLongestLastingAnimattion();
			if (longestLastingAnimation != null) {
				longestLastingAnimation.addEndAction(endListener);
			}
		}
		return self();
	}

	private AnimationSequence getLongestLastingAnimattion() {

		AnimationSequence longestLastingAnimation = null;
		long              longestDuration         = 0;

		for (AnimationSequence sequence : animations) {
			if (sequence.getTotalDurationInSequence() > longestDuration) {
				longestLastingAnimation = sequence;
			}
		}

		return longestLastingAnimation;

	}
}
