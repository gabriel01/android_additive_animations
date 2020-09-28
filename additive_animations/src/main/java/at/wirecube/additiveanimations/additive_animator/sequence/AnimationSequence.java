package at.wirecube.additiveanimations.additive_animator.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.wirecube.additiveanimations.additive_animator.AnimationEndListener;


public abstract class AnimationSequence<T extends AnimationSequence> {


	protected final ArrayList<T> animations = new ArrayList<>();


	public static AnimationSequence playTogether(AnimationSequence... animations) {

		return new PlayTogetherAnimationSequence(Arrays.asList(animations));
	}

	public static AnimationSequence playTogether(List<AnimationSequence> animations) {

		return new PlayTogetherAnimationSequence(animations);
	}

	public static AnimationSequence playSequentially(AnimationSequence... animations) {

		return new PlaySequentiallyAnimationSequence(Arrays.asList(animations));
	}

	public static AnimationSequence playSequentially(List<AnimationSequence> animations) {

		return new PlaySequentiallyAnimationSequence(animations);
	}


	public static AnimationSequence playRepeated(int repeatCount, AnimationSequence... animations) {

		return new PlayRepeatedAnimationSequence(repeatCount, Arrays.asList(animations));
	}

	public static AnimationSequence playRepeated(int repeatCount, List<AnimationSequence> animations) {

		return new PlayRepeatedAnimationSequence(repeatCount, animations);
	}


	public abstract void setDelayInSequence(long delay);

	public abstract long getTotalDurationInSequence();

	public abstract T addEndAction(final AnimationEndListener endListener);

	public abstract void start();


	public T addStartAction(Runnable r) {

		if (animations.size() > 0) {
			animations.get(0).addStartAction(r);
		}

		return self();
	}


	protected T self() {

		try {
			return (T) this;
		} catch (ClassCastException e) {
			throw new RuntimeException("Could not cast to subclass. Did you forget to implement `newInstance()`?");
		}
	}
}
