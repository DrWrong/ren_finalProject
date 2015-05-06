package tk.drwrong.ontology;

import java.util.Properties;

import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer.Args;

//import com.sun.corba.se.spi.orb.StringPair;
//import com.sun.net.ssl.internal.www.protocol.https.Handler;
//import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;


import tk.drwrong.ontology.handler.OntologyHandler;
//import com.clarkparsia.sparqlowl.parser.antlr.SparqlOwlParser.functionCall_return;
//import tk.drwrong.ontology.thrift.OntologyService;
import tk.drwrong.ontology.thrift.OntologyService.Iface;
import tk.drwrong.ontology.thrift.OntologyService.Processor;
//import tk.drwrong.ontology.utils.PropertyFactory;

public class ThriftOntologyServer {
	public static OntologyHandler handler;
	public static Processor<Iface> processor;
	
	public static void main(String[] args){
		try{
			handler = new OntologyHandler();
			processor = new Processor<Iface>(handler);
			Runnable simple = new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					simple(processor);
				}
			};
			new Thread(simple).start();
		} catch(Exception x){
			x.printStackTrace();
		}
	}
	
	public static void simple(Processor<Iface> processor){
//		Properties prop;
//		try {
//			PropertyFactory propertyFactory = new PropertyFactory();
//			prop = propertyFactory.prop;
//		} catch (Exception e) {
//			// TODO: handle exception
//			return;
//		}
		try {
			TServerTransport serverTransport = new TServerSocket(9090);
			TSimpleServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
