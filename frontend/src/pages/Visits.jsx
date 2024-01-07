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
  Spacer,
  FormControl,
  FormLabel,
  Input,
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
  const [conversation, setConversation] = useState([]);
  const [analysisResult, setAnalysisResult] = useState("");
  const [nearestPlannedVisit, setNearestPlannedVisit] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [userQuestion, setUserQuestion] = useState("");
  const [isAnswerForUserQuestionLoading, setIsAnswerForUserQuestionLoading] =
    useState("");
  const [answerForUserQuestion, setAnswerForUserQuestion] = useState("");

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

  const removePolishCharacters = (str) => {
    const polishChars = {
      ą: "a",
      ć: "c",
      ę: "e",
      ł: "l",
      ń: "n",
      ó: "o",
      ś: "s",
      ź: "z",
      ż: "z",
    };
    return str.replace(/[ąćęłńóśźż]/g, (match) => polishChars[match]);
  };

  const analyzeVisitsByOpenAI = async () => {
    setIsLoading(true);

    try {
      const visitsResultsReportResponse = await fetch(
        `http://localhost:8082/visit/visit/generateReportPatientPesel/${pesel}`
      );
      const visitsResultsObject = await visitsResultsReportResponse.json();
      const visitsResultsString = JSON.stringify(visitsResultsObject);

      setConversation((prevConversation) => {
        let entryTextForOpenAI =
          "Prowadzisz konwersacje z pacjentka w ciazy. To lista jej wizyt: ";
        const updated = [...prevConversation];
        updated[0] = entryTextForOpenAI + visitsResultsString;
        return updated;
      });

      const res = await fetch(
        `http://localhost:8082/visit/visit/analyzeReportsByPesel/${pesel}`
      );
      const analyzeString = await res.text();

      const cleanedAnalysisResult = removePolishCharacters(analyzeString);

      setConversation((prevConversation) => {
        let analyzedTextEntryForOpenAI =
          "Przeanalizowales wizyty i to jest Twoja analiza: ";
        const updated = [...prevConversation];
        updated[1] = analyzedTextEntryForOpenAI + cleanedAnalysisResult;
        return updated;
      });

      setAnalysisResult(analyzeString);
      setIsLoading(false);
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    console.log("Zaktualizowane conversation:", conversation);
  }, [conversation]);

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

  const handleQuestionChange = (e) => {
    setUserQuestion(e.target.value);
  };

  const handleSubmitQuestion = async () => {
    setIsAnswerForUserQuestionLoading(true);

    const userQuestionEntryOpenAI = "Odpowiedz na pytanie pacjentki: ";
    let updatedConversation = [...conversation];

    // Jeśli długość conversation wynosi mniej niż 2, dodaj puste miejsca
    while (updatedConversation.length < 2) {
      updatedConversation.push("");
    }

    const cleanedUserQuestion = removePolishCharacters(userQuestion);
    updatedConversation[2] = userQuestionEntryOpenAI + cleanedUserQuestion;

    // setUserQuestion("");
    const requestBody = JSON.stringify({
      conversationHistory: updatedConversation,
    });
    console.log(requestBody);
    try {
      const answerForUserQuestionResponse = await fetch(
        `http://localhost:8083/api/v1/askQuestion`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
          body: requestBody,
        }
      );
      const answerForUserQuestionString =
        await answerForUserQuestionResponse.text();
      setAnswerForUserQuestion(answerForUserQuestionString);
      setIsAnswerForUserQuestionLoading(false);
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
      setIsAnswerForUserQuestionLoading(false);
    }

    setConversation(updatedConversation);
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
                  PESEL pacjentki: {nearestPlannedVisit.patientPesel}
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

      <Heading mb="30px" mt="100px" textAlign="center">
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
        <Flex
          direction="column"
          justifyContent="center"
          alignItems="center"
          mt="10px"
        >
          <Button
            bg="purple.300"
            color="white"
            onClick={() => analyzeVisitsByOpenAI()}
          >
            Kliknij tutaj, aby OpenAI przeanalizowało wszystkie wizyty
          </Button>
          {isLoading && <Spinner size="xl" mt="20px" />}
          <Text mt="20px" textAlign="center" mb="20px">
            {analysisResult}
          </Text>
        </Flex>
      )}

      {analysisResult !== "" && (
        <Flex
          direction="column"
          justifyContent="center"
          alignItems="center"
          mt="10px"
        >
          <FormControl>
            <FormLabel textAlign="center" fontWeight="bold" size="xl">
              Zadaj pytanie AI:
            </FormLabel>
            <Input
              placeholder="Napisz nurtujące Cię pytanie :)"
              type="text"
              value={userQuestion}
              onChange={handleQuestionChange}
            />
          </FormControl>
          <Button mt="10px" colorScheme="blue" onClick={handleSubmitQuestion}>
            Wyślij pytanie
          </Button>
          {isAnswerForUserQuestionLoading && <Spinner size="xl" mt="20px" />}
          <Text mt="20px" textAlign="center" mb="20px">
            {answerForUserQuestion}
          </Text>
        </Flex>
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
                        PESEL pacjentki: {myvisit.patientPesel}
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
            <Text>Brak wizyt.</Text>
          )}
        </SimpleGrid>
      )}
    </Box>
  );
}
