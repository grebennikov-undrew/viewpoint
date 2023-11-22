import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { IconButton, Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import AddCircleIcon from '@mui/icons-material/AddCircle'

import { useAlert } from '../components/AlertContext';
import { getData } from '../service/httpQueries';
import { RowActions } from '../components/basic/RowActions';
import EditUserDialog from '../components/user/EditUserDialog';
import DeleteDialog from '../components/basic/DeleteDialog';
import EditSourceDialog from '../components/source/EditSourceDialog';


const Users = () => {
    const { showAlert } = useAlert();
    const [rows, setRows] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const [editDialogOpen, setEditDialogOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            getData("user/", showAlert, setRows);
        }
        fetchData();
    }, [deleteConfirmationOpen, editDialogOpen]);

    const handleAddClick = () => {
        setSelectedRow({id: null, roles: []});
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


    const columns = rows ? [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'username', headerName: 'Username', width: 150 },
        { field: 'firstname', headerName: 'First name', width: 150 },
        { field: 'lastname', headerName: 'Last name', width: 150 },
        { field: 'email', headerName: 'Email', width: 300 },
        { headerName: 'Roles', width: 200, valueGetter: (params) => params.row.roles ? `${params.row.roles.map(p => p.name).join(", ")}` : "" },
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
    ] : [];

    return (
        <Container maxWidth="xl">
        <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
            <Typography variant="h2" >
                Users
            </Typography>
            <IconButton
              size="large"
              aria-label="Add user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              style={customButtonStyle}
              onClick={handleAddClick}
              >
              <AddCircleIcon/>
            </IconButton>
            </div>
            <div>
            <div style={{ height: "calc(100vh - 146px)", width: '100%' }}>
                <DataGrid
                rows={rows}
                columns={columns}
                pageSize={15}
                disableSelectionOnClick
                />
            </div>
        
            {editDialogOpen &&
            <EditUserDialog
                user={selectedRow}
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
            </div>
        </Container>
    );
};

const customButtonStyle = {
    margin: 'auto 0', // Задаем отступы
    padding: '0 12px',
  };

export default Users;
