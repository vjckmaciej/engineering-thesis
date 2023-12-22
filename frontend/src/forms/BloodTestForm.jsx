import React, { useState } from "react";
import { FormControl, FormLabel, Input, Button, Stack } from "@chakra-ui/react";

const BloodTestForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    hemoglobin: "",
    whiteBloodCellCount: "",
    plateletCount: "",
    fastingGlucose: "",
    ironLevel: "",
    infectionTest: "",
    bloodGroup: "",
  });

  const handleChange = (event) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit(formData); // Przekazanie danych z formularza
  };

  return (
    <form onSubmit={handleSubmit}>
      <Stack spacing={4}>
        <FormControl isRequired>
          <FormLabel>Hemoglobina</FormLabel>
          <Input
            name="hemoglobin"
            value={formData.hemoglobin}
            onChange={handleChange}
          />
        </FormControl>

        <FormControl isRequired>
          <FormLabel>Liczba białych krwinek</FormLabel>
          <Input
            name="whiteBloodCellCount"
            value={formData.whiteBloodCellCount}
            onChange={handleChange}
          />
        </FormControl>

        <FormControl isRequired>
          <FormLabel>Liczba płytek krwi</FormLabel>
          <Input
            name="plateletCount"
            value={formData.plateletCount}
            onChange={handleChange}
          />
        </FormControl>

        <FormControl isRequired>
          <FormLabel>Poziom glukozy na czczo</FormLabel>
          <Input
            name="fastingGlucose"
            value={formData.fastingGlucose}
            onChange={handleChange}
          />
        </FormControl>

        <FormControl isRequired>
          <FormLabel>Poziom żelaza</FormLabel>
          <Input
            name="ironLevel"
            value={formData.ironLevel}
            onChange={handleChange}
          />
        </FormControl>

        <FormControl>
          <FormLabel>Test na obecność infekcji</FormLabel>
          <Input
            name="infectionTest"
            value={formData.infectionTest}
            onChange={handleChange}
          />
        </FormControl>

        <FormControl>
          <FormLabel>Grupa krwi</FormLabel>
          <Input
            name="bloodGroup"
            value={formData.bloodGroup}
            onChange={handleChange}
          />
        </FormControl>

        <Button type="submit" colorScheme="blue">
          Zapisz wyniki badania krwi
        </Button>
      </Stack>
    </form>
  );
};

export default BloodTestForm;
