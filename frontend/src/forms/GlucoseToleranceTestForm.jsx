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

const GlucoseToleranceTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Poziom glukozy na czczo
    fastingGlucoseLevelDescription: "",
    fastingGlucoseLevelNumericalResult: "",
    fastingGlucoseLevelUnit: "mg/dl",
    fastingGlucoseLevelDoctorNote: "",

    // Poziom glukozy po 2 godzinach
    glucoseLevelAfter2HoursDescription: "",
    glucoseLevelAfter2HoursNumericalResult: "",
    glucoseLevelAfter2HoursUnit: "mg/dl",
    glucoseLevelAfter2HoursDoctorNote: "",
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
            Poziom glukozy na czczo
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="fastingGlucoseLevelDescription"
              value={formData.fastingGlucoseLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mg/dl)</FormLabel>
            <Input
              name="fastingGlucoseLevelNumericalResult"
              value={formData.fastingGlucoseLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="fastingGlucoseLevelDoctorNote"
              value={formData.fastingGlucoseLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Poziom glukozy po 2 godzinach
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="glucoseLevelAfter2HoursDescription"
              value={formData.glucoseLevelAfter2HoursDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mg/dl)</FormLabel>
            <Input
              name="glucoseLevelAfter2HoursNumericalResult"
              value={formData.glucoseLevelAfter2HoursNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="glucoseLevelAfter2HoursDoctorNote"
              value={formData.glucoseLevelAfter2HoursDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki badania tolerancji glukozy
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default GlucoseToleranceTestForm;
