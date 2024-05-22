import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";

import RootLayout from "./layouts/RootLayout";
import Dashboard, { tasksLoader } from "./pages/Dashboard";
import Visits from "./pages/Visits";
import HeroLayout from "./layouts/HeroLayout";
import Calendar from "./pages/Calendar";
import Forum, { threadsLoader } from "./pages/Forum";
import ThreadDetails from "./components/ThreadDetails";
import CreateThread from "./pages/thread/CreateThread";
import CreateVisit from "./pages/visit/CreateVisit";
import VisitDetails from "./components/VisitDetails";
import DietPlan from "./pages/DietPlan";
import LoginHero from "./auth/LoginHero";
import Register from "./auth/Register";

// router and routes
const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<HeroLayout />}>
      <Route index element={<LoginHero />} />
      <Route path="register" element={<Register />} />
      <Route path="app" element={<RootLayout />}>
        <Route path="calendar" element={<Calendar />} loader={tasksLoader} />
        <Route path="forum" element={<Forum />} loader={threadsLoader} />
        <Route path="forum/:threadId" element={<ThreadDetails />} />
        <Route path="forum/create" element={<CreateThread />} />
        <Route path="dashboard" element={<Dashboard />} loader={tasksLoader} />
        <Route path="dietplan" element={<DietPlan />} />
        <Route path="visits" element={<Visits />} />
        <Route path="visits/create" element={<CreateVisit />} />
        <Route path="visits/:visitId" element={<VisitDetails />} />
      </Route>
    </Route>
  )
);

function App() {
  return <RouterProvider router={router} />;
}

export default App;