package com.engineeringthesis.userservice.mapper;

import com.engineeringthesis.userservice.dto.DoctorDTO;
import com.engineeringthesis.userservice.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DoctorMapper {
    @Mapping(target = "patients", ignore = true)
    Doctor doctorDTOToDoctor(DoctorDTO doctorDTO);

    @Mapping(target = "doctorId", source = "doctorId")
    DoctorDTO doctorToDoctorDTO(Doctor doctor);
}
