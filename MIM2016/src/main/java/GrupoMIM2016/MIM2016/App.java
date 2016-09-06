package GrupoMIM2016.MIM2016;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.AttachmentDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.dstu2.valueset.NameUseEnum;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;

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
    	
    	//Create a Client
      String serverBaseUrl = "http://spark.furore.com/fhir";
      IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
    	 
    	// The hapi context object is used to create a new XML parser
    	// instance. The parser can then be used to parse (or unmarshall) the
    	// string message into a Patient object
    	//IParser parser = ctxDstu2.newXmlParser();
    	//Patient patient = parser.parseResource(Patient.class, msgString);
    	 
      // Use the client to read back the new instance using the
  		// ID we retrieved from the read
  		Patient patient = client.read(Patient.class, "spark6451");
      
    	// The patient object has accessory methods to retrieve all of the
    	// data which has been parsed into the instance.
    	String patientId = patient.getIdentifier().get(0).getValue();
    	String familyName = patient.getName().get(0).getFamily().get(0).getValue();
    	String addres = patient.getAddress().get(0).getLine().get(0).getValue();
    	
    	System.out.println(patientId);
    	System.out.println(familyName);
    	System.out.println(addres);
    	System.out.println("Found ID:    " + patient.getId());
    	
    	Patient patient2 = new Patient();
    	//Add an MRN (a patient identifier)
    	IdentifierDt id = patient2.addIdentifier();
    	id.setSystem("wwww.regcivil.cl/rut");
    	id.setValue("1234556-K");
    
    	//Add a name
    	HumanNameDt name = patient2.addName();
    	name.setUse(NameUseEnum.OFFICIAL);
    	name.addFamily("McCartney");
    	name.addGiven("James");
    	name.addGiven("Paul");
    	name.addPrefix("Sir");
   
    	HumanNameDt name2 = patient2.addName();
    	name2.setUse(NameUseEnum.USUAL);
    	name2.addFamily("McCartney");
    	name2.addGiven("Paul");
   
    	//Add gender, Active?, Birthdate
    	patient2.setGender(AdministrativeGenderEnum.MALE);
    	patient2.setActive(true);
    	patient2.setBirthDate(new DateDt("1942-06-18"));
   
    	//Add deceased?
    	patient2.setDeceased(new BooleanDt(false));
   
    	//Add marital status
    	CodingDt married = patient2.getMaritalStatus().addCoding();
    	married.setSystem("http://hl7.org/fhir/v3/MaritalStatus");
    	married.setCode("M");
    	married.setDisplay("Married");
   
    	//Add address
    	AddressDt HomeAddress = patient2.addAddress();
    	HomeAddress.addLine("7 Cavendish Avenue, St Johns Wood");
    	HomeAddress.setCity("London");
    	HomeAddress.setCountry("United Kingdom");
    	HomeAddress.setPostalCode("3245");
   
    	//Add photo
    	AttachmentDt Photo = patient2.addPhoto();
    	Photo.setUrl(new UriDt("http://www.sopitas.com/wp-content/uploads/2016/03/PaulMcCartney.jpg"));
   
    	// We can now use a parser to encode this resource into a string.
    	IParser p = ctxDstu2.newXmlParser();
    	p.setPrettyPrint(true);
   
   		//Use the client to store a new resource instance
   		MethodOutcome outcome = client.create().resource(patient2).execute();
   
   //Print the id of the newly created resource
		System.out.println(outcome.getId());
   
   //String encoded = p.encodeResourceToString(patient2);
   //System.out.println(encoded);
   //
    }
}