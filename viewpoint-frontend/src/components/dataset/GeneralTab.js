import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Button, Container, Chip } from '@mui/material';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import FormControl from '@mui/material/FormControl';
import SendIcon from '@mui/icons-material/Send';

import { useAlert } from '../AlertContext';
import { httpRequest } from '../../service/httpRequest';

const GeneralTab = ({datasetData, onFieldChange, onSelectChange, setTableData, setTab}) => {
    const { showAlert } = useAlert();
    const [sources, setSources] = useState();

    useEffect(() => {
        const fetchSources = async () => {
            try {
                const response = await httpRequest.get(`/source/`);
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                setSources(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchSources();
    }, []);

    const handleExecuteQuery = (event) => {
        event.preventDefault();
        
        const queryData = {
            "sqlQuery": datasetData.sqlQuery,
            "sourceId": datasetData.source.id,
        };

        const fetchData = async () => httpRequest.post(`/dataset/execute`, queryData)
        .then(response => {
            if (response.status === 400) {
                showAlert('Error: ' + response.data, "error");
                return;
            } 
            setTableData(response.data);
            onSelectChange(null, "columns", Object.keys(response.data.coltypes).map(k => {return {"name": k, "type": response.data.coltypes[k] }}));
            setTab(1);
        })
        .catch(error => {
            console.error('Error submitting data:', error);
        });
        fetchData();
    }
    
    if (!datasetData.source) return;
    return (
        <Grid container>
            <Grid container xs={3} spacing={3} alignContent="start">
                <Grid item xs={12}>
                    <TextField
                        required
                        id="name"
                        label="Name"
                        defaultValue="Unnamed"
                        value={datasetData.name}
                        fullWidth
                        onChange={onFieldChange}
                        variant='standard'
                    />
                </Grid>
                <Grid item xs={12}>
                    <FormControl variant='standard' fullWidth>
                        <InputLabel id="source-select-label">Source</InputLabel>
                        <Select
                            labelId="source-select-label"
                            id="source_id"
                            value={datasetData.source && datasetData.source.name}
                            label="Source"
                            required
                            onChange={(e) => onSelectChange(e, "source", {"id": sources.find(s => s.name === e.target.value).id, "name": e.target.value})}
                            renderValue={(selected) => (
                                <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                    <Chip label={selected} />
                                </Box>
                            )}
                        >
                            {sources && sources.map((source) => (
                                <MenuItem
                                key={source.id}
                                value={source.name}
                                >
                                {source.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        id="user"
                        label="Author"
                        disabled
                        fullWidth
                        defaultValue="You"
                        value={datasetData.user}
                        variant='standard'
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        id="updatedOn"
                        label="Updated"
                        disabled
                        fullWidth
                        defaultValue="Now"
                        value={datasetData.updatedOn}
                        variant='standard'
                    />
                </Grid>
                <Grid item xs={12} alignContent="end">
                    <Button 
                        startIcon={<SendIcon />} 
                        variant="contained" 
                        fullWidth 
                        onClick={handleExecuteQuery}>
                        Execute query
                    </Button>
                </Grid>
            </Grid>
            <Grid container xs={9} paddingLeft={5}>
                <TextField
                    id="sqlQuery"
                    label="Query"
                    multiline
                    rows={20}
                    defaultValue="SELECT ..."
                    value={datasetData.sqlQuery}
                    onChange={onFieldChange}
                    fullWidth
                    style={{backgroundColor: "white"}}
                />
            </Grid>
        </Grid>
    )
}

export default GeneralTab;