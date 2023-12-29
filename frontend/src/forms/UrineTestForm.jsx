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

const UrineTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Poziom białka
    proteinLevelDescription: "",
    proteinLevelNumericalResult: "",
    proteinLevelUnit: "mg/dL",
    proteinLevelDescriptiveResult: "",
    proteinLevelDoctorNote: "",

    // Poziom cukru
    sugarLevelDescription: "",
    sugarLevelNumericalResult: "",
    sugarLevelUnit: "mg/dL",
    sugarLevelDescriptiveResult: "",
    sugarLevelDoctorNote: "",

    // Ciała ketonowe
    ketoneBodiesDescription: "",
    ketoneBodiesNumericalResult: "",
    ketoneBodiesUnit: "mg/dL",
    ketoneBodiesDescriptiveResult: "",
    ketoneBodiesDoctorNote: "",

    // Obecność bakterii
    presenceOfBacteriaDescription: "",
    presenceOfBacteriaDescriptiveResult: "",
    presenceOfBacteriaDoctorNote: "",
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
            Poziom białka
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="proteinLevelDescription"
              value={formData.proteinLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mg/dL)</FormLabel>
            <Input
              name="proteinLevelNumericalResult"
              value={formData.proteinLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="proteinLevelDescriptiveResult"
              value={formData.proteinLevelDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="proteinLevelDoctorNote"
              value={formData.proteinLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Poziom cukru
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="sugarLevelDescription"
              value={formData.sugarLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (g/dl)</FormLabel>
            <Input
              name="sugarLevelNumericalResult"
              value={formData.sugarLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="sugarLevelDescriptiveResult"
              value={formData.sugarLevelDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="sugarLevelDoctorNote"
              value={formData.sugarLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Ciała ketonowe
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="ketoneBodiesDescription"
              value={formData.ketoneBodiesDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl isRequired>
            <FormLabel>Wynik liczbowy (g/dl)</FormLabel>
            <Input
              name="ketoneBodiesNumericalResult"
              value={formData.ketoneBodiesNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="ketoneBodiesDescriptiveResult"
              value={formData.ketoneBodiesDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="ketoneBodiesDoctorNote"
              value={formData.ketoneBodiesDoctorNote}
              onChange={handleChange}
            />
          </FormControl>
          <Heading size="md" mt="15px">
            Obecność bakterii
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="presenceOfBacteriaDescription"
              value={formData.presenceOfBacteriaDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="presenceOfBacteriaDescriptiveResult"
              value={formData.presenceOfBacteriaDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="presenceOfBacteriaDoctorNote"
              value={formData.presenceOfBacteriaDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki badania moczu
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default UrineTestForm;
