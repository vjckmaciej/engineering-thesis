import React, { useState, useEffect } from "react";
import {
  Box,
  Text,
  Heading,
  Button,
  Card,
  Stack,
  CardBody,
  FormControl,
  FormLabel,
  Textarea,
  useToast,
  HStack,
  Flex,
  Spacer,
  CardFooter,
} from "@chakra-ui/react";
import { useParams } from "react-router-dom";
import { format } from "date-fns";
import { AddIcon } from "@chakra-ui/icons";
import { Form } from "react-router-dom";

export default function ThreadDetails() {
  const authorId = parseInt(sessionStorage.getItem("authorId"), 10);
  const { threadId } = useParams();
  const [threadDetails, setThreadDetails] = useState(null);
  const [allComments, setAllComments] = useState(null);
  const commentAddedToast = useToast();
  const commentDeletedToast = useToast();
  const [charCounter, setCharCounter] = useState(0);

  const fetchComments = async () => {
    try {
      const res = await fetch(
        `http://localhost:8084/forum/comment/${threadId}`
      );
      const data = await res.json();

      const commentsWithAuthors = await Promise.all(
        data.map(async (comment) => {
          const authorUsername = await fetchAuthorUsername(comment.authorId);
          return { ...comment, authorUsername };
        })
      );

      setAllComments(commentsWithAuthors);
    } catch (error) {
      console.error("Błąd pobierania danych:", error);
    }
  };

  useEffect(() => {
    const fetchThreadDetails = async () => {
      try {
        const res = await fetch(
          `http://localhost:8084/forum/thread/${threadId}`
        );
        const data = await res.json();
        setThreadDetails(data);
      } catch (error) {
        console.error("Błąd pobierania danych:", error);
      }
    };

    fetchThreadDetails();
    fetchComments();
  }, [threadId]);

  const fetchAuthorUsername = async (authorId) => {
    try {
      const res = await fetch(
        `http://localhost:8084/forum/forumUser/getForumUserUsernameByAuthorId/${authorId}`
      );
      const data = await res.text();
      return data;
    } catch (error) {
      console.error("Błąd pobierania nazwy użytkownika:", error);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const commentData = {
      content: formData.get("content"),
      threadId: parseInt(threadId, 10),
      authorId,
    };

    try {
      const response = await fetch("http://localhost:8084/forum/comment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(commentData),
      });

      if (response.ok) {
        console.log("Comment added successfully");

        commentAddedToast({
          title: "Comment successfully added!",
          status: "success",
          duration: 2000,
          isClosable: true,
        });

        setTimeout(() => {
          window.location.reload();
        }, 2000);
      } else {
        console.error("Failed to add comment");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleDelete = async (commentId) => {
    try {
      console.log("Deleting comment with commentId:", commentId);
      console.log("authorId:", authorId);

      const response = await fetch(
        `http://localhost:8084/forum/comment/${commentId}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log(`Comment with commentId ${commentId} deleted successfully`);

        commentDeletedToast({
          title: "Comment successfully deleted!",
          status: "success",
          duration: 2000,
          isClosable: true,
        });
        fetchComments();

        setTimeout(() => {
          window.location.reload();
        }, 1000);
      } else {
        console.error(`Failed to delete comment with commentId ${commentId}`);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <Box>
      {threadDetails ? (
        <>
          <Heading mb="4">Title: {threadDetails.title}</Heading>
          <Card
            key={threadDetails.threadId}
            direction={{ base: "column", sm: "row" }}
            overflow="hidden"
            variant="outline"
          >
            <Stack>
              <CardBody>
                <Heading size="md">Title: {threadDetails.title}</Heading>
                <Text py="2">Category: {threadDetails.category}</Text>
                <Text py="2">Content: {threadDetails.content}</Text>
                <Text py="3">
                  Created:{" "}
                  {format(
                    new Date(threadDetails.creationDate),
                    "yyyy-MM-dd HH:mm:ss"
                  )}
                </Text>
              </CardBody>
            </Stack>
          </Card>
        </>
      ) : (
        <Text>Loading...</Text>
      )}
      <Heading mb="4" mt="40px">
        All comments
      </Heading>
      {allComments &&
        allComments.map((comment) => (
          <Card
            key={comment.commentId}
            direction={{ base: "column", sm: "row" }}
            overflow="hidden"
            variant="outline"
            mb="2"
          >
            <Stack>
              <CardBody>
                <Heading size="sm" mb="5px">
                  Author: {comment.authorUsername}
                </Heading>
                <Text py="3">{comment.content}</Text>
                <Text py="3">
                  Added:{" "}
                  {format(new Date(comment.creationDate), "yyyy-MM-dd HH:mm")}
                </Text>
              </CardBody>
              <CardFooter>
                {comment.authorId === authorId && (
                  <Button
                    variant="solid"
                    colorScheme="red"
                    onClick={() => handleDelete(comment.commentId)}
                  >
                    Delete your comment
                  </Button>
                )}
              </CardFooter>
            </Stack>
          </Card>
        ))}

      <Form method="post" onSubmit={handleSubmit}>
        <FormControl mb="10px">
          <FormLabel mt="40px">New comment:</FormLabel>
          <Textarea
            placeholder="Write your comment here... It must contain at least 3 characters and at most 1000 characters! Remember to be kind to others! :)"
            name="content"
            onChange={(e) => setCharCounter(e.target.value.length)}
          />
        </FormControl>

        <Flex p="10px" mb="10px" alignItems="center">
          {charCounter <= 1000 && charCounter >= 3 ? (
            <>
              <Button
                type="submit"
                variant="solid"
                colorScheme="blue"
                leftIcon={<AddIcon />}
              >
                Add comment
              </Button>
              <Spacer />
              <p>Characters counter: {charCounter}</p>
            </>
          ) : (
            <>
              <Button
                type="submit"
                variant="solid"
                colorScheme="blue"
                isDisabled="true"
                leftIcon={<AddIcon />}
              >
                Add comment
              </Button>
              <Spacer />
              <p>Characters counter: {charCounter}</p>
            </>
          )}
        </Flex>
      </Form>
    </Box>
  );
}
