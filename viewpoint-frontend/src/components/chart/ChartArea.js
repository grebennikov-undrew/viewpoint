import React, { useState, useEffect } from 'react';
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
import { httpRequest } from '../../service/httpRequest';
import { width } from '@mui/system';
import TableArea from './Table/TableArea';

const ChartArea = ({chartData, chartResult}) => {
    if (!chartResult) return;
    return (
        <Grid item sx={{m: 1}}>
            <h3 style={{margin: "0 0 10px 10px"}}>{chartData.name}</h3>
            <TableArea chartData={chartData} chartResult={chartResult}/>
        </Grid>
    )
}

export default ChartArea;