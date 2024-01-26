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
  Spinner,
  useToast,
} from "@chakra-ui/react";
import { Form } from "react-router-dom";

export default function DietPlan() {
  const pesel = sessionStorage.getItem("pesel");
  const [daysNumber, setDaysNumber] = useState("1");
  const [dishesNumber, setDishesNumber] = useState("1");
  const [caloriesRange, setCaloriesRange] = useState("1000-1500");
  const [allergens, setAllergens] = useState("");
  const [proposedDietPlan, setProposedDietPlan] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const dietPlanGeneratingFailed = useToast();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsLoading(true);

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
        setIsLoading(false);
        setProposedDietPlan(dietPlan);
      } else {
        setIsLoading(false);
        dietPlanGeneratingFailed({
          title: "Niestety nie udało się wygenerować planu dietetycznego.",
          status: "error",
          duration: 2000,
          isClosable: true,
        });
        console.error("Failed to create diet plan");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <Box>
      <Heading size="xl" textAlign="center">
        Stwórz swój własny plan dietetyczny!
      </Heading>

      <Heading size="md" mt="20px" mb="20px">
        Na ile dni chcesz dostać plan dietetyczny?
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
        Ile posiłków dziennie ma zawierać plan dietetyczny?
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
        Ile kcal dziennie chcesz spożywać?
      </Heading>
      <Form method="post" onSubmit={handleSubmit}>
        <FormControl isRequired mb="40px">
          <FormLabel>Liczba kcal</FormLabel>
          <Select
            value={caloriesRange}
            onChange={(e) => setCaloriesRange(e.target.value)}
            placeholder="Wybierz przedział kaloryczny"
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
          <FormLabel>Alergeny:</FormLabel>
          <Textarea
            value={allergens}
            onChange={(e) => setAllergens(e.target.value)}
            placeholder="Wypisz tutaj wszystkie alergeny i składniki, których nie chcesz w swojej diecie :)"
            name="allergens"
          />
        </FormControl>

        <Button type="submit" variant="solid" colorScheme="green" mb="30px">
          Kliknij tutaj, aby OpenAI stworzyło specjalny plan dietetyczny!
        </Button>
      </Form>
      {isLoading && <Spinner size="xl" />}

      {proposedDietPlan.map((day) => (
        <Box key={day.dayOfTheWeekName} mt="40px">
          <Heading size="lg">{day.dayOfTheWeekName}</Heading>
          {day.dishDTO.map((dish) => (
            <Box key={dish.dishName} mt="20px">
              <Heading size="md">{dish.mealName}</Heading>
              <Heading size="sm">{dish.dishName}</Heading>
              <p>Liczba kalorii: {dish.dishCaloriesNumber}</p>
              <p>Składniki: </p>
              <ul>
                {dish.quantityWithIngredient.map((ingredient, index) => (
                  <li key={index}>{ingredient}</li>
                ))}
              </ul>
            </Box>
          ))}
          <Heading size="md" mt="10px">
            Uzasadnienie: {day.choiceReason}
          </Heading>
        </Box>
      ))}
    </Box>
  );
}
