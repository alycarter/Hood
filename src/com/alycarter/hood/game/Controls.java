package com.alycarter.hood.game;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;


public class Controls implements KeyListener, MouseListener{
	
	private ArrayList<Integer> tempKeysPressed = new ArrayList<Integer>();
	private ArrayList<Integer> tempKeysTyped = new ArrayList<Integer>();
	private ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	private ArrayList<Integer> keysTyped = new ArrayList<Integer>();
	
	private boolean leftDown =false;
	private boolean leftClicked = false;
	private boolean leftDownTemp =false;
	private boolean leftClickedTemp = false;
	private boolean rightDown =false;
	private boolean rightClicked = false;
	private boolean rightDownTemp =false;
	private boolean rightClickedTemp = false;
	public Point mouseLocation;
	
	private Game game;
	
	public Controls(Game game){
		this.game=game;
	}
	
	public void update(){
		double lx=game.getLocationOnScreen().getX();//+(game.getWidth()/2);
		double ly=game.getLocationOnScreen().getY();//+(game.getHeight()/2);
		
		mouseLocation = new Point((int)(MouseInfo.getPointerInfo().getLocation().getX()-lx),(int)( MouseInfo.getPointerInfo().getLocation().getY()-ly));
		
		this.keysPressed.clear();
		for (int i = 0;i<tempKeysPressed.size();i++){
			this.keysPressed.add(tempKeysPressed.get(i).intValue());
		}
		this.keysTyped.clear();
		for (int i = 0;i<tempKeysTyped.size();i++){
			this.keysTyped.add(tempKeysTyped.get(0).intValue());
			this.tempKeysTyped.remove(0);
		}
		leftClicked=leftClickedTemp;
		leftClickedTemp=false;
		leftDown=leftDownTemp;
		rightClicked=rightClickedTemp;
		rightClickedTemp=false;
		rightDown=rightDownTemp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!this.tempKeysPressed.contains(new Integer(e.getKeyCode()))){
			this.tempKeysPressed.add(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.tempKeysPressed.remove(new Integer(e.getKeyCode()));
		if(!this.tempKeysTyped.contains(new Integer(e.getKeyCode()))){
			this.tempKeysTyped.add(e.getKeyCode());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public boolean isPressed(int key){
		return this.keysPressed.contains(new Integer(key));
	}
	public boolean isTyped(int key){
		return this.keysTyped.contains(new Integer(key));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			leftDownTemp=true;
		}
		if(SwingUtilities.isRightMouseButton(e)){
			rightDownTemp=true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			leftClickedTemp=true;
			leftDownTemp=false;
			
		}
		if(SwingUtilities.isRightMouseButton(e)){
			rightClickedTemp=true;
			rightDownTemp=false;
			
		}
		
	}
	
	public boolean leftMousePressed(){
		return leftDown;
	}
	
	public boolean leftMouseClicked(){
		return leftClicked;
	}
	
	public boolean rightMousePressed(){
		return rightDown;
	}
	
	public boolean rightMouseClicked(){
		return rightClicked;
	}
	
}
