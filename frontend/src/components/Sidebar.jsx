import { List, ListItem, ListIcon, Spacer } from "@chakra-ui/react";
import {
  CalendarIcon,
  EditIcon,
  AtSignIcon,
  ChatIcon,
  CopyIcon,
  AddIcon,
} from "@chakra-ui/icons";
import { NavLink } from "react-router-dom";

export default function Sidebar() {
  const isDoctor = sessionStorage.getItem("isDoctor");

  return (
    <List color="white" fontSize="1.2em" spacing={6}>
      <ListItem>
        <NavLink to="profile">
          <ListIcon as={AtSignIcon} color="white" />
          Profile
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="visits">
          <ListIcon as={CopyIcon} color="white" />
          Visits
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="calendar">
          <ListIcon as={CalendarIcon} color="white" />
          Calendar
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="forum">
          <ListIcon as={ChatIcon} color="white" />
          Forum
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="dietplan">
          <ListIcon as={EditIcon} color="white" />
          Diet plan
        </NavLink>
      </ListItem>
      {isDoctor === "true" && (
        <ListItem>
          <NavLink to="visits/create">
            <ListIcon as={AddIcon} color="white" />
            Add new visit
          </NavLink>
        </ListItem>
      )}

      <ListItem>
        <NavLink to="dashboard">
          <ListIcon as={CalendarIcon} color="white" />
          Dashboard
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="create">
          <ListIcon as={EditIcon} color="white" />
          New Task
        </NavLink>
      </ListItem>
    </List>
  );
}
