package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.dto.ResultDTO;
import com.engineeringthesis.visitservice.dto.ResultsReportDTO;
import com.engineeringthesis.visitservice.entity.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ResultMapper {
    @Mapping(target = "resultId", ignore = true)
    @Mapping(target = "medicalExamination", ignore = true)
    Result resultDTOToResult(ResultDTO resultDTO);

    @Mapping(target = "medicalExaminationId", source = "result.medicalExamination.medicalExaminationId")
    ResultDTO resultToResultDTO(Result result);

    ResultsReportDTO resultToResultsReportDTO(Result result);
}
