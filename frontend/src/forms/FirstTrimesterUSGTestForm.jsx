import React, { useState } from "react";
import {
  FormControl,
  FormLabel,
  Input,
  Button,
  Stack,
  Box,
  Heading,
} from "@chakra-ui/react";
import { Form } from "react-router-dom";

const FirstTrimesterUSGTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Pomiar przezierności karkowej (NT)
    nuchalTransluchencyDescription: "",
    nuchalTransluchencyNumericalResult: "",
    nuchalTransluchencyUnit: "mm",
    nuchalTransluchencyDoctorNote: "",

    // Ocena struktur anatomicznych płodu
    anatomicalStructuresOfTheFetusDescription: "",
    anatomicalStructuresOfTheFetusDescriptiveResult: "",
    anatomicalStructuresOfTheFetusDoctorNote: "",
  });

  const handleChange = (event) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit(formData);
  };

  return (
    <Box maxW="480px">
      <Form onSubmit={handleSubmit}>
        <Stack spacing={4}>
          <Heading size="md" mt="15px">
            Pomiar przezierności karkowej (NT)
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="nuchalTransluchencyDescription"
              value={formData.nuchalTransluchencyDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mm)</FormLabel>
            <Input
              name="nuchalTransluchencyNumericalResult"
              value={formData.nuchalTransluchencyNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="nuchalTransluchencyDoctorNote"
              value={formData.nuchalTransluchencyDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Ocena struktur anatomicznych płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="anatomicalStructuresOfTheFetusDescription"
              value={formData.anatomicalStructuresOfTheFetusDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="anatomicalStructuresOfTheFetusDescriptiveResult"
              value={formData.anatomicalStructuresOfTheFetusDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="anatomicalStructuresOfTheFetusDoctorNote"
              value={formData.anatomicalStructuresOfTheFetusDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki USG pierwszego trymestru
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default FirstTrimesterUSGTestForm;
