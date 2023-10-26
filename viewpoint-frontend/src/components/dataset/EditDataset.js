import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import FormControl from '@mui/material/FormControl';
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import GeneralTab from './GeneralTab';
import SqlTab from './SqlTab';

const EditDataset = () => {
    const { id } = useParams(); 
    const [data, setData] = useState();
    const [tab, setTab] = React.useState(0);
    const [datasetData, setDatasetData] = useState();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/dataset/${id}`)
                setData(response.data)
                setDatasetData(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
    
        fetchData();
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();
        const {columns, ...submitData} = data;
        axios.post(`http://localhost:8080/api/dataset/`, submitData)
          .then(response => {
            setData(response.data);
          })
          .catch(error => {
            console.error('Error submitting data:', error);
          });
      }

    const handleFieldChange = (e) => {
        setDatasetData({
            ...datasetData,
            [e.target.id]: e.target.value,
        });
    };

    const handleSelectChange = (e) => {
        setDatasetData({
            ...datasetData,
            [e.target.id]: e.target.key,
        });
    };

    const handleChangeTab = (event, newValue) => {
        setTab(newValue);
    };

    return (
        <form onSubmit={handleSubmit}>
        <Container maxWidth="xl">
            <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
                <Typography variant="h2" m={1}>
                    Edit dataset
                </Typography>
                <ButtonGroup variant="contained" aria-label="outlined primary button group" style={{marginLeft: "auto", paddingRight: "200 px"}}>
                    <Button type='submit'>Save</Button>
                    <Button>Save As</Button>
                    <Button color="error" variant="outlined">Close</Button>
                </ButtonGroup>
            </div>
            <Tabs value={tab} onChange={handleChangeTab} aria-label="basic tabs example">
                <Tab label="General" id={0} aria-controls='tab0'/>
                <Tab label="SQL" id={1} aria-controls='tab1'/>
                <Tab label="Result" id={2} aria-controls='tab2'/>
            </Tabs>
            <CustomTabPanel value={tab} index={0}>
                {datasetData && <GeneralTab onFieldChange={handleFieldChange} onSelectChange={handleSelectChange} datasetData={datasetData}/>}
            </CustomTabPanel>
            <CustomTabPanel value={tab} index={1}>
                {data && <SqlTab {...data} setData={setData}/>}
            </CustomTabPanel>
            <CustomTabPanel value={tab} index={2}>
                Item Three
            </CustomTabPanel>
        </Container>
        </form>
    )
}

function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`simple-tabpanel-${index}`}
        aria-labelledby={`simple-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box sx={{ p: 3 }}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
  }

export default EditDataset;