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

const Filter = ({parameter, handleFilterChange, filterValue}) => {
    // const [filterState, setFilterState] = React.useState();
    const [filterOptions, setFilterOptions] = React.useState([]);

    useEffect(() => {
        const fetchOptions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/dashboard/parameter/${parameter.id}`)
                setFilterOptions(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchOptions();
    }, []);

    // const handleFilterChange = (e) => {
    //     setFilterState(e.target.value);
    // }

    return (
        <FormControl fullWidth sx={{m:1}}>
            <InputLabel id={`filter-select-label-${parameter.id}`}>{parameter.name}</InputLabel>
            <Select
                labelId={`filter-select-label-${parameter.id}`}
                id={parameter.name}
                value={filterValue}
                label={parameter.name}
                required
                onChange={(e) => {
                    handleFilterChange(e, parameter.name, e.target.value)
                }}
                fullWidth
            >
                {filterOptions.map(option => (
                    <MenuItem value={option}>
                        {option}
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    )
}

export default Filter;