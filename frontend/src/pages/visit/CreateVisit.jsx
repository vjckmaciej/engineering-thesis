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
          title: "Visit successfully added!",
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
            "Hi Doctor! Patient with this PESEL doesn't exist in database or you have already booked visit for this date before!",
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
      <Heading mb="40px">New visit</Heading>
      <Box mb="20px">
        <Heading size="md">Choose date:</Heading>
        <DatePicker
          selected={visitDatePicker}
          onChange={(date) => {
            setVisitDatePicker(date);
            console.log(visitDatePicker);
          }}
          showTimeSelect
          filterTime={filterPassedTime}
          dateFormat="yyyy-MM-dd HH:mm"
        />
      </Box>

      <Form method="post" onSubmit={handleSubmit}>
        <FormControl isRequired mb="40px">
          <FormLabel>Patient PESEL:</FormLabel>
          <Input
            placeholder="Enter patient's PESEL"
            type="text"
            name="patientPesel"
          />
        </FormControl>

        <Button type="submit" variant="solid" colorScheme="green" mb="30px">
          Create
        </Button>
      </Form>
    </Box>
  );
}
