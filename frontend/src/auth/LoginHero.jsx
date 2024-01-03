import {
  Box,
  Button,
  Checkbox,
  Container,
  Divider,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Link,
  Stack,
  Text,
  useBreakpointValue,
  FormHelperText,
  Flex,
  useDisclosure,
  InputGroup,
  InputRightElement,
  useMergeRefs,
  IconButton,
  useToast,
  RadioGroup,
  Radio,
} from "@chakra-ui/react";
import React, { useState } from "react";
import { HiEye, HiEyeOff } from "react-icons/hi";
import { NavLink, useNavigate } from "react-router-dom";

export default function LoginHero() {
  const navigate = useNavigate();
  const [pesel, setPesel] = useState("");
  const [isDoctor, setIsDoctor] = useState("");
  const [password, setPassword] = useState("");
  const [isPasswordOpen, setIsPasswordOpen] = useState(false);
  const peselFailedToast = useToast();
  const passwordFailedToast = useToast();

  const handlePeselChange = (e) => {
    setPesel(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleLogin = async () => {
    // Save pesel in sessionStorage
    sessionStorage.setItem("pesel", pesel);
    sessionStorage.setItem("isDoctor", isDoctor.toString());

    if (pesel.length !== 11) {
      peselFailedToast({
        title: "Nieprawidłowy PESEL! PESEL musi składać się z 11 cyfr.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    try {
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
        let loginCredentialsData = {
          pesel,
          password,
        };
        const areCredentialsCorrectResponse = await fetch(
          "http://localhost:8084/forum/forumUser/checkCredentials",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(loginCredentialsData),
          }
        );

        const isAuthenticated = await areCredentialsCorrectResponse.json();

        if (typeof isAuthenticated === "boolean" && isAuthenticated === true) {
          const usernameRes = await fetch(
            `http://localhost:8084/forum/forumUser/getForumUserUsernameByPesel/${pesel}`
          );
          const usernameData = await usernameRes.text();
          sessionStorage.setItem("username", usernameData);

          navigate("/app/calendar");
        } else {
          peselFailedToast({
            title:
              "Podano niewłaściwe hasło lub użytkownik o tym numerze PESEL nie istnieje!",
            status: "error",
            duration: 2000,
            isClosable: true,
          });
        }
      } else {
        peselFailedToast({
          title:
            "Podano niewłaściwe hasło lub użytkownik o tym numerze PESEL nie istnieje!",
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
      px={useBreakpointValue({ base: 4, md: 8 })}
      bgGradient={"linear(to-r, cyan.600, transparent)"}
    >
      <Container
        maxW="lg"
        py={{
          base: "12",
          md: "24",
        }}
        px={{
          base: "0",
          sm: "8",
        }}
      >
        <Stack spacing="8">
          <Stack spacing="6">
            {/* <Logo /> */}
            <Stack
              spacing={{
                base: "2",
                md: "3",
              }}
              textAlign="center"
            >
              <Heading size="xl" color="white">
                Witaj w naszej aplikacji!
              </Heading>
            </Stack>
          </Stack>
          <Box
            py={{
              base: "0",
              sm: "8",
            }}
            px={{
              base: "4",
              sm: "10",
            }}
            bg="white"
            boxShadow={{
              base: "none",
              sm: "md",
            }}
            borderRadius={{
              base: "none",
              sm: "xl",
            }}
            color="black"
          >
            <Stack spacing="6">
              <Heading size="md" textAlign="center">
                Logowanie
              </Heading>
              <Stack spacing="5">
                <FormControl>
                  <FormLabel fontWeight="bold">Typ użytkownika</FormLabel>
                  <RadioGroup
                    onChange={(value) => setIsDoctor(value === "true")}
                    value={isDoctor.toString()}
                    mb="20px"
                  >
                    <Stack direction="row">
                      <Radio value="true">Doktor</Radio>
                      <Radio value="false">Pacjentka</Radio>
                    </Stack>
                  </RadioGroup>
                  <FormLabel fontWeight="bold">PESEL</FormLabel>
                  <Input
                    type="text"
                    name="pesel"
                    value={pesel}
                    required
                    onChange={handlePeselChange}
                  />
                  <FormHelperText mb="20px">Wprowadź PESEL</FormHelperText>
                  <FormLabel fontWeight="bold">Hasło</FormLabel>
                  <InputGroup>
                    <InputRightElement>
                      <IconButton
                        variant="text"
                        aria-label={
                          isPasswordOpen ? "Ukryj hasło" : "Pokaż hasło"
                        }
                        icon={isPasswordOpen ? <HiEyeOff /> : <HiEye />}
                        onClick={() => setIsPasswordOpen(!isPasswordOpen)}
                      />
                    </InputRightElement>
                    <Input
                      id="password"
                      type={isPasswordOpen ? "text" : "password"}
                      name="password"
                      required
                      value={password}
                      onChange={handlePasswordChange}
                    />
                  </InputGroup>
                  <FormHelperText mb="20px">
                    Hasło musi zawierać między 5, a 20 znaków.
                  </FormHelperText>
                </FormControl>
              </Stack>
              <Stack spacing="6">
                <Button
                  bg={"purple.300"}
                  rounded={"full"}
                  color={"white"}
                  _hover={{ bg: "purple.500" }}
                  onClick={handleLogin}
                >
                  Zaloguj
                </Button>
              </Stack>
              <Stack spacing="6">
                <Button
                  bg={"pink.300"}
                  rounded={"full"}
                  color={"white"}
                  _hover={{ bg: "pink.500" }}
                  onClick={() => {
                    navigate("/register");
                  }}
                >
                  Rejestracja
                </Button>
              </Stack>
            </Stack>
          </Box>
        </Stack>
      </Container>
    </Flex>
  );
}
