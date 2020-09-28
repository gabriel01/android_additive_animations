package at.wirecube.additiveanimations.additive_animator.sequence;

import java.util.List;

import at.wirecube.additiveanimations.additive_animator.AnimationEndListener;


class PlaySequentiallyAnimationSequence<T extends AnimationSequence> extends AnimationSequence<T> {

	private long delay = 0;


	public PlaySequentiallyAnimationSequence() {

	}

	public PlaySequentiallyAnimationSequence(List<T> animations) {

		this.animations.addAll(animations);
	}

	@Override
	public void setDelayInSequence(long delay) {

		this.delay = delay;
	}

	@Override
	public long getTotalDurationInSequence() {

		long totalDelay = delay;
		for (int i = 0; i < animations.size(); i++) {
			AnimationSequence sequence = animations.get(i);
			totalDelay += sequence.getTotalDurationInSequence();
		}
		return totalDelay + this.delay;
	}



	@Override
	public T addEndAction(AnimationEndListener endListener) {

		if (animations.size() > 0) {
			animations.get(animations.size() - 1).addEndAction(endListener);
		}
		return self();
	}

	@Override
	public void start() {

		long totalDelay = 0;

		for (AnimationSequence sequence : animations) {

			long totalDurationInSequence = sequence.getTotalDurationInSequence();
			sequence.setDelayInSequence(totalDelay + this.delay);
			totalDelay += totalDurationInSequence;
			sequence.start();
		}
	}
}
