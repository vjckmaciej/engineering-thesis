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

    // Upewnij się, że wszystkie wymagane pola są uwzględnione
    const medicalExaminationData = {
      medicalExaminationName: selectedExaminationType, // Nazwa badania
      visitId: visitIdForm, // ID wizyty
      // Dodaj inne wymagane pola z examinationData
    };

    console.log("Dane wysyłane do serwera:", medicalExaminationData);

    try {
      const response = await fetch(
        "http://localhost:8082/visit/medicalExamination",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(medicalExaminationData),
        }
      );

      if (response.ok) {
        console.log("Badanie medyczne dodane pomyślnie.");
        // Dodaj odpowiednią logikę po sukcesie
      } else {
        console.error("Nie udało się dodać badania medycznego.");
        // Dodaj odpowiednią logikę błędu
      }
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
                      <Container mb="10px" key={result.resultId}>
                        <Text fontWeight="bold">
                          Result Id: {result.resultId}
                        </Text>
                        <Text>Result Name: {result.resultName}</Text>
                        <Text>
                          Result Description: {result.resultDescription}
                        </Text>
                        <Text>Numerical Result: {result.numericalResult}</Text>
                        <Text>Unit: {result.unit}</Text>
                        <Text>
                          Descriptive Result: {result.descriptiveResult}
                        </Text>
                        <Text>Doctor Note: {result.doctorNote}</Text>
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
