import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import Slide from '@mui/material/Slide';
import Stack from '@mui/material/Stack';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

import IconButton from '@mui/material/IconButton';
import GridOnIcon from '@mui/icons-material/GridOn';
import BarChartIcon from '@mui/icons-material/BarChart';
import SsidChartIcon from '@mui/icons-material/SsidChart';
import PieChartIcon from '@mui/icons-material/PieChart';

import { useAlert } from '../AlertContext';
import { httpRequest } from '../../service/httpRequest';
import SettingsArea from './SettingsArea';
import ChartArea from './ChartArea';

const defaultChartData = {
    name: "New chart",
    chartType: "TABLE",
    username: "user",
    datasetName: "",
    chartSettings: { dimensions: [], metrics: [{}] , orderBy: [] },
}

const EditChart = () => {
    const { showAlert } = useAlert();
    const { id } = useParams(); 
    const [chartData, setChartData] = useState();
    const [needUpdate, setNeedUpdate] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                try {
                    const response = await httpRequest.get(`/chart/${id}`);
                    if (response.status === 400) {
                        showAlert('Error: ' + response.data, "error");
                        return;
                    } 
                    setChartData(response.data);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
            fetchData();
        } else {
            setChartData(defaultChartData);
        }
    }, []);

    const handleRefresh = (event) => {
        if (needUpdate) {
            const fetchData = async () => {
                try {
                    const response = await httpRequest.post(`/chart/data`, chartData);
                    if (response.status === 400) {
                        showAlert('Error: ' + response.data, "error");
                        return;
                    } 
                    setChartData(response.data);
                    setNeedUpdate(false);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
            fetchData();
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        const {...submitData} = chartData;
        httpRequest.post(`/chart/`, submitData)
          .then(response => {
            if (response.status === 400) {
                showAlert('Error: ' + response.data, "error");
                return;
            } 
            setChartData(response.data);
          })
          .catch(error => {
            console.error('Error submitting data:', error);
          });
    }

    const handleFieldChange = (e) => {
        setChartData({
            ...chartData,
            [e.target.id]: e.target.value,
        });
        setNeedUpdate(true);
    };

    const handleSelectChange = (e, field, value) => {
        setChartData({
            ...chartData,
            [field]: value,
        });
        setNeedUpdate(true);
    };

    const handleCloseClick = () => {
        navigate(`/chart/`);
    };

    const handleSaveAsClick = (e) => {
        if (validateData) {
            setChartData({
                ...chartData,
                [id]: null,
            });
            handleSubmit(e);
        }
    };

    const validateData = () => {
        if (chartData.name && chartData.source && chartData.sqlQuery)
        return true;
    }

    // if (!dataLoaded) return <></>

    return (chartData && <form onSubmit={handleSubmit}>
        <Container maxWidth="xl">
            <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
                <Typography variant="h2" m={1}>
                    Edit chart
                </Typography>
                <ButtonGroup variant="contained" aria-label="outlined primary button group" style={{marginLeft: "auto", paddingRight: "200 px"}}>
                    <Button type='submit'>Save</Button>
                    <Button onClick={handleSaveAsClick}>Save As</Button>
                    <Button color="error" variant="outlined" onClick={handleCloseClick}>Close</Button>
                </ButtonGroup>
            </div>
            <Grid container >
                <Grid container xs={3} pr={3}>
                    <Stack direction="row" spacing={1} mb={3}>
                        <IconButton aria-label="delete" color={chartData.chartType.toLowerCase() === "table" ? "primary" : "disabled"} onClick={(e) => handleSelectChange(e,"chartType", "TABLE")}>
                            <GridOnIcon />
                        </IconButton>
                        <IconButton aria-label="delete" color={chartData.chartType.toLowerCase() === "pie" ? "primary" : "disabled"} onClick={(e) => handleSelectChange(e,"chartType","PIE")}> 
                            <PieChartIcon />
                        </IconButton>
                        <IconButton aria-label="add an alarm" color={chartData.chartType.toLowerCase() === "bar" ? "primary" : "disabled"} onClick={(e) => handleSelectChange(e,"chartType","BAR")}>
                            <BarChartIcon />
                        </IconButton>
                        <IconButton aria-label="add to shopping cart" color={chartData.chartType.toLowerCase() === "line" ? "primary" : "disabled"} onClick={(e) => handleSelectChange(e,"chartType","LINE")}>
                            <SsidChartIcon />
                        </IconButton>
                    </Stack>
                    <SettingsArea chartData={chartData} onFieldChange={handleFieldChange} onSelectChange={handleSelectChange}/>
                </Grid>
                <Grid container xs={9}>
                    <Grid item xs={12} > 
                        <div style={{ width: "100%", height: "400px"}}>
                            <ChartArea chartData={chartData} />
                        </div>
                    </Grid>
                    {/* <ChartArea chartData={chartData} chartResult={chartResult} /> */}
                    <Snackbar open={needUpdate} anchorOrigin={{ vertical: "bottom", horizontal:"right" }} TransitionComponent={TransitionUp}>
                        <MuiAlert severity="warning" sx={{ width: '100%' }} action={<Button color="warning" size="small" onClick={handleRefresh}>REFRESH</Button>} >
                            This chart is not up to date
                        </MuiAlert>
                    </Snackbar>
                </Grid>
            </Grid>
        </Container>
        </form>
    )
}

function TransitionUp(props) {
    return <Slide {...props} direction="up" />;
}

export default EditChart;
