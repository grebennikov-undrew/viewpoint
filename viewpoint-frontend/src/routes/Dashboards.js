import React, { useState, useEffect, useContext } from 'react';
import { Grid } from '@mui/material';
import { Link } from 'react-router-dom';
import DashboardCard from '../components/dashboard/utils/DashboardCard';
import { useNavigate } from "react-router-dom";
import { Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import AddCircleIcon from '@mui/icons-material/AddCircle';

import { useAlert } from '../components/AlertContext';
import { httpRequest } from '../service/httpRequest';
import DeleteDialog from '../components/basic/DeleteDialog';
import { RowActions } from '../components/basic/RowActions';

function Dashboards() {
  const { showAlert } = useAlert();
  const [dashboardList, setDashboardList] = useState([]);
  const [selectedId, setSelectedId] = useState(null);
  const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
      const fetchData = async () => {
          try {
              const response = await httpRequest.get('/dashboard/', {withCredentials: true});
              if (response.status === 400) {
                showAlert('Error: ' + response.data, "error");
                return;
            } 
              setDashboardList(response.data);
          } catch (error) {
              console.error('Error fetching data:', error);
          }
      }
  
      fetchData();
  }, []);

  const handleOpenClick = (e, id) => {
      navigate(`/dashboard/${id}`)
  };

  const handleEditClick = (e, id) => {
    navigate(`/dashboard/${id}/edit`)
  }

  const handleDeleteClick = (e,id) => {
      setSelectedId(id);
      setDeleteConfirmationOpen(true);
  };

  const handleDeleteConfirmationClose = () => {
      setDeleteConfirmationOpen(false);
  };

  return (
    <Container maxWidth="xl">
        <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
            <Typography variant="h2" >
                Dashboards
            </Typography>
            {/* <Button variant="text">Add</Button> */}
            <IconButton
              size="large"
              aria-label="Add dashboard"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              style={customButtonStyle}
              href='/dashboard/new'
              >
              <AddCircleIcon/>
            </IconButton>
        </div>
        <div>
          <Grid container spacing={2} columns={12}>
              {dashboardList.map(dashData => (
                <>
                  <Grid item xs={4}>
                      <DashboardCard {...dashData} 
                        handleEditClick={handleEditClick} 
                        handleOpenClick={handleOpenClick}
                        handleDeleteClick={handleDeleteClick}
                      />
                  </Grid>
                </>
              ))}
          </Grid>
        </div>
        {deleteConfirmationOpen && 
          <DeleteDialog
              open={deleteConfirmationOpen}
              deleteUri={"dashboard"}
              id={selectedId}
              onClose={handleDeleteConfirmationClose}
          />
          }
    </Container>
  );
}

const customButtonStyle = {
  margin: 'auto 0', // Задаем отступы
  padding: '0 12px',
};

export default Dashboards;
