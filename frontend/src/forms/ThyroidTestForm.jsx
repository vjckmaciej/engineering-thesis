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

const ThyroidTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Poziom TSH
    tshLevelDescription: "",
    tshLevelNumericalResult: "",
    tshLevelUnit: "mIU/L",
    tshLevelDoctorNote: "",

    // Poziom FT4
    ft4LevelDescription: "",
    ft4LevelNumericalResult: "",
    ft4LevelUnit: "ng/dl",
    ft4LevelDoctorNote: "",
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
            Poziom TSH
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="tshLevelDescription"
              value={formData.tshLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mIU/L)</FormLabel>
            <Input
              name="tshLevelNumericalResult"
              value={formData.tshLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="tshLevelDoctorNote"
              value={formData.tshLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Poziom FT4
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="ft4LevelDescription"
              value={formData.ft4LevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (ng/dl)</FormLabel>
            <Input
              name="ft4LevelNumericalResult"
              value={formData.ft4LevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="ft4LevelDoctorNote"
              value={formData.ft4LevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki badania tarczycy
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default ThyroidTestForm;
