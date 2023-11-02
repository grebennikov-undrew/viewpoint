import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom'
import { TextField, Button, Grid, Typography, Container } from '@mui/material';
import AuthenticationService from "../service/AuthenticationService"
import { margin } from '@mui/system';

const API_URL = 'http://localhost:8080'

const LoginForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const fetchData = async () => {
        try {
            const response = await AuthenticationService.executeBasicAuthenticationService(formData.username, formData.password);
            if (response.status === 200) navigate('/dataset');
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }
    fetchData();
  };

  return (
    <Container maxWidth="sm">
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2} style={{marginTop: 10}}>
          <Grid item xs={12}>
            <Typography variant="h2" align="center">
              Sign in
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              variant="outlined"
              label="Username"
              name="username"
              value={formData.username}
              onChange={handleInputChange}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              variant="outlined"
              type="password"
              label="Password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              fullWidth
              variant="contained"
              color="primary"
              type="submit"
            >
              Sign in
            </Button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
};

export default LoginForm;
