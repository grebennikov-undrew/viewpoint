import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import { MainDataGrid } from '../components/basic/StyledComponents';
import { Container } from '@mui/material';

import { useAlert } from '../components/AlertContext';
import { httpRequest } from '../service/httpRequest';
import DeleteDialog from '../components/basic/DeleteDialog';
import { RowActions } from '../components/basic/RowActions';

const Dataset = () => {
    const { showAlert } = useAlert();
    const [data, setData] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get('/dataset/', {withCredentials: true});
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
        navigate(`/dataset/${row.id}`)
    };

    const handleAddClick = (row) => {
        setSelectedRow(row);
        navigate(`/dataset/new`)
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Name', width: 150 },
        { field: 'author', headerName: 'Author', width: 150, valueGetter: (params) => `${params.row.user}`},
        { field: 'source', headerName: 'Source', width: 150, valueGetter: (params) => `${params.row.source.name}` },
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
                title="datasets"
                filterField="name"
                onAddClick={handleAddClick}
            />
            {deleteConfirmationOpen && 
            <DeleteDialog
                open={deleteConfirmationOpen}
                deleteUri={"dataset"}
                id={selectedRow.id}
                onClose={handleDeleteConfirmationClose}
            />
            }
        </Container>
    );
}

export default Dataset;