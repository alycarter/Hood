package com.alycarter.hood.game.level.entity.particle;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.Sound;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Pickup extends Particle{
	private double sparkleDelay=0;
	private static final double DRAWRANGE = 1.5;
	public Pickup(Game game, Point2D.Double location) {
		super(game, location, 0.35, 30, 0, 0);
		sprite.addAnimationLayer(new CogAnimation(game));
		
	}
	
	public void onUpdate(){
		sparkleDelay-=getGame().getDeltaTime();
		if(sparkleDelay<=0){
			double x = (Math.random()*0.35)-(0.35/2)+getLocation().getX();
			double y = (Math.random()*0.35)-(0.35/2)+getLocation().getY();
			Particle p = new Particle(getGame(),new Point2D.Double(x, y),0.15,0.3,Math.random()*360,0.1);
			p.sprite.addAnimationLayer(new ParticleAnimation(getGame()));
			p.sprite.getAnimationLayer(0).setDirection(Math.random()*360);
			getGame().getLevel().entities.add(p);
			sparkleDelay=0.25;
		}
		if(distanceTo(getGame().getLevel().player)<getGame().getLevel().player.getHitBoxWidth()/2){
			getGame().getLevel().towerPoints++;
			Sound.pickup.play();
			markRemoved();
			for(int i=0; i<10;i++){
				Particle p = new Particle(getGame(),getGame().getLevel().player.getLocation(),0.15,0.5,Math.random()*360,1);
				p.sprite.addAnimationLayer(new ParticleAnimation(getGame()));
				p.sprite.getAnimationLayer(0).setDirection(Math.random()*360);
				getGame().getLevel().entities.add(p);
			}
		}else{
			if(distanceTo(getGame().getLevel().player)<DRAWRANGE){
				this.setSpeed(3-(3*distanceTo(getGame().getLevel().player)/DRAWRANGE));
				double x = getGame().getLevel().player.getLocation().getX()-getLocation().getX();
				double y = getGame().getLevel().player.getLocation().getY()-getLocation().getY();
				this.setDirection(new Point2D.Double(x, y));
			}else{
				setSpeed(0);
			}
		}
	}

}

class ParticleAnimation extends AnimationLayer{
	
	public ParticleAnimation(Game game){
		addAnimation(new Animation(game,"sparkle","sparkle.png",8,1),true);
	}
}

class CogAnimation extends AnimationLayer{
	
	public CogAnimation(Game game){
		addAnimation(new Animation(game,"cog","cog.png",16,1),true);
	}
}
