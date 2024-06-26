package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.commons.dto.visit.ResultDTO;
import com.engineeringthesis.commons.dto.visit.ResultsReportDTO;
import com.engineeringthesis.visitservice.entity.MedicalExamination;
import com.engineeringthesis.visitservice.entity.Result;
import com.engineeringthesis.visitservice.repository.MedicalExaminationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResultMapperImpl implements ResultMapper {
    private final MedicalExaminationRepository medicalExaminationRepository;

    @Override
    public Result resultDTOToResult(ResultDTO resultDTO) {
        if (resultDTO == null) {
            return null;
        }

        Result result = new Result();

        result.setResultId(resultDTO.getResultId());
        result.setResultName(resultDTO.getResultName());
        result.setResultDescription(resultDTO.getResultDescription());
        result.setNumericalResult(resultDTO.getNumericalResult());
        result.setUnit(resultDTO.getUnit());
        result.setDescriptiveResult(resultDTO.getDescriptiveResult());
        result.setDoctorNote(resultDTO.getDoctorNote());

        if (resultDTO.getMedicalExaminationId() != null) {
            Optional<MedicalExamination> optionalMedicalExamination = medicalExaminationRepository.findByMedicalExaminationId(resultDTO.getMedicalExaminationId());
            if (optionalMedicalExamination.isPresent()) {
                MedicalExamination medicalExamination = optionalMedicalExamination.get();
                result.setMedicalExamination(medicalExamination);
                result.setMedicalExaminationIdReference(resultDTO.getMedicalExaminationId());
            } else {
                String message = String.format("Medical examination with given medicalExaminationId: %d doesn't exist in database!", resultDTO.getMedicalExaminationId());
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
        }

        return result;
    }

    @Override
    public ResultDTO resultToResultDTO(Result result) {
        if (result == null) {
            return null;
        }

        ResultDTO resultDTO = new ResultDTO();

        resultDTO.setResultId(result.getResultId());
        resultDTO.setResultName(result.getResultName());
        resultDTO.setResultDescription(result.getResultDescription());
        resultDTO.setNumericalResult(result.getNumericalResult());
        resultDTO.setUnit(result.getUnit());
        resultDTO.setDescriptiveResult(result.getDescriptiveResult());
        resultDTO.setDoctorNote(result.getDoctorNote());

        if (result.getMedicalExamination() != null) {
            resultDTO.setMedicalExaminationId(result.getMedicalExamination().getMedicalExaminationId());
        }

        return resultDTO;
    }

    public ResultsReportDTO resultToResultsReportDTO(Result result) {
        if (result == null) {
            return null;
        }

        ResultsReportDTO resultsReportDTO = new ResultsReportDTO();

        resultsReportDTO.setResultName(result.getResultName());
        resultsReportDTO.setResultDescription(result.getResultDescription());
        resultsReportDTO.setNumericalResult(result.getNumericalResult());
        resultsReportDTO.setUnit(result.getUnit());
        resultsReportDTO.setDescriptiveResult(result.getDescriptiveResult());
        resultsReportDTO.setDoctorNote(result.getDoctorNote());

        return resultsReportDTO;
    }
}
