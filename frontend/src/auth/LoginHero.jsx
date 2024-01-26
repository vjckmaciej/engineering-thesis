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
  const [username, setUsername] = useState("");
  const [isDoctor, setIsDoctor] = useState("");
  const [password, setPassword] = useState("");
  const [isPasswordOpen, setIsPasswordOpen] = useState(false);
  const peselFailedToast = useToast();
  const passwordFailedToast = useToast();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleLogin = async () => {
    // Save pesel in sessionStorage
    // sessionStorage.setItem("pesel", pesel);
    sessionStorage.setItem("username", username);
    sessionStorage.setItem("isDoctor", isDoctor.toString());

    if (username.length < 5 || username.length > 20) {
      peselFailedToast({
        title:
          "Nieprawidłowa nazwa użytkownika! Musi ona składać się minimum 5 znaków, a maksymalnie z 20 znaków.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    try {
      let loginCredentialsData = {
        username,
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
        const peselRes = await fetch(
          `http://localhost:8084/forum/forumUser/getForumUserPeselByUsername/${username}`
        );
        const peselData = await peselRes.text();

        sessionStorage.setItem("pesel", peselData);
        console.log(peselData);

        navigate("/app/calendar");
      } else {
        peselFailedToast({
          title:
            "Podano niewłaściwe hasło lub użytkownik o takiej nazwie użytkownika nie istnieje!",
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
      minH={useBreakpointValue({ base: "auto", md: "100vh" })}
      backgroundColor="cyan"
      backgroundSize={"cover"}
      backgroundPosition={"center center"}
      px={useBreakpointValue({ base: 4, md: 8 })}
      bgGradient={"linear(to-r, cyan.600, transparent)"}
    >
      <Container
        maxW="lg"
        py={{
          base: "6",
          md: "12",
        }}
        px={{
          base: "4",
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
                  <FormLabel fontWeight="bold">Nazwa użytkownika</FormLabel>
                  <Input
                    type="text"
                    name="username"
                    value={username}
                    required
                    onChange={handleUsernameChange}
                  />
                  <FormHelperText mb="20px">
                    Wprowadź swoją nazwę użytkownika.
                  </FormHelperText>
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
