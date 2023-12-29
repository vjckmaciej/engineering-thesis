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

const SecondTrimesterUSGTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Wielkość płodu
    fetalSizeDescription: "",
    fetalSizeNumericalResult: "",
    fetalSizeUnit: "cm",
    fetalSizeDoctorNote: "",

    // Pomiar obwodu główki płodu
    fetalHeadCircumferenceDescription: "",
    fetalHeadCircumferenceNumericalResult: "",
    fetalHeadCircumferenceUnit: "cm",
    fetalHeadCircumferenceDoctorNote: "",

    // Pomiar długości kości udowej płodu
    fetalFemurLengthDescription: "",
    fetalFemurLengthNumericalResult: "",
    fetalFemurLengthUnit: "cm",
    fetalFemurLengthDoctorNote: "",

    // Ocena narządów płodu
    assessmentOfFetalOrgansDescription: "",
    assessmentOfFetalOrgansDescriptiveResult: "",
    assessmentOfFetalOrgansDoctorNote: "",
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
            Wielkość płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="fetalSizeDescription"
              value={formData.fetalSizeDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (cm)</FormLabel>
            <Input
              name="fetalSizeNumericalResult"
              value={formData.fetalSizeNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="fetalSizeDoctorNote"
              value={formData.fetalSizeDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Pomiar obwodu główki płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="fetalHeadCircumferenceDescription"
              value={formData.fetalHeadCircumferenceDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (cm)</FormLabel>
            <Input
              name="fetalHeadCircumferenceNumericalResult"
              value={formData.fetalHeadCircumferenceNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="fetalHeadCircumferenceDoctorNote"
              value={formData.fetalHeadCircumferenceDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Pomiar długości kości udowej płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="fetalFemurLengthDescription"
              value={formData.fetalFemurLengthDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (cm)</FormLabel>
            <Input
              name="fetalFemurLengthNumericalResult"
              value={formData.fetalFemurLengthNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="fetalFemurLengthDoctorNote"
              value={formData.fetalFemurLengthDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Ocena narządów płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="assessmentOfFetalOrgansDescription"
              value={formData.assessmentOfFetalOrgansDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="assessmentOfFetalOrgansDescriptiveResult"
              value={formData.assessmentOfFetalOrgansDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="assessmentOfFetalOrgansDoctorNote"
              value={formData.assessmentOfFetalOrgansDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki USG drugiego trymestru
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default SecondTrimesterUSGTestForm;
