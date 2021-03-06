package uk.nhs.digital.hapispike;

import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(FhirConfiguration.class)
public class DeserializationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientStore patientStore;

    @Test
    void deserializesJsonToFhir() throws Exception {
        mockMvc.perform(post("/Patient")
                .contentType("application/fhir+json")
                .content("{\n" +
                        "  \"resourceType\": \"Patient\",\n" +
                        "  \"id\": \"example\",\n" +
                        "  \"text\": {\n" +
                        "    \"status\": \"generated\",\n" +
                        "    \"div\": \"<div xmlns=\\\"http://www.w3.org/1999/xhtml\\\">\\n\\t\\t\\t<table>\\n\\t\\t\\t\\t<tbody>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Name</td>\\n\\t\\t\\t\\t\\t\\t<td>Peter James \\n              <b>Chalmers</b> (&quot;Jim&quot;)\\n            </td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Address</td>\\n\\t\\t\\t\\t\\t\\t<td>534 Erewhon, Pleasantville, Vic, 3999</td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Contacts</td>\\n\\t\\t\\t\\t\\t\\t<td>Home: unknown. Work: (03) 5555 6473</td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Id</td>\\n\\t\\t\\t\\t\\t\\t<td>MRN: 12345 (Acme Healthcare)</td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t</tbody>\\n\\t\\t\\t</table>\\n\\t\\t</div>\"\n" +
                        "  },\n" +
                        "  \"identifier\": [\n" +
                        "    {\n" +
                        "      \"use\": \"usual\",\n" +
                        "      \"type\": {\n" +
                        "        \"coding\": [\n" +
                        "          {\n" +
                        "            \"system\": \"http://terminology.hl7.org/CodeSystem/v2-0203\",\n" +
                        "            \"code\": \"MR\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"system\": \"urn:oid:1.2.36.146.595.217.0.1\",\n" +
                        "      \"value\": \"12345\",\n" +
                        "      \"period\": {\n" +
                        "        \"start\": \"2001-05-06\"\n" +
                        "      },\n" +
                        "      \"assigner\": {\n" +
                        "        \"display\": \"Acme Healthcare\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"active\": true,\n" +
                        "  \"name\": [\n" +
                        "    {\n" +
                        "      \"use\": \"official\",\n" +
                        "      \"family\": \"Chalmers\",\n" +
                        "      \"given\": [\n" +
                        "        \"Peter\",\n" +
                        "        \"James\"\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"use\": \"usual\",\n" +
                        "      \"given\": [\n" +
                        "        \"Jim\"\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"use\": \"maiden\",\n" +
                        "      \"family\": \"Windsor\",\n" +
                        "      \"given\": [\n" +
                        "        \"Peter\",\n" +
                        "        \"James\"\n" +
                        "      ],\n" +
                        "      \"period\": {\n" +
                        "        \"end\": \"2002\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"telecom\": [\n" +
                        "    {\n" +
                        "      \"use\": \"home\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"system\": \"phone\",\n" +
                        "      \"value\": \"(03) 5555 6473\",\n" +
                        "      \"use\": \"work\",\n" +
                        "      \"rank\": 1\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"system\": \"phone\",\n" +
                        "      \"value\": \"(03) 3410 5613\",\n" +
                        "      \"use\": \"mobile\",\n" +
                        "      \"rank\": 2\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"system\": \"phone\",\n" +
                        "      \"value\": \"(03) 5555 8834\",\n" +
                        "      \"use\": \"old\",\n" +
                        "      \"period\": {\n" +
                        "        \"end\": \"2014\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"gender\": \"male\",\n" +
                        "  \"birthDate\": \"1974-12-25\",\n" +
                        "  \"_birthDate\": {\n" +
                        "    \"extension\": [\n" +
                        "      {\n" +
                        "        \"url\": \"http://hl7.org/fhir/StructureDefinition/patient-birthTime\",\n" +
                        "        \"valueDateTime\": \"1974-12-25T14:35:45-05:00\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"deceasedBoolean\": false,\n" +
                        "  \"address\": [\n" +
                        "    {\n" +
                        "      \"use\": \"home\",\n" +
                        "      \"type\": \"both\",\n" +
                        "      \"text\": \"534 Erewhon St PeasantVille, Rainbow, Vic  3999\",\n" +
                        "      \"line\": [\n" +
                        "        \"534 Erewhon St\"\n" +
                        "      ],\n" +
                        "      \"city\": \"PleasantVille\",\n" +
                        "      \"district\": \"Rainbow\",\n" +
                        "      \"state\": \"Vic\",\n" +
                        "      \"postalCode\": \"3999\",\n" +
                        "      \"period\": {\n" +
                        "        \"start\": \"1974-12-25\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"contact\": [\n" +
                        "    {\n" +
                        "      \"relationship\": [\n" +
                        "        {\n" +
                        "          \"coding\": [\n" +
                        "            {\n" +
                        "              \"system\": \"http://terminology.hl7.org/CodeSystem/v2-0131\",\n" +
                        "              \"code\": \"N\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"name\": {\n" +
                        "        \"family\": \"du March??\",\n" +
                        "        \"_family\": {\n" +
                        "          \"extension\": [\n" +
                        "            {\n" +
                        "              \"url\": \"http://hl7.org/fhir/StructureDefinition/humanname-own-prefix\",\n" +
                        "              \"valueString\": \"VV\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        },\n" +
                        "        \"given\": [\n" +
                        "          \"B??n??dicte\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"telecom\": [\n" +
                        "        {\n" +
                        "          \"system\": \"phone\",\n" +
                        "          \"value\": \"+33 (237) 998327\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"address\": {\n" +
                        "        \"use\": \"home\",\n" +
                        "        \"type\": \"both\",\n" +
                        "        \"line\": [\n" +
                        "          \"534 Erewhon St\"\n" +
                        "        ],\n" +
                        "        \"city\": \"PleasantVille\",\n" +
                        "        \"district\": \"Rainbow\",\n" +
                        "        \"state\": \"Vic\",\n" +
                        "        \"postalCode\": \"3999\",\n" +
                        "        \"period\": {\n" +
                        "          \"start\": \"1974-12-25\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"gender\": \"female\",\n" +
                        "      \"period\": {\n" +
                        "        \"start\": \"2012\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"managingOrganization\": {\n" +
                        "    \"reference\": \"Organization/1\"\n" +
                        "  }\n" +
                        "}"))
                .andExpect(status().isCreated());

        var patientCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(patientStore).save(patientCaptor.capture());
        assertThat(patientCaptor.getValue()).isNotNull();
    }
}
