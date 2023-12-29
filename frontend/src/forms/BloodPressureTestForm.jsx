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

const BloodPressureTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Ciśnienie skurczowe
    systolicBloodPressureDescription: "",
    systolicBloodPressureNumericalResult: "",
    systolicBloodPressureUnit: "mmHg",
    systolicBloodPressureDoctorNote: "",

    // Ciśnienie rozkurczowe
    diastolicBloodPressureDescription: "",
    diastolicBloodPressureNumericalResult: "",
    diastolicBloodPressureUnit: "mmHg",
    diastolicBloodPressureDoctorNote: "",
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
            Ciśnienie skurczowe
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="systolicBloodPressureDescription"
              value={formData.systolicBloodPressureDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mmHg)</FormLabel>
            <Input
              name="systolicBloodPressureNumericalResult"
              value={formData.systolicBloodPressureNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="systolicBloodPressureDoctorNote"
              value={formData.systolicBloodPressureDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Ciśnienie rozkurczowe
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="diastolicBloodPressureDescription"
              value={formData.diastolicBloodPressureDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mmHg)</FormLabel>
            <Input
              name="diastolicBloodPressureNumericalResult"
              value={formData.diastolicBloodPressureNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="diastolicBloodPressureDoctorNote"
              value={formData.diastolicBloodPressureDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki ciśnienia krwi
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default BloodPressureTestForm;
