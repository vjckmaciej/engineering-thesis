import React, { useEffect, useState } from "react";
import {
  Heading,
  Box,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  NumberInput,
  Select,
  FormLabel,
  FormControl,
  Button,
  Textarea,
} from "@chakra-ui/react";
import { Form } from "react-router-dom";

export default function DietPlan() {
  const pesel = sessionStorage.getItem("pesel");
  const [daysNumber, setDaysNumber] = useState("1");
  const [dishesNumber, setDishesNumber] = useState("1");
  const [caloriesRange, setCaloriesRange] = useState("1000-1500");
  const [allergens, setAllergens] = useState("");
  const [proposedDietPlan, setProposedDietPlan] = useState([]);

  const handleSubmit = async (event) => {
    event.preventDefault();

    const dietPlanData = {
      daysNumber: parseInt(daysNumber, 10),
      dishesNumber: parseInt(dishesNumber, 10),
      caloriesRange,
      allergens,
    };

    console.log(dietPlanData);

    try {
      const response = await fetch(
        `http://localhost:8082/visit/visit/generateDietPlan/${pesel}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(dietPlanData),
        }
      );

      if (response.ok) {
        const dietPlan = await response.json();
        console.log(dietPlan);
        setProposedDietPlan(dietPlan);
      } else {
        console.error("Failed to create diet plan");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <Box>
      <Heading size="xl" textAlign="center">
        Create your own diet plan!
      </Heading>

      <Heading size="md" mt="20px" mb="20px">
        For how many days do you want a diet plan?
      </Heading>
      <NumberInput
        value={daysNumber}
        onChange={(value) => setDaysNumber(value)}
        min={1}
        max={30}
      >
        <NumberInputField />
        <NumberInputStepper>
          <NumberIncrementStepper />
          <NumberDecrementStepper />
        </NumberInputStepper>
      </NumberInput>

      <Heading size="md" mt="20px" mb="20px">
        How many dishes per day do you want?
      </Heading>
      <NumberInput
        value={dishesNumber}
        onChange={(value) => setDishesNumber(value)}
        min={1}
        max={7}
      >
        <NumberInputField />
        <NumberInputStepper>
          <NumberIncrementStepper />
          <NumberDecrementStepper />
        </NumberInputStepper>
      </NumberInput>

      <Heading size="md" mt="20px" mb="20px">
        How many calories do you want to eat per day?
      </Heading>
      <Form method="post" onSubmit={handleSubmit}>
        <FormControl isRequired mb="40px">
          <FormLabel>Calories range</FormLabel>
          <Select
            value={caloriesRange}
            onChange={(e) => setCaloriesRange(e.target.value)}
            placeholder="Select calories range"
          >
            <option value="1000-1500">1000-1500</option>
            <option value="1500-2000">1500-2000</option>
            <option value="2000-2500">2000-2500</option>
            <option value="2500-3000">2500-3000</option>
            <option value="3000-3500">3000-3500</option>
            <option value="3500-4000">3500-4000</option>
          </Select>
        </FormControl>

        <FormControl mb="40px">
          <FormLabel>Allergens:</FormLabel>
          <Textarea
            value={allergens}
            onChange={(e) => setAllergens(e.target.value)}
            placeholder="Write all your allergens and ingredients you want to avoid here :)"
            name="allergens"
          />
        </FormControl>

        <Button type="submit" variant="solid" colorScheme="green" mb="30px">
          Create diet plan with OpenAI!
        </Button>
      </Form>

      {proposedDietPlan.map((day) => (
        <Box key={day.dayName} mt="40px">
          <Heading size="lg">{day.dayName}</Heading>
          {day.dishDTO.map((dish) => (
            <Box key={dish.name} mt="20px">
              <Heading size="md">{dish.name}</Heading>
              <p>Liczba kalorii: {dish.caloriesNumber}</p>
              <ul>
                {dish.ingredients.map((ingredient, index) => (
                  <li key={index}>{ingredient}</li>
                ))}
              </ul>
            </Box>
          ))}
          <Heading size="md">Choice reason: {day.choiceReason}</Heading>
        </Box>
      ))}
    </Box>
  );
}
