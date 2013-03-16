package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;
import com.alycarter.hood.game.level.entity.Hair;
import com.alycarter.hood.game.level.entity.sprite.Animation;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class Rapunzel extends Mob{
	private double hairDelay=0;
	private Hair lastHairStrand=null;
	
	private final double burstDelay =2;
	private final double burstDuration =2;
	private double burstCoolDown = burstDelay;
	private double burstTime = 0;
	public Rapunzel(Game game, Point2D.Double location) {
		super(game, Entity.TYPE_ENEMY, location,20, 0, 1.5, 0.5);
		sprite.addAnimationLayer(new RapunzelAnimation(game));
	}
	
	public void onUpdate(){
		double x = getGame().getLevel().player.getLocation().getX()-getLocation().getX();
		double y = getGame().getLevel().player.getLocation().getY()-getLocation().getY();
		setDirection(new Point2D.Double(x, y));
		sprite.getAnimationLayer(0).setDirection(getDirectionAsAngle());
		if(burstCoolDown<0){
			if(burstTime<burstDuration){
				if(hairDelay<0){
					Hair h=new Hair(getGame(),getLocation(),getDirectionAsAngle(),4,4,this,lastHairStrand);
					getGame().getLevel().entities.add(h);
					lastHairStrand=h;
					hairDelay=(0.25/4);
				}else{
					hairDelay-=getGame().getDeltaTime();
				}
				burstTime+=getGame().getDeltaTime();
			}else{
				burstCoolDown=burstDelay;
				burstTime=0;
				lastHairStrand=null;
			}
		}else{
			burstCoolDown-=getGame().getDeltaTime();
		}
	}

}


class RapunzelAnimation extends AnimationLayer{
	
	public RapunzelAnimation(Game game){
		addAnimation(new Animation(game,"rapunzel","rapunzel.png",128,1),true);
	}
}
