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
import Chip from '@mui/material/Chip';
import IconButton from '@mui/material/IconButton';
import Stack from '@mui/material/Stack';

import GridOnIcon from '@mui/icons-material/GridOn';
import BarChartIcon from '@mui/icons-material/BarChart';
import SsidChartIcon from '@mui/icons-material/SsidChart';
import PieChartIcon from '@mui/icons-material/PieChart';

import { httpRequest } from '../../service/httpRequest';
import SelectTags from './Components/SelectTags';

const SettingsArea = ({chartData, chartResult, onFieldChange, onSelectChange}) => {
    const [datasets, setDatasets] = useState();
    const { chartSettings, dataset } = chartData;
    const { xColumns } = chartSettings;
    const { columns } = dataset;

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
    
    if (!chartData.dataset) return;
    return (
        <Grid container spacing={3}>
            <Grid item xs={12}>
                <Stack direction="row" spacing={1}>
                    <IconButton aria-label="delete" color="primary">
                        <GridOnIcon />
                    </IconButton>
                    <IconButton aria-label="delete" color="disabled">
                        <PieChartIcon />
                    </IconButton>
                    <IconButton color="disabled" aria-label="add an alarm">
                        <BarChartIcon />
                    </IconButton>
                    <IconButton color="disabled" aria-label="add to shopping cart">
                        <SsidChartIcon />
                    </IconButton>
                </Stack>
            </Grid>
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
                        onChange={(e) => onSelectChange(e, "dataset", {"id": datasets.find(d => d.name === e.target.value).id, "name": e.target.value})}
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
                        options={columns} 
                        values={xColumns} 
                        onSelectChange={(event, value) => onSelectChange(event, "chartSettings", {
                        ...chartSettings,
                        "xColumns": value,
                    })}/>
                </FormControl>
            </Grid>
        </Grid>
    )
}

export default SettingsArea;