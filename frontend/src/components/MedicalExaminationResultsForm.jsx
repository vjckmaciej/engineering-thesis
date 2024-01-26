import React, { useState, useEffect } from "react";
import {
  Box,
  Text,
  Heading,
  Button,
  Card,
  Stack,
  CardBody,
} from "@chakra-ui/react";
import { useParams } from "react-router-dom";
import AddMedicalExaminationResultsForm from "./AddMedicalExaminationResultsForm";

export default function VisitDetails() {
  const { visitId } = useParams();
  const [visitDetails, setVisitDetails] = useState(null);
  const [medicalExaminations, setMedicalExaminations] = useState(null);
  const [showAddResultsForm, setShowAddResultsForm] = useState(false);
  const [selectedExaminationId, setSelectedExaminationId] = useState(null);

  useEffect(() => {
    setVisitDetails({ visitId, visitStatus: "SCHEDULED" });
    setMedicalExaminations([
      { medicalExaminationId: 1, medicalExaminationName: "Badanie Krwi" },
    ]);
  }, [visitId]);

  const handleAddResultsClick = (medicalExaminationId) => {
    setSelectedExaminationId(medicalExaminationId);
    setShowAddResultsForm(true);
  };

  const handleResultsSubmit = async (resultsData) => {
    console.log("Wysłanie danych:", resultsData);
    setShowAddResultsForm(false);
  };

  return (
    <Box>
      <Heading mb="4">
        ID Wizyty: {visitDetails && visitDetails.visitId}
      </Heading>
      {/* Renderowanie szczegółów wizyty i listy badań */}
      {medicalExaminations &&
        medicalExaminations.map((medicalExamination) => (
          <Card key={medicalExamination.medicalExaminationId}>
            <CardBody>
              <Heading size="md">
                {medicalExamination.medicalExaminationName}
              </Heading>
              <Button
                colorScheme="blue"
                onClick={() =>
                  handleAddResultsClick(medicalExamination.medicalExaminationId)
                }
              >
                Dodaj wyniki badań
              </Button>
            </CardBody>
          </Card>
        ))}

      {showAddResultsForm && (
        <AddMedicalExaminationResultsForm
          medicalExaminationId={selectedExaminationId}
          onResultsSubmit={handleResultsSubmit}
        />
      )}
    </Box>
  );
}
