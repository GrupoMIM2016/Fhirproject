package GrupoMIM2016.MIM2016;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.parser.IParser;


public class App 
{
    public static void main( String[] args )
    {
    	
    	
    	// Alternately, create a context for DSTU2
    	FhirContext ctxDstu2 = FhirContext.forDstu2();
    	// The following is an example Patient resource
    	String msgString = "<Patient xmlns=\"http://hl7.org/fhir\">"
    	  + "<text><status value=\"generated\" /><div xmlns=\"http://www.w3.org/1999/xhtml\">John Cardinal</div></text>"
    	  + "<identifier><system value=\"http://orionhealth.com/mrn\" /><value value=\"PRP1660\" /></identifier>"
    	  + "<name><use value=\"official\" /><family value=\"Cardinal\" /><given value=\"John\" /></name>"
    	  + "<gender><coding><system value=\"http://hl7.org/fhir/v3/AdministrativeGender\" /><code value=\"M\" /></coding></gender>"
    	  + "<address><use value=\"home\" /><line value=\"2222 Home Street\" /></address><active value=\"true\" />"
    	  + "</Patient>";
    	 
    	// The hapi context object is used to create a new XML parser
    	// instance. The parser can then be used to parse (or unmarshall) the
    	// string message into a Patient object
    	IParser parser = ctxDstu2.newXmlParser();
    	Patient patient = parser.parseResource(Patient.class, msgString);
    	 
    	// The patient object has accessor methods to retrieve all of the
    	// data which has been parsed into the instance.
    	String patientId = patient.getIdentifier().get(0).getValue();
    	String familyName = patient.getName().get(0).getFamily().get(0).getValue();
    	String gender = patient.getAddress().get(0).getCity();
    	
    	System.out.println(patientId); // PRP1660
    	System.out.println(familyName); // Cardinal
    	System.out.println(gender); // M
    }
}
