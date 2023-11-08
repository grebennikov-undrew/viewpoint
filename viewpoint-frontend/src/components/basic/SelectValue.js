import React, { useState, useEffect } from 'react';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import { Select as MuiSelect } from '@mui/material';
import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';

export default function SelectValue(props) {

    const {options, label} = props;

    // if (!options) return;
    return (
        <FormControl variant="standard" fullWidth>
            <InputLabel id="dataset-select-label">{label}</InputLabel>
            <MuiSelect
                {...props}
                renderValue={(selected) => (
                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                        <Chip label={selected} />
                    </Box>)}
            >
                {options && options.map((option, idx) => (
                    <MenuItem key={idx} value={option}>{option}</MenuItem>
                ))}
            </MuiSelect>
        </FormControl>
    );
}
