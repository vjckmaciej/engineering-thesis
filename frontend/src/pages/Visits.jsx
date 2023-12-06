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
} from "@chakra-ui/react";
import { ViewIcon } from "@chakra-ui/icons";

export default function Visits() {
  const navigate = useNavigate();
  const pesel = sessionStorage.getItem("pesel");
  const isDoctor = sessionStorage.getItem("isDoctor");
  const [loading, setLoading] = useState(false);
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

  return (
    <Box>
      <Heading mb="40px" textAlign="center">
        Next visit
      </Heading>

      {nearestPlannedVisit ? (
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
                  Visit status: {nearestPlannedVisit.visitStatus}
                </Heading>
                <Heading as="h2" size="sm">
                  Visit date:{" "}
                  {format(
                    new Date(nearestPlannedVisit.visitDate),
                    "yyyy-MM-dd HH:mm"
                  )}
                </Heading>
              </Box>
            </Flex>
          </CardHeader>
          <CardBody color="gray.500" textAlign="center">
            <Text>
              Week of pregnancy: {nearestPlannedVisit.weekOfPregnancy}
            </Text>
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
                Show more
              </Button>
            </HStack>
          </CardFooter>
        </Card>
      ) : (
        <Text>Loading next planned visit...</Text>
      )}

      <Heading mb="40px" mt="100px" textAlign="center">
        All visits
      </Heading>
      {isDoctor === "false" && (
        <>
          <Button bg="cyan" onClick={() => analyzeVisitsByOpenAI()}>
            Click to analyze by OpenAI
          </Button>
          <Text>Analysis result: {analysisResult}</Text>
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
                        Visit status: {myvisit.visitStatus}
                      </Heading>
                      <Heading as="h2" size="sm">
                        Visit date:{" "}
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
                  <Text>Week of pregnancy: {myvisit.weekOfPregnancy}</Text>
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
                      Show more
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
