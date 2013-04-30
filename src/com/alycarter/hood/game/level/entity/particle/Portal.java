package com.alycarter.hood.game.level.entity.particle;

import java.awt.geom.Point2D.Double;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.TextureTileLoader;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Portal extends Particle {
	
	public Portal(Game game, Double location,double duration) {
		super(game, location, 1, duration, 0, 0);
		sprite.addAnimationLayer(new PortalAnimation(game));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		sprite.getAnimationLayer(0).setDirection(sprite.getAnimationLayer(0).getDirection()+getGame().getDeltaTime()*180);
	}
}


class PortalAnimation extends AnimationLayer{
	private static TextureTileLoader portal= new TextureTileLoader("swirly.png", 228);
	
	public PortalAnimation(Game game){
		addAnimation(new Animation(game,"portal",portal,1),true);
	}
}
