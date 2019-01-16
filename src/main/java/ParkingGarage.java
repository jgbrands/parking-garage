import simulator.Simulator;
import simulator.config.SimulatorConfiguration;
import simulator.config.SpawnRatio;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class ParkingGarage {
	public static void main(String[] args) {
		SimulatorConfiguration configuration = new SimulatorConfiguration();

		for (int i = 0; i < 7; i++) {
			SpawnRatio spawnRatio = new SpawnRatio();
			spawnRatio.index = i;
			spawnRatio.base = 1 + 2 * i;
			spawnRatio.min = (float) (Math.abs(Math.sin(i) / 2));
			spawnRatio.max = (float) (Math.abs(Math.cos(i) / 2));

			configuration.spawnRatios.add(spawnRatio);
		}

		try {
			JAXBContext context = JAXBContext.newInstance(SimulatorConfiguration.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(configuration, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Simulator simulator = new Simulator();
		simulator.run();
	}
}
