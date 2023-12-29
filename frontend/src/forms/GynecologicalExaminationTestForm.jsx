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

const GynecologicalExaminationTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Ocena szyjki macicy
    assessmentOfTheCervixDescription: "",
    assessmentOfTheCervixDescriptiveResult: "",
    assessmentOfTheCervixDoctorNote: "",

    // Badanie USG narządów rodnych
    ultrasoundExaminationOfReproductiveOrgansDescription: "",
    ultrasoundExaminationOfReproductiveOrgansDescriptiveResult: "",
    ultrasoundExaminationOfReproductiveOrgansDoctorNote: "",
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
            Ocena szyjki macicy
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="assessmentOfTheCervixDescription"
              value={formData.assessmentOfTheCervixDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="assessmentOfTheCervixDescriptiveResult"
              value={formData.assessmentOfTheCervixDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="assessmentOfTheCervixDoctorNote"
              value={formData.assessmentOfTheCervixDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Badanie USG narządów rodnych
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="ultrasoundExaminationOfReproductiveOrgansDescription"
              value={
                formData.ultrasoundExaminationOfReproductiveOrgansDescription
              }
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="ultrasoundExaminationOfReproductiveOrgansDescriptiveResult"
              value={
                formData.ultrasoundExaminationOfReproductiveOrgansDescriptiveResult
              }
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="ultrasoundExaminationOfReproductiveOrgansDoctorNote"
              value={
                formData.ultrasoundExaminationOfReproductiveOrgansDoctorNote
              }
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki badania ginekologicznego
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default GynecologicalExaminationTestForm;
