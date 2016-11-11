package GrupoMIM2016.MIM2016;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.api.ResourceMetadataKeyEnum;
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
  	
  	//Create a Client
    String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
  	//String serverBaseUrl = "http://10.42.0.10:8080/fhir/baseDstu2";
  	IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
    //Log requests and responses
 		client.registerInterceptor(new LoggingInterceptor(true));
    
    
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
    
    //Diagnostic report characteristics
    
    //Add MetaData
    dr1.getResourceMetadata().put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.no/fhir/StructureDefinition/LabPatientNorway");
    
    //Add Status
    if(status.equals("Final")){
    	dr1.setStatus(DiagnosticReportStatusEnum.FINAL);
    	}
    if (status.equals("Partial")) {
    	dr1.setStatus(DiagnosticReportStatusEnum.PARTIAL);
			}
    if (status.equals("Registered")) {
    	dr1.setStatus(DiagnosticReportStatusEnum.REGISTERED);
			};
    
    //Add Coding
    CodingDt code = dr1.getCode().addCoding();
    
  	if(sampleType.equals("Blood")){
    code.setSystem("http://loinc.org/");
  	code.setCode("600-7");
  	code.setDisplay("Bacteria identified in Blood by Culture");}
  	if(sampleType.equals("Urine")){
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
  	
  	//Add comments
  	dr1.setConclusion(comments);
  	
 		//Use the client to store a new resource instance
 		MethodOutcome outcome = client.create().resource(dr1).execute();
   
    //Print the id of the newly created resource
 		System.out.println(outcome.getId());
  }
}
