package Rain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3135285603565489289L;
	private Graphics2D 		bufferedGraphics;
	private Timer			time;
	private BufferedImage 	image;
	
	public static final int HEIGHT 	= 480;
	public static final int WIDTH	= 600; 
	public static final Random RNG = new Random();
	private static final int[] dXes = { -4, -3, -2, -1, 1, 2, 3, 4 }; 
	private Particle[] rainDrops;
	
	private enum Speed{
		SLOW(1), MEDIUM(2), FAST(6);
		private int verticalSpeed;
		Speed(int verticalSpeed){
			this.verticalSpeed = verticalSpeed;
		}
	}
	
	private Speed genSpeed(){
		switch(RNG.nextInt(3)){
			case 0:	return Speed.SLOW;
			case 1: return Speed.MEDIUM;
			case 2: return Speed.FAST;
		}
		return Speed.SLOW;
	}
	public Canvas(int rainDrops, Speed speed){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = image.createGraphics();
		this.rainDrops = new Particle[rainDrops];
		for(int i = 0; i < this.rainDrops.length; ++i){
			this.rainDrops[i] = new Particle(RNG.nextInt(WIDTH - Particle.DIAMETER),
					RNG.nextInt(HEIGHT-Particle.DIAMETER), dXes[RNG.nextInt(dXes.length)], speed.verticalSpeed);
		}
		time = new Timer(15, this);
	}
	
	@Override public void paintComponent(Graphics g){
		super.paintComponent(g);
		bufferedGraphics.clearRect(0, 0, WIDTH, HEIGHT);
		bufferedGraphics.setColor(Color.WHITE);
		bufferedGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		bufferedGraphics.setColor(Color.GREEN);
		bufferedGraphics.fillRect(0, (HEIGHT-(HEIGHT/4)), WIDTH, (HEIGHT-(HEIGHT/4)));
		bufferedGraphics.setColor(Color.BLUE);
		for(Particle p : rainDrops){
			bufferedGraphics.fillOval(p.getXPos(), p.getYPos(), Particle.DIAMETER, Particle.DIAMETER);
		}
		g.drawImage(image, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public boolean outOfBounds(Particle p){
		if(p.getXPos() < 0 || p.getXPos() > WIDTH)
			return true;
		if(p.getYPos() > HEIGHT)
			return true;
		return false;
	}
	
	
	
	public void checkBounds(Particle p){
		for(Particle x : rainDrops){
			if(p.collide(x)){
				System.out.println("Collision");
				p.setDX(p.getDX() * -1);
				p.setDY(p.getDY() * -1);
				x.setDX(x.getDX() * -1);
				x.setDY(x.getDY() * -1);
				if(p.getXPos() < x.getXPos()){
					p.setX(p.getXPos() - Particle.DIAMETER);
					x.setX(p.getXPos() + Particle.DIAMETER);
				}
				else{
					p.setX(p.getXPos() + Particle.DIAMETER);
					x.setX(x.getXPos() - Particle.DIAMETER);
				}
				p.setCount(7);
				x.setCount(7);
				p.setHit(true);
				x.setHit(true);
				
			}
		}
		if(p.getXPos() <= 0 || p.getXPos() >= WIDTH-20){
			p.setDX(p.getDX() * -1);
		}
		if(p.getYPos() <= 0 || p.getYPos() >= HEIGHT-20){
			p.setDY(p.getDY() * -1);
		}
	}
	public void start()	{	time.start();		}
	public void stop()	{	time.stop();		}
	public void update(Graphics g){	paintComponent(g);	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(Particle p : rainDrops){
			checkBounds(p);
		}
		for(Particle p : rainDrops){
			p.setX(p.getXPos() + p.getDX());
			p.setY(p.getYPos() + p.getDY());
		}
		repaint();
		/**
		for(Particle p : rainDrops){
			if(outOfBounds(p)){
				p.setX(RNG.nextInt(WIDTH));
				p.setY(RNG.nextInt(HEIGHT));
			}
		}
		**/
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		final Canvas c = new Canvas(15, Speed.SLOW);
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				c.start();
			}
		});
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				c.stop();
			}
		});
		buttons.add(start);
		buttons.add(stop);
		frame.add(buttons, BorderLayout.SOUTH);
		frame.add(c, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
