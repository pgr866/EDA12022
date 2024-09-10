package eda1.practica04;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class RoadNetworkTestJUnit5 {
	private String directorioEntrada = System.getProperty("user.dir") + File.separator + "Practicas - src"
			+ File.separator + "eda1" + File.separator + "practica04" + File.separator;

	@Test
	@Order(1)
	public void ejemploClase() {
		Network<Integer> net = new Network<Integer>();
		net.addVertex(0);
		net.addVertex(1);
		net.addVertex(2);
		net.addVertex(3);
		net.addVertex(4);
		net.addVertex(5);
		net.addVertex(6);

		net.addEdge(4, 0, 1);
		net.addEdge(4, 6, 1);
		net.addEdge(4, 3, 1);
		net.addEdge(1, 4, 1);
		net.addEdge(3, 1, 1);
		net.addEdge(3, 2, 1);
		net.addEdge(2, 5, 1);
		net.addEdge(5, 4, 1);

		assertEquals("{0={}, 1={4=1.0}, 2={5=1.0}, 3={1=1.0, 2=1.0}, 4={0=1.0, 3=1.0, 6=1.0}, 5={4=1.0}, 6={}}", net.toString());
		assertEquals("[0, 3, 6]", net.getNeighbors(4).toString());
		assertEquals("[1, 2]", net.getNeighbors(3).toString());
		assertNull(net.getNeighbors(8));
		assertNull(net.getNeighbors(8));
		assertTrue(net.getWeight(2, 5) == 1.0);
		assertTrue(net.getWeight(0, 3) == -1);
		assertTrue(net.numberOfVertices() == 7);
		assertNull(net.toArrayBF(8));
		assertNull(net.toArrayDF(8));
		assertEquals("[3, 1, 2, 4, 5, 0, 6]", net.toArrayBF(3).toString());
		assertEquals("[3, 2, 5, 4, 6, 0, 1]", net.toArrayDF(3).toString());

		net.removeVertex(0);
		net.removeVertex(1);
		net.removeVertex(2);
		net.removeVertex(3);
		net.removeVertex(4);
		assertTrue(net.numberOfVertices() == 2);
		assertEquals("{5={}, 6={}}", net.toString());
		net.clear();
		assertEquals("{}", net.toString());
		net = null;
	}

	@Test
	@Order(2)
	public void testReadRoadNetwork() {
		RoadNetwork net = new RoadNetwork();
		net.load(directorioEntrada + "data.txt");

		assertTrue(net.numberOfVertices() == 21);
//		assertTrue(net.numberOfEdges() == 58);

		String cadena = "{Albacete={Madrid=251.0, Murcia=150.0, Valencia=191.0}, "
				+ "Almeria={Granada=173.0, Murcia=224.0}, " + "Badajoz={Caceres=90.0, Huelva=234.0, Madrid=403.0}, "
				+ "Barcelona={Gerona=100.0, Valencia=349.0, Zaragoza=296.0}, "
				+ "Bilbao={Madrid=395.0, Oviedo=304.0, Valladolid=280.0, Zaragoza=324.0}, " + "Caceres={Badajoz=90.0}, "
				+ "Cadiz={Sevilla=125.0}, " + "Corunya={Valladolid=455.0, Vigo=171.0}, "
				+ "Gerona={Barcelona=100.0, Lerida=222.0}, "
				+ "Granada={Almeria=173.0, Jaen=99.0, Murcia=278.0, Sevilla=256.0}, "
				+ "Huelva={Badajoz=234.0, Sevilla=92.0}, " + "Jaen={Granada=99.0, Madrid=335.0, Sevilla=242.0}, "
				+ "Lerida={Gerona=222.0}, "
				+ "Madrid={Albacete=251.0, Badajoz=403.0, Bilbao=395.0, Jaen=335.0, Valladolid=193.0, Zaragoza=325.0}, "
				+ "Murcia={Albacete=150.0, Almeria=224.0, Granada=278.0, Valencia=241.0}, " + "Oviedo={Bilbao=304.0}, "
				+ "Sevilla={Cadiz=125.0, Granada=256.0, Huelva=92.0, Jaen=242.0}, "
				+ "Valencia={Albacete=191.0, Barcelona=349.0, Murcia=241.0}, "
				+ "Valladolid={Bilbao=280.0, Corunya=455.0, Madrid=193.0, Vigo=356.0}, "
				+ "Vigo={Corunya=171.0, Valladolid=356.0}, "
				+ "Zaragoza={Barcelona=296.0, Bilbao=324.0, Madrid=325.0}}";

		assertEquals(cadena, net.toString());

		net.clear();
		net = null;
	}

	@Test
	@Order(3)
	public void testNetworkIteratorL_BFS_DFS() {
		RoadNetwork net = new RoadNetwork();
		net.load(directorioEntrada + "data.txt");

		String str = "";

		for (String v : net) { // Iteracion sobre keySet() ... orden lexicografico
			str += v + " ";
		}
		assertEquals(
				"Albacete Almeria Badajoz Barcelona Bilbao Caceres Cadiz Corunya Gerona Granada Huelva Jaen Lerida Madrid Murcia Oviedo Sevilla Valencia Valladolid Vigo Zaragoza ",
				str);

		str = "";
		for (String v : net.breadthFirstIterator("Almeria")) { // Iteracion en anchura
			str += v + " ";
		}
		assertEquals(
				"Almeria Granada Murcia Jaen Sevilla Albacete Valencia Madrid Cadiz Huelva Barcelona Badajoz Bilbao Valladolid Zaragoza Gerona Caceres Oviedo Corunya Vigo Lerida ",
				str);

		str = "";
		for (String v : net.depthFirstIterator("Almeria")) { // Iteracion en profundidad
			str += v + " ";
		}
		assertEquals(
				"Almeria Murcia Valencia Barcelona Zaragoza Madrid Valladolid Vigo Corunya Bilbao Oviedo Jaen Sevilla Huelva Badajoz Caceres Granada Cadiz Albacete Gerona Lerida ",
				str);

		net.clear();
		net = null;
	}

	@Test
	@Order(4)
	public void testToArray() {
		RoadNetwork net = new RoadNetwork();
		net.load(directorioEntrada + "data.txt");
		assertEquals(net.toArrayDF("Almeria").toString(),
				"[Almeria, Murcia, Valencia, Barcelona, Zaragoza, Madrid, Valladolid, Vigo, Corunya, Bilbao, Oviedo, Jaen, Sevilla, Huelva, Badajoz, Caceres, Granada, Cadiz, Albacete, Gerona, Lerida]");

		assertEquals(net.toArrayBF("Almeria").toString(),
				"[Almeria, Granada, Murcia, Jaen, Sevilla, Albacete, Valencia, Madrid, Cadiz, Huelva, Barcelona, Badajoz, Bilbao, Valladolid, Zaragoza, Gerona, Caceres, Oviedo, Corunya, Vigo, Lerida]");

		for (String start : net) {
			assertEquals(net.toArrayDF(start), net.toArrayDFRecursive(start));
		}

		net.clear();
		net = null;
	}

	@Test
	@Order(5)
	public void testDijkstra() {
		RoadNetwork net = new RoadNetwork();
		net.load(directorioEntrada + "data.txt");

		assertNull(net.getDijkstra(null, "Granada"));
		assertNull(net.getDijkstra("Granada", null));
		assertNull(net.getDijkstra("Sevilla", "Sevilla"));
		assertNull(net.getDijkstra("Almeria", "Sinsinati"));

		assertEquals("[Granada, Jaen, Madrid, Bilbao, Oviedo]", net.getDijkstra("Granada", "Oviedo").toString());
		assertEquals("[Almeria, Granada, Sevilla, Huelva, Badajoz, Caceres]",
				net.getDijkstra("Almeria", "Caceres").toString());

		assertEquals("[845.0, Almeria=0.0, Granada=173.0, Sevilla=256.0, Huelva=92.0, Badajoz=234.0, Caceres=90.0]",
				net.getDijkstraWithDistance("Almeria", "Caceres").toString());
		assertEquals("[1100.0, Oviedo=0.0, Bilbao=304.0, Madrid=395.0, Albacete=251.0, Murcia=150.0]",
				net.getDijkstraWithDistance("Oviedo", "Murcia").toString());

		net.clear();
		net = null;
	}
}
