package GrupoMIM2016.MIM2016;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.DiagnosticReport;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticReportStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.IGenericClient;


//@ResourceDef(name="DiagnosticReport", profile="http://hl7.no/fhir/StructureDefinition/LabDiagnosticReportNorway")
public class diagnosticReport2  {
  public static void main( String[] args )
  {
  	// Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	//Create a Client
    String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
    IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
    
    //Diagnostic report characteristics
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
  	dr1.setIssued(new InstantDt("2016-05-05T16:41:32.6-03:00"));
    
  	//Add performer
  	ResourceReferenceDt performer = dr1.getPerformer();
  	performer.setReference("Organization/63496");
  	
  	//Add result
  	//ResourceReferenceDt result = dr1.getResult();
  	
  	//("Observation/5226");
  	
  	//Add coclusion
  	dr1.setConclusion("Positive bloood culture");
  	
 		//Use the client to store a new resource instance
 		MethodOutcome outcome = client.create().resource(dr1).execute();
   
    //Print the id of the newly created resource
 		System.out.println(outcome.getId());
 		
  }
}
