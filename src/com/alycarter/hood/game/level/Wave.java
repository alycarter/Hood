package com.alycarter.hood.game.level;

import com.alycarter.hood.game.level.entity.Entity;

public class Wave {
	public Level level;
	public Wave(Level level) {
		this.level=level;
	}
	
	public void startWave(){
		onStart();
	}
	
	public void onStart() {
		
	}

	public void update(){
		boolean enemiesLeft=false;
		for(int i=0;i<level.entities.size();i++){
			if(level.entities.get(i).entityType==Entity.TYPE_ENEMY){
				enemiesLeft=true;
			}
		}
		if(!enemiesLeft){
			endWave();
		}
	}
	
	public void endWave(){
		onEnd();
		level.nextWave();
	}

	public void onEnd() {
		
	}

}
