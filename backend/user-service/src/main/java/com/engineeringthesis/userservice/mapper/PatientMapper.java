package com.engineeringthesis.userservice.mapper;

import com.engineeringthesis.commons.dto.user.PatientDTO;
import com.engineeringthesis.userservice.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PatientMapper {
    @Mapping(target = "doctorId", source = "pregnancyDoctor.doctorId")
    Patient patientDTOToPatient(PatientDTO patientDTO);

    @Mapping(target = "pregnancyDoctor.doctorId", source = "doctorId")
    PatientDTO patientToPatientDTO(Patient patient);
}
