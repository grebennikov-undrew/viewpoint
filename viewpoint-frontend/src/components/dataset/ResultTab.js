import React, { useState, useEffect } from 'react';
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
import dayjs from 'dayjs';
import { httpRequest } from '../../service/httpRequest';

const ResultTab = ({tableData}) => {
    
    const getColumn = (column) => {
        return {
            field: column,
            headerName: column
        }
    }

    if (!tableData) return;
    return (
        <DataGrid
            rows={tableData && tableData.rows}
            columns={tableData && Object.keys(tableData.coltypes).map(c => getColumn(c))}
            initialState={{
            pagination: {
                paginationModel: { page: 0, pageSize: 10 },
            },
            }}
            pageSizeOptions={[10, 20, 50]}
            // checkboxSelection
            
            disableColumnMenu={true}
        />
    )
}

export default ResultTab;