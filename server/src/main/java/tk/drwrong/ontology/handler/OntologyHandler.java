package tk.drwrong.ontology.handler;

import java.io.InputStream;
import java.util.Properties;

import org.apache.thrift.TException;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
//import com.hp.hpl.jena.sparql.function.library.namespace;


import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

import tk.drwrong.ontology.thrift.OntologyService.Iface;
import tk.drwrong.ontology.thrift.ReasonResult;
import tk.drwrong.ontology.thrift.SenseorValues;

public class OntologyHandler implements Iface{

	private OntModel model;
	private static String nameSpace = "http://www.semanticweb.org/user/ontologies/2015/3/untitled-ontology-27#";
	private static String owlFileName = "UnderwaterEnvironment.owl";
	private Individual exH2S1;
	private Individual exOXY2;
	private Individual exampleConducitivity2;
	private Individual exPH2;
	private Individual exTempL;
	private Property hasH2S_ConcentrationSensorValue;
	private Property hasOxygenSensorValue;
	private Property hasConductivitySensorValue;
	private Property hasPHSensorValue;
	private Property hasTemperatureSensorValue;
	private Individual exH2SReport;
	private Individual exGQOW;
	private Individual exOIL;
	private Individual exVD;
	private OntClass reportGoodQualityOfWater;
	private OntClass reportH2sAlert;
	private OntClass reportOilLeak;
	private OntClass verifyDataWithOtherAUVS;

	public OntologyHandler() throws Exception{
		System.out.println("Now init the handler");
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(owlFileName);
		model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC, null );
		if(inputStream == null){
			throw new Exception("owl file not found");
		}
		model.read(inputStream, "RDF/XML");
		exH2S1 = model.getIndividual(nameSpace + "exH2S1");
		exOXY2 = model.getIndividual(nameSpace + "exOXY2");
		exampleConducitivity2 = model.getIndividual(nameSpace + "exampleConductivity2");
		exPH2 = model.getIndividual(nameSpace + "exPH2");
		exTempL = model.getIndividual(nameSpace + "exTempL");
		exH2SReport = model.getIndividual(nameSpace + "exH2SReport");
		exGQOW = model.getIndividual(nameSpace + "exGQOW");
		exOIL = model.getIndividual(nameSpace + "exOIL");
		exVD = model.getIndividual(nameSpace + "exVD");
		reportGoodQualityOfWater = model.getOntClass(nameSpace + "ReportGoodQualityOfWater");
		reportH2sAlert = model.getOntClass(nameSpace + "ReportH2SAlert");
		reportOilLeak = model.getOntClass(nameSpace + "ReportOilLeak");
		verifyDataWithOtherAUVS = model.getOntClass(nameSpace + "VerifyDataWithOtherAUVs");
		hasH2S_ConcentrationSensorValue = model.getProperty(nameSpace + "hasH2S_ConcentrationSensorValue");
		hasOxygenSensorValue = model.getProperty(nameSpace + "hasOxygenSensorValue");
		hasConductivitySensorValue = model.getProperty(nameSpace + "hasConductivitySensorValue");
		hasPHSensorValue = model.getProperty(nameSpace + "hasPHSensorValue");
		hasTemperatureSensorValue = model.getProperty(nameSpace + "hasTemperatureSensorValue");
		// model.prepare();
		// System.out.println(exGQOW.hasOntClass(reportGoodQualityOfWater));
		// // System.out.println(exH2SReport.hasOntClass(reportH2sAlert));
		// System.out.println(exOIL.hasOntClass(reportOilLeak));
		// System.out.println(exVD.hasOntClass(verifyDataWithOtherAUVS));
//		System.out.println(exTempL);
	}

	private Double getPropertyValue(Individual ind, Property prop){
		RDFNode value = ind.getPropertyValue(prop);
		Literal literal = value.asLiteral();
		return new Double(literal.getFloat());
	}

	private void setPropertyValue(Individual ind, Property prop, Double doub){
		Literal literal = model.createTypedLiteral(new Float(doub));
		ind.setPropertyValue(prop, literal);
	}

	public SenseorValues getInitValues() throws TException {
		// TODO Auto-generated method stub
		SenseorValues sensorValues = new SenseorValues();
		sensorValues.exH2s = getPropertyValue(exH2S1, hasH2S_ConcentrationSensorValue);
		sensorValues.exOxy2 = getPropertyValue(exOXY2, hasOxygenSensorValue);
		sensorValues.exampleConducitivity2 = getPropertyValue(
				exampleConducitivity2, hasConductivitySensorValue);
		sensorValues.exPH2 = getPropertyValue(exPH2,hasPHSensorValue);
		sensorValues.exTempl = getPropertyValue(exTempL,hasTemperatureSensorValue);
		return sensorValues;
	}

	public ReasonResult perfomReason(SenseorValues sensors) throws TException {
		// TODO Auto-generated method stub
		 setPropertyValue(exH2S1, hasH2S_ConcentrationSensorValue, sensors.exH2s);
		 setPropertyValue(exOXY2, hasOxygenSensorValue, sensors.exOxy2);
		 setPropertyValue(exampleConducitivity2, hasConductivitySensorValue, sensors.exampleConducitivity2);
		 setPropertyValue(exPH2, hasPHSensorValue, sensors.exPH2);
		 setPropertyValue(exTempL, hasTemperatureSensorValue, sensors.exTempl);
		model.prepare();
		ReasonResult reasonResult = new ReasonResult();
		reasonResult.reportH2S = exH2SReport.hasOntClass(reportH2sAlert);
		// reasonResult.reportH2S = true;
		reasonResult.reportGQOW = exGQOW.hasOntClass(reportGoodQualityOfWater);
		reasonResult.reportOil = exOIL.hasOntClass(reportOilLeak);
		reasonResult.verifyData = exVD.hasOntClass(verifyDataWithOtherAUVS);
		return reasonResult;
	}

}
