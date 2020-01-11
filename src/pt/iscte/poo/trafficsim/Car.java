package pt.iscte.poo.trafficsim;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import pt.iul.ista.poo.gui.ImageComponent;
import pt.iul.ista.poo.utils.Orientation;

public abstract class Car implements ImageComponent {
	
	private String imageName;
	private int speed;
	private int NormalCar_speed;
	private int SlowCar_speed;
	private int FastCar_speed;
	private Point2D position;
	private int layer = 1;
	private Orientation orientation;
	private ArrayList<Car> cars;
	private ArrayList<Street> streets;
	private Street street;
	
	public Car(Street street, TrafficSim traffic) {
		this.street = street;
		streets = traffic.getStreets();
		cars = street.getCars();
	}
	
	@Override
	public String getName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setNormalCar_speed(int normalCar_speed) {
		NormalCar_speed = normalCar_speed;
	}

	public void setSlowCar_speed(int slowCar_speed) {
		SlowCar_speed = slowCar_speed;
	}

	public void setFastCar_speed(int fastCar_speed) {
		FastCar_speed = fastCar_speed;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	@Override
	public int getLayer() {
		return layer ;
	}
	
	public Street getStreet() {
		return street;
	}

	@Override
	public ImageIcon getImage() {
		return new ImageIcon();
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public void carSpeed() {
		if(imageName.equals("BlueCar_E") || imageName.equals("BlueCar_W") || imageName.equals("BlueCar_S") || imageName.equals("BlueCar_N")) {
			speed = NormalCar_speed;
		}
		if(imageName.equals("RedCar_E") || imageName.equals("RedCar_W") || imageName.equals("RedCar_S") || imageName.equals("RedCar_N")) {
			speed = FastCar_speed;
		}
		if(imageName.equals("GreenCar_E") || imageName.equals("GreenCar_W") || imageName.equals("GreenCar_S") || imageName.equals("GreenCar_N")) {
			speed = SlowCar_speed;
		}
	}
	
	public void carPosition() {
		if(getOrientation() == Orientation.EAST) {
			position = new Point2D.Double(position.getX() + speed , position.getY());
		}
		if(getOrientation() == Orientation.SOUTH) {
			position = new Point2D.Double(position.getX(), position.getY() + speed);
		}
		if(getOrientation() == Orientation.NORTH) {
			position = new Point2D.Double(position.getX(), position.getY() - speed);
		}
		if(getOrientation() == Orientation.WEST) {
			position = new Point2D.Double(position.getX() - speed, position.getY());
		}
	}

	
	public boolean isEmpty() {
		int n = 41;
		for(int i = 0;i < streets.size(); i++) {
			Street s = streets.get(i);
			cars = s.getCars();
				for(int j = 0; j < cars.size(); j++) {
					if(cars.get(j) != this){
						if(street.getName() == 'E' && street.getOrientation() == Orientation.EAST) {
							if(cars.get(j).getPosition().getX() > position.getX() && cars.get(j).getPosition().getY() >= position.getY() - getWidth()) {
								if(cars.get(j).getPosition().getX() < position.getX() + n + speed && cars.get(j).getPosition().getY() <= position.getY() + getHeight()) {
									return true;
								}
							}
						}
						if(street.getName() == 'S' && street.getOrientation() == Orientation.SOUTH) {
							if(cars.get(j).getPosition().getY() > position.getY() && cars.get(j).getPosition().getX() >= position.getX() - getHeight()) {
								if(cars.get(j).getPosition().getY() < position.getY() + n + speed && cars.get(j).getPosition().getX() <= position.getX() + getWidth()) {
									return true;
								}
							}
						}
						if(street.getName() == 'N' && street.getOrientation() == Orientation.NORTH) {
							if(cars.get(j).getPosition().getY() + cars.get(j).getHeight() < position.getY() + 40 && cars.get(j).getPosition().getX() >= position.getX() - getHeight()){
								if(cars.get(j).getPosition().getY() + cars.get(j).getHeight() > position.getY() - speed - 1 && cars.get(j).getPosition().getX() <= position.getX() + getWidth()) {
									return true;
								}
							}
						}
						if(street.getName() == 'W' && street.getOrientation() == Orientation.WEST) {
							if(cars.get(j).getPosition().getX() + cars.get(j).getWidth() < position.getX() + 40 && cars.get(j).getPosition().getY() >= position.getY() - getWidth()) {
								if(cars.get(j).getPosition().getX() + cars.get(j).getWidth() > position.getX() - speed - 1 && cars.get(j).getPosition().getY() <= position.getY() + getHeight()) {
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
	
	public void stopCollisions() {
		int n = 41;
		if(isEmpty()) {
			for(int i = 0; i < cars.size(); i++) {
				if(cars.get(i) != this) {
					if(street.getName() == 'E' && street.getOrientation() == Orientation.EAST) {
						if(cars.get(i).getPosition().getX() > position.getX() && cars.get(i).getPosition().getX() < position.getX() + n + speed) {
								speed = 0;
							}
						}
						if(street.getName() == 'S' && street.getOrientation() == Orientation.SOUTH)	{
							if(cars.get(i).getPosition().getY() > position.getY() && cars.get(i).getPosition().getY() < position.getY() + n + speed)	{
								speed = 0;
							}
						}
						if(street.getName() == 'N' && street.getOrientation() == Orientation.NORTH) {
							if(cars.get(i).getPosition().getY() + cars.get(i).getHeight() < position.getY() + getWidth() && cars.get(i).getPosition().getY() + cars.get(i).getHeight() > position.getY() - speed - 1) {
								speed = 0;
							}
						}
						if(street.getName() == 'W' && street.getOrientation() == Orientation.WEST) {
							if(cars.get(i).getPosition().getX() + cars.get(i).getWidth() < position.getX() + getHeight() && cars.get(i).getPosition().getX() + cars.get(i).getWidth() > position.getX() - speed - 1) {
								speed = 0;
							}
						}
					}
					else { 
						speed = cars.get(i-1).getSpeed();
					}
				}
			}
		}
	
	public void move() {
		carSpeed();
		stopCollisions();	
		carPosition();
	}

	@Override
	public String toString() {
		return "Car [imageName=" + imageName + ", speed=" + speed
				+ ", position=" + position + ", layer=" + layer + "]";
	}
}

