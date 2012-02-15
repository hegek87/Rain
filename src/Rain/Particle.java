package Rain;

import java.awt.Point;

public class Particle {
	private int xPos; 
	private int yPos;
	private int dX;
	private int dY;
	private int i = 1;
	public static final int DIAMETER = 13;
	
	private boolean hit;
	private int count;
	
	public Particle(int xPos, int yPos, int dX, int dY){
		this.xPos = xPos;
		this.yPos = yPos;
		this.dX = dX;
		this.dY = dY;
		hit = false;
	}
	
	public boolean collide(Particle p){
		if(this==p) return false;
		Point center = new Point(xPos + (DIAMETER / 2), yPos + (DIAMETER/2));
		Point pCenter = new Point(p.xPos + (DIAMETER/2), p.yPos +(DIAMETER/2));
		if(distance(center, pCenter) <= DIAMETER)
			return true;
		return false;
	}
	
	public float distance(Point p1, Point p2){
		float deltaX = Math.abs(p1.x - p2.x);
		float deltaY = Math.abs(p1.y - p2.y);
		float aPlusB = (deltaX * deltaX) + (deltaY * deltaY);
		float distance = (float) Math.sqrt(aPlusB);
		return distance;
	}
	
	public boolean getHit(){ return hit; }
	public void setHit(boolean hit){ this.hit = hit; }
	public int getCount(){ return count; }
	public void setCount(int count){ this.count = count; }
	public int getXPos(){ return xPos; 	}
	public int getYPos(){ return yPos; 	}
	public int getDX()	{ return dX;	}
	public int getDY()	{ return dY;	}
	
	public void setX(int xPos)	{	this.xPos = xPos;	}
	public void setY(int yPos)	{	this.yPos = yPos; 	}
	public void setDX(int dX)	{ 	this.dX = dX;		}
	public void setDY(int dY){ this.dY = dY; }	
	
	public static void main(String[] args){
		Particle p = new Particle(100, 100, 10, 10);
		Particle x = new Particle(500, 500, 11, 11);
		System.out.println(p.collide(x));
	}
	
}