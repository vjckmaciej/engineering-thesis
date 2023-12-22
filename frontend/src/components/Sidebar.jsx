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
          Profil
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="visits">
          <ListIcon as={CopyIcon} color="white" />
          Wizyty
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="calendar">
          <ListIcon as={CalendarIcon} color="white" />
          Kalendarz
        </NavLink>
      </ListItem>
      <ListItem>
        <NavLink to="forum">
          <ListIcon as={ChatIcon} color="white" />
          Forum
        </NavLink>
      </ListItem>
      {isDoctor === "false" && (
        <ListItem>
          <NavLink to="dietplan">
            <ListIcon as={EditIcon} color="white" />
            Plan dietetyczny
          </NavLink>
        </ListItem>
      )}
      {isDoctor === "true" && (
        <ListItem>
          <NavLink to="visits/create">
            <ListIcon as={AddIcon} color="white" />
            Dodaj nową wizytę
          </NavLink>
        </ListItem>
      )}
    </List>
  );
}
