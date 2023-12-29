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

const BloodTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Hemoglobina
    hemoglobinDescription: "",
    hemoglobinNumericalResult: "",
    hemoglobinUnit: "g/dl",
    hemoglobinDescriptiveResult: "",
    hemoglobinDoctorNote: "",
    // Liczba białych krwinek
    whiteBloodCellCountDescription: "",
    whiteBloodCellCountNumericalResult: "",
    whiteBloodCellCountUnit: "x10^9/L",
    whiteBloodCellCountDescriptiveResult: "",
    whiteBloodCellCountDoctorNote: "",
    // Liczba płytek krwi
    plateletCountDescription: "",
    plateletCountNumericalResult: "",
    plateletCountUnit: "x10^9/L",
    plateletCountDescriptiveResult: "",
    plateletCountDoctorNote: "",
    // Poziom glukozy na czczo
    fastingGlucoseDescription: "",
    fastingGlucoseNumericalResult: "",
    fastingGlucoseUnit: "mg/dl",
    fastingGlucoseDescriptiveResult: "",
    fastingGlucoseDoctorNote: "",
    // Poziom żelaza
    ironLevelDescription: "",
    ironLevelNumericalResult: "",
    ironLevelUnit: "μg/dl",
    ironLevelDescriptiveResult: "",
    ironLevelDoctorNote: "",
    // Test na obecność infekcji
    infectionTestDescription: "",
    infectionTestDescriptiveResult: "",
    infectionTestDoctorNote: "",
    // Grupa krwi
    bloodGroupDescription: "",
    bloodGroupDescriptiveResult: "",
    bloodGroupDoctorNote: "",
    // Czynnik Rh
    rhFactorDescription: "",
    rhFactorDescriptiveResult: "",
    rhFactorDoctorNote: "",
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
            Hemoglobina
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="hemoglobinDescription"
              value={formData.hemoglobinDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (g/dl)</FormLabel>
            <Input
              name="hemoglobinNumericalResult"
              value={formData.hemoglobinNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="hemoglobinDescriptiveResult"
              value={formData.hemoglobinDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="hemoglobinDoctorNote"
              value={formData.hemoglobinDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Liczba białych krwinek
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="whiteBloodCellCountDescription"
              value={formData.whiteBloodCellCountDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (x10^9/L)</FormLabel>
            <Input
              name="whiteBloodCellCountNumericalResult"
              value={formData.whiteBloodCellCountNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="whiteBloodCellCountDescriptiveResult"
              value={formData.whiteBloodCellCountDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="whiteBloodCellCountDoctorNote"
              value={formData.whiteBloodCellCountDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Liczba płytek krwi
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="plateletCountDescription"
              value={formData.plateletCountDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (x10^9/L)</FormLabel>
            <Input
              name="plateletCountNumericalResult"
              value={formData.plateletCountNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="plateletCountDescriptiveResult"
              value={formData.plateletCountDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="plateletCountDoctorNote"
              value={formData.plateletCountDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Poziom glukozy na czczo
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="fastingGlucoseDescription"
              value={formData.fastingGlucoseDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (mg/dl)</FormLabel>
            <Input
              name="fastingGlucoseNumericalResult"
              value={formData.fastingGlucoseNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="fastingGlucoseDescriptiveResult"
              value={formData.fastingGlucoseDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="fastingGlucoseDoctorNote"
              value={formData.fastingGlucoseDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Poziom żelaza
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="ironLevelDescription"
              value={formData.ironLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (μg/dl)</FormLabel>
            <Input
              name="ironLevelNumericalResult"
              value={formData.ironLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="ironLevelDescriptiveResult"
              value={formData.ironLevelDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="ironLevelDoctorNote"
              value={formData.ironLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Test na obecność infekcji
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="infectionTestDescription"
              value={formData.infectionTestDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="infectionTestDescriptiveResult"
              value={formData.infectionTestDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="infectionTestDoctorNote"
              value={formData.infectionTestDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Grupa krwi
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="bloodGroupDescription"
              value={formData.bloodGroupDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="bloodGroupDescriptiveResult"
              value={formData.bloodGroupDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="bloodGroupDoctorNote"
              value={formData.bloodGroupDoctorNote}
              onChange={handleChange}
            />
          </FormControl>
          <Heading size="md" mt="15px">
            Czynnik Rh
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="rhFactorDescription"
              value={formData.rhFactorDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="rhFactorDescriptiveResult"
              value={formData.rhFactorDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="rhFactorDoctorNote"
              value={formData.rhFactorDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki badania krwi
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default BloodTestForm;
