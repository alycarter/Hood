package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;

public class Mob extends Entity{

	public Mob(Game game,int entityType,Point2D.Double location,double maxHealth,double resistance,double imageWidth,double hitBoxWidth) {
		super(game,entityType,location,maxHealth,resistance,imageWidth,hitBoxWidth,true,true);
	}

}
