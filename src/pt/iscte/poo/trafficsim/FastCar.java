package pt.iscte.poo.trafficsim;

import java.awt.geom.Point2D;
import pt.iul.ista.poo.utils.Orientation;

public class FastCar extends Car {
	
	public FastCar(Street street, TrafficSim traffic){
		super(street, traffic);
		createFastCar();
		verifyOrientation();
		speed();
	}
	
	@Override
	public int getHeight() {
		if(getOrientation() == Orientation.EAST || getOrientation() == Orientation.WEST) {
			return 20;
		} 
		else {
			return 40;
		}
	}

	@Override
	public int getWidth() {
		if(getOrientation() == Orientation.EAST || getOrientation() == Orientation.WEST) {
			return 40;
		} 
		else {
			return 20;
		}
	}
	
	public void createFastCar() {
		int center = 10;
			if(getStreet().getOrientation() == Orientation.EAST) {
				setOrientation(Orientation.EAST);
				setImageName("RedCar_E");
				setPosition(new Point2D.Double(getStreet().getStartX() - getWidth(), getStreet().getStartY() + center));
			}
			if(getStreet().getOrientation() == Orientation.SOUTH) {
				setOrientation(Orientation.SOUTH);
				setImageName("RedCar_S");
				setPosition(new Point2D.Double(getStreet().getStartX() + center, getStreet().getStartY() - getHeight()));
			}
			if(getStreet().getOrientation() == Orientation.NORTH) {
				setOrientation(Orientation.NORTH);
				setImageName("RedCar_N");
				setPosition(new Point2D.Double(getStreet().getStartX() + center, getStreet().getStreetHeight()));
			}
			if(getStreet().getOrientation() == Orientation.WEST) {
				setOrientation(Orientation.WEST);
				setImageName("RedCar_W");
				setPosition(new Point2D.Double(getStreet().getStreetLength(), getStreet().getStartY() + center));
			}
		}
	
	public void verifyOrientation() {
		if(getOrientation() == Orientation.EAST && !getName().equals("RedCar_E")) {
			System.out.println("Imagem do carro não corresponde ao Este!!");
			System.exit(1);
		}
		if(getOrientation() == Orientation.WEST && !getName().equals("RedCar_W")) {
			System.out.println("Imagem do carro não corresponde ao Oeste!!");
			System.exit(1);
		}
		if(getOrientation() == Orientation.NORTH && !getName().equals("RedCar_N")){	
			System.out.println("Imagem do carro não corresponde ao Norte!!");
			System.exit(1);
		}
		if(getOrientation() == Orientation.SOUTH && !getName().equals("RedCar_S")) {
			System.out.println("Imagem do carro não corresponde ao Sul!!");
			System.exit(1);
		}
	}
	
	public void speed() {
		setFastCar_speed(20);
	}
}
