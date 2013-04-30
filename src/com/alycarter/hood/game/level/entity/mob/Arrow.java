package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.TextureTileLoader;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.particle.Particle;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Arrow extends Entity{
	private double duration;
	private double sparkleDelay=0;
	private double damage;
	private int[] targets;
	public Arrow(Game game, Point2D.Double location, double direction, double speed, double maxDistance, double damage, int targets[]) {
		super(game, Entity.TYPE_ARROW, location, 1, 1, 0.3, 0.1, false, true);
		if(speed<0.1){
			speed=0.1;
		}
		setDirection(direction);
		setSpeed(speed);
		duration = maxDistance/getSpeed();
		sprite.addAnimationLayer(new ArrowAnimation(game));
		sprite.getAnimationLayer(0).setDirection(direction);
		hideHealthBar();
		this.damage=damage;
		this.targets=targets;
	}
	
	public void onUpdate(){
		duration-=getGame().getDeltaTime();
		if(duration<=0){
			markRemoved();
		}
		sparkleDelay-=getGame().getDeltaTime();
		if(sparkleDelay<=0){
			Particle p = new Particle(getGame(),getLocation(),0.2,0.25,Math.random()*360,0.5);
			p.sprite.addAnimationLayer(new ParticleAnimation(getGame()));
			p.sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
			getGame().getLevel().entities.add(p);
			sparkleDelay=0.1;
		}
	}
	
	public void onCollide(Entity entity){
		boolean found= false;
		int i=0;
		while ((!found)&&(i<targets.length)){
			if(entity.entityType==targets[i]){
				entity.damage(this, damage);
			}
			i++;
		}
		this.markRemoved();
	}

}

class ArrowAnimation extends AnimationLayer{
	private static TextureTileLoader arrow= new TextureTileLoader("arrow.png", 58);
	
	public ArrowAnimation(Game game){
		addAnimation(new Animation(game,"arrow",arrow,1),true);
	}
}

class ParticleAnimation extends AnimationLayer{
	private static TextureTileLoader dust= new TextureTileLoader("dust.png", 16);
	
	public ParticleAnimation(Game game){
		addAnimation(new Animation(game,"dust",dust,1),true);
	}
}

