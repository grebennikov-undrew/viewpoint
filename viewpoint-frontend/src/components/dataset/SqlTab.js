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
import Parameters from './Parameters';

const SqlTab = ({datasetData, onFieldChange, onSelectChange, setDatasetData}) => {
    
    return (
        <div>
        <Grid container >
            <Grid container paddingRight={5} xs={3}>
               <Parameters datasetData={datasetData} onFieldChange={onFieldChange} onSelectChange={onSelectChange} setDatasetData={setDatasetData}/> 
            </Grid>
            <Grid container xs={9}>
                <TextField
                    id="sqlQuery"
                    label="Query"
                    multiline
                    rows={20}
                    defaultValue="SELECT ..."
                    value={datasetData.sqlQuery}
                    onChange={onFieldChange}
                    style={{backgroundColor: "white"}}
                    fullWidth
                />
            </Grid>
        </Grid>
        </div>
    )
}

export default SqlTab;