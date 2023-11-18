import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
} from '@mui/material';

import { httpRequest } from '../../service/httpRequest';
import { useAlert } from '../AlertContext';

const EditSourceDialog = ({ source, open, onClose }) => {
    const [ editedSource, setEditedSource ] = useState({});
    const { showAlert } = useAlert();
    const { id } = source;

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                try {
                    const response = await httpRequest.get(`/source/${id}`)
                    if (response.status === 400) {
                        showAlert('Error: ' + response.data, "error");
                        return;
                    } 
                    setEditedSource(response.data);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }
            fetchData();
        }
    }, []);

    const handleFieldChange = (field, value) => {
        setEditedSource((prevSource) => ({ ...prevSource, [field]: value }));
    };

    const handleValidate = () => {
        httpRequest.post(`/source/validate`, editedSource)
            .then(response => {
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                showAlert('Connection validated')
            })
            .catch(error => {
                console.log(error);
            });
    }

    const handleSave = () => {
        httpRequest.post(`/source/`, editedSource)
            .then(response => {
                if (response.status === 400) {
                    showAlert('Error: ' + response.data, "error");
                    return;
                } 
                showAlert('Connection saved')
            })
            .catch(error => {
                console.log(error);
            });
    }

    return (
        <Dialog open={open} onClose={onClose}>
        <DialogTitle>Edit Source</DialogTitle>
        <DialogContent>
            <TextField
            label="Name"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.name}
            onChange={(e) => handleFieldChange('name', e.target.value)}
            />
            <TextField
            label="Type"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.type}
            onChange={(e) => handleFieldChange('type', e.target.value)}
            />
            <TextField
            label="Address"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.netloc}
            onChange={(e) => handleFieldChange('netloc', e.target.value)}
            />
            <TextField
            label="Port"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.port}
            onChange={(e) => handleFieldChange('port', e.target.value)}
            />
            <TextField
            label="Database"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.dbname}
            onChange={(e) => handleFieldChange('dbname', e.target.value)}
            />
            <TextField
            label="Parameters"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            value={editedSource.params}
            onChange={(e) => handleFieldChange('params', e.target.value)}
            />
            <TextField
            label="Username"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.username}
            onChange={(e) => handleFieldChange('username', e.target.value)}
            />
            <TextField
            label="Password"
            fullWidth
            type="password"
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={editedSource.password}
            onChange={(e) => handleFieldChange('password', e.target.value)}
            />
        </DialogContent>
        <DialogActions>
            <Button onClick={onClose} color='error'>Cancel</Button>
            <Button onClick={handleValidate} color="primary">
            Validate
            </Button>
            <Button onClick={handleSave} color="primary">
            Save
            </Button>
        </DialogActions>
        </Dialog>
    );
};

export default EditSourceDialog;
