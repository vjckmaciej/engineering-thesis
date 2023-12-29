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

const BloodGroupTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
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
            Zapisz wyniki grupy krwi
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default BloodGroupTestForm;
