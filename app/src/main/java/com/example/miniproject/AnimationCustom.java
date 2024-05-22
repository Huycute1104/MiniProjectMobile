package com.example.miniproject;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class AnimationCustom {
    // Declare KonfettiView konffetiview -> FindViewById -> Call Function with value (konfettiView,this)
    // Created KonfettiView in Race Layout - Nhat with Id : kftView
    // Call function will create a firework animation in 3 second
    public void CongratAnimation(KonfettiView kftView, Context context)
    {
        Shape.DrawableShape drawableShape = new Shape.DrawableShape(context.getDrawable(R.drawable.ic_android),true,true);
        EmitterConfig emitterConfig = new Emitter(300,TimeUnit.MILLISECONDS).max(300);
        kftView.start( new PartyFactory(emitterConfig)
                .shapes(Shape.Circle.INSTANCE,Shape.Square.INSTANCE,drawableShape)
                .spread(360).position(0.5,0.25,1,1)
                .sizes(new Size(8,50,10))
                .timeToLive(3000).fadeOutEnabled(true).
                build());
    }
}
