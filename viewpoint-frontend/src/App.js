import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './routes/HomePage';
import Dashboards from './routes/Dashboards'
import ErrorPage from './routes/ErrorPage';
import './App.css';
import Navbar from './components/Navbar';
import { ThemeProvider } from '@mui/material/styles';
import customTheme from './CustomTheme';
import Dataset from './routes/Dataset';
import EditDataset from './components/dataset/EditDataset';

function App() {
  return (
    <ThemeProvider theme={customTheme}>
      <Navbar/>
      <Router>
        <Routes>
          <Route path='/' element={<HomePage/>}/>
          <Route path='/dashboard' element={<Dashboards/>}/>
          <Route path='/dataset' element={<Dataset/>}/>
          <Route path='/dataset/:id' element={<EditDataset/>}/>
          <Route path='/dataset/new' element={<EditDataset/>}/>
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
