import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, IconButton, Paper, InputBase, TextField, Stack } from '@mui/material';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import EditIcon from '@mui/icons-material/Edit';
import { Button } from '@mui/material';
import ButtonGroup from '@mui/material/ButtonGroup';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import SendIcon from '@mui/icons-material/Send';
import AddCircleIcon from '@mui/icons-material/AddCircle';
// import { InputBase, TextField } from "@material-ui/core";

import { useAlert } from '../AlertContext';
import { httpRequest } from '../../service/httpRequest';
import DashboardLayot from './DashboardLayot';
import SelectTags from '../basic/SelectTags';
import DropdownCheckboxList from './utils/dropdown/DropdownCheckboxList';
import DropdownFilters from './utils/dropdown/DropdownFilters';
import Filters from './utils/Filters';

const defaultSettings = {
    name: "New dashboard",
    charts: [],
    description: "",
    layot: {},
    filters: [],
}

function buildRequestBody(dashboardData) {
    const submitData = Object.assign({},dashboardData);
    delete submitData.charts;
    delete submitData.filters;
    submitData["chartsId"] = dashboardData.charts.map(c => c.id);
    submitData["layout"] = JSON.stringify({position: (dashboardData.layout), filters: dashboardData.filters});
    return submitData;
}

function destructResponseBody(body) {
    const extraSettings = JSON.parse(body["layout"]);
    body["layout"] = extraSettings["position"];
    body["filters"] = extraSettings["filters"];
    return body;
}

const Dashboard = (props) => {
    const { showAlert } = useAlert();
    const { id } = useParams(); 
    const [ mode, setMode ] = useState("read");
    const [ dashboardData, setDashboardData ] = useState();

    useEffect(() => {
        fetchData()
    }, []);

    

    const fetchData = () => {
        if (id) {
            const fetchData = async () => {
                try {
                    const response = await httpRequest.get(`/dashboard/${id}`);
                    if (response.status === 400) {
                        showAlert('Error: ' + response.data, "error");
                        return;
                    } 
                    const newData = destructResponseBody(response.data)
                    setDashboardData(newData);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
            fetchData();
        }
        else {
            setDashboardData(defaultSettings);
            setMode("edit");
        }
    }

    const handleLayoutChange = (newCharts, newLayout) => {
        setDashboardData({
            ...dashboardData,
            "charts": newCharts,
            "layout": newLayout,
        })
    }

    const handleDataChange = (e, field, value) => {
        e.preventDefault();
        setDashboardData({
            ...dashboardData,
            [field]: value,
        });
    };

    const handleAddChart = (key) => {

        const fetchAndAddChart = async (chartId, newLayout) => {
            try {
                const response = await httpRequest.get(`/chart/${chartId}`);
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                const newCharts = dashboardData.charts ? dashboardData.charts.slice() : [];
                newCharts.push(response.data)
                handleLayoutChange(newCharts,newLayout);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }

        const newLayout = dashboardData.layout ? dashboardData.layout.slice() : [];
        const newItem = {
            i: `${key}`,
            x: 0,
            y: Infinity, // Поместить новую диарамму вниз
            w: 6,
            h: 5,
        }
        newLayout.push(newItem)
        fetchAndAddChart(key, newLayout);
    }

    const handleChangeFilter = (e, newFilter) => {
        e.preventDefault();
        const oldFilters = dashboardData.filters ? dashboardData.filters.slice() : [];
        const checked = e.target.checked;
        const newFilters = checked ?
            [...oldFilters, newFilter] :
            oldFilters.filter(old => old.name !== newFilter.name || old.type !== newFilter.type)
        
        handleDataChange(e, "filters", newFilters)
    }

    const handleSaveClick = (event) => {
        event.preventDefault();
        const submitData = buildRequestBody(dashboardData);
        httpRequest.post(`/dashboard/`, submitData)
            .then(response => {
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                const newData = destructResponseBody(response.data)
                setDashboardData(newData);
            })
            .catch(error => {
                console.error('Error submitting data:', error);
            });
        
        setMode("read");
    }

    const handleSaveAsClick = (e) => {
        setMode("read");
    }

    const handleCloseClick = (e) => {
        
    }

    const handleCancelClick = (e) => {
        fetchData();
        setMode("read");
    }

    const handleApplyFilters = (e, filterValues) => {
        e.preventDefault();
        if (filterValues) {
            const submitData = Object.keys(filterValues).map(key => {
                const filter = filterValues[key]
                const column = dashboardData.filters.find(c => c.name === key);
                column['filterValues'] = filter;
                return column;
            });

            httpRequest.post(`/dashboard/${id}`, submitData)
            .then(response => {
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                const newData = destructResponseBody(response.data);
                setDashboardData(newData);
            })
            .catch(error => {
                console.error('Error submitting data:', error);
            });
        }
    }

    const backgroundColor = mode==="read" ? "transparent" : "#transparent";

    return ( dashboardData &&
        <div style={{backgroundColor: backgroundColor, height: "100%" }}>
            <Container maxWidth="xl">
                <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
                    {mode==="edit" && <TextField
                            inputProps={{
                                style: {fontSize: "27px"},
                            }}
                            variant="standard"
                            value={dashboardData.name}
                            onChange={(e) => handleDataChange(e, "name", e.target.value)}
                        />}
                    {mode==="read" && <Typography variant="h2" >
                        {dashboardData.name}
                    </Typography>}
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
                    <Filters dashboardData={dashboardData} mode={mode} handleApplyFilters={handleApplyFilters}/>
                    <Grid item xs>
                        <DashboardLayot 
                            mode={mode}
                            dashboardData={dashboardData} 
                            handleLayoutChange={handleLayoutChange}/>
                    </Grid>
                </Grid>
                {mode === "edit" && <Stack spacing={1} style={stackStyle}>
                    <DropdownCheckboxList dashboardData={dashboardData} handleAddChart={handleAddChart}/>
                    <DropdownFilters dashboardData={dashboardData} handleChangeFilter={handleChangeFilter}/>
                </Stack>}
            </Container>
        </div>
        
    )
}

const customButtonStyle = {
    margin: 'auto 0', // Задаем отступы
    padding: '0 12px',
  };

const stackStyle = {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
};

export default Dashboard;