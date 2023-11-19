import React, { useState } from 'react';
import {
  Button,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';

import { useAlert } from '../AlertContext';
import { deleteEntity } from '../../service/httpQueries';

const DeleteDialog = ({open, deleteUri, id, onClose}) => {
    const { showAlert } = useAlert();

    const handleDeleteConfirmed = (e) => {
        e.preventDefault();
        deleteEntity(deleteUri,id,showAlert);
        onClose()
    }

    return (
        <Dialog
            open={open}
            onClose={onClose}
        >
            <DialogTitle>Confirm Delete</DialogTitle>
            <DialogContent>
            Are you sure you want to delete the selected row?
            </DialogContent>
            <DialogActions>
            <Button onClick={onClose}>Cancel</Button>
            <Button onClick={handleDeleteConfirmed} color="error">
                Delete
            </Button>
            </DialogActions>
        </Dialog>
    )
}

export default DeleteDialog;