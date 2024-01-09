import React, { useState, useEffect } from 'react';
import { MainDataGrid } from '../components/basic/StyledComponents';
import { Container } from '@mui/material';

import { useAlert } from '../components/AlertContext';
import { httpRequest } from '../service/httpRequest';
import DeleteDialog from '../components/basic/DeleteDialog';
import { RowActions } from '../components/basic/RowActions';
import EditSourceDialog from '../components/source/EditSourceDialog';


const Sources = () => {
    const { showAlert } = useAlert();
    const [data, setData] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const [editDialogOpen, setEditDialogOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get('/source/', {withCredentials: true});
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

    const handleAddClick = () => {
        setSelectedRow({id: null});
        setEditDialogOpen(true);
    };

    const handleEditClick = (row) => {
        setSelectedRow(row);
        setEditDialogOpen(true);
    };

    const handleDeleteClick = (row) => {
        setSelectedRow(row);
        setDeleteConfirmationOpen(true);
    };

    const handleEditDialogClose = () => {
        setEditDialogOpen(false);
    };

    const handleDeleteConfirmationClose = () => {
        setDeleteConfirmationOpen(false);
    };


    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Name', width: 200 },
        { field: 'type', headerName: 'Type', width: 130 },
        { field: 'dbname', headerName: 'Database', width: 200 },
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
                title="sources"
                filterField="name"
                onAddClick={handleAddClick}
            />
            {editDialogOpen &&
            <EditSourceDialog
                source={selectedRow}
                open={editDialogOpen}
                onClose={handleEditDialogClose}
            />}
            {deleteConfirmationOpen && 
            <DeleteDialog
                open={deleteConfirmationOpen}
                deleteUri={"user"}
                id={selectedRow.id}
                onClose={handleDeleteConfirmationClose}
            />
            }
        </Container>
    );
};

export default Sources;
