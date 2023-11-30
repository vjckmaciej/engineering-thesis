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
  Button
} from "@chakra-ui/react"
import { useLoaderData } from "react-router-dom"

export default function Dashboard() {
  const calendarWeeks = useLoaderData()

  return (
    <Box>
      <Heading mb="40px">Dashboard</Heading>
      <SimpleGrid spacing={10} minChildWidth={300}>
        {calendarWeeks && calendarWeeks.map(calendarWeek => (
          <Card key={calendarWeek.calendarWeekId} borderTop="8px" borderColor="purple.400" bg="white" 
              _hover={{
              transform: 'translateY(-2px)',
              boxShadow: 'lg',
            }}
          >

            <CardHeader color="gray.700" >
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
                <Button variant="ghost" leftIcon={<ViewIcon />}>Watch</Button>
                <Button variant="ghost" leftIcon={<EditIcon />}>Comment</Button>
              </HStack>
            </CardFooter>

          </Card>
        ))}
      </SimpleGrid>
    </Box>
  )
}

export const tasksLoader = async () => {
  const res = await fetch('http://localhost:8082/visit/calendarWeek')

  return res.json()
}


