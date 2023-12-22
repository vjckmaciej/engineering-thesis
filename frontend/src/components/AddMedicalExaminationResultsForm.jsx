import React, { useState } from "react";
import {
  FormControl,
  FormLabel,
  Input,
  Textarea,
  Button,
  Stack,
} from "@chakra-ui/react";

const AddMedicalExaminationResultsForm = ({
  medicalExaminationId,
  onResultsSubmit,
}) => {
  const [resultData, setResultData] = useState({
    resultName: "",
    resultDescription: "",
    numericalResult: "",
    unit: "",
    descriptiveResult: "",
    doctorNote: "",
    medicalExaminationId,
  });

  const handleChange = (e) => {
    setResultData({ ...resultData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onResultsSubmit(resultData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <Stack spacing={4}>
        {/* Pola formularza */}
        <FormControl isRequired>
          <FormLabel>Nazwa wyniku</FormLabel>
          <Input name="resultName" onChange={handleChange} />
        </FormControl>
        {/* Inne pola formularza */}
        {/* ... */}
        <Button type="submit" colorScheme="blue">
          Dodaj wyniki
        </Button>
      </Stack>
    </form>
  );
};

export default AddMedicalExaminationResultsForm;
