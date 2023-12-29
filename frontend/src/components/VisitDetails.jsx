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
import UrineTestForm from "../forms/UrineTestForm";
import FirstTrimesterUSGTestForm from "../forms/FirstTrimesterUSGTestForm";
import SecondTrimesterUSGTestForm from "../forms/SecondTrimesterUSGTestForm";
import ThirdTrimesterUSGTestForm from "../forms/ThirdTrimesterUSGTestForm";
import BloodPressureTestForm from "../forms/BloodPressureTestForm";
import GlucoseToleranceTestForm from "../forms/GlucoseToleranceTestForm";
import ThyroidTestForm from "../forms/ThyroidTestForm";
import VitaminsMineralsTestForm from "../forms/VitaminsMineralsTestForm";
import FetalCardiographyTestForm from "../forms/FetalCardiographyTestForm";
import GynecologicalExaminationTestForm from "../forms/GynecologicalExaminationTestForm";

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

    const medicalExaminationData = {
      medicalExaminationName: selectedExaminationType,
      visitId: visitIdForm,
    };

    try {
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

      const examinationResponse = await response.json();
      const medicalExaminationId = examinationResponse.id;

      const tests = [
        //Badanie krwi
        "hemoglobin",
        "whiteBloodCellCount",
        "plateletCount",
        "fastingGlucose",
        "ironLevel",
        "infectionTest",
        "bloodGroup",

        //Badanie moczu
        "proteinLevel",
        "sugarLevel",
        "ketoneBodies",
        "presenceOfBacteria",

        //USG pierwszego trymestru
        "nuchalTransluchency",
        "anatomicalStructuresOfTheFetus",

        //USG drugiego trymestru
        "fetalSize",
        "fetalHeadCircumference",
        "fetalFemurLength",
        "assessmentOfFetalOrgans",

        //USG trzeciego trymestru
        "fetalPosition",
        "amnioticFluid",
        "assessmentOfBloodFlowInTheUmbilicalCord",

        //Badanie ciśnienia krwi
        "systolicBloodPressure",
        "diastolicBloodPressure",

        //Badanie tolerancji glukozy
        "fastingGlucoseLevel",
        "glucoseLevelAfter2Hours",

        //Badanie tarczycy
        "tshLevel",
        "ft4Level",

        //Badanie witamin i minerałów
        "vitaminDLevel",
        "calciumLevel",

        // Ocena rytmu serca płodu
        "assessmentOfFetalHeartRhythm",

        //Badanie ginekologiczne
        "assessmentOfTheCervix",
        "ultrasoundExaminationOfReproductiveOrgans",
      ];

      for (const test of tests) {
        const numericalResult = formData[`${test}NumericalResult`];
        const descriptiveResult = formData[`${test}DescriptiveResult`];

        if (numericalResult || descriptiveResult) {
          const resultData = {
            resultName: test,
            numericalResult: numericalResult,
            unit: formData[`${test}Unit`],
            resultDescription: formData[`${test}Description`],
            descriptiveResult: descriptiveResult,
            doctorNote: formData[`${test}DoctorNote`],
            medicalExaminationId: medicalExaminationId,
          };

          response = await fetch("http://localhost:8082/visit/result", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(resultData),
          });

          if (!response.ok) {
            console.error(`Błąd podczas dodawania wyniku dla badania: ${test}`);
          }
        }
      }

      console.log("Wszystkie wypełnione wyniki badań dodane pomyślnie.");
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
          Kliknij tutaj, aby OpenAI przeanalizowało wizytę
        </Button>
      )}
      <Text>Wynik analizy: {analysisResult}</Text>

      <Heading mb="4" mt="40px">
        Wszystkie badania
      </Heading>
      <Box maxW="480px" mb="30px">
        <FormControl>
          <FormLabel>Wybierz zestaw badań do dodania:</FormLabel>
          <Select
            name="medicalExaminationName"
            placeholder="Wybierz kategorię"
            onChange={handleExaminationTypeChange}
          >
            <option value="Badanie krwi">Badanie krwi</option>
            <option value="Badanie moczu">Badanie moczu</option>
            <option value="USG pierwszego trymestru">
              USG pierwszego trymestru
            </option>
            <option value="USG drugiego trymestru">
              USG drugiego trymestru
            </option>
            <option value="USG trzeciego trymestru">
              USG trzeciego trymestru
            </option>
            <option value="Badanie ciśnienia krwi">
              Badanie ciśnienia krwi
            </option>
            <option value="Badanie tolerancji glukozy">
              Badanie tolerancji glukozy
            </option>
            <option value="Badanie tarczycy">Badanie tarczycy</option>
            <option value="Badanie poziomu witamin i minerałów">
              Badanie poziomu witamin i minerałów
            </option>
            <option value="Kardiografia płodu">Kardiografia płodu</option>
            <option value="Badanie ginekologiczne">
              Badanie ginekologiczne
            </option>
          </Select>
        </FormControl>
      </Box>
      {selectedExaminationType === "Badanie krwi" && (
        <BloodTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Badanie moczu" && (
        <UrineTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "USG pierwszego trymestru" && (
        <FirstTrimesterUSGTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "USG drugiego trymestru" && (
        <SecondTrimesterUSGTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "USG trzeciego trymestru" && (
        <ThirdTrimesterUSGTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Badanie ciśnienia krwi" && (
        <BloodPressureTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Badanie tolerancji glukozy" && (
        <GlucoseToleranceTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Badanie tarczycy" && (
        <ThyroidTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Badanie poziomu witamin i minerałów" && (
        <VitaminsMineralsTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Kardiografia płodu" && (
        <FetalCardiographyTestForm onSubmit={handleSubmitMedicalExamination} />
      )}
      {selectedExaminationType === "Badanie ginekologiczne" && (
        <GynecologicalExaminationTestForm
          onSubmit={handleSubmitMedicalExamination}
        />
      )}

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

const resultNameMapping = {
  //Badanie krwi
  hemoglobin: "Hemoglobina",
  whiteBloodCellCount: "Liczba białych krwinek",
  plateletCount: "Liczba płytek krwi",
  fastingGlucose: "Poziom glukozy na czczo",
  ironLevel: "Poziom żelaza",
  infectionTest: "Test na obecność infekcji",
  bloodGroup: "Grupa krwi",
  rhFactor: "Czynnik Rh",

  //Badanie moczu
  proteinLevel: "Poziom białka",
  sugarLevel: "Poziom cukru",
  ketoneBodies: "Ciała ketonowe",
  presenceOfBacteria: "Obecność bakterii",

  //USG pierwszego trymestru
  nuchalTransluchency: "Pomiar przezierności karkowej (NT)",
  anatomicalStructuresOfTheFetus: "Ocena struktur anatomicznych płodu",

  //USG drugiego trymestru
  fetalSize: "Wielkość płodu",
  fetalHeadCircumference: "Pomiar obwodu główki płodu",
  fetalFemurLength: "Pomiar długości kości udowej płodu",
  assessmentOfFetalOrgans: "Ocena narządów płodu",

  //USG trzeciego trymestru
  fetalPosition: "Pozycja płodu",
  amnioticFluid: "Pomiar ilości płynu owodniowego",
  assessmentOfBloodFlowInTheUmbilicalCord: "Ocena przepływu krwi w pępowinie",

  //Badanie ciśnienia krwi
  systolicBloodPressure: "Ciśnienie skurczowe",
  diastolicBloodPressure: "Ciśnienie rozkurczowe",

  //Badanie tolerancji glukozy
  fastingGlucoseLevel: "Poziom glukozy na czczo",
  glucoseLevelAfter2Hours: "Poziom glukozy po 2 godzinach",

  //Badanie tarczycy
  tshLevel: "Poziom TSH",
  ft4Level: "Poziom FT4",

  //Badanie poziomu witamin i minerałów
  vitaminDLevel: "Poziom witaminy D",
  calciumLevel: "Poziom wapnia",

  //Kardiografia płodu
  assessmentOfFetalHeartRhythm: "Ocena rytmu serca płodu",

  //Badanie ginekologiczne
  assessmentOfTheCervix: "Ocena szyjki macicy",
  ultrasoundExaminationOfReproductiveOrgans: "Badanie USG narządów rodnych",
};
