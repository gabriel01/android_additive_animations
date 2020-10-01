package additive_animations.fragments.sequence;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.additive_animator.sequence.AnimationSequence;
import at.wirecube.additiveanimations.additiveanimationsdemo.R;
import at.wirecube.additiveanimations.helper.FloatProperty;


public class AnimationSequenceDemoFragment extends Fragment {

	private FrameLayout rootView;
	private View        animatedView;
	private View        mTouchView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = (FrameLayout) inflater.inflate(R.layout.fragment_tap_to_move_demo, container, false);
		mTouchView = rootView.findViewById(R.id.touch_view);
		animatedView = rootView.findViewById(R.id.animated_view);

		rootView.setOnClickListener(v -> doSequenceAnimation());

		return rootView;
	}

	private void doSequenceAnimation() {

		buildAnimationSequenceDemo();
	}

	private void buildAnimationSequenceDemo() {
		// using a json string for illustration purposes for how to dynamically build animations from a string representation.
		String                demoJson             = "{\"animationType\":\"Spawn\",\"children\":[{\"animationType\":\"Sequence\",\"children\":[{\"animationType\":\"AtOnce\",\"animations\":[{\"by\":true,\"propertyName\":\"x\",\"value\":100.0},{\"by\":true,\"propertyName\":\"y\",\"value\":0.0}]},{\"animationType\":\"AtOnce\",\"animations\":[{\"by\":true,\"propertyName\":\"x\",\"value\":-100.0},{\"by\":true,\"propertyName\":\"y\",\"value\":0.0}]}]},{\"animationType\":\"Sequence\",\"children\":[{\"animationType\":\"AtOnce\",\"animations\":[{\"by\":true,\"propertyName\":\"x\",\"value\":0.0},{\"by\":true,\"propertyName\":\"y\",\"value\":100.0}]},{\"animationType\":\"AtOnce\",\"animations\":[{\"by\":true,\"propertyName\":\"x\",\"value\":0.0},{\"by\":true,\"propertyName\":\"y\",\"value\":-100.0}]}]}]}";
//		AnimationSequenceJson animationDescription = new Gson().fromJson(demoJson, AnimationSequenceJson.class);




		//createSimpleSequence().start()

//		createAnimationSequence().start();

		createRepeatedSequence().start();
	}


	private AnimationSequence createSimpleSequence(){
        return AnimationSequence.playTogether(
            AnimationSequence.playSequentially(
                AdditiveAnimator.animate(animatedView).xBy(100),
                AdditiveAnimator.animate(animatedView).xBy(-100)
            ),
            AnimationSequence.playSequentially(
                AdditiveAnimator.animate(animatedView).yBy(100),
                AdditiveAnimator.animate(animatedView).yBy(-100)
            )
        );
	}


	private AnimationSequence createAnimationSequence() {


		long startTime = System.currentTimeMillis();

		AnimationSequence spawn1 = AnimationSequence.playTogether(

				AnimationSequence.playSequentially(
						AdditiveAnimator.animate(animatedView).setDuration(1000).xBy(300),
						AdditiveAnimator.animate(animatedView).setDuration(1000).xBy(-300)
				)
						.addStartAction(() -> Log.d("ANIMATION", "-- THE BEGINNING (Spawn1)"))
						.addEndAction(wasCancelled -> Log.d("ANIMATION", "-- THE END (Spawn1) --")));

		AnimationSequence spawn2 = AnimationSequence.playTogether(

				AnimationSequence.playSequentially(
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).xBy(300),
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).xBy(-300)
				),

				AnimationSequence.playSequentially(
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).yBy(300),
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).yBy(-300)
								.addEndAction(wasCancelled -> Log.e("ANIMATION", "TOTAL DURATION = " + (System.currentTimeMillis() - startTime)))
				))
				.addStartAction(() -> Log.d("ANIMATION", "-- THE BEGINNING (Spawn2)"))
				.addEndAction(wasCancelled -> Log.d("ANIMATION", "-- THE END (Spawn2) --"));

		AnimationSequence spawn3 = AnimationSequence.playTogether(

				AnimationSequence.playSequentially(
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).xBy(300),
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).xBy(-300)
				),

				AnimationSequence.playSequentially(
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).yBy(300),
						AdditiveAnimator.animate(animatedView).setDuration(1000).thenWithDelay(1000).yBy(-300)
								.addEndAction(wasCancelled -> Log.e("ANIMATION", "TOTAL DURATION = " + (System.currentTimeMillis() - startTime)))
				))
				.addStartAction(() -> Log.d("ANIMATION", "-- THE BEGINNING (Spawn3)"))
				.addEndAction(wasCancelled -> Log.d("ANIMATION", "-- THE END (Spawn3) --"));


		AnimationSequence sequence1 = AnimationSequence
				.playSequentially(spawn1, spawn2, spawn3)
				.addStartAction(() -> Log.d("ANIMATION", "-- THE TOTAL BEGINNING (SEQ)"))
				.addEndAction(wasCancelled -> {
					Log.e("ANIMATION", "-- THE TOTAL END (SEQ) --");
				});


		AnimationSequence sequence2 = AnimationSequence
				.playRepeated(2, spawn1)
				.addStartAction(() -> Log.d("ANIMATION", "-- THE TOTAL BEGINNING (REPEAT)"))
				.addEndAction(wasCancelled -> {
					Log.e("ANIMATION", "-- THE TOTAL END (REPEAT) --");
				});


		return sequence2;
	}


	private AnimationSequence createRepeatedSequence() {

		AnimationSequence spawn1 = AnimationSequence.playTogether(

				AnimationSequence.playSequentially(
						AdditiveAnimator.animate(animatedView).setDuration(1000).xBy(300),
						AdditiveAnimator.animate(animatedView).setDuration(1000).xBy(-300)
				)
						.addStartAction(() -> Log.d("ANIMATION", "-- THE BEGINNING (Spawn1)"))
						.addEndAction(wasCancelled -> Log.d("ANIMATION", "-- THE END (Spawn1) --")));

		AnimationSequence sequence = AnimationSequence
				.playRepeated(2, spawn1)
				.addStartAction(() -> Log.d("ANIMATION", "-- THE TOTAL BEGINNING (REPEAT)"))
				.addEndAction(wasCancelled -> {
					Log.e("ANIMATION", "-- THE TOTAL END (REPEAT) --");
				});


		return sequence;
	}


	private FloatProperty<View> getProperty(String propertyName) {
		// TODO: add more property names here for all the animations you might need to parse
		Map<String, FloatProperty<View>> propertyMap = new HashMap<>();
		propertyMap.put("x", FloatProperty.create(View.X));
		propertyMap.put("y", FloatProperty.create(View.Y));
		propertyMap.put("alpha", FloatProperty.create(View.ALPHA));

		return propertyMap.get(propertyName);
	}
}
