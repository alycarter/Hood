package com.alycarter.hood.game.level;

import com.alycarter.hood.game.level.entity.Entity;

public class Wave {
	public Level level;
	private double delay;
	private boolean delayEnd = false;
	private boolean loaded = false;
	
	public Wave(Level level,double waveDelay) {
		this.level=level;
		delay=waveDelay;
	}
	
	public void onStart() {
		
	}

	public void update(){
		delay-=level.game.getDeltaTime();
		if(delay<0){	
			if(!delayEnd){
				new Thread(){
					public void run() {
						onStart();
						loaded=true;
					}
				}.start();
			}
			delayEnd=true;
			boolean enemiesLeft=false;
			for(int i=0;i<level.entities.size();i++){
				if(level.entities.get(i).entityType==Entity.TYPE_ENEMY){
					enemiesLeft=true;
				}
			}
			if(loaded && !enemiesLeft && extraEndConditionsMet()){
				endWave();
			}
		}
	}
	
	public void endWave(){
		onEnd();
		level.nextWave();
	}
	
	public boolean extraEndConditionsMet(){
		return true;
	}

	public void onEnd() {
		
	}

}
