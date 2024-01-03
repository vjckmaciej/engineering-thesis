import React from "react";
import {
  Flex,
  Text,
  Button,
  Spacer,
  HStack,
  Divider,
  useToast,
} from "@chakra-ui/react";

export default function Navbar() {
  const pesel = sessionStorage.getItem("pesel");
  const isDoctor = sessionStorage.getItem("isDoctor");
  const username = sessionStorage.getItem("username");
  const loggedOutToast = useToast();

  const handleLogout = () => {
    sessionStorage.clear();
    loggedOutToast({
      title: "Pomyślnie wylogowano z portalu.",
      status: "success",
      duration: 2000,
      isClosable: true,
    });
    setTimeout(() => {
      window.location.href = "http://localhost:5173";
    }, 2000);
  };

  return (
    <>
      <Flex as="nav" mb="30px" alignItems="center">
        <Spacer />
        <HStack spacing="20px">
          {isDoctor === "true" ? (
            <Text fontWeight="bold">
              Witaj! Korzystasz jako Doktor pod nazwą użytkownika: {username}!
            </Text>
          ) : (
            <Text fontWeight="bold">
              Witaj! Korzystasz jako Pacjentka pod nazwą użytkownika: {username}
              !
            </Text>
          )}
          <Button colorScheme="purple" onClick={handleLogout}>
            Wyloguj
          </Button>
        </HStack>
      </Flex>
      <Divider borderColor="gray.400" mb="50px" />
    </>
  );
}
