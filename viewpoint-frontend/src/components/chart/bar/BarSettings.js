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

import { httpRequest } from '../../../service/httpRequest';
import SelectTags from '../../basic/SelectTags';
import { aggFunctions } from '../../basic/Enum';
import SelectValue from '../../basic/SelectValue'

const BarSettings = ({chartData, chartResult, onFieldChange, onSelectChange}) => {
    const [ datasets, setDatasets] = useState();
    const { chartSettings, dataset, chartType } = chartData;
    const { where, orderBy, desc, dimensions, metrics, xAxis } = chartSettings;
    const { columns } = dataset;

    const columnsValues = columns.map(c => c.name);

    useEffect(() => {
        const fetchSources = async () => {
            try {
                const response = await httpRequest.get(`/dataset/`)
                setDatasets(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchSources();
    }, []);

    const getDatasetInfo = async (e, id) => {
        try {
            const response = await httpRequest.get(`/dataset/${id}`)
            onSelectChange(e, "dataset", response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }
    
    if (!chartData.dataset) return;
    return (
        <Grid container spacing={3}>
            <Grid item xs={12}>
                <TextField
                    required
                    id="name"
                    label="Name"
                    defaultValue="Unnamed"
                    value={chartData.name}
                    fullWidth
                    onChange={onFieldChange}
                    variant='standard'
                />
            </Grid>
            <Grid item xs={12}>
                <FormControl variant="standard" fullWidth >
                    <InputLabel id="dataset-select-label">Dataset</InputLabel>
                    <Select
                        labelId="dataset-select-label"
                        id="dataset_id"
                        value={chartData.dataset && chartData.dataset.name}
                        label="Dataset"
                        required
                        onChange={(e) => {
                            const datasetId = datasets.find(d => d.name === e.target.value).id;
                            getDatasetInfo(e, datasetId);
                        }}
                        renderValue={(selected) => (
                            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                <Chip label={selected} />
                            </Box>
                        )}
                    >
                        {datasets && datasets.map((datasets) => (
                            <MenuItem
                            key={datasets.id}
                            value={datasets.name}
                            >
                            {datasets.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </Grid>
            <Grid item xs={12}>
                <FormControl fullWidth>
                    <SelectValue 
                        id="xAxis"
                        options={columnsValues} 
                        value={xAxis}
                        label="X axis"
                        onChange={(e) => {
                            return onSelectChange(e, "chartSettings", {
                                ...chartSettings,
                                "xAxis": e.target.value,
                            })
                    }}
                    />
                </FormControl>
            </Grid>
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
                                options={columnsValues} 
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
                        options={columnsValues} 
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
            <Grid item xs={12}>
                <TextField
                    id="where"
                    label="Conditions"
                    value={where}
                    fullWidth
                    onChange={(event) => onSelectChange(event, "chartSettings", {
                        ...chartSettings,
                        "where": event.target.value,
                    })}
                    variant='standard'
                    multiline
                />
            </Grid>
        </Grid>
    )
}

export default BarSettings;