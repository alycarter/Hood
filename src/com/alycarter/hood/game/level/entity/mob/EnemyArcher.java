package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.particle.Pickup;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class EnemyArcher extends Mob{
	private double arrowDuration = 3;
	private double arrowDamage = 0.5;
	private double walkSpeed = 4;
	private final double attackDelay = 1;
	private double attackCoolDown = 0;
	
	public EnemyArcher(Game game ,Point2D.Double location) {
		super(game,Entity.TYPE_ENEMY,location,2.5,0,1.5,0.5);
		sprite.addAnimationLayer(new ArcherAnimation(game));
	}
	
	public void onUpdate() {
		attackCoolDown-=getGame().getDeltaTime();
		double x = getGame().getLevel().player.getLocation().getX()-getLocation().getX();
		double y = getGame().getLevel().player.getLocation().getY()-getLocation().getY();
		setDirection(new Point2D.Double(x, y));
		if(distanceBetweenHitBoxes(getGame().getLevel().player)>arrowDuration){
			setSpeed(walkSpeed);
		}else{
			if(distanceBetweenHitBoxes(getGame().getLevel().player)<1.5){
				setDirection(getDirectionAsAngle()+180);
				setSpeed(walkSpeed);
			}else{
				setSpeed(0);
				if(attackCoolDown<=0){
					double xl=getLocation().getX()+(getDirectionAsVector().getX()*(getImageWidth()/2));
					double yl=getLocation().getY()+(getDirectionAsVector().getY()*(getImageWidth()/2));
					int[] t ={TYPE_TURRET,TYPE_PLAYER}; 
					getGame().getLevel().entities.add(new Arrow(getGame(),new Point2D.Double(xl,yl),getDirectionAsAngle(),4,arrowDuration,arrowDamage,t));
					attackCoolDown=attackDelay;
				}
			}
		}
		sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
	}
	
	public void onKill(Entity sender) {
		markRemoved();
		if(Math.random()<0.5){
			getGame().getLevel().entities.add(new Pickup(getGame(),getLocation()));
		}
	}
}

class ArcherAnimation extends AnimationLayer{
	
	public ArcherAnimation(Game game){
		addAnimation(new Animation(game,"bow","bow.png",128,1),true);
	}
}
