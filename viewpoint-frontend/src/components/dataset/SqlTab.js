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
import Parameters from './Parameters';

const SqlTab = ({datasetData, onFieldChange, onSelectChange}) => {
    
    return (
        <Grid container spacing={2}>
            <Grid container paddingRight={5} spacing={3} xs={3}>
               <Parameters {...datasetData}/> 
            </Grid>
            <Grid container xs={9}>
                <TextField
                    id="sqlQuery"
                    label="Query"
                    multiline
                    rows={20}
                    // defaultValue="SELECT ..."
                    // value={data & data.sqlQuery}
                    defaultValue={datasetData.sqlQuery || "SELECT ..."}
                    fullWidth
                />
            </Grid>
        </Grid>
    )
}

export default SqlTab;