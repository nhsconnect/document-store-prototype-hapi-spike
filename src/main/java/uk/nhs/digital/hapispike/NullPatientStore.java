package uk.nhs.digital.hapispike;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class NullPatientStore implements PatientStore {
    @Override
    public void save(Patient patient) {
    }
}
