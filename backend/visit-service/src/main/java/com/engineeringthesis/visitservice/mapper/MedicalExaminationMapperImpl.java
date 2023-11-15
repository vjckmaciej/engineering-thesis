package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.dto.MedicalExaminationDTO;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import com.engineeringthesis.visitservice.entity.Visit;
import com.engineeringthesis.visitservice.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MedicalExaminationMapperImpl implements MedicalExaminationMapper {
    private final VisitRepository visitRepository;

    @Override
    public MedicalExamination medicalExaminationDTOToMedicalExamination(MedicalExaminationDTO medicalExaminationDTO) {
        if (medicalExaminationDTO == null) {
            return null;
        }

        MedicalExamination medicalExamination = new MedicalExamination();

        medicalExamination.setMedicalExaminationId(medicalExaminationDTO.getMedicalExaminationId());
        medicalExamination.setMedicalExaminationName(medicalExaminationDTO.getMedicalExaminationName());

        if (medicalExaminationDTO.getVisitId() != null) {
            Optional<Visit> optionalVisit = visitRepository.findByVisitId(medicalExaminationDTO.getVisitId());
            if (optionalVisit.isPresent()) {
                Visit visit = optionalVisit.get();
                medicalExamination.setVisit(visit);
                medicalExamination.setVisitIdReference(medicalExaminationDTO.getVisitId());
            } else {
                String message = String.format("Visit with given visitId: %d doesn't exist in database!", medicalExaminationDTO.getVisitId());
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
        }

        return medicalExamination;
    }

    @Override
    public MedicalExaminationDTO medicalExaminationToMedicalExaminationDTO(MedicalExamination medicalExamination) {
        if (medicalExamination == null) {
            return null;
        }

        MedicalExaminationDTO medicalExaminationDTO = new MedicalExaminationDTO();

        medicalExaminationDTO.setMedicalExaminationId(medicalExamination.getMedicalExaminationId());
        medicalExaminationDTO.setMedicalExaminationName(medicalExamination.getMedicalExaminationName());

        if (medicalExamination.getVisit() != null) {
            medicalExaminationDTO.setVisitId(medicalExamination.getVisit().getVisitId());
        }

        return medicalExaminationDTO;
    }
}
