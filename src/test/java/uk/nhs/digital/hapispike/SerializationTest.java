package uk.nhs.digital.hapispike;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(FhirConfiguration.class)
public class SerializationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void serializesObjectToFhir() throws Exception {
        mockMvc.perform(get("/Basic/1")
                        .accept("application/fhir+json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/fhir+json"))
                .andExpect(content().json("{\n" +
                        "  \"resourceType\": \"Basic\",\n" +
                        "  \"id\": \"referral\",\n" +
                        "  \"text\": {\n" +
                        "    \"status\": \"generated\",\n" +
                        "    \"div\": \"<div xmlns=\\\"http://www.w3.org/1999/xhtml\\\">\\n      <p><b>Patient:</b>Roel</p>\\n      <p><b>Requestor:</b>Dokter Bronsig</p>\\n      <p><b>Type:</b>Consultation</p>\\n      <p><b>Target Date:</b>April 1 - April 31</p>\\n      <p>COMPLETED</p>\\n      <b>The patient had fever peaks over the last couple of days. He is worried about these peaks.</b>\\n    </div>\"\n" +
                        "  },\n" +
                        "  \"identifier\": [\n" +
                        "    {\n" +
                        "      \"system\": \"http://goodhealth.org/basic/identifiers\",\n" +
                        "      \"value\": \"19283746\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"code\": {\n" +
                        "    \"coding\": [\n" +
                        "      {\n" +
                        "        \"system\": \"http://terminology.hl7.org/CodeSystem/basic-resource-type\",\n" +
                        "        \"code\": \"referral\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"subject\": {\n" +
                        "    \"reference\": \"Patient/f201\",\n" +
                        "    \"display\": \"Roel\"\n" +
                        "  },\n" +
                        "  \"created\": \"2013-05-14\",\n" +
                        "  \"author\": {\n" +
                        "    \"reference\": \"Practitioner/example\"\n" +
                        "  }\n" +
                        "}"));
    }
}
