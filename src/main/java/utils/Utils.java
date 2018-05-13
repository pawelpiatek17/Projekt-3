package utils;

public class Utils{
	public static Key calculateBucketIndex(Point point, double radius){
		return new Key((int) Math.floor(point.getX()/radius),(int) Math.floor(point.getY()/radius),(int) Math.floor(point.getZ()/radius));
	}
	public static float distanceBetween(Point a, Point b) {
		return (float) Math.sqrt(Math.pow(b.getX()-a.getX(), 2) + Math.pow(b.getY()-a.getY(), 2)+ Math.pow(b.getZ()-a.getZ(), 2));
	}
}
