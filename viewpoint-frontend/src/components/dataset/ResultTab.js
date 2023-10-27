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
import Filter from '../dashboard/Filter'

const ResultTab = ({datasetData}) => {
    
    return (
        <div>
            <Grid container >
                <Grid container paddingRight={5} xs={3}>
                    <Grid item xs={12}>
                        {datasetData.parameters.map(parameter => <Filter parameter={parameter}/>)}
                    </Grid>
                </Grid>
                {/* <Grid container xs={9}>
                    <TextField
                        id="sqlQuery"
                        label="Query"
                        multiline
                        rows={20}
                        defaultValue="SELECT ..."
                        value={datasetData.sqlQuery}
                        onChange={onFieldChange}
                        fullWidth
                    />
                </Grid> */}
            </Grid>
        </div>
    )
}

export default ResultTab;