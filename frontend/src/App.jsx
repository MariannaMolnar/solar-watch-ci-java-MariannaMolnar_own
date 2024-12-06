import { useState } from 'react'
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Layout from './features/main/components/organisms/Layout';
import Login from './features/auth/Login';
import Register from './features/auth/Register';
import SolarWatch from './features/solarwatch/SolarWatch';

function App() {

  return (
    <BrowserRouter
      future={{
        v7_startTransition: true,
        v7_relativeSplatPath: true,
      }}
    >
      <Routes>
        <Route
          path='/'
          element={<Layout />}
        >
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path='/solar-watch' element={< SolarWatch/>} />*
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
