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

const TableSettingsArea = ({chartData, chartResult, onFieldChange, onSelectChange}) => {
    const [ datasets, setDatasets] = useState();
    const { chartSettings, dataset, chartType } = chartData;
    const { xColumns, where, orderBy, desc } = chartSettings;
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
                        <SelectTags 
                        options={columnsValues} 
                        values={xColumns} 
                        label="Columns"
                        onSelectChange={(event, value) => onSelectChange(event, "chartSettings", {
                        ...chartSettings,
                        "xColumns": value,
                    })}/>
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
            <Grid item xs={12}>
                <FormControl fullWidth>
                        <SelectTags 
                        options={columnsValues} 
                        values={orderBy} 
                        label="Order by"
                        onSelectChange={(event, value) => onSelectChange(event, "chartSettings", {
                        ...chartSettings,
                        "orderBy": value,
                    })}/>
                </FormControl>
            </Grid>
            {orderBy && orderBy.length > 0 && <Grid item xs={12}>
                <FormControl variant="standard" fullWidth >
                    <InputLabel id="desc-select-label">Sort</InputLabel>
                    <Select
                        labelId="desc-select-label"
                        id="desc-id"
                        label="Sort"
                        required
                        value={desc ? "descending" : "ascending"}
                        onChange={
                            (e) => onSelectChange(e, "chartSettings", {
                                    ...chartSettings,
                                    "desc": e.target.value === "descending" ? true : false,
                                })
                        }
                    >
                        <MenuItem value="ascending">ascending</MenuItem>
                        <MenuItem value="descending">descending</MenuItem>
                    </Select>
                </FormControl>
            </Grid>}
        </Grid>
    )
}

export default TableSettingsArea;