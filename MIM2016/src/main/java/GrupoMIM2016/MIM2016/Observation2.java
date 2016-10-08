package GrupoMIM2016.MIM2016;

import java.util.List;

import org.hl7.fhir.instance.hapi.validation.FhirInstanceValidator;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.valueset.ObservationStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

public class Observation2 {
  public static void main( String[] args )
  {
  	// Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	// Create a FhirInstanceValidator and register it to a validator
  	FhirValidator validator = ctxDstu2.newValidator();
  	FhirInstanceValidator instanceValidator = new FhirInstanceValidator();
  	validator.registerValidatorModule(instanceValidator);
  	
  	// Create a Client
    String serverBaseUrl = "http://spark.furore.com/fhir";
    IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
  	// Observation report resource
    Observation obs1 = new Observation();
    
    //Observation report characteristics
    //Add Status
    obs1.setStatus(ObservationStatusEnum.PRELIMINARY);
    
    //Add Code
    CodingDt code = obs1.getCode().addCoding();
  	code.setSystem("http://loinc.org/");
  	code.setCode("600-7");
  	code.setDisplay("Bacteria identified in Blood by Culture");
    
  	//Add Subject
  	ResourceReferenceDt subject = obs1.getSubject();
  	subject.setReference("Patient/109908");
  	
  	//Add effective time
  	obs1.setEffective(new DateTimeDt("2016-05-05"));
  	
  	//Add issued
  	obs1.setIssued(new InstantDt("2016-05-05T16:41:32.6-03:00"));
  	
  	//Add performer
  	List<ResourceReferenceDt> performerList = obs1.getPerformer();
  	performerList.add(new ResourceReferenceDt("Organization/63496"));
  	
  	//Add value
  	obs1.setValue(new StringDt("positive blood culture"));
  	
  	//Add interpretation
  	CodingDt interpretationCoding = new CodingDt();
  	interpretationCoding.setSystem("http://hl7.org/fhir/ValueSet/observation-interpretation");
  	interpretationCoding.setCode("POS");
  	interpretationCoding.setDisplay("Positive");
  	interpretationCoding.setUserSelected(true);
  	obs1.getInterpretation().addCoding(interpretationCoding);
  	//S: Susceptible. R: Resistant. VS: Very susceptible. SYN-S: Synergy - susceptible. SYN-R: Synergy - resistant. SSD: Susceptible-dose dependent
  	//POS: Positive, NEG: Negative
  	obs1.getInterpretation().setText("Cultivo positivo");
  	
  	//Add comments
  	obs1.setComments("E. coli BLEE");
  	
  	//Add reference range
  	obs1.addReferenceRange().setText("null");
  	
  	// Validate
  	ValidationResult result = validator.validateWithResult(obs1);
  	 
  	// Do we have any errors or fatal errors?
  	System.out.println(result.isSuccessful()); // false
  	 
  	// Show the issues
  	for (SingleValidationMessage next : result.getMessages()) {
  	   System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
  	}
  	
    //Use the client to store a new resource instance
 		MethodOutcome outcome = client.create().resource(obs1).execute();
   
    //Print the id of the newly created resource
 		System.out.println(outcome.getId());
 		
  }
}
