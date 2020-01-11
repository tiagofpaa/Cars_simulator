package pt.iscte.poo.trafficsim;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import pt.iul.ista.poo.gui.UserInterfaceWindow;

public class TrafficSim implements Observer {

	private boolean stop;
	private ArrayList<Street> streets = new ArrayList<Street>();

	public ArrayList<Street> getStreets() {
		return streets;
	}
	
	public void addStreet() {
		UserInterfaceWindow gui = UserInterfaceWindow.getInstance();
		//Street EAST
		Street east = new Street('E', this);
		east.readFile();
		streets.add(east);
		//Street WEST
		gui.addShape(streets.get(0));
		Street west = new Street('W', this);
		west.readFile();
		streets.add(west);
		//Street SOUTH
		gui.addShape(streets.get(1));
		Street south = new Street('S', this);
		south.readFile();
		streets.add(south);
		gui.addShape(streets.get(2));
		//Street NORTH
		Street north = new Street('N', this);
		north.readFile();
		streets.add(north);
		gui.addShape(streets.get(3));
	}
	
	public void run() {
		UserInterfaceWindow gui = UserInterfaceWindow.getInstance();
		addStreet();
		
		gui.go();
		gui.addObserver(this);
			
			while (!stop) {
				double probability = Math.random();
					for(int i = 0; i < streets.size(); i++) {
						Street s = streets.get(i);
						if(!s.canCreate() && probability <= s.getCarProbability()) {
							s.createCar();
						}
						s.moveCars();	
						s.removeCars();
						}
						gui.update();
						
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					gui.dispose();
				}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Observer notified, shutting down");
		stop = true;
	}
}
