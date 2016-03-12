package generators;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WorldBuilder {
	private BoardObject[][] world;
	private int minRoomSize = 5;
	private int maxRoomSize = 11;
	private int width = 111;
	private int height = 107;
	private Random rand;
	
	
	public static final int NUM_ATTEMPTS = 200;
	
	public WorldBuilder() {
		world = new BoardObject[height][width];
		rand = new Random();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				world[y][x] = new BoardObject('@');
			}
		}
	}
	/*
	 * H >> 1 2 3 4 5 6
	 * E >> 7 8 9 10 11 12
	 * I>> a b c d e f g
	 * G >> h i j k l m n
	 * H >> o p q r s t u
	 * T >> v w x y z 1 2
	 * 
	 */
	
	public static void main(String[] args) {
		WorldBuilder builder = new WorldBuilder();
		builder.populate();
	}
	
	
	public String toString() {
		String result = "";
		for (int y = 0; y < height; y++) {
			String currentLevel = "";
			for (int x = 0; x < width; x++) {
				currentLevel += world[y][x] + " ";
			}
			currentLevel += "\n";
			result += currentLevel;
		}
		return result;
	}
	
	
	public void populate() {		
		this.placeRooms();	//place random non-overlapping rooms

		this.placeMazes();	//fill in remaining solid regions with mazes
		this.connectMaze();//connect mazes with rooms, with chances for extra connections.
		//System.out.println(this);
		this.sparsify();//remove dead ends from the dungeon.
		System.out.println(this);

	}	
	
	private void sparsify() {
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				Point p = new Point(x, y);
				removeDeadEnd(p);
			}
		}
	}
	
	private void removeDeadEnd(Point p) {
		if (isDeadEnd(p) && world[p.y][p.x].isOpen()) {
			world[p.y][p.x].c = '@';
			removeDeadEnd(new Point(p.x, p.y - 1));
			removeDeadEnd(new Point(p.x, p.y + 1));
			removeDeadEnd(new Point(p.x + 1, p.y));
			removeDeadEnd(new Point(p.x - 1, p.y));			
		}
	}
	
	private boolean isDeadEnd(Point p) {
		int numEdges = 0;
		if (inBounds(p)) {
			numEdges += world[p.y-1][p.x].isWall() ? 1 : 0;
			numEdges += world[p.y+1][p.x].isWall() ? 1 : 0;
			numEdges += world[p.y][p.x-1].isWall() ? 1 : 0;
			numEdges += world[p.y][p.x+1].isWall() ? 1 : 0;
		}
		return numEdges >= 3;
	}
	
	private void connectMaze() {
		Map<Integer, List<Point>> roomConnections = new HashMap<>(); //map of room number to a list of possible connections into the maze.
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				int realnum = -1;
				int num = world[y][x].roomNum;
				if (num == -1) {
					if (num != world[y-1][x].roomNum) {
						realnum = world[y-1][x].roomNum;
					} else if(num != world[y+1][x].roomNum ) {
						realnum = world[y+1][x].roomNum;
					} else if (num != world[y][x-1].roomNum ) {
						realnum = world[y][x-1].roomNum;
					} else if (num != world[y][x+1].roomNum) {
						realnum = world[y][x+1].roomNum;
					}
				}
				if (realnum != -1) { 
					if (roomConnections.get(realnum) == null) {
						roomConnections.put(realnum, new ArrayList<Point>());
					}
					roomConnections.get(realnum).add(new Point(x, y));	
					//world[y][x].c = '.';

				}
				
			}
		}
		System.out.println(roomConnections.keySet());
		for (Integer i: roomConnections.keySet()) {
			List<Point> list = roomConnections.get(i);
			int idx = rand.nextInt(list.size());
			Point removed = list.get(idx);
			world[removed.y][removed.x].c = ' ';
			while (isDeadEnd(removed)) {
				world[removed.y][removed.x].c = '@';
				idx = rand.nextInt(list.size());
				removed = list.get(idx);
				world[removed.y][removed.x].c = ' ';

			}
			list.remove(idx);
			for (int j = 0; j < list.size(); j++) {
				int val = rand.nextInt(100);
				if (val < 10) {//1/10 chance of generating an extra hole.
					Point p = list.get(j);
					world[p.y][p.x].c = ' ';
				}
			}
			//System.out.println(this);
		}
				
		
	}
	
	//starts mazes for all tiles that could possibly have a point be placed there.
	private void placeMazes() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (checkValidSpot(x, y, true)) {
					Point next = new Point(x, y);
					generateMaze(next, next);
				}
			}
		}
	}
	
	private boolean inBounds(Point p) {
		return p.x > 1 && p.x < this.width - 1 && p.y > 1 && p.y < this.height - 1;
	}
	
	private boolean isSurroundedByWalls(Point p) {
		if (p.x <= 0 || p.x >= width - 1 || p.y <= 0 || p.y >= height - 1) {
			return false;
		} else {
			return world[p.y][p.x - 1].isWall() &&
					world[p.y][p.x + 1].isWall() &&
					world[p.y + 1][p.x + 1].isWall() &&
					world[p.y - 1][p.x + 1].isWall() &&
					world[p.y + 1][p.x - 1].isWall() &&
					world[p.y - 1][p.x - 1].isWall() &&
					world[p.y + 1][p.x].isWall() &&
					world[p.y - 1][p.x].isWall();

		}
	}
	
	//generates a perfect maze, by creating a minimally spanning tree
	private void generateMaze(Point p, Point p2) {
		if (inBounds(p) && !world[p.y][p.x].visited) {
			if (!nextToRoom(p) && !adjacentToMoreThanOne(p) && !createsLoop(p, p2)) {			
				world[p.y][p.x].markVisited();//mark visited and set character to tunnel.
				world[p.y][p.x].carve();
				List<Point> directions = new ArrayList<Point>();
				directions.add(new Point(p.x, p.y - 1));
				directions.add(new Point(p.x, p.y + 1));
				directions.add(new Point(p.x + 1, p.y));
				directions.add(new Point(p.x - 1, p.y));
				Collections.shuffle(directions);
				for (Point pt : directions) {
					generateMaze(pt, p);
				}
				
			}
		}
		
	}
	
	private boolean createsLoop(Point to, Point from) {
		int dx = to.x - from.x;
		int dy = to.y - from.y;
		return world[to.y + dy][to.x + dx].visited;		
	}
	
	private boolean adjacentToMoreThanOne(Point p) {
		//check that tiles adjacent in a corner shape are not already carved.
		return (world[p.y - 1][p.x].visited && world[p.y][p.x + 1].visited ||
		world[p.y + 1][p.x].visited && world[p.y][p.x + 1].visited ||
		world[p.y - 1][p.x].visited && world[p.y][p.x - 1].visited ||
		world[p.y + 1][p.x].visited && world[p.y][p.x - 1].visited);
	}

	public boolean nextToRoom(Point p) {
		return world[p.y][p.x - 1].roomNum != -1 ||
				world[p.y][p.x + 1].roomNum != -1 ||
				world[p.y + 1][p.x + 1].roomNum != -1 ||
				world[p.y - 1][p.x + 1].roomNum != -1 ||
				world[p.y + 1][p.x - 1].roomNum != -1 ||
				world[p.y - 1][p.x - 1].roomNum != -1 ||
				world[p.y + 1][p.x].roomNum != -1 ||
				world[p.y - 1][p.x].roomNum != -1;

	
	}
	
	private void placeRooms() {
		for (int i = 0; i < NUM_ATTEMPTS; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			int roomWidth = rand.nextInt(this.minRoomSize) + (this.maxRoomSize - this.minRoomSize);
			int roomHeight = rand.nextInt(this.minRoomSize) + (this.maxRoomSize - this.minRoomSize);
			attemptRoomPlace(x, y, roomWidth, roomHeight, i);
		}
	}
	
	/*How we will treat the array:
	 * H >> 1 2 3 4 5 6
	 * E >> 7 8 9 10 11 12
	 * I>> a b c d e f g
	 * G >> h i j k l m n
	 * H >> o p q r s t u
	 * T >> v w x y z 1 2
	 * 
	 */
	
	private boolean attemptRoomPlace(int x, int y, int roomWidth, int roomHeight, int attempt) {
		for (int h = y; h < roomHeight + y; h++) {
			for (int w = x; w < roomWidth + x; w++) {
				if (!checkValidSpot(w, h, false)) {
					return false;
				}
			}
		}
		//at this point,its safe to place a room.
		for (int h = y; h < roomHeight + y; h++) {
			for (int w = x; w < roomWidth + x; w++) {
				world[h][w].makeRoom(attempt);
			}
		}
		return true;
	}
	
	/*This method returns if a spot will allow a room to be placed there. A spot is valid if:
		1- it is inside the bounds of the arrya
		2- it is not already a room
		3- it not be placed adjacent to another room.
	takes an x, y coordinate to check
	takes a boolean, determining if we're analyzing a maze.
	if it's a maze, we don't case about adjacency.
	if it's a room, we do care about adjacency.*/
	private boolean checkValidSpot(int x, int y, boolean maze) {
		//TODO: check corners as well.
		boolean valid = !(x < 1 || x >= width - 1 || y < 1 || y >= height - 1 || world[y][x].isOpen()||
						world[y + 1][x].isOpen() || world[y - 1][x].isOpen() || world[y][x + 1].isOpen()||
						world[y][x - 1].isOpen());
		return valid;
	}

	private class BoardObject {
		private char c;
		private boolean visited;
		private int roomNum;
		
		public BoardObject(char c, int roomNum) {
			this.c = c;
			this.roomNum = roomNum;
		}
		
		public void carve() {
			this.c = ' ';		
		}

		public BoardObject(char c) {
			this(c, -1);
		}
		
		public String toString() {
			return c + "";
		}
		
		public void markVisited() {
			this.visited = true;
		}
		
		public void setDisplayVal(char c) {
			this.c = c;
		}
		
		public void makeRoom(int roomNum) {
			this.roomNum = roomNum;
			this.c = ' ';
		}
		
		public boolean isWall() {
			return this.c == '@';
		}
		
		public boolean isOpen() {
			return this.c == ' ';
		}
		
	}

	public char[][] toCharArray() {
		char[][] arr = new char[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				arr[y][x] = world[y][x].c;
			}
		}
		return arr;
	}
	
}

