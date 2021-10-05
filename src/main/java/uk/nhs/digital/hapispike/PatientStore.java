package uk.nhs.digital.hapispike;

import org.hl7.fhir.r4.model.Patient;

public interface PatientStore {
    void save(Patient patient);
}
