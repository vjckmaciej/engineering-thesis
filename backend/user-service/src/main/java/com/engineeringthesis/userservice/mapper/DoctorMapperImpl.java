package com.engineeringthesis.userservice.mapper;


import com.engineeringthesis.commons.dto.user.DoctorDTO;
import com.engineeringthesis.userservice.entity.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public Doctor doctorDTOToDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO == null) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setDoctorId(doctorDTO.getDoctorId());
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setBirthDate(doctorDTO.getBirthDate());
        doctor.setPesel(doctorDTO.getPesel());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());

        return doctor;
    }

    @Override
    public DoctorDTO doctorToDoctorDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDTO doctorDTO = new DoctorDTO();

        doctorDTO.setDoctorId(doctor.getDoctorId());
        doctorDTO.setFirstName(doctor.getFirstName());
        doctorDTO.setLastName(doctor.getLastName());
        doctorDTO.setBirthDate(doctor.getBirthDate());
        doctorDTO.setPesel(doctor.getPesel());
        doctorDTO.setPhoneNumber(doctor.getPhoneNumber());

        return doctorDTO;
    }
}
