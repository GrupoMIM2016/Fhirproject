package GrupoMIM2016.MIM2016;

import java.util.List;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.DiagnosticReport;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.valueset.BundleTypeEnum;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticReportStatusEnum;
import ca.uhn.fhir.model.dstu2.valueset.HTTPVerbEnum;
import ca.uhn.fhir.model.dstu2.valueset.ObservationStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.client.IGenericClient;

public class Transaction2 {
  public static void main( String[] args )
  {
  	// Observation resource
    Observation obs1 = new Observation();
    
    //Observation report characteristics
    //Add Status
    obs1.setStatus(ObservationStatusEnum.PRELIMINARY);
    
    //Add Code
    CodingDt obsCode = obs1.getCode().addCoding();
  	obsCode.setSystem("http://loinc.org/");
  	obsCode.setCode("600-7");
  	obsCode.setDisplay("Bacteria identified in Blood by Culture");
    
  	//Add Subject
  	ResourceReferenceDt obsSubject = obs1.getSubject();
  	obsSubject.setReference("Patient/109908");
  	
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
  	
  	//Add a temporary UUID so that other resources in the transaction can refer to it
  	obs1.setId(IdDt.newRandomUuid());
  	
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
    
    //Diagnostic report characteristics
    //Add Status
    dr1.setStatus(DiagnosticReportStatusEnum.PARTIAL);
    
    //Add Coding
    CodingDt drCode = dr1.getCode().addCoding();
  	drCode.setSystem("http://hl7.org/fhir/ValueSet/report-codes");
  	drCode.setCode("600-7");
  	drCode.setDisplay("Bacteria identified in Blood by Culture");
  	
  	//Add Subject
  	ResourceReferenceDt drSubject = dr1.getSubject();
  	drSubject.setReference("Patient/109908");
  	
  	//Add efective time
  	dr1.setEffective(new DateTimeDt("2016-05-05"));
  	
  	//Add issued
  	dr1.setIssued(new InstantDt("2016-05-05T16:41:32.6-03:00"));
    
  	//Add performer
  	ResourceReferenceDt performer = dr1.getPerformer();
  	performer.setReference("Organization/63496");
  	
  	//Add coclusion
  	dr1.setConclusion("Positive bloood culture");
  	
  	//Add result
  	//The Diagnostic Report refers to the Observation using the ID, which is already set to a temporary UUID 
  	List<ResourceReferenceDt> resultList = dr1.getResult();
  	resultList.add(new ResourceReferenceDt(obs1.getId().getValue()));
  	
  	// Create a bundle that will be used as a transaction
  	Bundle bundle = new Bundle();
  	bundle.setType(BundleTypeEnum.TRANSACTION);
  	
  	// Add the Observation as an entry
  	bundle.addEntry()
    .setFullUrl(obs1.getId().getValue())
    .setResource(obs1)
    .getRequest()
       .setUrl("Observation")
       .setMethod(HTTPVerbEnum.POST);
  	
  	// Add the Diagnostic Report as an entry
  	bundle.addEntry()
    .setResource(dr1)
    .getRequest()
       .setUrl("DiagnosticReport")
       .setMethod(HTTPVerbEnum.POST);
  	
  	//Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	//Create a Client
    String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
    IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
    
    //Use the client to store a new resource instance
    Bundle resp = client.transaction().withBundle(bundle).execute();
    
    // Log the response
    System.out.println(ctxDstu2.newXmlParser().setPrettyPrint(true).encodeResourceToString(resp));
  }
}