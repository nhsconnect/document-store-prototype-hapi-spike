package uk.nhs.digital.hapispike;

import ca.uhn.fhir.context.FhirContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

import static org.hl7.fhir.r4.model.Narrative.NarrativeStatus.GENERATED;

@RestController
@RequestMapping("/Basic")
public class BasicResource {
    private final FhirContext fhirContext;
    private final ObjectMapper objectMapper;

    @Autowired
    public BasicResource(FhirContext fhirContext, ObjectMapper objectMapper) {
        this.fhirContext = fhirContext;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/{id}", produces = "application/fhir+json")
    public String read(@PathVariable("id") String id) {
        var jsonParser = fhirContext.newJsonParser();

        var narrative = new Narrative().setStatus(GENERATED);
        narrative.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">\n      <p><b>Patient:</b>Roel</p>\n      <p><b>Requestor:</b>Dokter Bronsig</p>\n      <p><b>Type:</b>Consultation</p>\n      <p><b>Target Date:</b>April 1 - April 31</p>\n      <p>COMPLETED</p>\n      <b>The patient had fever peaks over the last couple of days. He is worried about these peaks.</b>\n    </div>");

        var resource = new Basic()
                .addIdentifier(new Identifier()
                        .setSystem("http://goodhealth.org/basic/identifiers")
                        .setValue("19283746"))
                .setCode(new CodeableConcept().addCoding(new Coding()
                        .setSystem("http://terminology.hl7.org/CodeSystem/basic-resource-type")
                        .setCode("referral")))
                .setSubject(new Reference()
                        .setReference("Patient/f201")
                        .setDisplay("Roel"))
                .setCreated(Date.from(Instant.parse("2013-05-14T00:00:00Z")))
                .setAuthor(new Reference()
                        .setReference("Practitioner/example"))
                .setText(narrative)
                .setId("referral");

        // Cannot use an object mapper to serialise resources
        // System.out.println(objectMapper.writeValueAsString(resource));

        return jsonParser.encodeResourceToString(resource);
    }
}
