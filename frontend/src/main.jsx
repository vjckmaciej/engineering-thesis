import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

import { ChakraProvider } from "@chakra-ui/react";
import theme from "./themes/theme";
import "@fontsource/roboto";

// const config = {
//   initialColorMode: 'dark',
//   useSystemColorMode: true,
// }

// 3. extend the theme
// const theme = extendTheme({ config })

ReactDOM.createRoot(document.getElementById("root")).render(
  <ChakraProvider theme={theme}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </ChakraProvider>
);
