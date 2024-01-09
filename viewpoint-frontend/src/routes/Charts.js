import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import { MainDataGrid } from '../components/basic/StyledComponents';
import { Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import AddCircleIcon from '@mui/icons-material/AddCircle';

import { useAlert } from '../components/AlertContext';
import { httpRequest } from '../service/httpRequest';
import DeleteDialog from '../components/basic/DeleteDialog';
import { RowActions } from '../components/basic/RowActions';

const Chart = () => {
    const { showAlert } = useAlert();
    const [data, setData] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get('/chart/', {withCredentials: true});
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                setData(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
    
        fetchData();
    }, []);

    const handleDeleteClick = (row) => {
        setSelectedRow(row);
        setDeleteConfirmationOpen(true);
    };

    const handleDeleteConfirmationClose = () => {
        setDeleteConfirmationOpen(false);
    };

    const handleEditClick = (row) => {
        setSelectedRow(row);
        navigate(`/chart/${row.id}`)
    };

    const handleAddClick = (row) => {
        setSelectedRow(row);
        navigate(`/chart/new`)
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 70,},
        { field: 'name', headerName: 'Name', flex: 1},
        { field: 'type', headerName: 'Type', width: 100, valueGetter: (params) => `${params.row.chartType}`},
        { field: 'author', headerName: 'Author', width: 150, valueGetter: (params) => `${params.row.username}`},
        { field: 'dataset', headerName: 'Dataset', flex: 1, valueGetter: (params) => `${params.row.datasetName}`},
        { field: 'source', headerName: 'Source', width: 150, valueGetter: (params) => `${params.row.sourceName}` },
        {
            field: 'actions',
            headerName: 'Actions',
            width: 130,
            renderCell: (params) => (
                <RowActions 
                    onEdit = {() => handleEditClick(params.row)}
                    onDelete={() => handleDeleteClick(params.row)}
                />
            ),
            },
    ];

    return (
        <Container 
            maxWidth="xl" 
            sx={{height: "calc(100vh - 51px)"}}
        >
            <MainDataGrid
                rows={data}
                columns={columns}
                title="charts"
                filterField="name"
                onAddClick={handleAddClick}
            />
            {deleteConfirmationOpen && 
            <DeleteDialog
                open={deleteConfirmationOpen}
                deleteUri={"chart"}
                id={selectedRow.id}
                onClose={handleDeleteConfirmationClose}
            />
            }
        </Container>
    );
}

export default Chart;