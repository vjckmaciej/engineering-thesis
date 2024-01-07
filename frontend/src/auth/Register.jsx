import {
  Box,
  Button,
  Container,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Stack,
  useBreakpointValue,
  FormHelperText,
  Flex,
  InputGroup,
  InputRightElement,
  IconButton,
  useToast,
  RadioGroup,
  Radio,
} from "@chakra-ui/react";
import React, { useState } from "react";
import { HiEye, HiEyeOff } from "react-icons/hi";
import { NavLink, useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import { format, setHours, setMinutes } from "date-fns";

export default function Register() {
  const navigate = useNavigate();
  const [isDoctor, setIsDoctor] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [pesel, setPesel] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [birthDate, setBirthDate] = useState(
    setHours(setMinutes(new Date(), 0), 9)
  );
  const [pregnancyStartDate, setPregnancyStartDate] = useState(
    setHours(setMinutes(new Date(), 0), 9)
  );
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");

  const [isPasswordOpen, setIsPasswordOpen] = useState(false);
  const registerFailedToast = useToast();
  const registerSuccessToast = useToast();
  const today = new Date();

  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };

  const handlePeselChange = (e) => {
    setPesel(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handlePhoneNumberChange = (e) => {
    setPhoneNumber(e.target.value);
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleRegister = async (event) => {
    // Save pesel in sessionStorage
    sessionStorage.setItem("pesel", pesel);
    sessionStorage.setItem("isDoctor", isDoctor.toString());

    const formattedBirthDate = format(birthDate, "yyyy-MM-dd");
    const formattedPregnancyStartDate = format(
      pregnancyStartDate,
      "yyyy-MM-dd"
    );

    if (firstName === "" || firstName.length > 50) {
      registerFailedToast({
        title: "Podane imię jest za krótkie lub za długie.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    if (lastName === "" || lastName.length > 50) {
      registerFailedToast({
        title: "Podane nazwisko jest za krótkie lub za długie.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    if (pesel.length !== 11) {
      registerFailedToast({
        title: "Nieprawidłowy PESEL! PESEL musi składać się z 11 cyfr.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    if (phoneNumber.length !== 9) {
      registerFailedToast({
        title: "Podano nieprawidłowy numer telefonu.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    if (username.length < 5 || username.length > 20) {
      registerFailedToast({
        title: "Podana nazwa użytkownika jest nieprawidłowa.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    if (password.length < 5 || password.length > 20) {
      registerFailedToast({
        title: "Podane hasło jest nieprawidłowe.",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
      return;
    }

    try {
      console.log("isDoctor: " + isDoctor);
      let doesUserAlreadyExist;
      if (isDoctor) {
        console.log("I'm a Doctor");
        doesUserAlreadyExist = await fetch(
          `http://localhost:8081/user/doctor/existsByPesel/${pesel}`
        );
      } else {
        console.log("I'm a patient");
        doesUserAlreadyExist = await fetch(
          `http://localhost:8081/user/patient/existsByPesel/${pesel}`
        );
      }

      const data = await doesUserAlreadyExist.json();

      if (typeof data === "boolean" && data === false) {
        let doctorData;
        let patientData;
        let response;
        let forumUserData;
        let forumUserResponse;

        if (isDoctor) {
          doctorData = {
            firstName,
            lastName,
            birthDate: formattedBirthDate,
            pesel,
            phoneNumber,
          };
          response = await fetch("http://localhost:8081/user/doctor", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(doctorData),
          });
        } else {
          patientData = {
            firstName,
            lastName,
            birthDate: formattedBirthDate,
            pregnancyStartDate: formattedPregnancyStartDate,
            pesel,
            phoneNumber,
          };
          response = await fetch("http://localhost:8081/user/patient", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(patientData),
          });
        }

        forumUserData = {
          username,
          password,
          email,
          pesel,
        };
        forumUserResponse = await fetch(
          "http://localhost:8084/forum/forumUser",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(forumUserData),
          }
        );

        if (response.ok && forumUserResponse.ok) {
          console.log("Rejestracja przebiegła pomyślnie.");

          registerSuccessToast({
            title: "Rejestracja przebiegła pomyślnie!",
            status: "success",
            duration: 2000,
            isClosable: true,
          });
        }
      } else {
        registerFailedToast({
          title: "Użytkownik o tym PESELu istnieje już w bazie danych.",
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
      h={"full"}
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
                Rejestracja
              </Heading>
              <Stack spacing="5">
                <FormControl isRequired>
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
                  <FormLabel fontWeight="bold">Imię</FormLabel>
                  <Input
                    id="firstNameField"
                    type="text"
                    name="firstName"
                    value={firstName}
                    required
                    mb="20px"
                    onChange={handleFirstNameChange}
                  />
                  <FormLabel fontWeight="bold">Nazwisko</FormLabel>
                  <Input
                    id="lastNameField"
                    type="text"
                    name="lastName"
                    value={lastName}
                    required
                    mb="20px"
                    onChange={handleLastNameChange}
                  />
                  <FormLabel fontWeight="bold">PESEL</FormLabel>
                  <Input
                    id="peselField"
                    type="text"
                    name="pesel"
                    value={pesel}
                    required
                    mb="20px"
                    onChange={handlePeselChange}
                  />
                  <FormLabel fontWeight="bold">Numer telefonu</FormLabel>
                  <Input
                    id="phoneNumberField"
                    type="text"
                    name="phoneNumber"
                    value={phoneNumber}
                    required
                    mb="20px"
                    onChange={handlePhoneNumberChange}
                  />
                  <FormLabel fontWeight="bold">Email</FormLabel>
                  <Input
                    id="emailField"
                    type="text"
                    name="email"
                    value={email}
                    required
                    mb="20px"
                    onChange={handleEmailChange}
                  />

                  <FormLabel fontWeight="bold">Data urodzenia</FormLabel>
                  <DatePicker
                    selected={birthDate}
                    onChange={(date) => setBirthDate(date)}
                    dateFormat="yyyy-MM-dd"
                    showYearDropdown
                    showMonthDropdown
                    dropdownMode="select"
                    maxDate={today}
                  />
                  {!isDoctor && (
                    <>
                      <FormLabel fontWeight="bold" mt="20px">
                        Data rozpoczęcia ciąży
                      </FormLabel>
                      <DatePicker
                        selected={pregnancyStartDate}
                        onChange={(date) => setPregnancyStartDate(date)}
                        dateFormat="yyyy-MM-dd"
                        showYearDropdown
                        showMonthDropdown
                        dropdownMode="select"
                        maxDate={today}
                      />
                    </>
                  )}
                  <FormLabel fontWeight="bold" mt="20px">
                    Nazwa użytkownika
                  </FormLabel>
                  <Input
                    type="text"
                    name="username"
                    value={username}
                    required
                    onChange={handleUsernameChange}
                  />
                  <FormHelperText mb="20px">
                    Nazwa użytkownika musi zawierać między 5, a 20 znaków.
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
                      id="passwordField"
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
                  bg={"pink.300"}
                  rounded={"full"}
                  color={"white"}
                  _hover={{ bg: "pink.500" }}
                  onClick={handleRegister}
                >
                  Zarejestruj
                </Button>
              </Stack>
              <Stack spacing="6">
                <Button
                  bg={"purple.300"}
                  rounded={"full"}
                  color={"white"}
                  _hover={{ bg: "purple.500" }}
                  onClick={() => {
                    navigate("/");
                  }}
                >
                  Powrót do logowania
                </Button>
              </Stack>
            </Stack>
          </Box>
        </Stack>
      </Container>
    </Flex>
  );
}
