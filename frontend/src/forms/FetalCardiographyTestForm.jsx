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

const FetalCardiographyTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Ocena rytmu serca płodu
    assessmentOfFetalHeartRhythmDescription: "",
    assessmentOfFetalHeartRhythmDescriptiveResult: "",
    assessmentOfFetalHeartRhythmDoctorNote: "",
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
            Ocena rytmu serca płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="assessmentOfFetalHeartRhythmDescription"
              value={formData.assessmentOfFetalHeartRhythmDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="assessmentOfFetalHeartRhythmDescriptiveResult"
              value={formData.assessmentOfFetalHeartRhythmDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="assessmentOfFetalHeartRhythmDoctorNote"
              value={formData.assessmentOfFetalHeartRhythmDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Button type="submit" colorScheme="pink">
            Zapisz wyniki kardiografii płodu
          </Button>
        </Stack>
      </Form>
    </Box>
  );
};

export default FetalCardiographyTestForm;
