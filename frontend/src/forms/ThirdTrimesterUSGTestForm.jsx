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

const ThirdTrimesterUSGTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    // Wielkość płodu
    fetalSizeDescription: "",
    fetalSizeNumericalResult: "",
    fetalSizeUnit: "cm",
    fetalSizeDoctorNote: "",

    // Pozycja płodu
    fetalPositionDescription: "",
    fetalPositionDescriptiveResult: "",
    fetalPositionDoctorNote: "",

    // Pomiar ilości płynu owodniowego
    amnioticFluidDescription: "",
    amnioticFluidNumericalResult: "",
    amnioticFluidUnit: "cm",
    amnioticFluidDoctorNote: "",

    // Ocena przepływu krwi w pępowinie
    assessmentOfBloodFlowInTheUmbilicalCordDescription: "",
    assessmentOfBloodFlowInTheUmbilicalCordDescriptiveResult: "",
    assessmentOfBloodFlowInTheUmbilicalCordDoctorNote: "",
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
            Pozycja płodu
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="fetalPositionDescription"
              value={formData.fetalPositionDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik opisowy</FormLabel>
            <Input
              name="fetalPositionDescriptiveResult"
              value={formData.fetalPositionDescriptiveResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="fetalPositionDoctorNote"
              value={formData.fetalPositionDoctorNote}
              onChange={handleChange}
            />
          </FormControl>

          <Heading size="md" mt="15px">
            Pomiar ilości płynu owodniowego
          </Heading>
          <FormControl>
            <FormLabel>Opis badania</FormLabel>
            <Input
              name="amnioticFluidDescription"
              value={formData.amnioticFluidDescription}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Wynik liczbowy (cm)</FormLabel>
            <Input
              name="amnioticFluidNumericalResult"
              value={formData.amnioticFluidNumericalResult}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl>
            <FormLabel>Notatka lekarza</FormLabel>
            <Input
              name="amnioticFluidDoctorNote"
              value={formData.amnioticFluidDoctorNote}
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

export default ThirdTrimesterUSGTestForm;
