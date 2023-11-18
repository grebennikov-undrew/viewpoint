import React, { useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import {
  Button,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import EditSourceDialog from '../components/source/EditSourceDialog';

const rows = [
  { id: 1, name: 'default', type: 'POSTGRESQL', dbname: 'viewpoint' },
  { id: 2, name: 'external', type: 'MYSQL', dbname: 'public' },
];

const Sources = () => {
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const [editDialogOpen, setEditDialogOpen] = useState(false);

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

    const handleDeleteConfirmed = () => {
        handleDeleteConfirmationClose();
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Name', width: 130 },
        { field: 'type', headerName: 'Type', width: 130 },
        { field: 'dbname', headerName: 'Database', width: 130 },
        {
        field: 'actions',
        headerName: 'Actions',
        width: 130,
        renderCell: (params) => (
            <div>
            <IconButton
                color="primary"
                aria-label="edit"
                onClick={() => handleEditClick(params.row)}
            >
                <EditIcon />
            </IconButton>
            <IconButton
                color="secondary"
                aria-label="delete"
                onClick={() => handleDeleteClick(params.row)}
            >
                <DeleteIcon />
            </IconButton>
            </div>
        ),
        },
    ];

    return (
        <div>
        <div style={{ height: 300, width: '100%' }}>
            <DataGrid
            rows={rows}
            columns={columns}
            pageSize={5}
            disableSelectionOnClick
            />
        </div>
        
        {editDialogOpen &&
        <EditSourceDialog
            source={selectedRow}
            open={editDialogOpen}
            onClose={handleEditDialogClose}
        />}


        {/* Delete Confirmation Dialog */}
        <Dialog
            open={deleteConfirmationOpen}
            onClose={handleDeleteConfirmationClose}
        >
            <DialogTitle>Confirm Delete</DialogTitle>
            <DialogContent>
            Are you sure you want to delete the selected row?
            </DialogContent>
            <DialogActions>
            <Button onClick={handleDeleteConfirmationClose}>Cancel</Button>
            <Button onClick={handleDeleteConfirmed} color="secondary">
                Delete
            </Button>
            </DialogActions>
        </Dialog>
        </div>
    );
};

export default Sources;
