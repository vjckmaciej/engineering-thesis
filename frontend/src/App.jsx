import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  RouterProvider,
} from "react-router-dom";

// layouts and pages
import RootLayout from "./layouts/RootLayout";
import Dashboard, { tasksLoader } from "./pages/Dashboard";
// import Create, { createAction } from './pages/Create'
import Profile from "./pages/Profile";
import Hero from "./pages/Hero";
import Visits from "./pages/Visits";
import HeroLayout from "./layouts/HeroLayout";
import Calendar from "./pages/Calendar";
import Forum, { threadsLoader } from "./pages/Forum";
import ThreadDetails from "./components/ThreadDetails";
import CreateThread, { createAction } from "./pages/thread/CreateThread";
import VisitDetails from "./components/VisitDetails";
import DietPlan from "./pages/DietPlan";

// router and routes
const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<HeroLayout />}>
      {/* <Route index element={<Dashboard />} loader={tasksLoader} /> */}
      <Route index element={<Hero />} />
      <Route path="app" element={<RootLayout />}>
        <Route path="calendar" element={<Calendar />} loader={tasksLoader} />
        <Route path="forum" element={<Forum />} loader={threadsLoader} />
        <Route path="forum/:threadId" element={<ThreadDetails />} />
        <Route
          path="forum/create"
          element={<CreateThread />}
          action={createAction}
        />
        <Route path="dashboard" element={<Dashboard />} loader={tasksLoader} />
        {/* <Route path="create" element={<Create />} action={createAction} /> */}
        <Route path="dietplan" element={<DietPlan />} />
        <Route path="profile" element={<Profile />} />
        <Route path="visits" element={<Visits />} />
        <Route path="visits/:visitId" element={<VisitDetails />} />
      </Route>
      {/* <Route path="start" element={<Hero />} /> */}
    </Route>
  )
);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
