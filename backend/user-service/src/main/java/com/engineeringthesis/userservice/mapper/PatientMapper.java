package com.engineeringthesis.userservice.mapper;

import com.engineeringthesis.userservice.dto.PatientDTO;
import com.engineeringthesis.userservice.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PatientMapper {
    @Mapping(target = "doctorId", source = "prenatalDoctor.doctorId")
    Patient patientDTOToPatient(PatientDTO patientDTO);

    @Mapping(target = "prenatalDoctor.doctorId", source = "doctorId")
    PatientDTO patientToPatientDTO(Patient patient);
}
