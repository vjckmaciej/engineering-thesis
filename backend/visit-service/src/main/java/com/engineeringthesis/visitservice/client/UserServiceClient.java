package com.engineeringthesis.visitservice.client;

import com.engineeringthesis.commons.dto.user.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081/user")
public interface UserServiceClient {
    @GetMapping("/patient/pregnancyWeek/{pesel}")
    Integer getPregnancyWeekByPesel(@PathVariable(name = "pesel") String pesel);

    @GetMapping("/patient/pesel/{pesel}")
    ResponseEntity<PatientDTO> getByPesel(@PathVariable(name = "pesel") String pesel);

    @GetMapping("/patient/existsByPesel/{pesel}")
    boolean existsByPeselPatient(@PathVariable(name = "pesel") String pesel);

    @GetMapping("/doctor/existsByPesel/{pesel}")
    boolean existsByPeselDoctor(@PathVariable(name = "pesel") String pesel);
}

