// AlertContext.js
import React, { createContext, useContext, useState } from 'react';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import { Slide } from '@mui/material';

const AlertContext = createContext();

export const AlertProvider = ({ children }) => {
  const [alertConfig, setAlertConfig] = useState({ open: false });

  const showAlert = (message, severity = 'success') => {
    setAlertConfig({
      open: true,
      message,
      severity,
    });
  };

  const closeAlert = () => {
    setAlertConfig((prevConfig) => ({
      ...prevConfig,
      open: false,
    }));
  };

  return (
    <AlertContext.Provider value={{ showAlert, closeAlert }}>
      {children}
      <Snackbar
        open={alertConfig.open}
        anchorOrigin={{ vertical: "bottom", horizontal:"right" }}
        autoHideDuration={3000}
        onClose={closeAlert}
        TransitionComponent={TransitionUp}
      >
        <Alert onClose={closeAlert} severity={alertConfig.severity}>
          {alertConfig.message}
        </Alert>
      </Snackbar>
    </AlertContext.Provider>
  );
};

export const useAlert = () => {
  const context = useContext(AlertContext);
  if (!context) {
    throw new Error('useAlert must be used within an AlertProvider');
  }
  return context;
};

function TransitionUp(props) {
    return <Slide {...props} direction="up" />;
}

