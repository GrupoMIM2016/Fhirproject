package GrupoMIM2016.MIM2016;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ca.uhn.fhir.context.FhirContext;
//import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.api.ResourceMetadataKeyEnum;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.DiagnosticReport;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.dstu2.resource.Organization;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.BundleTypeEnum;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticReportStatusEnum;
import ca.uhn.fhir.model.dstu2.valueset.HTTPVerbEnum;
import ca.uhn.fhir.model.dstu2.valueset.IdentifierUseEnum;
import ca.uhn.fhir.model.dstu2.valueset.ObservationStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

public class MakingTransaction {
	
	String comments = null;
	String idType = null;
	String patientId = null;
	String result = null;
	String sampleType = null;
	String status = null;
	String testDate = null;
	String testId = null;
	String gramStain = null;
	String MOidentification = null;
	String morpho = null;
	String name = null;
	String lastName = null;
	String patient = null;
	Patient pat1 = new Patient();
	IdDt firstResponseId = new IdDt();
	
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

	public String getTestId() {
	return this.testId;}
	
	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	public String getgramStain() {
	return this.gramStain;}

	public void setgramStain(String gramStain) {
		this.gramStain = gramStain;
	}
	
	public String getMOidentification() {
		return this.MOidentification;}
	
	public void setMOidentification(String MOidentification) {
		this.MOidentification = MOidentification;
	}
	
	public String getmorpho() {
		return this.morpho;}

	public void setmorpho(String morpho) {
			this.morpho = morpho;
		}
	
	public String getlastName() {
	return this.lastName;}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getname() {
		return this.name;}

	public void setname(String name) {
			this.name = name;
	}
	
	public String getPatient() {
		return this.patient;}
	
	public void setPatient(String patient) {
		this.patient = patient;
	}

