import { EditIcon, ViewIcon } from "@chakra-ui/icons"
import { 
  Box, 
  SimpleGrid,
  Text,
  Flex,
  Heading,
  Card, 
  CardHeader,
  CardBody,
  CardFooter,
  HStack,
  Divider,
  Button,
  Spinner
} from "@chakra-ui/react"
import { useLoaderData } from "react-router-dom"
import React, { useEffect, useState } from 'react';


export default function Calendar() {
  const pesel = sessionStorage.getItem('pesel');
  const [loading, setLoading] = useState(false);
  const calendarWeeks = useLoaderData();
  const [patientCalendarWeek, setPatientCalendarWeek] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);

      try {
        const res = await fetch(`http://localhost:8082/visit/calendarWeek/pesel/${pesel}`);
        const data = await res.json();
        setPatientCalendarWeek(data);
      } catch (error) {
        console.error('Błąd pobierania danych:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();

  }, [pesel]);

  return (
    <Box>
      <Heading mb="40px">Your calendar week</Heading>
      {patientCalendarWeek && (
        <Box>
           <Card key={patientCalendarWeek.calendarWeekId} borderTop="8px" borderColor="purple.400" bg="white">
              <CardHeader color="gray.700">
                <Flex gap={5}>
                  <Box>
                    <Heading as="h2" size="sm">Hi future mum! You are in pregnancy week number: {patientCalendarWeek.pregnancyWeek}</Heading>
                  </Box>
                </Flex>
              </CardHeader>
              <CardBody color="gray.500" textAlign="center">
                <Text>{patientCalendarWeek.description}</Text>
              </CardBody>
              <Divider borderColor="gray.200" />
              <CardFooter>
                  <Button variant="ghost" leftIcon={<ViewIcon />}>Show more</Button>
              </CardFooter>
            </Card>
        </Box>
      )}

      <Heading mb="40px" mt="100px">All calendar weeks</Heading>
      {loading ? (
        <Spinner />
      ) : (
        <SimpleGrid spacing={10} minChildWidth={300}>
          {calendarWeeks && calendarWeeks.map(calendarWeek => (
            <Card key={calendarWeek.calendarWeekId} borderTop="8px" borderColor="purple.400" bg="white">
              <CardHeader color="gray.700">
                <Flex gap={5}>
                  <Box>
                    <Heading as="h2" size="sm">Pregnancy week number: {calendarWeek.pregnancyWeek}</Heading>
                  </Box>
                </Flex>
              </CardHeader>
              <CardBody color="gray.500" textAlign="center">
                <Text>{calendarWeek.description}</Text>
              </CardBody>
              <Divider borderColor="gray.200" />
              <CardFooter>
                <HStack>
                  <Button variant="ghost" leftIcon={<ViewIcon />}>Show more</Button>
                </HStack>
              </CardFooter>
            </Card>
          ))}
        </SimpleGrid>
      )}
    </Box>
  )
}

export const tasksLoader = async () => {
  const res = await fetch('http://localhost:8082/visit/calendarWeek')
  return res.json()
}
