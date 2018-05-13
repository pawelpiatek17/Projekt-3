import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import utils.Key;
import utils.Point;
import utils.Utils;

public class Main {

	static Point p0;
	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<>();//(20, 1);
		for(int i = 0; i < 10; i++) {
			points.add(new Point(i,-i,0));
		}
		points.add(new Point(101, -20, 0));
		points.add(new Point(-2, -1, 0));
//		System.out.println(Utils.distanceBetween2D(new Point(101, -20, 0), new Point(102, -18, 0)));
		float radius = 3f;
		//fixedRadiusWithBuckets3D(points, radius); 
//		System.out.println("2D");
//		System.out.println();
		//fixedRadiusWithBuckets2D(points, radius);
		System.out.println();
		//fixedRadiusWithSort(points,radius);
		//fixedRadiusBruteForce(points, radius);
		//Point[] aPoints = points.toArray(new Point[0]);
		Point aPoints[] = {new Point(0,3,0),new Point(1,1,0),new Point(2,2,0),new Point(4,4,0),new Point(0,0,0),new Point(1,2,0),new Point(3,1,0),new Point(3,3,0)};
		jarvis(aPoints, aPoints.length);
		System.out.println();
		graham(aPoints, aPoints.length);
	}
	private static ArrayList<Point> generatePoints(int numberOfPoints, int dimension) {
		ArrayList<Point> points = new ArrayList<>();
		if (dimension == 1) {
			for(int i = 0; i < numberOfPoints; i++) {
				Random random = new Random();
				points.add(new Point(random.nextInt()+ random.nextFloat(), 0, 0));
			}
		}
		if (dimension == 2) {
			for(int i = 0; i < numberOfPoints; i++) {
				Random random = new Random();
				points.add(new Point(random.nextInt() + random.nextFloat(), random.nextInt()+ random.nextFloat(), 0));
			}
		}
		if (dimension == 3) {
			for(int i = 0; i < numberOfPoints; i++) {
				Random random = new Random();
				points.add(new Point(random.nextInt() + random.nextFloat(), random.nextInt() + random.nextFloat(), random.nextInt() + random.nextFloat()));
			}
		}
		return points;
	}
	private static <T> void print(ArrayList<T> points) {
		for (T point : points) {
			System.out.println(point);
		}
	}
	
	private static <T> void print(Point[] points) {
		for (Point point : points) {
			System.out.println(point);
		}
	}
	
	private static void fixedRadiusWithSort(ArrayList<Point> points, float radius) {
		ArrayList<Float> floatPoints = new ArrayList<>(points.size());
		for (Point point : points) {
			floatPoints.add(point.getX());
		}
		floatPoints.sort(null);
		for(int i = 0;i < floatPoints.size(); i++) {
			float point = floatPoints.get(i);
			for(int j = i+1;j < floatPoints.size(); j++) {
				if (Math.abs(point-floatPoints.get(j)) < radius) {
					System.out.println("Punkty (" + point + "),("+floatPoints.get(j)+")");
				} else {
					break;
				}
			}
		}
	}
	
	private static void fixedRadiusWithBuckets1D(ArrayList<Point> points, float radius) {
		HashMap<Integer, ArrayList<Point>> buckets = new HashMap<>();
		for (Point point : points) {
			Integer index = Utils.calculateBucketIndex(point, radius).getxId();
			if(buckets.containsKey(index)){
				buckets.get(index).add(point);
			} else {
				ArrayList<Point> list = new ArrayList<>();
				list.add(point);
				buckets.put(index,list);
			}
		}
		for (Map.Entry<Integer,ArrayList<Point>> entry : buckets.entrySet()) {
			ArrayList<Point> bucket = new ArrayList<>(entry.getValue());
			ArrayList<Point> secondBucket = new ArrayList<>(entry.getValue().size());
			if (buckets.containsKey(entry.getKey()+1)) {
				secondBucket.addAll(buckets.get(entry.getKey()+1));
			}
			for(int i = 0;i<bucket.size();i++){
				for(int j = i+1;j<bucket.size();j++){
						System.out.println("Punkty ("+bucket.get(i).getX() + ","+bucket.get(j).getX()+")" );
				}
				for (Point secondPoint : secondBucket) {
					if(Math.abs(bucket.get(i).getX()-secondPoint.getX()) < radius) {
						System.out.println("Punkty ("+bucket.get(i).getX() + ","+secondPoint.getX()+")" );
					}
				}
			}
		}
	}
	private static void fixedRadiusWithBuckets2D(ArrayList<Point> points, float radius) {
		HashMap<Key, ArrayList<Point>> buckets = new HashMap<>();
		for (Point point : points) {
			Key index = Utils.calculateBucketIndex(point, radius);
			if(buckets.containsKey(index)){
				buckets.get(index).add(point);
			} else {
				ArrayList<Point> list = new ArrayList<>();
				list.add(point);
				buckets.put(index,list);
			}
		}
		for (Map.Entry<Key,ArrayList<Point>> entry : buckets.entrySet()) {
			ArrayList<Point> bucket = new ArrayList<>(entry.getValue());
			ArrayList<Point> secondBucket = new ArrayList<>(1);
			ArrayList<Point> thirdBucket = new ArrayList<>(1);
			ArrayList<Point> fourthBucket = new ArrayList<>(1);
			ArrayList<Point> fifthBucket = new ArrayList<>(1);
			Key nextKey = new Key(entry.getKey());
			if (buckets.containsKey(nextKey.incrementXId().incrementYId())) { // x++ y++
				secondBucket.addAll(buckets.get(nextKey));
			}
			nextKey.decrementYId();
			if (buckets.containsKey(nextKey)) {// x++ y
				thirdBucket.addAll(buckets.get(nextKey));
			}
			nextKey.decrementYId();
			if (buckets.containsKey(nextKey)) { // x++ y--
				fourthBucket.addAll(buckets.get(nextKey));
			}
			nextKey.decrementXId();
			if (buckets.containsKey(nextKey)) {// x y--
				fifthBucket.addAll(buckets.get(nextKey));
			}
			for(int i = 0;i<bucket.size();i++){
				Point point = bucket.get(i);
				for(int j = i+1;j<bucket.size();j++){
						System.out.println("Punkty (" + point.getX() + "," + point.getY() +  "),(" + bucket.get(j).getX() + "," + bucket.get(j).getY() + ")" );
				}
				for (Point secondPoint : secondBucket) {
					if(Utils.distanceBetween(point, secondPoint) < radius) {
						System.out.println("Punkty (" + point.getX() + "," + point.getY() +  "),(" + secondPoint.getX() + "," + secondPoint.getY() + ")" );
					}
				}
				for (Point thirdPoint : thirdBucket) {
					if(Utils.distanceBetween(point, thirdPoint) < radius) {
						System.out.println("Punkty (" + point.getX() + "," + point.getY() +  "),(" + thirdPoint.getX() + "," + thirdPoint.getY() + ")" );
					}
				}
				for (Point fourthPoint : fourthBucket) {
					if(Utils.distanceBetween(point, fourthPoint) < radius) {
						System.out.println("Punkty (" + point.getX() + "," + point.getY() +  "),(" + fourthPoint.getX() + "," + fourthPoint.getY() + ")" );
					}
				}
				for (Point fifthPoint : fifthBucket) {
					if(Utils.distanceBetween(point, fifthPoint) < radius) {
						System.out.println("Punkty (" + point.getX() + "," + point.getY() +  "),(" + fifthPoint.getX() + "," + fifthPoint.getY() + ")" );
					}
				}
			}
		}
	}
	private static void fixedRadiusWithBuckets3D(ArrayList<Point> points, float radius) {
		HashMap<Key, ArrayList<Point>> buckets = new HashMap<>();
		for (Point point : points) {
			Key index = Utils.calculateBucketIndex(point, radius);
			if(buckets.containsKey(index)){
				buckets.get(index).add(point);
			} else {
				ArrayList<Point> list = new ArrayList<>();
				list.add(point);
				buckets.put(index,list);
			}
		}
		for (Map.Entry<Key,ArrayList<Point>> entry : buckets.entrySet()) {
			ArrayList<Point> bucket = new ArrayList<>(entry.getValue());
			ArrayList<Point> secondBucket = new ArrayList<>();
			Key nextKey = new Key(entry.getKey());
			if (buckets.containsKey(nextKey.incrementXId().incrementYId())) { // x++ y++ z
				secondBucket.addAll(buckets.get(nextKey));
			}
			if (buckets.containsKey(nextKey.decrementZId())) {// x++ y++ z--
				secondBucket.addAll(buckets.get(nextKey));
			}
			
			nextKey.decrementYId().incrementZId();
			if (buckets.containsKey(nextKey)) {// x++ y z
				secondBucket.addAll(buckets.get(nextKey));
			}
			if (buckets.containsKey(nextKey.decrementZId())) {// x++ y z--
				secondBucket.addAll(buckets.get(nextKey));
			}
			
			nextKey.decrementYId().incrementZId();
			if (buckets.containsKey(nextKey)) { // x++ y-- z
				secondBucket.addAll(buckets.get(nextKey));
			}
			if (buckets.containsKey(nextKey.decrementZId())) { // x++ y-- z--
				secondBucket.addAll(buckets.get(nextKey));
			}
			
			nextKey.decrementXId().incrementZId();
			if (buckets.containsKey(nextKey)) {// x y-- z
				secondBucket.addAll(buckets.get(nextKey));
			}
			if (buckets.containsKey(nextKey.decrementZId())) {// x y-- z--
				secondBucket.addAll(buckets.get(nextKey));
			}
			
			nextKey.incrementYId();
			if (buckets.containsKey(nextKey)) {// x y z--
				secondBucket.addAll(buckets.get(nextKey));
			}
//			System.out.println();
			for(int i = 0;i<bucket.size();i++){
				Point point = bucket.get(i);
				for(int j = i+1;j<bucket.size();j++){
						System.out.println("Punkty (" + point.getX() + "," + point.getY() + "," + point.getZ()  +  "),(" + bucket.get(j).getX() + "," + bucket.get(j).getY() + "," + bucket.get(j).getZ() + ")" );
				}
				for (Point secondPoint : secondBucket) {
					if(Utils.distanceBetween(point, secondPoint) < radius) {
						System.out.println("Punkty (" + point.getX() + "," + point.getY() + "," + point.getZ()  +  "),(" + secondPoint.getX() + "," + secondPoint.getY() + "," + secondPoint.getZ() + ")" );
					}
				}
			}
		}
	}
	private static void fixedRadiusBruteForce(ArrayList<Point> points, float radius) {
		for(int i = 0;i < points.size(); i++) {
			Point point = points.get(i);
			for(int j = i+1;j < points.size(); j++) {
				if (Utils.distanceBetween(point, points.get(j)) < radius) {
					System.out.println("Punkty (" + point.getX() + "," + point.getY() + "," + point.getZ()  +  "),(" + points.get(j).getX() + "," + points.get(j).getY() + "," + points.get(j).getZ() + ")" );
				}
			}
		}
	}
	private static void graham(Point points[], int n) {
		float yMin = points[0].getY();
		int min = 0;
		for(int i = 1; i < n; i++) {
			float y = points[i].getY();
			if((y < yMin) || (yMin == y && points[i].getX() < points[min].getX())){
				yMin = points[i].getY();
				min = i;
			}
		}		
		swap(points[0], points[min]);
		p0 = points[0];
		Arrays.sort(points, new customComparator());
		int m = 1;
		for(int i = 1;i < n; i++){
			while(i < n-1 && orientation(p0, points[i], points[i+1])==0){
				i++;
			}
			points[m] = points[i];
			m++;
		}
		if(m < 3){
			System.out.println("nie da rady");
			return;
		}
		Stack<Point> s = new Stack<>();
		s.push(points[0]);
		s.push(points[1]);
		s.push(points[2]);
		for(int i = 3; i < m; i++) {
			while(orientation(nextToTop(s), s.peek(), points[i]) != 2) {
				s.pop();
			}
			s.push(points[i]);
		}
		while(!s.empty()){
			System.out.println(s.pop());
		}
	}
	private static void jarvis(Point[] points, int n){
		if (n < 3) {
			return;
		}
		ArrayList<Point> result = new ArrayList<>();
		int leftMost = 0;
		for(int i = 1; i < n; i++){
			if (points[i].getX() < points[leftMost].getX()) {
				leftMost = i;
			}
		}
		int p = leftMost;
		int q;
		do {
			result.add(points[p]);
			q = (p + 1) % n;
			for(int i = 0; i < n; i++){
				if (orientation(points[p], points[i], points[q]) == 2) {
					q = i;
				}
			}
			p = q;
		} while (p != leftMost);
		print(result);
		
	}
	private static int orientation(Point p, Point q, Point r) {
		float result = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
		if(result > 0) {
			return 1;
		}
		else if(result < 0) {
			return 2;
		}
		else{
			return 0;
		}
	}
	private static void swap(Point p1, Point p2) {
		float tempX = p1.getX();
		float tempY = p1.getY();
		p1.setX(p2.getX());
		p1.setY(p2.getY());
		p2.setX(tempX);
		p2.setY(tempY);
	}
	private static float distSquare(Point p1, Point p2) {
		return (p1.getX() - p2.getX())*(p1.getX() - p2.getX()) +
		          (p1.getY() - p2.getY())*(p1.getY() - p2.getY());
	}
	private static Point nextToTop(Stack<Point> s){
		Point point = s.peek();
		s.pop();
		Point result = s.peek();
		s.push(point);
		return result;
	}
	static class customComparator implements Comparator<Point> {

		@Override
		public int compare(Point p1, Point p2) {
			int o = orientation(p0,p1,p2);
			if (o==0) {
				return (distSquare(p0, p2) >= distSquare(p0, p1))? -1 : 1;
			}
			return (o == 2) ? -1 : 1;
		}
	}
}
