import {
  Stack,
  Flex,
  Button,
  Text,
  VStack,
  useBreakpointValue,
  FormControl,
  FormLabel,
  FormHelperText,
  Input,
  RadioGroup,
  Radio,
  useToast,
} from "@chakra-ui/react";
import { NavLink, useNavigate } from "react-router-dom";
import React, { useState } from "react";

export default function Hero() {
  const navigate = useNavigate();
  const [pesel, setPesel] = useState("");
  const [isDoctor, setIsDoctor] = useState("");
  const loginFailedToast = useToast();

  const handleInputChange = (e) => {
    setPesel(e.target.value);
  };

  const handleSubmit = async () => {
    // Save pesel in sessionStorage
    sessionStorage.setItem("pesel", pesel);
    sessionStorage.setItem("isDoctor", isDoctor.toString());

    if (pesel.length !== 11) {
      loginFailedToast({
        title: "Invalid PESEL! PESEL must have 11 digits.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    try {
      console.log("isDoctor: " + isDoctor);
      let res;
      if (isDoctor) {
        console.log("I'm a Doctor");
        res = await fetch(
          `http://localhost:8081/user/doctor/existsByPesel/${pesel}`
        );
      } else {
        console.log("I'm a patient");
        res = await fetch(
          `http://localhost:8081/user/patient/existsByPesel/${pesel}`
        );
      }

      const data = await res.json();

      if (typeof data === "boolean" && data === true) {
        const usernameRes = await fetch(
          `http://localhost:8084/forum/forumUser/getForumUserUsernameByPesel/${pesel}`
        );
        const usernameData = await usernameRes.text();
        sessionStorage.setItem("username", usernameData);

        navigate("/app/calendar");
      } else {
        loginFailedToast({
          title: "Login failed! You entered wrong credentials.",
          status: "error",
          duration: 2000,
          isClosable: true,
        });
      }
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
    }
  };

  return (
    <Flex
      w={"full"}
      h={"100vh"}
      backgroundColor="cyan"
      backgroundSize={"cover"}
      backgroundPosition={"center center"}
    >
      <VStack
        w={"full"}
        justify={"center"}
        px={useBreakpointValue({ base: 4, md: 8 })}
        bgGradient={"linear(to-r, cyan.600, transparent)"}
      >
        <Stack maxW={"2xl"} align={"center"} spacing={6}>
          <Text
            color={"white"}
            fontWeight={700}
            lineHeight={1.2}
            fontSize="3em"
            textAlign="center"
          >
            Witaj
          </Text>
          <Text
            color={"white"}
            fontWeight={700}
            lineHeight={1.2}
            fontSize={useBreakpointValue({ base: "3xl", md: "4xl" })}
          >
            Razem przez ciążę
          </Text>
          <Stack direction={"column"} spacing={4} align="center">
            {/* <Button
              bg={"pink.400"}
              rounded={"full"}
              color={"white"}
              _hover={{ bg: "blue.500" }}
            >
              Zaloguj się
            </Button> */}
            <Button
              bg={"pink.200"}
              rounded={"full"}
              color={"white"}
              _hover={{ bg: "whiteAlpha.500" }}
            >
              Zarejestruj się
            </Button>
            {/* <Button>
              <NavLink to="/app/dashboard">Przejdź do aplikacji</NavLink>
            </Button> */}
            <FormControl isRequired mb="40px">
              <FormLabel>Podaj pesel:</FormLabel>
              <Input
                type="text"
                name="pesel"
                value={pesel}
                onChange={handleInputChange}
              />
              <FormHelperText>Wprowadź PESEL</FormHelperText>
            </FormControl>
            <FormControl isRequired mt="20px">
              <FormLabel>Wybierz typ użytkownika</FormLabel>
              <RadioGroup
                onChange={(value) => setIsDoctor(value === "true")}
                value={isDoctor.toString()}
              >
                <Stack direction="row">
                  <Radio value="false">Patient</Radio>
                  <Radio value="true">Doctor</Radio>
                </Stack>
              </RadioGroup>
            </FormControl>
            <Button
              bg={"pink.400"}
              rounded={"full"}
              color={"white"}
              _hover={{ bg: "purpleAlpha.500" }}
              onClick={handleSubmit}
            >
              Zaloguj
            </Button>
          </Stack>
        </Stack>
      </VStack>
    </Flex>
  );
}
