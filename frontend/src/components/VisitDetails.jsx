import React, { useState, useEffect } from "react";
import {
  Box,
  Text,
  Heading,
  Button,
  Card,
  Stack,
  CardBody,
  Container,
  FormControl,
  FormLabel,
  Select,
} from "@chakra-ui/react";
import { useParams } from "react-router-dom";
import BloodTestForm from "../forms/BloodTestForm";

export default function VisitDetails() {
  const isDoctor = sessionStorage.getItem("isDoctor");
  const visitIdForm = sessionStorage.getItem("visitId");
  const { visitId } = useParams();
  const [visitDetails, setVisitDetails] = useState(null);
  const [medicalExaminations, setMedicalExaminations] = useState(null);
  const [resultsMap, setResultsMap] = useState({});
  const [analysisResult, setAnalysisResult] = useState("");
  const [areResultsLoaded, setAreResultsLoaded] = useState(false);
  const [selectedExaminationType, setSelectedExaminationType] = useState("");
  const resultNameMapping = {
    hemoglobin: "Hemoglobina",
    whiteBloodCellCount: "Liczba białych krwinek",
    plateletCount: "Liczba płytek krwi",
    fastingGlucose: "Poziom glukozy na czczo",
    ironLevel: "Poziom żelaza",
    infectionTest: "Test na obecność infekcji",
    bloodGroup: "Grupa krwi",
  };

  useEffect(() => {
    const fetchVisitDetails = async () => {
      try {
        const res = await fetch(`http://localhost:8082/visit/visit/${visitId}`);
        const data = await res.json();
        setVisitDetails(data);
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      }
    };

    const fetchMedicalExaminations = async () => {
      try {
        const res = await fetch(
          `http://localhost:8082/visit/medicalExamination/filter/${visitId}`
        );
        const data = await res.json();
        setMedicalExaminations(data);
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      }
    };

    fetchVisitDetails();
    fetchMedicalExaminations();
  }, [visitId]);

  const fetchResults = async (medicalExaminationId) => {
    try {
      const res = await fetch(
        `http://localhost:8082/visit/result/filter/${medicalExaminationId}`
      );
      const data = await res.json();
      return data;
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
      return null;
    }
  };

  const loadResultsForMedicalExamination = async (medicalExaminationId) => {
    const resultsData = await fetchResults(medicalExaminationId);
    if (resultsData) {
      setResultsMap((prevResultsMap) => ({
        ...prevResultsMap,
        [medicalExaminationId]: resultsData,
      }));
      setAreResultsLoaded(true);
    }
  };

  const analyzeVisitByOpenAI = async () => {
    try {
      const res = await fetch(
        `http://localhost:8082/visit/visit/analyzeVisitByOpenAI/${visitId}`
      );
      const data = await res.text();
      setAnalysisResult(data);
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
    }
  };

  const handleExaminationTypeChange = (event) => {
    setSelectedExaminationType(event.target.value);
  };

  const handleSubmitMedicalExamination = async (formData) => {
    event.preventDefault();

    const medicalExaminationData = {
      medicalExaminationName: selectedExaminationType,
      visitId: visitIdForm,
    };

    try {
      // Dodanie MedicalExamination
      let response = await fetch(
        "http://localhost:8082/visit/medicalExamination",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(medicalExaminationData),
        }
      );

      if (!response.ok) {
        throw new Error("Nie udało się dodać badania medycznego.");
      }

      // Pobranie ID nowo dodanego MedicalExamination
      const examinationResponse = await response.json();
      const medicalExaminationId = examinationResponse.id;

      // Wysłanie wyników badań
      for (const [key, value] of Object.entries(formData)) {
        const resultData = {
          resultName: key,
          numericalResult: value,
          medicalExaminationId: medicalExaminationId,
          // Dodać inne wymagane pola do resultData
        };

        response = await fetch("http://localhost:8082/visit/result", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(resultData),
        });

        if (!response.ok) {
          console.error(`Błąd podczas dodawania wyniku: ${key}`);
        }
      }

      console.log("Wszystkie wyniki badań dodane pomyślnie.");
    } catch (error) {
      console.error("Wystąpił błąd:", error);
    }
  };

  return (
    <Box>
      {visitDetails ? (
        <>
          <Heading mb="4">ID Wizyty: {visitDetails.visitId}</Heading>
          <Card
            key={visitDetails.visitId}
            direction={{ base: "column", sm: "row" }}
            overflow="hidden"
            variant="outline"
          >
            <Stack>
              <CardBody>
                <Heading size="md">
                  Status wizyty: {visitDetails.visitStatus}
                </Heading>
                <Text py="2">Data wizyty: {visitDetails.visitDate}</Text>
                <Text py="2">
                  Tydzień ciąży: {visitDetails.weekOfPregnancy}
                </Text>
                <Text py="3">
                  Zalecenia lekarskie: {visitDetails.doctorRecommendations}
                </Text>
              </CardBody>
            </Stack>
          </Card>
        </>
      ) : (
        <Text>Loading...</Text>
      )}

      {visitDetails && visitDetails.visitStatus === "SCHEDULED" && (
        <Button
          bg="purple.300"
          color="white"
          mt="30px"
          onClick={() => analyzeVisitByOpenAI()}
        >
          Click to analyze this Visit by OpenAI
        </Button>
      )}
      <Text>Analysis result: {analysisResult}</Text>

      <Heading mb="4" mt="40px">
        All medical examinations
      </Heading>
      <Box maxW="480px">
        <FormControl mb="40px">
          <FormLabel>Wybierz zestaw badań do dodania:</FormLabel>
          <Select
            name="medicalExaminationName"
            placeholder="Wybierz kategorię"
            onChange={handleExaminationTypeChange}
          >
            <option value="Badanie krwi">Badanie krwi</option>
            <option value="Badanie moczu">Badanie moczu</option>
            <option value="USG ciąży">USG ciąży</option>
            <option value="Test tolerancji glukozy (OGTT)">
              Test tolerancji glukozy (OGTT)
            </option>
            <option value="Badanie tarczycy (TSH, FT4)">
              Badanie tarczycy (TSH, FT4)
            </option>
            <option value="Badanie poziomu witamin i minerałów">
              Badanie poziomu witamin i minerałów
            </option>
            <option value="Kardiografia płodu (KTG)">
              Kardiografia płodu (KTG)
            </option>
            <option value="Badanie ginekologiczne">
              Badanie ginekologiczne
            </option>
            <option value="Badanie przeciwciał">Badanie przeciwciał</option>
            <option value="Badanie grupy krwi">Badanie grupy krwi</option>
            <option value="Badania na obecność infekcji">
              Badania na obecność infekcji
            </option>
          </Select>
        </FormControl>
      </Box>
      {selectedExaminationType === "Badanie krwi" && (
        <BloodTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {/* {!areResultsLoaded && ( */}
      <Button
        bg="purple.500"
        color="white"
        mt="30px"
        onClick={() => handleAddResults()}
        maxW="480px"
        disabled={!selectedExaminationType}
      >
        Dodaj wyniki badań
      </Button>
      {/* )} */}

      {medicalExaminations &&
        medicalExaminations.map((medicalExamination) => (
          <Card
            key={medicalExamination.medicalExaminationId}
            direction={{ base: "column", sm: "row" }}
            overflow="hidden"
            variant="outline"
            mb="2"
          >
            <Stack>
              <CardBody>
                {/* <Heading size="md" mb="5px">
                  Medical examination Id: {medicalExamination.medicalExaminationId}
                </Heading> */}
                <Heading size="md">
                  {medicalExamination.medicalExaminationName}
                </Heading>

                <Button
                  mb="20px"
                  mt="20px"
                  variant="solid"
                  colorScheme="blue"
                  onClick={() =>
                    loadResultsForMedicalExamination(
                      medicalExamination.medicalExaminationId
                    )
                  }
                >
                  Pokaż wyniki
                </Button>

                {resultsMap[medicalExamination.medicalExaminationId] &&
                  resultsMap[medicalExamination.medicalExaminationId].map(
                    (result) => (
                      <Container mb="15px" key={result.resultId}>
                        <Text fontWeight="bold">
                          Nazwa badania:{" "}
                          {resultNameMapping[result.resultName] ||
                            result.resultName}
                        </Text>
                        <Text>Opis badania: {result.resultDescription}</Text>
                        <Text>Wynik liczbowy: {result.numericalResult}</Text>
                        <Text>Jednostka: {result.unit}</Text>
                        <Text>Wynik opisowy: {result.descriptiveResult}</Text>
                        <Text>Notatka lekarza: {result.doctorNote}</Text>
                      </Container>
                    )
                  )}
              </CardBody>
            </Stack>
          </Card>
        ))}
    </Box>
  );
}
