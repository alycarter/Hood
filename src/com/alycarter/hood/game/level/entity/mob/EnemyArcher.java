package com.alycarter.hood.game.level.entity.mob;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.Entity;

public class EnemyArcher extends Mob{

	public EnemyArcher(Game game ,Point2D.Double location) {
		super(game,Entity.TYPE_ENEMY,location,2.5,0,1,0.5);
	}

}
