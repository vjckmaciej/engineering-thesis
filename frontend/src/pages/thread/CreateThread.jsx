import {
  Box,
  FormControl,
  FormLabel,
  FormHelperText,
  Input,
  Textarea,
  Button,
  Checkbox,
  Select,
  Heading,
  useToast,
} from "@chakra-ui/react";
import { Form } from "react-router-dom";
import { useState } from "react";

export default function CreateThread() {
  const authorId = sessionStorage.getItem("authorId");
  const threadAddedToast = useToast();
  const [charCounter, setCharCounter] = useState(0);

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const threadData = {
      category: formData.get("category"),
      title: formData.get("title"),
      content: formData.get("content"),
      authorId,
    };

    console.log(threadData);

    try {
      const response = await fetch("http://localhost:8084/forum/thread", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(threadData),
      });

      if (response.ok) {
        console.log("Thread created successfully");
        threadAddedToast({
          title: "Wątek pomyślnie dodany!",
          status: "success",
          duration: 2000,
          isClosable: true,
        });

        setTimeout(() => {
          window.location.reload();
        }, 2000);
      } else {
        console.error("Failed to create thread");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <Box maxW="480px">
      <Heading mb="40px">Nowy wątek</Heading>
      <Form method="post" onSubmit={handleSubmit}>
        <FormControl isRequired mb="40px">
          <FormLabel>Kategoria</FormLabel>
          <Select name="category" placeholder="Wybierz kategorię">
            <option>Dieta</option>
            <option>Konsultacje medyczne</option>
            <option>Dorastanie dziecka</option>
            <option>Ciąża</option>
          </Select>
        </FormControl>
        <FormControl isRequired mb="40px">
          <FormLabel>Tytuł wątku:</FormLabel>
          <Input type="text" name="title" />
          <FormHelperText>
            Wprowadź opisowy tytuł wątku. Musi zawierać co najmniej 3 znaków i
            maksymalnie 100 znaków.
          </FormHelperText>
        </FormControl>

        <FormControl mb="40px">
          <FormLabel>Treść wątku:</FormLabel>
          <Textarea
            placeholder="Opisz swój problem lub coś o czym chcesz napisać! Pamiętaj, że musi zawierać co najmniej 3 znaki i maksymalnie 1000 znaków! :)"
            name="content"
            onChange={(e) => setCharCounter(e.target.value.length)}
          />
        </FormControl>

        {/* <Button type="submit">submit</Button> */}
        {charCounter <= 1000 && charCounter >= 3 ? (
          <>
            <Button type="submit" variant="solid" colorScheme="green" mb="30px">
              Dodaj wątek
            </Button>
          </>
        ) : (
          <>
            <Button
              isDisabled="true"
              type="submit"
              variant="solid"
              colorScheme="green"
              mb="30px"
            >
              Dodaj wątek
            </Button>
          </>
        )}
      </Form>
    </Box>
  );
}
