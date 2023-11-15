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
import { httpRequest } from '../service/httpRequest';

function Dashboards() {

  const [dashboardList, setDashboardList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
      const fetchData = async () => {
          try {
              const response = await httpRequest.get('/dashboard/', {withCredentials: true});
              setDashboardList(response.data);
          } catch (error) {
              console.error('Error fetching data:', error);
          }
      }
  
      fetchData();
  }, []);

  const handleOpenClick = (e,id) => {
      navigate(`/dashboard/${id}`)
  };

  const handleEditClick = (e,id) => {
    navigate(`/dashboard/${id}/edit`)
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
                      <DashboardCard {...dashData} handleEditClick={handleEditClick} handleOpenClick={handleOpenClick}/>
                  </Grid>
                </>
              ))}
          </Grid>
        </div>
        {/* <DataGrid
            rows={data}
            columns={columns}
            initialState={{
            pagination: {
                paginationModel: { page: 0, pageSize: 10 },
            },
            }}
            pageSizeOptions={[10, 20, 50]}
            // checkboxSelection
            onRowClick={handleRowClick}
            disableColumnMenu={true}
        /> */}
    </Container>
  );
}

const customButtonStyle = {
  margin: 'auto 0', // Задаем отступы
  padding: '0 12px',
};

export default Dashboards;
