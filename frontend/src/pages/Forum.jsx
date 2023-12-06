import { AddIcon } from "@chakra-ui/icons";
import {
  Box,
  Card,
  CardBody,
  Stack,
  CardFooter,
  Text,
  Heading,
  Button,
  Select,
  Tabs,
  TabList,
  Tab,
  TabPanels,
  TabPanel,
  HStack,
  useToast,
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { format } from "date-fns";

export default function Forum() {
  const pesel = sessionStorage.getItem("pesel");
  const navigate = useNavigate();
  const [selectedCategory, setSelectedCategory] = useState("");
  const [allThreads, setAllThreads] = useState([]);
  const [myThreads, setMyThreads] = useState([]);
  const [authorId, setAuthorId] = useState(null);
  const threadDeletedToast = useToast();

  useEffect(() => {
    const fetchAuthorId = async () => {
      try {
        const res = await fetch(
          `http://localhost:8084/forum/forumUser/getForumUserIdByPesel/${pesel}`
        );
        const data = await res.json();
        setAuthorId(data);
        sessionStorage.setItem("authorId", data.toString());
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      }
    };

    fetchAuthorId();
  }, [pesel]);

  useEffect(() => {
    const fetchAllThreads = async () => {
      try {
        const url = selectedCategory
          ? `http://localhost:8084/forum/thread/category/${selectedCategory}`
          : "http://localhost:8084/forum/thread";

        const response = await fetch(url);
        const data = await response.json();

        // Fetch and set author usernames for all threads
        const threadsWithAuthors = await Promise.all(
          data.map(async (thread) => {
            const authorUsername = await fetchAuthorUsername(thread.authorId);
            return { ...thread, authorUsername };
          })
        );

        setAllThreads(threadsWithAuthors);
      } catch (error) {
        console.error("Błąd podczas pobierania wątków:", error);
      }
    };

    fetchAllThreads();
  }, [selectedCategory, authorId]);

  const fetchAuthorUsername = async (authorId) => {
    try {
      const res = await fetch(
        `http://localhost:8084/forum/forumUser/getForumUserUsernameByAuthorId/${authorId}`
      );
      const data = await res.text();
      return data;
    } catch (error) {
      console.error("Błąd pobierania nazwy użytkownika:", error);
      return "Unknown";
    }
  };

  useEffect(() => {
    if (authorId !== null) {
      fetchMyThreads();
    }
  }, [authorId]);

  const fetchMyThreads = async () => {
    try {
      const response = await fetch(
        `http://localhost:8084/forum/thread/getAllThreadsByAuthorId/${authorId}`
      );

      const data = await response.json();
      setMyThreads(data);
    } catch (error) {
      console.error("Błąd podczas pobierania wątków użytkownika:", error);
    }
  };

  const handleShowMore = (threadId) => {
    navigate(`/app/forum/${threadId}`);
  };

  const handleDelete = async (threadId) => {
    try {
      const response = await fetch(
        `http://localhost:8084/forum/thread/${threadId}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log(`Thread with threadId ${threadId} deleted successfully`);

        threadDeletedToast({
          title: "Thread successfully deleted!",
          status: "success",
          duration: 2000,
          isClosable: true,
        });

        // Aktualizuj listę wątków użytkownika po usunięciu wątku
        fetchMyThreads();

        setTimeout(() => {
          window.location.reload();
        }, 1000);
      } else {
        console.error(`Failed to delete thread with threadId ${threadId}`);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleChangeCategory = (event) => {
    const newCategory = event.target.value;
    setSelectedCategory(newCategory);
  };

  const handleCreateNewThread = () => {
    navigate("/app/forum/create");
  };

  return (
    <Tabs variant="enclosed">
      <TabList>
        <Tab _selected={{ color: "white", bg: "pink.400" }}>All threads</Tab>
        <Tab _selected={{ color: "white", bg: "pink.400" }}>My threads</Tab>
      </TabList>

      <TabPanels>
        <TabPanel>
          <Box>
            <Heading mb="40px">All threads</Heading>
            <Heading size="sm" mb="10px">
              Choose category:
            </Heading>
            <Select
              placeholder="Select thread category"
              onChange={handleChangeCategory}
              value={selectedCategory}
              mb="30px"
            >
              <option value="">All</option>
              <option value="Diet">Diet</option>
              <option value="Medical consultation">Medical consultation</option>
              <option value="Baby growing">Baby growing</option>
              <option value="Pregnancy">Pregnancy</option>
            </Select>

            <Button
              variant="solid"
              colorScheme="green"
              leftIcon={<AddIcon />}
              mb="30px"
              onClick={handleCreateNewThread}
            >
              Create new thread
            </Button>

            {allThreads.map((thread) => (
              <Card
                key={thread.threadId}
                direction={{ base: "column", sm: "row" }}
                overflow="hidden"
                variant="outline"
                mb="10px"
              >
                <Stack>
                  <CardBody>
                    <Heading size="md">Author: {thread.authorUsername}</Heading>
                    <Heading size="md">Title: {thread.title}</Heading>
                    <Text py="2">Category: {thread.category}</Text>
                    <Text py="2">Content: {thread.content}</Text>
                    Created:{" "}
                    {format(
                      new Date(thread.creationDate),
                      "yyyy-MM-dd HH:mm:ss"
                    )}
                  </CardBody>
                  <CardFooter>
                    <HStack spacing="20px">
                      <Button
                        variant="solid"
                        colorScheme="blue"
                        onClick={() => handleShowMore(thread.threadId)}
                      >
                        Show more
                      </Button>
                      {thread.authorId === authorId && (
                        <Button
                          variant="solid"
                          colorScheme="red"
                          onClick={() => handleDelete(thread.threadId)}
                        >
                          Delete
                        </Button>
                      )}
                    </HStack>
                  </CardFooter>
                </Stack>
              </Card>
            ))}
          </Box>
        </TabPanel>
        <TabPanel>
          <Box>
            <Heading mb="40px">My threads</Heading>
            {myThreads.map((myThread) => (
              <Card
                key={myThread.threadId}
                direction={{ base: "column", sm: "row" }}
                overflow="hidden"
                variant="outline"
                mb="10px"
              >
                <Stack>
                  <CardBody>
                    <Heading size="md">Title: {myThread.title}</Heading>
                    <Text py="2">Category: {myThread.category}</Text>
                    <Text py="2">Content: {myThread.content}</Text>
                    <Text py="2">Content: {myThread.content}</Text>
                  </CardBody>

                  <CardFooter>
                    <HStack spacing="20px">
                      <Button
                        variant="solid"
                        colorScheme="blue"
                        onClick={() => handleShowMore(myThread.threadId)}
                      >
                        Show more
                      </Button>
                      <Button
                        variant="solid"
                        colorScheme="red"
                        onClick={() => handleDelete(myThread.threadId)}
                      >
                        Delete
                      </Button>
                    </HStack>
                  </CardFooter>
                </Stack>
              </Card>
            ))}
          </Box>
        </TabPanel>
      </TabPanels>
    </Tabs>
  );
}

export const threadsLoader = async () => {
  const res = await fetch("http://localhost:8084/forum/thread");
  return res.json();
};
