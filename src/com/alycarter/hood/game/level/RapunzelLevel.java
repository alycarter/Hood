package com.alycarter.hood.game.level;

import java.awt.geom.Point2D;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.mob.Enemy;
import com.alycarter.hood.game.level.entity.mob.EnemyArcher;
import com.alycarter.hood.game.level.entity.mob.EnemySpawner;
import com.alycarter.hood.game.level.entity.mob.Player;
import com.alycarter.hood.game.level.entity.mob.Rapunzel;

public class RapunzelLevel extends Level{
	
	private EnemySpawner spawnerLeft;
	private EnemySpawner spawnerRight;
	private EnemySpawner spawnerRapunzel;
	
	public RapunzelLevel(Game game) {
		super(game);
	}
	
	public void onLoad(){
		spawnerLeft=new EnemySpawner(game,new Point2D.Double(5,6),-90,2.75);
		spawnerRight = new EnemySpawner(game,new Point2D.Double(7,6),90,2.25);
		spawnerRapunzel = new EnemySpawner(game,new Point2D.Double(6,6),0,0);
		entities.add(spawnerRapunzel);
		entities.add(spawnerLeft);
		entities.add(spawnerRight);
		addWave(new Wave1(this));
		addWave(new Wave2(this));
		addWave(new Wave3(this));
		addWave(new Wave4(this));
		addWave(new Wave5(this));
		player = new Player(game,new Point2D.Double(6, 10));
		entities.add(player);
		
	}
	
	class Wave1 extends Wave{
		
		public Wave1(Level level) {
			super(level,5);
		}

		public void onStart(){
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
		}
		public boolean extraEndConditionsMet() {
			return (spawnerLeft.hasEmptySpawnQueue()&&spawnerRight.hasEmptySpawnQueue()&&spawnerRapunzel.hasEmptySpawnQueue());
		}
		
	}
	
	class Wave2 extends Wave{
		
		public Wave2(Level level) {
			super(level,5);
		}

		public void onStart(){
			spawnerLeft.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
		}
		public boolean extraEndConditionsMet() {
			return (spawnerLeft.hasEmptySpawnQueue()&&spawnerRight.hasEmptySpawnQueue()&&spawnerRapunzel.hasEmptySpawnQueue());
		}
		
	}
	class Wave3 extends Wave{
		
		public Wave3(Level level) {
			super(level,5);
		}

		public void onStart(){
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
		}
		public boolean extraEndConditionsMet() {
			return (spawnerLeft.hasEmptySpawnQueue()&&spawnerRight.hasEmptySpawnQueue()&&spawnerRapunzel.hasEmptySpawnQueue());
		}
		
	}
	class Wave4 extends Wave{
		
		public Wave4(Level level) {
			super(level,5);
		}
	
		public void onStart(){
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
		}
		public boolean extraEndConditionsMet() {
			return (spawnerLeft.hasEmptySpawnQueue()&&spawnerRight.hasEmptySpawnQueue()&&spawnerRapunzel.hasEmptySpawnQueue());
		}
	}

	class Wave5 extends Wave{
		
		public Wave5(Level level) {
			super(level,10);
		}

		public void onStart(){
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new Enemy(level.game,new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerLeft.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRight.addToSpawnQueue(new EnemyArcher(level.game, new Point2D.Double(0, 0)));
			spawnerRapunzel.addToSpawnQueue(new Rapunzel(game,new Point2D.Double(0, 0)));
		}
		
		public boolean extraEndConditionsMet() {
			return (spawnerLeft.hasEmptySpawnQueue()&&spawnerRight.hasEmptySpawnQueue()&&spawnerRapunzel.hasEmptySpawnQueue());
		}
		
	}

}
