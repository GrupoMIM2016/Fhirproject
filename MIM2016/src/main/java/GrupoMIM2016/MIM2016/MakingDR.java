package GrupoMIM2016.MIM2016;

import java.io.IOException;
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
	
	String comments = null;
	String idType = null;
	String patientId = null;
	String result = null;
	String sampleType = null;
	String status = null;
	String testDate = null;
	
	public String getIdType() {
	return this.idType;}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getComments() {
	return this.comments;}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPatientId() {
	return this.patientId;}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getResult() {
	return this.result;}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSampleType() {
	return this.sampleType;}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getStatus() {
	return this.status;}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTestDate() {
	return this.testDate;}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public void main( ) throws IOException{
		System.out.println("Test code: ");
		System.out.println("Report status: " + status);
		System.out.println("Sample: " + sampleType);
		System.out.println("Type of Patient Id: " + idType);
		System.out.println("Number of Patient Id: " + patientId);
		System.out.println("Date of Sample Withdrawal: " + testDate);
		System.out.println("Test Result: " + result);
		System.out.println("Comments: " + comments);
				
  	//Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	/*
  	// Create a FhirInstanceValidator and register it to a validator
   	FhirValidator validator = ctxDstu2.newValidator();
   	FhirInstanceValidator instanceValidator = new FhirInstanceValidator();
   	validator.registerValidatorModule(instanceValidator);
  	*/
  	
  	//Create a Client
    String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
    IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
    
    /*
    IGenericClient client2 = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
    // Log requests and responses
 		client2.registerInterceptor(new LoggingInterceptor(true));
    */
    
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
    
    //Diagnostic report characteristics
    //Add Status
    if(status.equals("final")){
    	dr1.setStatus(DiagnosticReportStatusEnum.FINAL);
    	}
    if (status.equals("partial")) {
    	dr1.setStatus(DiagnosticReportStatusEnum.PARTIAL);
			}
    if (status.equals("registered")) {
    	dr1.setStatus(DiagnosticReportStatusEnum.REGISTERED);
			};
    
    //Add Coding
    CodingDt code = dr1.getCode().addCoding();
    
  	if(sampleType.equals("blood")){
    code.setSystem("http://loinc.org/");
  	code.setCode("600-7");
  	code.setDisplay("Bacteria identified in Blood by Culture");}
  	if(sampleType.equals("urine")){
  	code.setSystem("http://loinc.org/");
    code.setCode("630-4");
    code.setDisplay("Bacteria identified in Urine by Culture");}	
  	
  	
  	//Add Subject    
    String web = "";
    if(idType.equals("RUT")){
    	 web= "wwww.regcivil.cl/rut";}
    if(idType.equals("Furore")){	
    	 web = "http://fhir.furore.com/NationalPatientID";}
    
		// Build a search and execute it
    Bundle response = client.search()
  	.forResource(Patient.class)
  	.where(Patient.IDENTIFIER.exactly().systemAndIdentifier(web, patientId))
  	.execute();
    IdDt firstResponseId = response.getEntries().get(0).getResource().getId();
  	ResourceReferenceDt subject = dr1.getSubject();
  	subject.setReference(firstResponseId);
  	
  	
  	//Add effective time
  	dr1.setEffective(new DateTimeDt(testDate));
  	
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
  	
  	//Add comments
  	dr1.setConclusion(comments);
  	
  	/*
  	// Validate
  	ValidationResult result = validator.validateWithResult(dr1);
  	 
  	// Do we have any errors or fatal errors?
  	System.out.println(result.isSuccessful()); // false
  	 
  	// Show the issues
  	for (SingleValidationMessage next : result.getMessages()) {
  	   System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
  	}  	
  	*/
  	
 		//Use the client to store a new resource instance
 		MethodOutcome outcome = client.create().resource(dr1).execute();
   
    //Print the id of the newly created resource
 		System.out.println(outcome.getId());
  }
}
