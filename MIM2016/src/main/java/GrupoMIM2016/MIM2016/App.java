package GrupoMIM2016.MIM2016;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.NameUseEnum;
import ca.uhn.fhir.parser.IParser;


public class App 
{
    public static void main( String[] args )
    {
    	// Context for DSTU2
    	FhirContext ctxDstu2 = FhirContext.forDstu2();
    	
    	// Patient resource
    	String msgString = "<Patient xmlns=\"http://hl7.org/fhir\">"
    	  + "<image><contentType value=\"application/dicom\" /><url value=\"http://10.1.2.3:1000/wado?requestType=WADO&amp;wado_details...\" /><hash value=\"EQH/..AgME\" /></image>"
    	  + "<text><status value=\"generated\" /><div xmlns=\"http://www.w3.org/1999/xhtml\">John Cardinal</div></text>"
    	  + "<identifier><system value=\"http://orionhealth.com/mrn\" /><value value=\"1234556\" /></identifier>"
    	  + "<name><use value=\"official\" /><family value=\"del Aguila\" /><given value=\"Daniel\" /></name>"
    	  + "<gender><coding><system value=\"http://hl7.org/fhir/v3/AdministrativeGender\" /><code value=\"M\" /></coding></gender>"
    	  + "<address><use value=\"home\" /><line value=\"Av Colón 4362\" /></address><active value=\"true\" />"
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
    	String addres = patient.getAddress().get(0).getLine().get(0).getValue();
    	String image = patient.getPhotoFirstRep().getTitle();
    	
    	System.out.println(patientId);
    	System.out.println(familyName);
    	System.out.println(addres);
    	System.out.println(image);
    	
    	Patient patient2 = new Patient();
   // Add an MRN (a patient identifier)
   IdentifierDt id = patient2.addIdentifier();
   id.setSystem("http://example.com/fictitious-mrns");
   id.setValue("MRN001");
    
   // Add a name
   HumanNameDt name = patient2.addName();
   name.setUse(NameUseEnum.OFFICIAL);
   name.addFamily("del Aguila");
   name.addGiven("Dalí");
   
   GenderDt gender = patient2.addGender();
    
   // We can now use a parser to encode this resource into a string.
   String encoded = ctxDstu2.newXmlParser().encodeResourceToString(patient2);
   System.out.println(encoded);
    }
    
}
