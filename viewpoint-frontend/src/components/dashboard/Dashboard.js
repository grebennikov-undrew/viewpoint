import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, IconButton, Paper } from '@mui/material';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import EditIcon from '@mui/icons-material/Edit';
import { Button } from '@mui/material';
import ButtonGroup from '@mui/material/ButtonGroup';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import SendIcon from '@mui/icons-material/Send';
import AddCircleIcon from '@mui/icons-material/AddCircle';

import { httpRequest } from '../../service/httpRequest';
import DashboardLayot from './DashboardLayot';
import SelectTags from '../basic/SelectTags';
import DropdownCheckboxList from './utils/DropdownCheckboxList';

import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';

const defaultSettings = {
    name: "New dashboard",
    charts: [],
    description: "",
    layot: {},
}

const Dashboard = (props) => {
    const { id } = useParams(); 
    const [ mode, setMode ] = useState("read");
    // const [ dashboardSettings, setDashboardSettings] = useState({});
    const [ dashboardData, setDashboardData ] = useState();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                try {
                    const response = await httpRequest.get(`/dashboard/${id}`)
                    const settings = response.data;
                    settings["layout"] = JSON.parse(settings["layout"])["position"];
                    setDashboardData(settings);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
            fetchData();
        }
        else {
            setDashboardData(defaultSettings);
        }
    }, []);

    // useEffect(() => {
    //     if (id) {
    //         const fetchData = async () => {
    //             try {
    //                 const response = await httpRequest.get(`/dashboard/${id}/data`)
    //                 setDashboardData(response.data);
    //             } catch (error) {
    //                 console.error('Error fetching data:', error);
    //             }
    //         }
    //         fetchData();
    //     }
    // }, []);

    const handleSettingsChange = (property, value) => {
        setDashboardData({
            ...dashboardData,
            [property]: value,
        })
    }

    const handleSaveClick = (e) => {
        setMode("read");
    }

    const handleSaveAsClick = (e) => {
        setMode("read");
    }

    const handleCloseClick = (e) => {
        
    }

    const handleCancelClick = (e) => {
        setMode("read");
    }

    const backgroundColor = mode==="read" ? "transparent" : "#transparent";

    return ( dashboardData &&
        <div style={{backgroundColor: backgroundColor, height: "100%" }}>
            <Container maxWidth="xl">
                <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
                    <Typography variant="h2" >
                        {dashboardData.name}
                    </Typography>
                    {/* <Button variant="text">Add</Button> */}
                    <IconButton
                        size="large"
                        aria-label="Edit dashboard"
                        aria-controls="menu-appbar"
                        aria-haspopup="true"
                        style={customButtonStyle}
                        onClick={(e) => setMode("edit")}
                        >
                        <EditIcon/>
                    </IconButton>
                    <ButtonGroup variant="contained" aria-label="outlined primary button group" style={{marginLeft: "auto", paddingRight: "200 px"}}>
                        {mode==="read" && <Button onClick={e => setMode("edit")}>Edit</Button>}
                        {mode==="edit" && <Button onClick={handleSaveClick}>Save</Button>}
                        {mode==="edit" && <Button onClick={handleSaveAsClick}>Save As</Button>}
                        {mode==="read" && <Button color="error" variant="outlined" onClick={handleCloseClick}>Close</Button>}
                        {mode==="edit" && <Button color="error" variant="outlined" onClick={handleCancelClick}>Cancel</Button>}
                    </ButtonGroup>
                </div>
                <Grid container columns={12}>
                    <Grid item xs={2} height={"100%"} bgcolor={"white"} borderRadius={4} p={2} mt={2.5} component={Paper}>
                        <Typography variant="h4" fontWeight={600}>
                            Filters
                        </Typography>
                        <Grid container columns={1} spacing={3}>
                            <Grid item xs={1}>
                                <SelectTags options={["test", "test2", "test3", "test4", "test5"]} label={"test label"}/>
                            </Grid>
                            <Grid item xs={1}>
                                {mode === "edit" && <Button 
                                    startIcon={<AddCircleIcon />} 
                                    variant="contained" 
                                    fullWidth 
                                    // onClick={handleExecuteQuery}
                                    >
                                    Add filter
                                </Button>}
                                {mode === "read" && <Button 
                                    startIcon={<SendIcon />} 
                                    variant="contained" 
                                    fullWidth 
                                    // onClick={handleExecuteQuery}
                                    >
                                    Apply
                                </Button>}
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={10}>
                        <DashboardLayot 
                            mode={mode}
                            dashboardData={dashboardData} 
                            handleSettingsChange={handleSettingsChange}/>
                    </Grid>
                </Grid>
                {mode === "edit" && <DropdownCheckboxList/>}
            </Container>
        </div>
        
    )
}

const customButtonStyle = {
    margin: 'auto 0', // Задаем отступы
    padding: '0 12px',
  };

const fabStyle = {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
};

export default Dashboard;