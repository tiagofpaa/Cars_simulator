package pt.iscte.poo.trafficsim;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ShapeComponent;
import pt.iul.ista.poo.gui.UserInterfaceWindow;
import pt.iul.ista.poo.utils.Orientation;

public class Street implements ShapeComponent {
	
	private char name;
	private Color color;
	private int layer = 0;
	private Shape shape;
	private int startX;
	private int startY;
	private int streetHeight;
	private int streetLength;
	private Orientation orientation;
	private double carProbability;
	private int maxSpeed;
	private TrafficSim traffic;
	private Car car;
	private ArrayList<Car> cars = new ArrayList<Car>();

	public Street(char name, TrafficSim traffic) {
		this.name = name;
		this.traffic = traffic;
	}
	
	public char getName() {
		return name;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public int getLayer() {
		return layer;
	}
	
	@Override
	public Shape getShape(){
		shape = new Rectangle2D.Double(startX, startY, streetLength, streetHeight);
		return shape;
	}
	
	@Override
	public Point2D getPosition() {
		return new Point2D.Double((int)shape.getBounds().x, (int)shape.getBounds().y);
	}
	
	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getStreetLength() {
		return streetLength;
	}

	public int getStreetHeight() {
		return streetHeight;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public double getCarProbability() {
		return carProbability;
	}
	
	public int getMaxSpeed(){
		return maxSpeed;
	}
	
	public ArrayList<Car> getCars() {
		return cars;
	}
	
	private Scanner extracted(String string) {
		return new Scanner(string);
	}
	
	public void readFile() {
		try {
			Scanner scanner = new Scanner(new File("streets.txt"));
			String string = scanner.nextLine();
				while(string.charAt(6) != name) {
					if(string == "")
						break;
					string = scanner.nextLine();
				}
				Scanner scan = extracted(string).useDelimiter(" |,");
					for(int i = 0; scan.hasNext(); i++) {
						String n = scan.next();
							if(i == 5) {
								startX = Integer.parseInt(n);
							}
							if(i == 8) {
								startY = Integer.parseInt(n);
							}
							if(i == 11) {
								streetLength = Integer.parseInt(n);
							}
							if(i == 14) {
								streetHeight = Integer.parseInt(n);
							}
							if(i == 17) {
								if(n.matches("NORTH")) {
									orientation = Orientation.NORTH;
								}
								if(n.matches("SOUTH")) {
									orientation = Orientation.SOUTH;
								}
								if(n.matches("EAST")) {
									orientation = Orientation.EAST;
								}
								if(n.matches("WEST")) {
									orientation = Orientation.WEST;
								}
							}
							if(i == 20) {
								carProbability = Double.parseDouble(n);
							}
							if(i == 23) {
								maxSpeed = Integer.parseInt(n);
							}
						}
						scan.close();
						scanner.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

	public void factoryCar(double typeCarProbability, double firstProbability, double secondProbability) {
		if(typeCarProbability <= firstProbability) {
			car = new NormalCar(this, traffic);
			cars.add(car);
			UserInterfaceWindow.getInstance().addImage(car);
		}
		if(typeCarProbability > firstProbability && typeCarProbability < secondProbability) {
			car = new SlowCar(this, traffic);
			cars.add(car);
			UserInterfaceWindow.getInstance().addImage(car);
		}
		if(typeCarProbability > secondProbability) {
			car = new FastCar(this, traffic);
			cars.add(car);
			UserInterfaceWindow.getInstance().addImage(car);
		}
	}

	public void createCar() {
		double typeCarProbability = Math.random();
		double firstProbability = 0.6;
		double secondProbability = 0.8;
		factoryCar(typeCarProbability, firstProbability, secondProbability);
	}

	public boolean canCreate() {
		for(int i = 0; i < cars.size(); i++) {
			Car car = cars.get(i);
			if(orientation == Orientation.EAST && car.getPosition().getX() < car.getWidth()) {
				return true;
			}
			if(orientation == Orientation.SOUTH && car.getPosition().getY() < car.getHeight()) {
				return true;
			}
			if(orientation == Orientation.NORTH && car.getPosition().getY() > (streetHeight - car.getHeight())) {
				return true;
			}
			if(orientation == Orientation.WEST && car.getPosition().getX() > (streetLength - car.getWidth())) {
				return true;
			}
		}
		return false;
	}
	
	public void moveCars() {
		for(int i = 0; i < cars.size(); i++) {
			car = cars.get(i);
			car.move();
		}
	}
	
	public void removeCars() {
		for(int i = 0; i < cars.size(); i++) {
			car = cars.get(i);
				if(name == 'E' && car.getOrientation() == Orientation.EAST && car.getPosition().getX() >= streetLength) {
					cars.remove(car);
					UserInterfaceWindow.getInstance().removeImage(car);
				}
				if(name == 'W' && car.getOrientation() == Orientation.WEST && car.getPosition().getX() <= startX - car.getWidth()) {
					cars.remove(car);
					UserInterfaceWindow.getInstance().removeImage(car);
				}
				if(name == 'S'&& car.getOrientation() == Orientation.SOUTH && car.getPosition().getY() >= streetHeight) {
					cars.remove(car);
					UserInterfaceWindow.getInstance().removeImage(car);
				if(name == 'N' && car.getOrientation() == Orientation.NORTH && car.getPosition().getY() <= startY - car.getHeight()) {
					cars.remove(car);
					UserInterfaceWindow.getInstance().removeImage(car);
				}
			}
		}
	}
}