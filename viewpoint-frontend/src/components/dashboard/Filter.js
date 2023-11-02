import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Button, Checkbox, Container, FormControlLabel } from '@mui/material';
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
import DatePicker from '../basic/DatePicker'
import { httpRequest } from '../../service/httpRequest';

const Filter = ({parameter, handleFilterChange, filterValue, sourceId}) => {
    // const [filterState, setFilterState] = React.useState();
    const [filterOptions, setFilterOptions] = React.useState([]);

    useEffect(() => {
        if (parameter.type === 'String') {
            const fetchOptions = async () => {
                try {
                    const body = {
                        sourceId: sourceId,
                        sqlQuery: parameter.sqlQuery,
                        type: parameter.type,
                    }
                    const response = await httpRequest.post(`/dashboard/parameter`, body)
                    setFilterOptions(response.data);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
            fetchOptions();
        }
    }, []);

    if (parameter.type === "String") {
        return (
            <FormControl fullWidth sx={{m:1}}>
                <InputLabel id={`filter-select-label-${parameter.name}`}>{parameter.name}</InputLabel>
                <Select
                    labelId={`filter-select-label-${parameter.name}`}
                    id={parameter.name}
                    value={filterValue}
                    label={parameter.name}
                    onChange={(e) => {
                        handleFilterChange(e, parameter.name, e.target.value)
                    }}
                    fullWidth
                >
                    <MenuItem value="">
                            <em>None</em>
                    </MenuItem>
                    {filterOptions.map(option => (
                        <MenuItem value={option}>
                            {option}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
        )
    } else if (parameter.type === "Double") {
        return (
            <TextField
                id={`filter-double-${parameter.name}`}
                label={parameter.name}
                value={filterValue}
                fullWidth
                sx={{m: 1}}
                onChange={(e) => {
                    handleFilterChange(e, parameter.name, e.target.value)
                }}
            />
        )
    }
    else if (parameter.type === "Timestamp") {
        return (
            <FormControl fullWidth sx={{m:1}}>
                <DatePicker 
                    id={`filter-calend-${parameter.name}`}
                    value={filterValue}
                    onChange={(newValue) => {
                        handleFilterChange(null, parameter.name,newValue)
                    }}
                    label={parameter.name}
                    // onClose={(newValue) => {
                    //     handleFilterChange(null, parameter.name,newValue)
                    // }}
                />
            </FormControl>
        )
    }
    else if (parameter.type === "Boolean") {
        return (
            <FormControlLabel 
                label={parameter.name}
                fullWidth
                sx={{m: 1}}
                control={<Checkbox 
                    id={`filter-checkbox-${parameter.name}`}
                    checked={parameter.value}
                    onChange={(e) => {
                        handleFilterChange(e, parameter.name,e.target.checked)
                    }}
                />}
            />
        )
    }
}

export default Filter;