import {
  Flex,
  Heading,
  Box,
  Text,
  Button,
  Spacer,
  HStack,
} from "@chakra-ui/react";
import { NavLink } from "react-router-dom";
import React from "react";

export default function Navbar() {
  const pesel = sessionStorage.getItem("pesel");
  const isDoctor = sessionStorage.getItem("isDoctor");
  console.log("calendar isDoctor: " + isDoctor);

  return (
    <Flex as="nav" p="10px" mb="60px" alignItems="center">
      <Spacer />

      <HStack spacing="20px">
        <Box bg="gray.200" p="10px 15px" borderRadius="50%">
          M
        </Box>
        {isDoctor === "true" ? (
          <Text fontWeight="bold">User type: Doctor</Text>
        ) : (
          <Text fontWeight="bold">User type: Patient</Text>
        )}
        <Heading size="xs">Pesel: {pesel}</Heading>
        <Button colorScheme="purple">Logout</Button>
      </HStack>
    </Flex>
  );
}
