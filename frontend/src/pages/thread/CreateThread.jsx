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

export default function CreateThread() {
  const authorId = sessionStorage.getItem("authorId");
  const threadAddedToast = useToast();

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
          // Add any additional headers if needed
        },
        body: JSON.stringify(threadData),
      });

      if (response.ok) {
        // Handle success, e.g., redirect to a different page
        console.log("Thread created successfully");
        // Add your redirect logic here
        threadAddedToast({
          title: "Thread successfully added!",
          status: "success",
          duration: 2000,
          isClosable: true,
        });

        setTimeout(() => {
          window.location.reload();
        }, 2000);
      } else {
        // Handle error, e.g., display an error message
        console.error("Failed to create thread");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <Box maxW="480px">
      <Heading mb="40px">New thread</Heading>
      <Form method="post" onSubmit={handleSubmit}>
        <FormControl isRequired mb="40px">
          <FormLabel>Category</FormLabel>
          <Select name="category" placeholder="Select category">
            <option>Diet</option>
            <option>Medical consultation</option>
            <option>Baby growing</option>
            <option>Pregnancy</option>
          </Select>
        </FormControl>
        <FormControl isRequired mb="40px">
          <FormLabel>Thread title:</FormLabel>
          <Input type="text" name="title" />
          <FormHelperText>Enter a descriptive thread title.</FormHelperText>
        </FormControl>

        <FormControl mb="40px">
          <FormLabel>Thread content:</FormLabel>
          <Textarea
            placeholder="Describe your problem or something you want to write about! :)"
            name="content"
          />
        </FormControl>

        {/* <Button type="submit">submit</Button> */}
        <Button type="submit" variant="solid" colorScheme="green" mb="30px">
          Create
        </Button>
      </Form>
    </Box>
  );
}
