import Hero from "../pages/Hero";
import { Outlet } from "react-router-dom"


export default function RootLayout() {
console.log('HeroLayout rendered');

  return (
    <Outlet />
  )
}
