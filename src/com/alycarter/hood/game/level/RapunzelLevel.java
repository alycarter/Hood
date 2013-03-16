package com.alycarter.hood.game.level;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.mob.Enemy;
import com.alycarter.hood.game.level.entity.mob.EnemySpawner;
import com.alycarter.hood.game.level.entity.mob.Rapunzel;

public class RapunzelLevel extends Level{
	
	private EnemySpawner spawnerTopLeft;
	private EnemySpawner spawnerTopRight;
	private EnemySpawner spawnerRapunzel;
	
	public RapunzelLevel(Game game) {
		super(game);
	}
	
	public void onLoad(){
		spawnerTopLeft=new EnemySpawner(game,new Point2D.Double(1,1),45,10);
		spawnerTopRight = new EnemySpawner(game,new Point2D.Double(9,1),-45,10);
		spawnerRapunzel = new EnemySpawner(game,new Point2D.Double(5,1),0,10);
		entities.add(spawnerRapunzel);
		entities.add(spawnerTopLeft);
		entities.add(spawnerTopRight);
		addWave(new Wave1(this));
		addWave(new Wave2(this));
		addWave(new Wave3(this));
	}
	
	class Wave1 extends Wave{
		
		public Wave1(Level level) {
			super(level);
		}

		public void onStart(){
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
		}
		
	}
	
	class Wave2 extends Wave{
		
		public Wave2(Level level) {
			super(level);
		}

		public void onStart(){
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
		}
		
	}

	class Wave3 extends Wave{
		
		public Wave3(Level level) {
			super(level);
		}

		public void onStart(){
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerTopLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRapunzel.addToSpawnQueue(new Rapunzel(game,new Point2D.Double(0, 0)));
		}
		
	}

}
