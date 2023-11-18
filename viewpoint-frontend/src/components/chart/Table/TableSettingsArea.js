import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Checkbox, Container, FormControlLabel } from '@mui/material';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import FormControl from '@mui/material/FormControl';
import Chip from '@mui/material/Chip';
import IconButton from '@mui/material/IconButton';
import Stack from '@mui/material/Stack';

import GridOnIcon from '@mui/icons-material/GridOn';
import BarChartIcon from '@mui/icons-material/BarChart';
import SsidChartIcon from '@mui/icons-material/SsidChart';
import PieChartIcon from '@mui/icons-material/PieChart';

import { httpRequest } from '../../../service/httpRequest';
import SelectTags from '../../basic/SelectTags';


const TableSettingsArea = ({chartData, columns, onFieldChange, onSelectChange}) => {
    const { chartSettings } = chartData;
    const { dimensions } = chartSettings;

    if (!chartData) return;
    return (
            <Grid item xs={12}>
                <FormControl fullWidth>
                    <SelectTags 
                    options={columns.map(c => c.name)} 
                    values={dimensions && dimensions.map(d => d.label)} 
                    label="Dimensions"
                    onSelectChange={(event, value) => {
                        const newArray = value.map(listValue => {return {label: listValue, value: listValue}});
                        return onSelectChange(event, "chartSettings", {
                        ...chartSettings,
                        "dimensions": newArray,
                    })}}/>
                </FormControl>
            </Grid>
    )
}

export default TableSettingsArea;