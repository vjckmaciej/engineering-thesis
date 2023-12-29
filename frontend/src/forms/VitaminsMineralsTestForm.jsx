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

const VitaminsMineralsTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Poziom witaminy D
    vitaminDLevelDescription: "",
    vitaminDLevelNumericalResult: "",
    vitaminDLevelUnit: "ng/ml",
    vitaminDLevelDoctorNote: "",

    // Poziom wapnia
    calciumLevelDescription: "",
    calciumLevelNumericalResult: "",
    calciumLevelUnit: "mg/dl",
    calciumLevelDoctorNote: "",
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
            Poziom witaminy D
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="vitaminDLevelDescription"
              value={formData.vitaminDLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (ng/ml)</FormLabel>
            <Input
              name="vitaminDLevelNumericalResult"
              value={formData.vitaminDLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="vitaminDLevelDoctorNote"
              value={formData.vitaminDLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Poziom wapnia
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="calciumLevelDescription"
              value={formData.calciumLevelDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (mg/dl)</FormLabel>
            <Input
              name="calciumLevelNumericalResult"
              value={formData.calciumLevelNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="calciumLevelDoctorNote"
              value={formData.calciumLevelDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki badań witamin i minerałów
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default VitaminsMineralsTestForm;
