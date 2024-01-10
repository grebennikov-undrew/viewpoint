import React, { useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'
import { TextField, Button, Grid, Snackbar, Alert, Container, Dialog, DialogTitle, DialogContent, Slide } from '@mui/material';

import { useAlert } from '../components/AlertContext';
import { postData } from '../service/httpQueries';


const LoginForm = () => {
  const { showAlert } = useAlert();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) navigate("/")
  }, []);

  const saveToken = (response) => {
    const token = response.accessToken
    if (token) localStorage.setItem('token', token)
  }

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
      postData("auth/jwt", formData, showAlert, saveToken)
    }
    fetchData();
  };

  return (
    <Dialog
      fullWidth
        onClose={() => {}}
        open={true}
        maxWidth="xs"
        sx={{
          backdropFilter: "blur(5px)",
        }}
    >
      <DialogTitle variant="h3" align="center">Sign in</DialogTitle>
      <DialogContent style={{ textAlign: "center" }}>
        {/* <form> */}
          <Grid container spacing={6} style={{marginTop: 10}}>
            <Grid item xs={12} >
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
            <Grid item xs={12} mt={4}>
              <Button
                fullWidth
                variant="contained"
                color="primary"
                // type="submit"
                size='large'
                onClick={handleSubmit}
              >
                Sign in
              </Button>
            </Grid>
          </Grid>
        {/* </form> */}
      </DialogContent>
      <Snackbar
        open={true}
        anchorOrigin={{ vertical: "bottom", horizontal:"right" }}
        autoHideDuration={3000}
        TransitionComponent={TransitionUp}
      >
        <Alert severity={'info'}>
          Для входа использовать: <br/>
          analyst / analyst
        </Alert>
      </Snackbar>
    </Dialog>
  );
};

function TransitionUp(props) {
  return <Slide {...props} direction="up" />;
}

export default LoginForm;
