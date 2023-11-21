import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import { DataGrid } from '@mui/x-data-grid';
import { Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import AddCircleIcon from '@mui/icons-material/AddCircle';

import { useAlert } from '../components/AlertContext';
import { httpRequest } from '../service/httpRequest';
import DeleteDialog from '../components/basic/DeleteDialog';
import { RowActions } from '../components/basic/RowActions';

const customButtonStyle = {
    margin: 'auto 0', // Задаем отступы
    padding: '0 12px',
};

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

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Name', width: 150 },
        { field: 'type', headerName: 'Type', width: 100, valueGetter: (params) => `${params.row.chartType}`},
        { field: 'author', headerName: 'Author', width: 150, valueGetter: (params) => `${params.row.username}`},
        { field: 'dataset', headerName: 'Dataset', width: 150, valueGetter: (params) => `${params.row.datasetName}`},
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
        <Container maxWidth="xl">
            <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
                <Typography variant="h2" >
                    Charts
                </Typography>
                {/* <Button variant="text">Add</Button> */}
                <IconButton
                size="large"
                aria-label="Add chart"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                style={customButtonStyle}
                href='/chart/new'
                >
                <AddCircleIcon/>
                </IconButton>
            </div>
            <DataGrid
                rows={data}
                columns={columns}
                initialState={{
                pagination: {
                    paginationModel: { page: 0, pageSize: 10 },
                },
                }}
                pageSizeOptions={[10, 20, 50]}
                disableColumnMenu={true}
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