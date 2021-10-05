package uk.nhs.digital.hapispike;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Reader;
import java.net.URI;

@RestController
@RequestMapping("/Patient")
public class PatientResource {
    private final FhirContext fhirContext;
    private final PatientStore patientStore;

    @Autowired
    public PatientResource(FhirContext fhirContext, PatientStore patientStore) {
        this.fhirContext = fhirContext;
        this.patientStore = patientStore;
    }

    @PostMapping(consumes = "application/fhir+json")
    public ResponseEntity<Void> create(Reader bodyReader) {
        var jsonParser = fhirContext.newJsonParser();

        var patient = jsonParser.parseResource(Patient.class, bodyReader);
        patientStore.save(patient);

        // Cannot use an object mapper to serialise resources
        // var mapper = new ObjectMapper();
        // mapper.writeValueAsString(patient);

        return ResponseEntity.created(URI.create("")).build();
    }
}
