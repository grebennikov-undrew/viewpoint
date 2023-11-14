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
import { aggFunctions } from '../../basic/Enum';
import SelectValue from '../../basic/SelectValue'

const PieSettings = ({chartData, columns, onFieldChange, onSelectChange}) => {
    const { chartSettings } = chartData;
    const { dimensions, metrics } = chartSettings;
    
    if (!chartData) return;
    return (
        <>
            <Grid item xs={12} >
                <Box style={{backgroundColor: "#F3F3F3"}} pt={1} pb={1} pl={1} pr={1}>
                    <InputLabel style={{fontWeight: "bold", marginBottom: "5px"}} fullWidth>Metric</InputLabel>
                    <Grid container spacing={3}>
                        <Grid item xs={6}>
                            <FormControl variant="standard" fullWidth >
                                <InputLabel id="dataset-select-label">Function</InputLabel>
                                <Select
                                    labelId="agg-select-label"
                                    id="agg_id"
                                    value={metrics[0]["aggFunction"]}
                                    label="Function"
                                    required
                                    onChange={(e) => {
                                            const newMetric = metrics[0];
                                            newMetric["aggFunction"] = e.target.value;
                                            newMetric["label"] = `${newMetric["aggFunction"]}(${newMetric["value"]})`;
                                            return onSelectChange(e, "chartSettings", {
                                                ...chartSettings,
                                                "metrics": [newMetric],
                                            })
                                    }}
                                    renderValue={(selected) => (
                                        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                            <Chip label={selected} />
                                        </Box>
                                    )}
                                >
                                    {aggFunctions.map((func, idx) => (
                                        <MenuItem key={idx} value={func}>{func}</MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={6}>
                            <SelectValue 
                                options={columns.map(c => c.name)} 
                                value={metrics[0].value}
                                label="Column"
                                onChange={
                                    (e) => {
                                        const newMetric = metrics[0];
                                        newMetric["value"] = e.target.value;
                                        newMetric["label"] = `${newMetric["aggFunction"]}(${newMetric["value"]})`;
                                        onSelectChange(e, "chartSettings", {
                                            ...chartSettings,
                                            "metrics": [newMetric],
                                        })
                                    }
                                }
                                />
                        </Grid>
                    </Grid>
                </Box>
            </Grid>
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
        </>
    )
}

export default PieSettings;