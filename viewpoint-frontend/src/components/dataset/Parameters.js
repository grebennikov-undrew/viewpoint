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
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const customAccordionStyle = {
    height: '50 px',
    width: '200 px'
};

const TYPES = [
    { value: "String", key: "String"},
    { value: "Number", key: "Double"},
    { value: "Boolean", key: "Boolean"},
    { value: "Datetime", key: "Timestamp"}
]

const Parameter = (parameter) => {
    return (
        <Accordion >
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1a-content"
            id={parameter.id}
            style={customAccordionStyle}
            >
            <Typography>{parameter.name}</Typography>
            </AccordionSummary>
            <AccordionDetails>
            <FormControl sx={{m:1}} fullWidth>
                <InputLabel id="source-select-label">Type</InputLabel>
                <Select
                    labelId="type-select-label"
                    id="type"
                    value={TYPES.find(item => item.key === parameter.type).value}
                    key={parameter.type}
                    label="Type"
                    required
                >
                    {TYPES.map((type) => (
                        <MenuItem
                        key={type.key}
                        value={type.value}
                        >
                        {type.value}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
            </AccordionDetails>
        </Accordion>
    )
}

const Parameters = (data) => {
    const Parameters = data.parameters;
    
    return (
        <div style={{width: '100%'}}>
            {data && Parameters.map((parameter) => <Parameter {...parameter}/>)}
        </div>
    )
}

export default Parameters;