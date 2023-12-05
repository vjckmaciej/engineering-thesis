import {
  Stack,
  Flex,
  Button,
  Text,
  VStack,
  useBreakpointValue,
  Textarea,
  FormControl,
  FormLabel,
  Input,
  FormHelperText,
} from "@chakra-ui/react";
import { NavLink, useNavigate, Outlet } from "react-router-dom";
import { useState } from "react";

export default function Hero() {
  const navigate = useNavigate();
  const [pesel, setPesel] = useState("");

  const handleInputChange = (e) => {
    setPesel(e.target.value);
  };

  const handleSubmit = () => {
    // Save pesel in sessionStorage
    sessionStorage.setItem("pesel", pesel);

    console.log(pesel);
    // Navigate to the '/create' route
    navigate("app/dashboard");
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
        <Stack maxW={"2xl"} align={"flex-center"} spacing={6}>
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
          d
          <Stack direction={"column"}>
            <Button
              bg={"pink.400"}
              rounded={"full"}
              color={"white"}
              _hover={{ bg: "blue.500" }}
            >
              Zaloguj się
            </Button>
            /*dodac rejestracje */
            <Button
              bg={"pink.200"}
              rounded={"full"}
              color={"white"}
              _hover={{ bg: "whiteAlpha.500" }}
            >
              Zarejestruj się
            </Button>
            <Button>
              <NavLink to="create">Przejdz do aplikacji</NavLink>
            </Button>
            <FormControl isRequired mb="40px">
              <FormLabel>Podaj pesel:</FormLabel>
              <Input
                type="text"
                name="pesel"
                value={pesel}
                onChange={handleInputChange}
              />
              <FormHelperText>Enter PESEL</FormHelperText>
            </FormControl>
            <Button
              bg={"pink.400"}
              rounded={"full"}
              color={"white"}
              _hover={{ bg: "purpleAlpha.500" }}
              onClick={handleSubmit}
            >
              Submit
            </Button>
          </Stack>
        </Stack>
      </VStack>
    </Flex>
  );
}
