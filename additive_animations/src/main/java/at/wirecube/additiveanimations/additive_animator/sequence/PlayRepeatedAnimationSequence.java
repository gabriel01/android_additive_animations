package at.wirecube.additiveanimations.additive_animator.sequence;

import java.util.List;


class PlayRepeatedAnimationSequence<T extends AnimationSequence> extends PlayTogetherAnimationSequence<T> {

	private final int repeatCount;

	private int currentRepetitions = 0;


	public PlayRepeatedAnimationSequence(int repeatCount, List<T> animations) {

		this.repeatCount = repeatCount;
		this.animations.addAll(animations);

		addEndAction(wasCancelled -> {

			if(!wasCancelled){
				currentRepetitions++;
				if( currentRepetitions < repeatCount){
					start();
				}
			}
		});
	}
}
