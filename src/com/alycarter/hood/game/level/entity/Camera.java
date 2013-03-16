package com.alycarter.hood.game.level.entity;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.mob.Player;

public class Camera extends Entity{

	public Camera(Game game) {
		super(game,Entity.TYPE_UTILITY,new Point2D.Double(game.getLevel().player.getLocation().getX(),game.getLevel().player.getLocation().getY()),1,1,0,0,false,false);
	}
	
	public void onUpdate(){
		double xd =getGame().getLevel().player.getLocation().getX()-this.getLocation().getX();
		double yd =getGame().getLevel().player.getLocation().getY()-this.getLocation().getY();
		this.setDirection(new Point2D.Double(xd,yd));
		this.setSpeed(Player.moveSpeed*Math.sqrt(Math.pow(xd, 2)+Math.pow(yd, 2)));
	}

}
