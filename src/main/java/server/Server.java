package server;
import javax.xml.ws.Endpoint;

public class Server {
	public static void main(String[] args) {

		 AJSService ajsService = ObjectFactory.INSTANCE.getAJSService();

		Endpoint.publish("http://localhost:9999/ajsservice", ajsService);

	}

}
