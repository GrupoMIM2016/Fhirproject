package GrupoMIM2016.MIM2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.instance.hapi.validation.FhirInstanceValidator;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.DiagnosticReport;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticReportStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

public class MakingDR {

	public static void main( String[] args ) throws IOException
  {
  	//Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	//Receive Input
  	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  	
  	// Create a FhirInstanceValidator and register it to a validator
   	FhirValidator validator = ctxDstu2.newValidator();
   	FhirInstanceValidator instanceValidator = new FhirInstanceValidator();
   	validator.registerValidatorModule(instanceValidator);
  	
  	//Create a Client
    String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
    IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
    IGenericClient client2 = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
    // Log requests and responses
 		client2.registerInterceptor(new LoggingInterceptor(true));
    
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
    
    //Diagnostic report characteristics
    //Add Status
    System.out.print("Status de la muestra: ");
    String status = br.readLine();
    if(status.equals("final")){
    	dr1.setStatus(DiagnosticReportStatusEnum.FINAL);
    	}
    else if (status.equals("partial")) {
    	dr1.setStatus(DiagnosticReportStatusEnum.PARTIAL);
			};
    
    //Add Coding
    CodingDt code = dr1.getCode().addCoding();
  	code.setSystem("http://hl7.org/fhir/ValueSet/report-codes");
  	code.setCode("600-7");
  	code.setDisplay("Bacteria identified in Blood by Culture");
  	
  	//Add Subject
  	System.out.print("Nombre del paciente: ");
    String nombre = br.readLine();
    
    // Build a search and execute it
    Bundle response = client.search()
  	.forResource(Patient.class)
  	.where(Patient.IDENTIFIER.exactly().systemAndIdentifier("wwww.regcivil.cl/rut", nombre))
  	.execute();
   
    IdDt firstResponseId = response.getEntries().get(0).getResource().getId();
    
  	ResourceReferenceDt subject = dr1.getSubject();
  	subject.setReference(firstResponseId);
  	
  	//Add effective time
  	System.out.print("Fecha de toma de muestra: ");
    String fecha = br.readLine();
  	dr1.setEffective(new DateTimeDt(fecha));
  	
  	//Add issued
  	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  Date date = new Date();
	  DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	  Date time = new Date();
  	dr1.setIssued(new InstantDt((dateFormat.format(date))+"T"+(timeFormat.format(time))+".0-03:00"));
 
  	//Add performer
  	ResourceReferenceDt performer = dr1.getPerformer();
  	performer.setReference("Organization/63496");
  	
  	//Add result
  	List<ResourceReferenceDt> resultList = dr1.getResult();
  	resultList.add(new ResourceReferenceDt("Observation/14901"));
  	resultList.add(new ResourceReferenceDt("Observation/14902"));
  	
  	//Add conclusion
    System.out.print("Culture conclusion: ");
    String conclusion = br.readLine();
  	dr1.setConclusion(conclusion);
  	
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