	public void main( ) throws IOException{
		
		/*
		System.out.println("Test code: " + testId);
		System.out.println("Report status: " + status);
		System.out.println("Sample: " + sampleType);
		System.out.println("Patient name: " + name);
		System.out.println("Patient lastname: " + lastName);
		System.out.println("Type of patient Id: " + idType);
		System.out.println("Number of patient Id: " + patientId);
		System.out.println("Date of sample withdrawal: " + testDate);
		System.out.println("Test result: " + result);
		System.out.println("Comments: " + comments);
		System.out.println("Gram stain: " + gramStain);
		System.out.println("Morphology: " + morpho);
		System.out.println("Microorganism identification: " + MOidentification);
		*/
		
		System.out.println("Microorganism identification: " + MOidentification);
		
	Organization org1 = new Organization();
	IdentifierDt orgId = org1.addIdentifier();
  	orgId.setSystem("http://fhir.furore.com/NamingSystem/NationalOrganizationIdentifier");
  	orgId.setValue("123");
  	org1.setName("Culture Club");
  	
  	String web = "";
    if(idType.equals("RUT")){
    	 web= "wwww.regcivil.cl/rut";}
    if(idType.equals("Furore")){	
    	 web = "http://fhir.furore.com/NationalPatientID";}
  	
  	if(patient.equals("Create")){
  	IdentifierDt patId = pat1.addIdentifier();
  	patId.setSystem(web);
  	patId.setValue(patientId);
  	patId.setUse(IdentifierUseEnum.OFFICIAL);
  	HumanNameDt patientName = pat1.addName();
  	patientName.addFamily(lastName);
  	patientName.addGiven(name);
  	}
		
	//Context for DSTU2
  	FhirContext ctxDstu2 = FhirContext.forDstu2();
  	
  	//Create a Client
  	String serverBaseUrl = "http://fhirtest.uhn.ca/baseDstu2";
//  	String serverBaseUrl = "http://172.31.5.42:8080/baseDstu2"; //Austriacos
//  	String serverBaseUrl = "http://172.31.4.81:8080/baseDstu2"; //Marcela

  	IGenericClient client = ctxDstu2.newRestfulGenericClient(serverBaseUrl);
  	
  	//Log requests and responses
 	client.registerInterceptor(new LoggingInterceptor(true));
		
	// Observation resources
    Observation obs1 = new Observation();
    Observation obs2 = new Observation();
    Observation obs3 = new Observation();
    
    //1st Observation report characteristics
    
    //Add MetaData
    obs1.getResourceMetadata().put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.no/fhir/StructureDefinition/LabObservationNorway");
    
    //Add Status
    if(status.equals("Final")){
    	obs1.setStatus(ObservationStatusEnum.FINAL);
    	}
    if (status.equals("Partial")) {
    	obs1.setStatus(ObservationStatusEnum.PRELIMINARY);
			}
    if (status.equals("Registered")) {
    	obs1.setStatus(ObservationStatusEnum.REGISTERED);
			};
			
		//Add Code
	  CodingDt obsCode = obs1.getCode().addCoding();
	  if(sampleType.equals("Blood")){
	  	obsCode.setSystem("http://loinc.org/");
	  	obsCode.setCode("600-7");
	  	obsCode.setDisplay("Bacteria identified in Blood by Culture");}
	  if(sampleType.equals("Urine")){
	  	obsCode.setSystem("http://loinc.org/");
	  	obsCode.setCode("630-4");
	  	obsCode.setDisplay("Bacteria identified in Urine by Culture");}
	  
	  if(patient.equals("Assign")){
	// Build a search and execute it
    ca.uhn.fhir.model.api.Bundle response = client.search()
  	.forResource(Patient.class)
  	.where(Patient.IDENTIFIER.exactly().systemAndIdentifier(web, patientId))
  	.execute();
    firstResponseId = response.getEntries().get(0).getResource().getId();
    ResourceReferenceDt obsSubject = obs1.getSubject();
    obsSubject.setReference(firstResponseId);
	  }
    
	  if(patient.equals("Create")){
	  obs1.getSubject().setResource(pat1);
	  }
	  
    //Add effective time
  	obs1.setEffective(new DateTimeDt((testDate)));
    
  	//Add issued
  	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  	Date date = new Date();
  	String fecha = dateFormat.format(date);
  	fecha = fecha.substring(0, fecha.length() - 2) + ":" + fecha.substring(fecha.length() - 2);
  	obs1.setIssued(new InstantDt(fecha));
  	
  	//Add performer
  	List<ResourceReferenceDt> performerList = obs1.getPerformer();
  	performerList.add(new ResourceReferenceDt(org1));
  	
  	//Add value
  	obs1.setValue(new StringDt(result + " " + sampleType + " Culture"));
  	
  	//Add interpretation
  	CodingDt interpretationCoding = new CodingDt();
  	
  	if(result.equals("Positive")){
  		interpretationCoding.setSystem("http://hl7.org/fhir/ValueSet/observation-interpretation");
  		interpretationCoding.setCode("POS");
  		interpretationCoding.setDisplay("Positive");
  		interpretationCoding.setUserSelected(true);}
  	if(result.equals("Negative")){
    	interpretationCoding.setSystem("http://hl7.org/fhir/ValueSet/observation-interpretation");
    	interpretationCoding.setCode("NEG");
   		interpretationCoding.setDisplay("Negative");
   		interpretationCoding.setUserSelected(true);}
  		
  	obs1.getInterpretation().addCoding(interpretationCoding);
  	//S: Susceptible. R: Resistant. VS: Very susceptible. SYN-S: Synergy - susceptible. SYN-R: Synergy - resistant. SSD: Susceptible-dose dependent
  	//POS: Positive, NEG: Negative
  	obs1.getInterpretation().setText(result + " " + sampleType + " Culture");
  	
  	//Add comments
  	obs1.setComments(comments);
  	
  	//Add a temporary UUID so that other resources in the transaction can refer to it
  	obs1.setId(IdDt.newRandomUuid());

    //2nd Observation report characteristics (Gram and morphology)
  	if(morpho != null && gramStain != null){
  	
    //Add MetaData
    obs2.getResourceMetadata().put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.no/fhir/StructureDefinition/LabObservationNorway");
    
    //Add Status
    if(status.equals("Final")){
    	obs2.setStatus(ObservationStatusEnum.FINAL);
    	}
    if (status.equals("Partial")) {
    	obs2.setStatus(ObservationStatusEnum.PRELIMINARY);
			}
    if (status.equals("Registered")) {
    	obs2.setStatus(ObservationStatusEnum.REGISTERED);
			};
			
		//Add Code
	  CodingDt obs2Code = obs2.getCode().addCoding();
	if(sampleType.equals("Blood")){
	  	obs2Code.setSystem("http://loinc.org/");
	  	obs2Code.setCode("600-7");
	  	obs2Code.setDisplay("Bacteria identified in Blood by Culture");}
	if(sampleType.equals("Urine")){
	  	obs2Code.setSystem("http://loinc.org/");
	  	obs2Code.setCode("630-4");
	  	obs2Code.setDisplay("Bacteria identified in Urine by Culture");}
	  
	  //Add Subject
		if(patient.equals("Assign")){
		ResourceReferenceDt obs2Subject = obs2.getSubject();
    obs2Subject.setReference(firstResponseId);
		}
		if(patient.equals("Create")){
	  obs2.getSubject().setResource(pat1);
		}
    
    //Add effective time
  	obs2.setEffective(new DateTimeDt((testDate)));
    
  	//Add issued
  	obs2.setIssued(new InstantDt(fecha));
  	
  	//Add performer
  	List<ResourceReferenceDt> performerList2 = obs2.getPerformer();
  	performerList2.add(new ResourceReferenceDt(org1));
  	
  	//Add value
  	obs2.setValue(new StringDt("Stain: " + gramStain + " Bacteria morphology: " + morpho));
  	
  	//Add interpretation
  	CodingDt interpretationCoding2 = new CodingDt();
  	
  	if((morpho.equals("Baccillus (Rod)")) && gramStain.equals("Gram Positive")){
  		interpretationCoding2.setSystem("http://snomed.info/sct");
  		interpretationCoding2.setCode("83514008");
  		interpretationCoding2.setDisplay("Gram-positive bacillus (organism)");
  		interpretationCoding2.setUserSelected(true);}
  	if((morpho.equals("Baccillus (Rod)")) && gramStain.equals("Gram Negative")){
  		interpretationCoding2.setSystem("http://snomed.info/sct");
  		interpretationCoding2.setCode("87172008");
  		interpretationCoding2.setDisplay("Gram-negative bacillus (organism)");
  		interpretationCoding2.setUserSelected(true);}
  	if((morpho.equals("Coccus")) && gramStain.equals("Gram Positive")){
  		interpretationCoding2.setSystem("http://snomed.info/sct");
  		interpretationCoding2.setCode("59206002");
  		interpretationCoding2.setDisplay("Gram-positive coccus (organism)");
  		interpretationCoding2.setUserSelected(true);}
  	if((morpho.equals("Coccus")) && gramStain.equals("Gram Negative")){
  		interpretationCoding2.setSystem("http://snomed.info/sct");
  		interpretationCoding2.setCode("18383003");
  		interpretationCoding2.setDisplay("Gram-negative coccus (organism)");
  		interpretationCoding2.setUserSelected(true);}
  		
  	obs2.getInterpretation().addCoding(interpretationCoding2);
  	//S: Susceptible. R: Resistant. VS: Very susceptible. SYN-S: Synergy - susceptible. SYN-R: Synergy - resistant. SSD: Susceptible-dose dependent
  	//POS: Positive, NEG: Negative
  	obs2.getInterpretation().setText("Stain: " + gramStain + " Bacteria morphology: " + morpho);
  	
  	//Add comments
  	obs2.setComments(comments);
  	
  	//Add a temporary UUID so that other resources in the transaction can refer to it
  	obs2.setId(IdDt.newRandomUuid());
  	}
  	
  //3er Observation report Microorganism identification
 	
  	if(MOidentification != null){
  	  	
  	    //Add MetaData
  	    obs3.getResourceMetadata().put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.no/fhir/StructureDefinition/LabObservationNorway");
  	    
  	    //Add Status
  	    if(status.equals("Final")){
  	    	obs3.setStatus(ObservationStatusEnum.FINAL);
  	    	}
  	    if (status.equals("Partial")) {
  	    	obs3.setStatus(ObservationStatusEnum.PRELIMINARY);
  				}
  	    if (status.equals("Registered")) {
  	    	obs3.setStatus(ObservationStatusEnum.REGISTERED);
  				};
  				
  			//Add Code
  		  CodingDt obs3Code = obs3.getCode().addCoding();
  		if(sampleType.equals("Blood")){
  		  	obs3Code.setSystem("http://loinc.org/");
  		  	obs3Code.setCode("600-7");
  		  	obs3Code.setDisplay("Bacteria identified in Blood by Culture");}
  		if(sampleType.equals("Urine")){
  		  	obs3Code.setSystem("http://loinc.org/");
  		  	obs3Code.setCode("630-4");
  		  	obs3Code.setDisplay("Bacteria identified in Urine by Culture");}
  		  
  	//Add Subject
  		if(patient.equals("Assign")){
  		ResourceReferenceDt obs3Subject = obs3.getSubject();
      obs3Subject.setReference(firstResponseId);
  		}
  		if(patient.equals("Create")){
  	  obs3.getSubject().setResource(pat1);
  		}
  	    
  	    //Add effective time
  	  	obs3.setEffective(new DateTimeDt((testDate)));
  	    
  	  	//Add issued
  	  	obs3.setIssued(new InstantDt(fecha));
  	  	
  	  	//Add performer
  	  	List<ResourceReferenceDt> performerList3 = obs3.getPerformer();
  	  	performerList3.add(new ResourceReferenceDt(org1));
  	  	
  	  	//Add value
  	  	obs3.setValue(new StringDt("Identified Microorganism: " + MOidentification));
  	  	
  	  	//Add interpretation
  	  	CodingDt interpretationCoding3 = new CodingDt();
  	  	
  	  	if(MOidentification.equals("Clostridium difficile")){
  	  		interpretationCoding3.setSystem("http://snomed.info/sct");
  	  		interpretationCoding3.setCode("5933001");
  	  		interpretationCoding3.setDisplay("Clostridium difficile (organism)");
  	  		interpretationCoding3.setUserSelected(true);}
  	  	if(MOidentification.equals("Pseudomonas aeruginosa")){
  	  		interpretationCoding3.setSystem("http://snomed.info/sct");
  	  		interpretationCoding3.setCode("52499004");
  	  		interpretationCoding3.setDisplay("Pseudomonas aeruginosa (organism)");
  	  		interpretationCoding3.setUserSelected(true);}
  	  	if(MOidentification.equals("Staphylococcus aureus")){
  	  		interpretationCoding3.setSystem("http://snomed.info/sct");
  	  		interpretationCoding3.setCode("3092008");
  	  		interpretationCoding3.setDisplay("Staphylococcus aureus (organism)");
  	  		interpretationCoding3.setUserSelected(true);}
  	  	if(MOidentification.equals("Neisseria meningitidis")){
  	  		interpretationCoding3.setSystem("http://snomed.info/sct");
  	  		interpretationCoding3.setCode("17872004");
  	  		interpretationCoding3.setDisplay("Neisseria meningitidis (organism)");
  	  		interpretationCoding3.setUserSelected(true);}
  	  	if(MOidentification.equals("Escherichia coli")){
  	  		interpretationCoding3.setSystem("http://snomed.info/sct");
  	  		interpretationCoding3.setCode("112283007");
  	  		interpretationCoding3.setDisplay("Escherichia coli (organism)");
  	  		interpretationCoding3.setUserSelected(true);}
  	  	
  	  	obs3.getInterpretation().addCoding(interpretationCoding3);
 
  	  	obs3.getInterpretation().setText("Identified Microorganism: " + MOidentification);
  	  	
  	  	//Add comments
  	  	obs3.setComments(comments);
  	  	
  	  	//Add a temporary UUID so that other resources in the transaction can refer to it
  	  	obs3.setId(IdDt.newRandomUuid());
  	  	}
  	
  	// Diagnostic report resource
    DiagnosticReport dr1 = new DiagnosticReport();
  
    //Diagnostic report characteristics
    
    //Add MetaData
    dr1.getResourceMetadata().put(ResourceMetadataKeyEnum.PROFILES, "http://hl7.no/fhir/StructureDefinition/LabDiagnosticReportNorway");
    
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
    CodingDt drCode = dr1.getCode().addCoding();
    
  	if(sampleType.equals("Blood")){
    drCode.setSystem("http://loinc.org/");
  	drCode.setCode("600-7");
  	drCode.setDisplay("Bacteria identified in Blood by Culture");}
  	if(sampleType.equals("Urine")){
 		drCode.setSystem("http://loinc.org/");
  	drCode.setCode("630-4");
  	drCode.setDisplay("Bacteria identified in Urine by Culture");}	
  	
  	
  	//Add Subject
		if(patient.equals("Assign")){
		ResourceReferenceDt dr1Subject = dr1.getSubject();
    dr1Subject.setReference(firstResponseId);
		}
		if(patient.equals("Create")){
	  dr1.getSubject().setResource(pat1);
		}
  	
  	//Add effective time
  	dr1.setEffective(new DateTimeDt(testDate));
  	
  	//Add issued
  	dr1.setIssued(new InstantDt(fecha));
 		
  	//Add performer
  	dr1.getPerformer().setResource(org1);
  	
  	//Add comments
  	dr1.setConclusion(comments);
  	
  	//Add result
  	//The Diagnostic Report refers to the Observation using the ID, which is already set to a temporary UUID 
  	List<ResourceReferenceDt> resultList = dr1.getResult();
  	resultList.add(new ResourceReferenceDt(obs1.getId().getValue()));
  	resultList.add(new ResourceReferenceDt(obs2.getId().getValue()));
  	resultList.add(new ResourceReferenceDt(obs3.getId().getValue()));
  	
  	// Create a bundle that will be used as a transaction
  	Bundle bundle = new Bundle();
  	bundle.setType(BundleTypeEnum.TRANSACTION);
  	
  	/*
  	ca.uhn.fhir.model.api.Bundle allDiagRepId = client.search().forResource(DiagnosticReport.class)
  			.where(new TokenClientParam("patient.identifier").exactly().systemAndCode(web, patientId))
  			.prettyPrint()
  			.execute();
  	String realDiagRepId = allDiagRepId.getEntries().get(0).getResource().getId().toString();
  	realDiagRepId = realDiagRepId.substring(realDiagRepId.length() - 5, realDiagRepId.length());
  	
  	System.out.println(realDiagRepId);
  	*/
  	
  	// Add the Observation as an entry
  	bundle.addEntry()
    .setFullUrl(obs1.getId().getValue())
    .setResource(obs1)
    .getRequest()
    .setUrl("Observation")
    //.setIfNoneExist("Observation?identifier=http://acme.org/mrns|12345")
    .setMethod(HTTPVerbEnum.POST);

  	if(morpho != null && gramStain != null){
  	bundle.addEntry()
    .setFullUrl(obs2.getId().getValue())
    .setResource(obs2)
    .getRequest()
       .setUrl("Observation")
       .setMethod(HTTPVerbEnum.POST);
  	}
  	
  	if(MOidentification != null){
    	bundle.addEntry()
      .setFullUrl(obs3.getId().getValue())
      .setResource(obs3)
      .getRequest()
         .setUrl("Observation")
         .setMethod(HTTPVerbEnum.POST);
    	}
  	
  	// Add the Diagnostic Report as an entry
  	bundle.addEntry()
    .setResource(dr1)
    .getRequest()
       .setUrl("DiagnosticReport")
       .setMethod(HTTPVerbEnum.POST);

    //Use the client to store a new resource instance
  	Bundle resp = client.transaction().withBundle(bundle).execute();
    
    // Log the response
    System.out.println(ctxDstu2.newXmlParser().setPrettyPrint(true).encodeResourceToString(resp));  	
 		
    /*
    ArrayList<String> idResourceList = new ArrayList<String>();
    
    //substring 
    idResourceList.add("Observation/14901");
    idResourceList.add("Observation/14902");
    
    Map<String, ArrayList<String>> valuemap = null;
    valuemap.put(testId, idResourceList);
    
    //properties file
    try {
      FileOutputStream fileOut = new FileOutputStream("Prueba.txt");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(valuemap);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in /tmp/employee.ser");
   }catch(IOException i) {
      i.printStackTrace();
   }
   */
  }
}
