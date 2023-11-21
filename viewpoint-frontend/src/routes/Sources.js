import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { IconButton, Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import AddCircleIcon from '@mui/icons-material/AddCircle'

import { useAlert } from '../components/AlertContext';
import { getList } from '../service/httpQueries';
import { RowActions } from '../components/basic/RowActions';
import EditSourceDialog from '../components/source/EditSourceDialog';
import DeleteDialog from '../components/basic/DeleteDialog';


const Sources = () => {
    const { showAlert } = useAlert();
    const [rows, setRows] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null);
    const [deleteConfirmationOpen, setDeleteConfirmationOpen] = useState(false);
    const [editDialogOpen, setEditDialogOpen] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            getList("source/", showAlert, setRows);
        }
        fetchData();
    }, [deleteConfirmationOpen]);

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
        <Container maxWidth="xl">
        <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
            <Typography variant="h2" >
                Sources
            </Typography>
            {/* <Button variant="text">Add</Button> */}
            <IconButton
              size="large"
              aria-label="Add source"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              style={customButtonStyle}
              onClick={handleAddClick}
              >
              <AddCircleIcon/>
            </IconButton>
            </div>
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
        </Container>
    );
};

const customButtonStyle = {
    margin: 'auto 0', // Задаем отступы
    padding: '0 12px',
  };

export default Sources;
