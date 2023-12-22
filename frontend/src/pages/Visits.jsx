import React, { useEffect, useState } from "react";
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
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
  Spinner,
  Container,
  Spacer,
  FormControl,
  FormLabel,
  Input,
  FormHelperText,
} from "@chakra-ui/react";
import { AddIcon, ViewIcon } from "@chakra-ui/icons";

export default function Visits() {
  const navigate = useNavigate();
  const pesel = sessionStorage.getItem("pesel");
  const isDoctor = sessionStorage.getItem("isDoctor");
  sessionStorage.setItem("visitId", 0);
  const [loading, setLoading] = useState(false);
  const [selectedPatientPesel, setSelectedPatientPesel] = useState("");
  const [myVisits, setMyVisits] = useState([]);
  const [analysisResult, setAnalysisResult] = useState("");
  const [nearestPlannedVisit, setNearestPlannedVisit] = useState(null);

  useEffect(() => {
    const fetchVisits = async () => {
      setLoading(true);

      try {
        const res = await fetch(
          `http://localhost:8082/visit/visit/myvisits/${pesel}?isDoctor=${isDoctor}`
        );

        const data = await res.json();
        setMyVisits(data);
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchVisits();
  }, [pesel]);

  useEffect(() => {
    const fetchNearestPlannedVisit = async () => {
      try {
        const res = await fetch(
          `http://localhost:8082/visit/visit/nearestPlannedVisit/${pesel}?isDoctor=${isDoctor}`
        );
        const data = await res.json();
        console.log(data);
        setNearestPlannedVisit(data);
      } catch (error) {
        console.error(
          "Błąd pobierania danych dla najbliższej zaplanowanej wizyty:",
          error
        );
      }
    };

    fetchNearestPlannedVisit();
  }, [pesel]);

  const handleShowMore = (visitId) => {
    sessionStorage.setItem("visitId", visitId);
    console.log(visitId);
    navigate(`/app/visits/${visitId}`);
  };

  const analyzeVisitsByOpenAI = async () => {
    try {
      const res = await fetch(
        `http://localhost:8082/visit/visit/analyzeReportsByPesel/${pesel}`
      );
      const data = await res.text();
      setAnalysisResult(data);
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
    }
  };

  const handleInputChange = (e) => {
    setSelectedPatientPesel(e.target.value);
  };

  const findDoctorVisitWithGivenPatientPesel = async () => {
    try {
      let res;
      if (selectedPatientPesel !== "") {
        res = await fetch(
          `http://localhost:8082/visit/visit/myvisitsWithGivenPatientPesel/${pesel}?patientPesel=${selectedPatientPesel}`
        );
      } else {
        res = await fetch(
          `http://localhost:8082/visit/visit/myvisits/${pesel}?isDoctor=${isDoctor}`
        );
      }

      const data = await res.json();
      setMyVisits(data);
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box>
      <Heading mb="40px" textAlign="center">
        Następna wizyta
      </Heading>

      {nearestPlannedVisit && nearestPlannedVisit.visitId ? (
        <Card
          key={nearestPlannedVisit.visitId}
          borderTop="8px"
          borderColor="purple.400"
          bg="white"
        >
          <CardHeader color="gray.700">
            <Flex gap={5}>
              <Box>
                <Heading as="h2" size="sm">
                  Status wizyty: {nearestPlannedVisit.visitStatus}
                </Heading>
                <Heading as="h2" size="sm">
                  Data wizyty:{" "}
                  {format(
                    new Date(nearestPlannedVisit.visitDate),
                    "yyyy-MM-dd HH:mm"
                  )}
                </Heading>
              </Box>
            </Flex>
          </CardHeader>
          <CardBody color="gray.500" textAlign="center">
            <Text>Tydzień ciąży: {nearestPlannedVisit.weekOfPregnancy}</Text>
            <Text>{nearestPlannedVisit.womanAge}</Text>
            <Text>{nearestPlannedVisit.doctorRecommendations}</Text>
          </CardBody>
          <Divider borderColor="gray.200" />
          <CardFooter>
            <HStack>
              <Button
                variant="ghost"
                leftIcon={<ViewIcon />}
                onClick={() => handleShowMore(nearestPlannedVisit.visitId)}
              >
                Pokaż więcej{" "}
              </Button>
            </HStack>
          </CardFooter>
        </Card>
      ) : (
        <Heading textAlign="center" size="md">
          Brak umówionych wizyt.
        </Heading>
      )}

      <Heading mb="40px" mt="100px" textAlign="center">
        Wszystkie wizyty{" "}
      </Heading>
      {isDoctor === "true" && (
        <FormControl mb="40px">
          <FormLabel>
            Wprowadź PESEL pacjenta, aby przefiltrować wizyty:
          </FormLabel>
          <Input
            type="text"
            name="selectedPatientPesel"
            value={selectedPatientPesel}
            onChange={handleInputChange}
          />
          <Button
            bg={"pink.500"}
            rounded={"full"}
            color={"white"}
            _hover={{ bg: "purple.300" }}
            mt="5px"
            onClick={findDoctorVisitWithGivenPatientPesel}
          >
            Znajdź wizyty pacjenta
          </Button>
          <Button
            ml="10px"
            bg={"blue.500"}
            rounded={"full"}
            color={"white"}
            _hover={{ bg: "green.300" }}
            mt="5px"
            onClick={() => {
              setSelectedPatientPesel("");
              findDoctorVisitWithGivenPatientPesel;
            }}
          >
            Wyczyść
          </Button>
        </FormControl>
      )}
      {isDoctor === "false" && (
        <>
          <Button bg="cyan" onClick={() => analyzeVisitsByOpenAI()}>
            Naciśnij przycisk, aby OpenAI przeanalizowało wizyty
          </Button>
          <Text>Wynik analizy: {analysisResult}</Text>
        </>
      )}

      {loading ? (
        <Spinner />
      ) : (
        <SimpleGrid spacing={10} minChildWidth={300}>
          {Array.isArray(myVisits) && myVisits.length > 0 ? (
            myVisits.map((myvisit) => (
              <Card
                key={myvisit.visitId}
                borderTop="8px"
                borderColor="purple.400"
                bg="white"
              >
                <CardHeader color="gray.700">
                  <Flex gap={5}>
                    <Box>
                      <Heading as="h2" size="sm">
                        Status wizyty: {myvisit.visitStatus}
                      </Heading>
                      <Heading as="h2" size="sm">
                        Data wizyty:{" "}
                        {format(
                          new Date(myvisit.visitDate),
                          "yyyy-MM-dd HH:mm"
                        )}
                      </Heading>
                      <Spacer />
                    </Box>
                  </Flex>
                </CardHeader>
                <CardBody color="gray.500" textAlign="center">
                  <Text> Tydzień ciąży: {myvisit.weekOfPregnancy}</Text>
                  <Text>{myvisit.womanAge}</Text>
                  <Text>{myvisit.doctorRecommendations}</Text>
                </CardBody>
                <Divider borderColor="gray.200" />
                <CardFooter>
                  <HStack>
                    <Button
                      variant="ghost"
                      leftIcon={<ViewIcon />}
                      onClick={() => handleShowMore(myvisit.visitId)}
                    >
                      Pokaż więcej
                    </Button>
                  </HStack>
                </CardFooter>
              </Card>
            ))
          ) : (
            <Text>No visits available.</Text>
          )}
        </SimpleGrid>
      )}
    </Box>
  );
}
