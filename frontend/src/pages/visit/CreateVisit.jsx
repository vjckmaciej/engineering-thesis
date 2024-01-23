import {
  Box,
  FormControl,
  FormLabel,
  Input,
  Button,
  Heading,
  useToast,
} from "@chakra-ui/react";
import { Form } from "react-router-dom";
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format, setHours, setMinutes } from "date-fns";

export default function CreateVisit() {
  const doctorPesel = sessionStorage.getItem("pesel");
  const [visitDatePicker, setVisitDatePicker] = useState(
    setHours(setMinutes(new Date(), 0), 9)
  );
  const visitAddedToast = useToast();
  const visitFailedToast = useToast();

  const filterPassedTime = (time) => {
    const currentDate = new Date();
    const selectedDate = new Date(time);
    return currentDate.getTime() < selectedDate.getTime();
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const formattedDate = format(visitDatePicker, "yyyy-MM-dd HH:mm");

    const visitData = {
      doctorPesel,
      patientPesel: formData.get("patientPesel"),
      visitDate: formattedDate,
    };

    try {
      const response = await fetch("http://localhost:8082/visit/visit", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(visitData),
      });

      console.log(visitData);

      if (response.ok) {
        console.log("Visit created successfully");

        visitAddedToast({
          title: "Wizyta pomyślnie dodana!",
          status: "success",
          duration: 2000,
          isClosable: true,
        });

        setTimeout(() => {
          window.location.reload();
        }, 2000);
      } else {
        console.error("Failed to create visit");

        visitFailedToast({
          title:
            "Pacjentka z tym PESELem nie istnieje w bazie danych lub masz już umówioną wizytę w tym terminie.",
          status: "error",
          duration: 2000,
          isClosable: true,
        });
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <Box maxW="550px">
      <Heading mb="40px">Nowa wizyta</Heading>
      <Box mb="20px">
        <Heading size="md">Wybierz datę:</Heading>
        <DatePicker
          selected={visitDatePicker}
          onChange={(date) => {
            setVisitDatePicker(date);
          }}
          showTimeSelect
          filterTime={filterPassedTime}
          dateFormat="yyyy-MM-dd HH:mm"
        />
      </Box>

      <Form method="post" onSubmit={handleSubmit}>
        <FormControl isRequired mb="40px">
          <FormLabel>PESEL pacjentki:</FormLabel>
          <Input
            placeholder="Wprowadź PESEL pacjentki"
            type="text"
            name="patientPesel"
          />
        </FormControl>

        <Button type="submit" variant="solid" colorScheme="green" mb="30px">
          Dodaj
        </Button>
      </Form>
    </Box>
  );
}
