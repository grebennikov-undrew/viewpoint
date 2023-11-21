import React, { useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import { RowActions } from '../components/basic/RowActions';
import EditSourceDialog from '../components/source/EditSourceDialog';
import DeleteDialog from '../components/basic/DeleteDialog';

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

        {deleteConfirmationOpen && 
        <DeleteDialog
            open={deleteConfirmationOpen}
            deleteUri={"source"}
            id={selectedRow.id}
            onClose={handleDeleteConfirmationClose}
        />
        }
        </div>
    );
};

export default Sources;