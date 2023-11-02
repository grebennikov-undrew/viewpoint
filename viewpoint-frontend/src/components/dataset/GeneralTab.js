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

const GeneralTab = ({datasetData, onFieldChange, onSelectChange}) => {
    const [sources, setSources] = useState();

    useEffect(() => {
        const fetchSources = async () => {
            try {
                const response = await httpRequest.get(`/source/`)
                setSources(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchSources();
    }, []);
    
    if (!datasetData.source) return;
    return (
        <Grid container>
            <Grid container xs={5}>
                <Grid item xs={12}>
                    <TextField
                        required
                        id="name"
                        label="Name"
                        defaultValue="Unnamed"
                        value={datasetData.name}
                        fullWidth
                        sx={{m: 1}}
                        onChange={onFieldChange}
                    />
                </Grid>
                <Grid item xs={12}>
                    <FormControl sx={{m:1}} fullWidth>
                        <InputLabel id="source-select-label">Source</InputLabel>
                        <Select
                            labelId="source-select-label"
                            id="source_id"
                            value={datasetData.source && datasetData.source.name}
                            label="Source"
                            required
                            onChange={(e) => onSelectChange(e, "source", {"id": sources.find(s => s.name === e.target.value).id, "name": e.target.value})}
                        >
                            {sources && sources.map((source) => (
                                <MenuItem
                                key={source.id}
                                value={source.name}
                                >
                                {source.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={6}>
                    <TextField
                        id="user"
                        label="Author"
                        sx={{ m: 1}}
                        disabled
                        fullWidth
                        defaultValue="You"
                        value={datasetData.user && datasetData.user.username}
                    />
                </Grid>
                <Grid item xs={6}>
                    <TextField
                        id="updatedOn"
                        label="Updated"
                        sx={{ m: 1}}
                        disabled
                        fullWidth
                        value={datasetData.updatedOn}
                    />
                </Grid>
            </Grid>
        </Grid>
    )
}

export default GeneralTab;