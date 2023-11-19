package com.engineeringthesis.forumservice.client;

import com.engineeringthesis.commons.dto.user.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081/user")
public interface UserServiceClient {
    @GetMapping("/patient/pesel/{pesel}")
    ResponseEntity<PatientDTO> getByPesel(@PathVariable("pesel") String pesel);
}
