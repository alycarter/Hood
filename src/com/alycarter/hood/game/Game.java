package com.alycarter.hood.game;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.alycarter.hood.game.level.Level;
import com.alycarter.hood.game.level.RapunzelLevel;
import com.alycarter.hood.game.level.map.tile.Tile;
import com.alycarter.hood.game.menu.GameOverMenu;
import com.alycarter.hood.game.menu.MainMenu;
import com.alycarter.hood.game.menu.PauseMenu;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public int windowWidth;
	public int windowHeight;
	private double tileWide = 8;
	public final String NAME = "Hood";
	
	public MainMenu mainMenu;
	public PauseMenu pauseMenu;
	public GameOverMenu gameOverMenu;
	
	private Thread gameThread = new Thread(this);
	
	private JFrame frame;
	private GraphicsDevice gd;
	
	private boolean running;
	private double deltaTime=0;
	
	private Level level;
	
	public Controls controls =new Controls(this);
	
	public boolean debugMode = false;
	
	public Game(JFrame frame) {
		this.frame=frame;
	}

	public void start(){
		intit();
		gameThread.run();
	}
	
	private void intit() {
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		windowWidth = gd.getDisplayMode().getWidth();
		windowHeight = gd.getDisplayMode().getHeight();
		Tile.tileResolution= (int) ((double)windowWidth/tileWide);
		frame.setSize(windowWidth, windowHeight);
		frame.setTitle(NAME);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		setSize(windowWidth,windowHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		gd.setFullScreenWindow(frame);
		this.addKeyListener(controls);
		this.addMouseListener(controls);
		mainMenu=new MainMenu(this);
		pauseMenu= new PauseMenu(this);
		gameOverMenu = new GameOverMenu(this);
		mainMenu.showMenu();
		level = new RapunzelLevel(this);
		System.out.println(Tile.tileResolution);
	}

	@Override
	public void run() {
		long start;
		long end;
		long timeTaken;
		long ns =  1000000000;
		running= true;
		while (running){
			if(frame.isFocused()){
				start = System.nanoTime();
				update();
				render();
				end = System.nanoTime();
				timeTaken = end - start;
				deltaTime = (double)timeTaken/(double)ns;
			}else{
				if(level.loaded){
					pauseMenu.showMenu();
				}
				controls.clearControls();
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
		gd.setFullScreenWindow(null);
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs ==null){
			createBufferStrategy(3);
		}else{
			Graphics2D g = (Graphics2D)bs.getDrawGraphics();
			g.clearRect(0, 0, windowWidth, windowHeight);
			if(mainMenu.isShown()){
				mainMenu.render(g);
			}else{
				if(level.loaded){
					if(gameOverMenu.isShown()){
						level.render(g);
						gameOverMenu.render(g);
					}else{
						if(pauseMenu.isShown()){
							level.render(g);
							pauseMenu.render(g);
						}else{
							level.render(g);
						}
					}
				}else{
					g.drawString("loading level", getWidth()/2, getHeight()/2);
				}
			}
			bs.show();
		}
	}

	public double getDeltaTime(){
		return deltaTime;
	}
	private void update() {
		if(!this.isFocusOwner()){
			this.requestFocusInWindow();
		}
		controls.update();
		if(controls.isPressed(KeyEvent.VK_CONTROL)&&controls.isTyped(KeyEvent.VK_D)){
			debugMode=!debugMode;
		}
		if(controls.isTyped(KeyEvent.VK_ESCAPE)){
			if(level.loaded){
				pauseMenu.showMenu();
			}
		}
		if(mainMenu.isShown()){
			mainMenu.update();
		}else{
			if(level.loaded){
				if(gameOverMenu.isShown()){
					gameOverMenu.update();
				}else{
					if(pauseMenu.isShown()){
						pauseMenu.update();
					}else{
						level.update();
					}
				}
			}
		}
	}

	public Level getLevel(){
		return level;
	}
	
	public void showCursor(){
		try {
			BufferedImage c;
			c = ImageIO.read(Game.class.getResourceAsStream("/cross.png"));
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(c, new Point(16, 16), "cursor");
			frame.getContentPane().setCursor(cursor);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void hideCursor(){
		try {
			BufferedImage c;
			c = ImageIO.read(Game.class.getResourceAsStream("/blank.png"));
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(c, new Point(16, 16), "cursor");
			frame.getContentPane().setCursor(cursor);
		} catch (IOException e) {e.printStackTrace();}
	}
}
