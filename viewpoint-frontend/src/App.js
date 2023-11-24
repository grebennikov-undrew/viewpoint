import React, {useContext, useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MuiAlert from '@mui/material/Alert';
import { AlertProvider } from './components/AlertContext';
import HomePage from './routes/HomePage';
import Dashboards from './routes/Dashboards'
import ErrorPage from './routes/ErrorPage';
import './App.css';
import Navbar from './components/Navbar';
import { ThemeProvider } from '@mui/material/styles';
import customTheme from './CustomTheme';
import { ViewPointTheme } from './theme/ViewPointTheme';
import Dataset from './routes/Dataset';
import EditDataset from './components/dataset/EditDataset';
import LoginForm from './routes/LoginForm';
import Chart from './routes/Charts';
import EditChart from './components/chart/EditChart';
import Dashboard from './components/dashboard/Dashboard';
import Sources from './routes/Sources';
import Users from './routes/Users';

function App() {
    return (
        <ThemeProvider theme={ViewPointTheme}>
          <AlertProvider>
            <Router>
            <Navbar/>
                <Routes>
                    <Route exact path='/' element={<HomePage/>}/>
                    <Route path='/dashboard' element={<Dashboards/>}/>
                    <Route path='/dashboard/:id' element={<Dashboard/>}/>
                    <Route path='/dashboard/new' element={<Dashboard/>}/>
                    <Route path='/dataset' element={<Dataset/>}/>
                    <Route path='/dataset/:id' element={<EditDataset/>}/>
                    <Route path='/dataset/new' element={<EditDataset/>}/>
                    <Route path='/chart' element={<Chart/>}/>
                    <Route path='/chart/:id' element={<EditChart/>}/>
                    <Route path='/chart/new' element={<EditChart/>}/>
                    <Route path='/source' element={<Sources/>}/>
                    <Route path='/user' element={<Users/>}/>
                    <Route exact path='/login' element={<LoginForm/>}/>
                </Routes>
            </Router>
          </AlertProvider>
        </ThemeProvider>
    );
}

export default App;
