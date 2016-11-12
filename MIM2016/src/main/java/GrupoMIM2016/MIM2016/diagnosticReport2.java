package GrupoMIM2016.MIM2016;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hl7.fhir.instance.hapi.validation.FhirInstanceValidator;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.ResourceMetadataKeyEnum;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.DiagnosticReport;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticReportStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

//@ResourceDef(name="DiagnosticReport", profile="http://hl7.no/fhir/StructureDefinition/LabDiagnosticReportNorway")
public class diagnosticReport2  {
  public static void main( String[] args )
  {
  	//Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	// Create a FhirInstanceValidator and register it to a validator
   	FhirValidator validator = ctxDstu2.newValidator();
   	FhirInstanceValidator instanceValidator = new FhirInstanceValidator();
   	validator.registerValidatorModule(instanceValidator);
  	
  	//Create a Client
    String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
    IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
    
    //Diagnostic report characteristics
    
    //Add MetaData
    dr1.getResourceMetadata().put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.no/fhir/StructureDefinition/LabPatientNorway");
    
    //Add Status
    dr1.setStatus(DiagnosticReportStatusEnum.PARTIAL);
    
    //Add Coding
    CodingDt code = dr1.getCode().addCoding();
  	code.setSystem("http://hl7.org/fhir/ValueSet/report-codes");
  	code.setCode("600-7");
  	code.setDisplay("Bacteria identified in Blood by Culture");
  	
  	//Add Subject
  	ResourceReferenceDt subject = dr1.getSubject();
  	subject.setReference("Patient/109908");
  	
  	//Add efective time
  	dr1.setEffective(new DateTimeDt("2016-05-05"));
  	
  	//Add issued
  	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  	Date date = new Date();
  	String fecha = dateFormat.format(date);
  	String fecha2 = fecha.substring(0, fecha.length() - 2) + ":" + fecha.substring(fecha.length() - 2);
  	dr1.setIssued(new InstantDt(fecha2));
    
  	//Add performer
  	ResourceReferenceDt performer = dr1.getPerformer();
  	performer.setReference("Organization/63496");
  	
  	//Add result
  	List<ResourceReferenceDt> resultList = dr1.getResult();
  	resultList.add(new ResourceReferenceDt("Observation/14901"));
  	resultList.add(new ResourceReferenceDt("Observation/14902"));
  	
  	//Add coclusion
  	dr1.setConclusion("Positive bloood culture");
  	
  	// Validate
  	ValidationResult result = validator.validateWithResult(dr1);
  	 
  	// Do we have any errors or fatal errors?
  	System.out.println(result.isSuccessful()); // false
  	 
  	// Show the issues
  	for (SingleValidationMessage next : result.getMessages()) {
  	   System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
  	}  	
  	
 		//Use the client to store a new resource instance
 		MethodOutcome outcome = client.create().resource(dr1).execute();
   
    //Print the id of the newly created resource
 		System.out.println(outcome.getId());
 		
  }
}
