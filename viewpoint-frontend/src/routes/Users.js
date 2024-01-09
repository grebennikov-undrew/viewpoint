import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import { MainDataGrid } from '../components/basic/StyledComponents';
import { Container } from '@mui/material';

import { useAlert } from '../components/AlertContext';
import { httpRequest } from '../service/httpRequest';
import EditUserDialog from '../components/user/EditUserDialog';
import DeleteDialog from '../components/basic/DeleteDialog';
import { RowActions } from '../components/basic/RowActions';


const Users = () => {
    const { showAlert } = useAlert();
    const [data, setData] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const [editDialogOpen, setEditDialogOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get('/user/', {withCredentials: true});
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


    const columns = [
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
    ];

    return (
        <Container 
            maxWidth="xl" 
            sx={{height: "calc(100vh - 51px)"}}
        >
            <MainDataGrid
                rows={data}
                columns={columns}
                title="users"
                filterField="username"
                onAddClick={handleAddClick}
            />
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
        </Container>
    );
};


export default Users;
