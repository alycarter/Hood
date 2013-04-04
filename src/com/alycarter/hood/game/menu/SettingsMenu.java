package com.alycarter.hood.game.menu;

import java.awt.image.AffineTransformOp;

import com.alycarter.hood.game.Game;
import com.alycarter.hood.game.level.entity.sprite.AnimationLayer;

public class SettingsMenu extends Menu {

	public SettingsMenu(Game game) {
		super(game);
		addButton(new Button(game,"back",10,10,100,25){
			public void onClick(){
				hideMenu();
				if(game.mainMenu.isShown()){
					game.mainMenu.showMenu();
				}else{
					game.pauseMenu.showMenu();
				}
			}
		});
		addButton(new Button(game,String.valueOf("fps limit: "+game.getFpsLimit()),10,50,100,25){
			public void onClick(){
				switch(game.getFpsLimit()){
					case 120:{
						game.setFpsLimit(60);
						break;
					}
					case 60:{
						game.setFpsLimit(30);
						break;
					}
					case 30:{
						game.setFpsLimit(15);
						break;
					}
					case 15:{
						game.setFpsLimit(120);
						break;
					}
					default:{
						game.setFpsLimit(120);
						break;
					}
				}
				name=String.valueOf("fps limit: "+game.getFpsLimit());
			}
		});
		addButton(new Button(game,String.valueOf("blend mode: "+AnimationLayer.transformMode),10,90,100,25){
			public void onClick(){
				switch(AnimationLayer.transformMode){
					case AffineTransformOp.TYPE_BICUBIC:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_BILINEAR;
						break;
					}
					case AffineTransformOp.TYPE_BILINEAR:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
						break;
					}
					case AffineTransformOp.TYPE_NEAREST_NEIGHBOR:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_BICUBIC;
						break;
					}
					default:{
						AnimationLayer.transformMode=AffineTransformOp.TYPE_BILINEAR;
						break;
					}
				}
				name=String.valueOf("blend mode: "+AnimationLayer.transformMode);
			}
		});
	}

}
