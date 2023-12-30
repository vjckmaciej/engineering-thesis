import React, { useEffect, useState } from "react";
import {
  Box,
  Heading,
  SimpleGrid,
  Spinner,
  Card,
  CardHeader,
  Flex,
  Button,
  Text,
  Divider,
  CardBody,
  CardFooter,
  HStack,
} from "@chakra-ui/react";
import { useLoaderData } from "react-router-dom";
import { ViewIcon } from "@chakra-ui/icons";

export default function Calendar() {
  const pesel = sessionStorage.getItem("pesel");
  const isDoctor = sessionStorage.getItem("isDoctor");
  const [loading, setLoading] = useState(false);
  const calendarWeeks = useLoaderData();
  const [patientCalendarWeek, setPatientCalendarWeek] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);

      try {
        const res = await fetch(
          `http://localhost:8082/visit/calendarWeek/pesel/${pesel}`
        );
        const data = await res.json();
        setPatientCalendarWeek(data);
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      } finally {
        setLoading(false);
      }
    };

    if (isDoctor === "false") {
      fetchData();
    }
  }, [pesel, isDoctor]);

  return (
    <Box>
      {isDoctor === "false" && patientCalendarWeek && (
        <Box>
          <Heading mb="40px" textAlign="center">
            Twój tydzień ciąży
          </Heading>
          <Card
            key={patientCalendarWeek.calendarWeekId}
            borderTop="8px"
            borderColor="purple.400"
            bg="white"
          >
            <CardHeader color="gray.700">
              <Flex gap={5}>
                <Box>
                  <Heading as="h2" size="sm">
                    Cześć przyszła Mamo! Jesteś w tygodniu ciąży numer:{" "}
                    {patientCalendarWeek.pregnancyWeek}
                  </Heading>
                </Box>
              </Flex>
            </CardHeader>
            <CardBody color="gray.500" textAlign="center">
              <Text>{patientCalendarWeek.description}</Text>
            </CardBody>
            <Divider borderColor="gray.200" />
            {/* <CardFooter>
              <Button variant="ghost" leftIcon={<ViewIcon />}>
                Pokaż
              </Button>
            </CardFooter> */}
          </Card>
        </Box>
      )}

      <Heading mb="40px" mt="30px" textAlign="center">
        Wszystkie tygodnie ciąży
      </Heading>
      {loading ? (
        <Spinner />
      ) : (
        <SimpleGrid spacing={10} minChildWidth={300}>
          {calendarWeeks &&
            calendarWeeks.map((calendarWeek) => (
              <Card
                key={calendarWeek.calendarWeekId}
                borderTop="8px"
                borderColor="purple.400"
                bg="white"
              >
                <CardHeader color="gray.700">
                  <Flex gap={5}>
                    <Box>
                      <Heading as="h2" size="sm">
                        Tydzień ciąży numer: {calendarWeek.pregnancyWeek}
                      </Heading>
                    </Box>
                  </Flex>
                </CardHeader>
                <CardBody color="gray.500" textAlign="center">
                  <Text>{calendarWeek.description}</Text>
                </CardBody>
                <Divider borderColor="gray.200" />
                {/* <CardFooter>
                  <HStack>
                    <Button variant="ghost" leftIcon={<ViewIcon />}>
                      Pokaż więcej
                    </Button>
                  </HStack>
                </CardFooter> */}
              </Card>
            ))}
        </SimpleGrid>
      )}
    </Box>
  );
}

export const tasksLoader = async () => {
  const res = await fetch("http://localhost:8082/visit/calendarWeek");
  return res.json();
};
