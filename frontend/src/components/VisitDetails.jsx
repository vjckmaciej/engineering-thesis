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
} from "@chakra-ui/react";
import { useParams } from "react-router-dom";

export default function VisitDetails() {
  const isDoctor = sessionStorage.getItem("isDoctor");
  const { visitId } = useParams();
  const [visitDetails, setVisitDetails] = useState(null);
  const [medicalExaminations, setMedicalExaminations] = useState(null);
  const [resultsMap, setResultsMap] = useState({});
  const [analysisResult, setAnalysisResult] = useState("");

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
    setResultsMap((prevResultsMap) => ({
      ...prevResultsMap,
      [medicalExaminationId]: resultsData,
    }));
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

  return (
    <Box>
      {visitDetails ? (
        <>
          <Heading mb="4">VisitId: {visitDetails.visitId}</Heading>
          <Card
            key={visitDetails.visitId}
            direction={{ base: "column", sm: "row" }}
            overflow="hidden"
            variant="outline"
          >
            <Stack>
              <CardBody>
                <Heading size="md">
                  VisitStatus: {visitDetails.visitStatus}
                </Heading>
                <Text py="2">Visit date: {visitDetails.visitDate}</Text>
                <Text py="2">
                  Week of pregnancy: {visitDetails.weekOfPregnancy}
                </Text>
                <Text py="3">
                  Doctor recommendations: {visitDetails.doctorRecommendations}
                </Text>
              </CardBody>
            </Stack>
          </Card>
        </>
      ) : (
        <Text>Loading...</Text>
      )}

      <Button bg="cyan" onClick={() => analyzeVisitByOpenAI()}>
        Click to analyze this Visit by OpenAI
      </Button>
      <Text>Analysis result: {analysisResult}</Text>

      <Heading mb="4" mt="40px">
        All medical examinations
      </Heading>

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
                  Load Results
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
