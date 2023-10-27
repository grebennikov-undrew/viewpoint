import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { Button, Container } from '@mui/material';
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
import { DataGrid } from '@mui/x-data-grid';
import SendIcon from '@mui/icons-material/Send';

const ResultTab = ({datasetData, handleSelectChange}) => {
    const [data, setData] = useState();
    const [filters, setFilters] = useState({});

    const handleExecuteQuery = (event) => {
        event.preventDefault();
        const queryData = {
            "sqlQuery": datasetData.sqlQuery,
            "sourceId": datasetData.source.id,
            "parameters": datasetData.parameters,
            "paramValues": filters
        };
        const fetchData = async () => axios.post(`http://localhost:8080/api/dataset/execute`, queryData)
            .then(response => {
            setData(response.data);
            handleSelectChange(null, "columns", Object.keys(response.data.coltypes).map(k => {return {"name": k, "type": response.data.coltypes[k] }}));
            })
            .catch(error => {
            console.error('Error submitting data:', error);
        });
        fetchData();
    }

    const handleFilterChange = (e, name, value) => {
        filters[name] = value;
    }
    
    const getColumn = (column) => {
        return {
            field: column,
            headerName: column
        }
    }

    // const columns = [
    //     { field: 'id', headerName: 'ID', width: 70 },
    //     { field: 'name', headerName: 'Name', width: 150 },
    //     { field: 'author', headerName: 'Author', width: 150, valueGetter: (params) => `${params.row.user.username}`},
    //     { field: 'source', headerName: 'Source', width: 150, valueGetter: (params) => `${params.row.source.name}` },
    // ];

    return (
        <div>
            <Grid container >
                <Grid container paddingRight={5} xs={3}>
                    <Grid item xs={12}>
                        {datasetData.parameters && datasetData.parameters.map(parameter => <Filter parameter={parameter} handleFilterChange={handleFilterChange} filterValue={filters[parameter.name]} sourceId={datasetData.source.id}/>)}
                        <Button startIcon={<SendIcon />} variant="contained" fullWidth onClick={handleExecuteQuery}>Execute query</Button>
                    </Grid>
                </Grid>
                <Grid container xs={9}>
                    {data && <DataGrid
                        rows={data && data.rows}
                        columns={data && Object.keys(data.coltypes).map(c => getColumn(c))}
                        initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 10 },
                        },
                        }}
                        pageSizeOptions={[10, 20, 50]}
                        // checkboxSelection
                        
                        disableColumnMenu={true}
                    />}
                </Grid>
            </Grid>
        </div>
    )
}

export default ResultTab;