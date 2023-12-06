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
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Forum() {
  const pesel = sessionStorage.getItem("pesel");
  const navigate = useNavigate();
  const [selectedCategory, setSelectedCategory] = useState("");
  const [allThreads, setAllThreads] = useState([]);
  const [myThreads, setMyThreads] = useState([]);
  const [authorId, setAuthorId] = useState(null);

  useEffect(() => {
    const fetchAuthorId = async () => {
      try {
        const res = await fetch(
          `http://localhost:8084/forum/forumUser/getForumUserIdByPesel/${pesel}`
        );
        const data = await res.json();
        setAuthorId(data);
        sessionStorage.setItem("authorId", data.toString());
        console.log("AuthorId: " + data);
        console.log(
          "session storage new authorId: " + sessionStorage.getItem("authorId")
        );
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      }
    };

    fetchAuthorId();
  }, [pesel]);

  useEffect(() => {
    if (authorId !== null) {
      fetchMyThreads();
    }
  }, [authorId]);

  useEffect(() => {
    if (!selectedCategory) {
      fetchAndRefreshThreads();
    } else {
      fetchAndRefreshThreads(selectedCategory);
    }
  }, [selectedCategory]);

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

  const fetchAndRefreshThreads = async (category) => {
    try {
      const url = category
        ? `http://localhost:8084/forum/thread/category/${category}`
        : "http://localhost:8084/forum/thread";

      const response = await fetch(url);
      const data = await response.json();

      setAllThreads(data);
    } catch (error) {
      console.error("Błąd podczas pobierania wątków:", error);
    }
  };

  const handleShowMore = (threadId) => {
    navigate(`/app/forum/${threadId}`);
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
                    <Heading size="md">AuthorId: {thread.authorId}</Heading>
                    <Heading size="md">Title: {thread.title}</Heading>
                    <Text py="2">Category: {thread.category}</Text>
                    <Text py="2">Content: {thread.content}</Text>
                  </CardBody>

                  <CardFooter>
                    <Button
                      variant="solid"
                      colorScheme="blue"
                      onClick={() => handleShowMore(thread.threadId)}
                    >
                      Show more
                    </Button>
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
                    <Heading size="md">AuthorId: {myThread.authorId}</Heading>
                    <Heading size="md">Title: {myThread.title}</Heading>
                    <Text py="2">Category: {myThread.category}</Text>
                    <Text py="2">Content: {myThread.content}</Text>
                  </CardBody>

                  <CardFooter>
                    <Button
                      variant="solid"
                      colorScheme="blue"
                      onClick={() => handleShowMore(myThread.threadId)}
                    >
                      Show more
                    </Button>
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
