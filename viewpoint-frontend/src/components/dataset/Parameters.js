import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { Button, Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import FormControl from '@mui/material/FormControl';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';

const customAccordionStyle = {
    height: '50 px',
    width: '200 px'
};

const TYPES = ["String", "Double", "Boolean", "Timestamp"];

const Parameter = ({parameter, handleParamChange, handleParamDelete}) => {

    return (
        <Accordion >
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1a-content"
            id={parameter.name}
            style={customAccordionStyle}
            >
                <Typography>
                    {parameter.name}
                </Typography>
                <IconButton
                    aria-label="Delete" 
                    onClick={e => handleParamDelete(e, parameter.name)}
                    style={{padding: 0, marginLeft: "auto"}}
                >
                    <DeleteIcon/>
                </IconButton>
            </AccordionSummary>
            <AccordionDetails>
            <TextField
                required
                id="name"
                label="Name"
                value={parameter.name}
                fullWidth
                sx={{m: 1}}
                onChange={e => handleParamChange(e, parameter.name, "name", e.target.value)}
            />
            <FormControl sx={{m:1}} fullWidth>
                <InputLabel id="source-select-label">Type</InputLabel>
                <Select
                    labelId="type-select-label"
                    id="type"
                    value={parameter.type}
                    key={parameter.type}
                    label="Type"
                    required
                    onChange={e => handleParamChange(e, parameter.name, "type", e.target.value)}
                >
                    {TYPES.map((type) => (
                        <MenuItem
                        value={type}
                        >
                        {type}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
            {parameter.type === "String" && 
                <TextField
                    id="sqlQuery"
                    label="Query"
                    multiline
                    rows={5}
                    sx={{m:1}}
                    defaultValue="SELECT ..."
                    value={parameter.sqlQuery}
                    onChange={e => handleParamChange(e, parameter.name, "sqlQuery", e.target.value)}
                    fullWidth
                />
            }
            </AccordionDetails>
        </Accordion>
    )
}

const Parameters = ({datasetData, onFieldChange, onSelectChange, setDatasetData}) => {
    const parameters = datasetData.parameters;

    const handleParamChange = (e, name, key, value) => {
        const oldParameters = [...parameters]
        const newParameters = oldParameters.map(p => p.name === name ? { ...p, [key]: value} : p)
        onSelectChange(e,"parameters",newParameters)
    };

    const handleParamDelete = (e, name) => {
        const oldParameters = [...parameters]
        const newParameters = oldParameters.filter(p => p.name !== name)
        onSelectChange(e,"parameters",newParameters)
    };

    const handleParamAdd = (e) => {
        const newParam = {name: "new_parameter", type: "String"}
        const oldParameters = [...parameters]
        const newParameters = [...oldParameters, newParam]
        onSelectChange(e,"parameters",newParameters)
    };
    
    return (
        <div style={{width: '100%'}}>
            {parameters && parameters.map((parameter) => <Parameter parameter={parameter} handleParamChange={handleParamChange} handleParamDelete={handleParamDelete}/>)}
            <Button startIcon={<AddIcon />} variant="outlined" fullWidth onClick={handleParamAdd}>Add</Button>
        </div>
    )
}

export default Parameters;