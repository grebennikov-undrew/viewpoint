import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  FormControl
} from '@mui/material';

import { useAlert } from '../AlertContext';
import { getData } from '../../service/httpQueries';
import { postData } from '../../service/httpQueries';
import SelectTags from '../basic/SelectTags';


const EditUserDialog = ({ user, open, onClose }) => {
    const [ data, setData ] = useState(user);
    const [ availRoles, setAvailRoles ] = useState([]);
    const { showAlert } = useAlert();
    const { id, roles } = data;

    useEffect(() => {
        const fetchRoles = async () => {
            getData("role/", showAlert, setAvailRoles);
        }
        fetchRoles();
    }, []);

    const handleFieldChange = (field, value) => {
        setData((oldData) => ({ ...oldData, [field]: value }));
    };

    const handleSave = () => {
        postData("user/", data, showAlert, setData, handleSuccessfulSave)
    }

    const handleSuccessfulSave = () => {
        onClose();
        showAlert("User saved");
    }

    return (
        <Dialog open={open} onClose={onClose}>
        <DialogTitle>Edit User</DialogTitle>
        <DialogContent>
            <TextField
            label="username"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={data.username}
            onChange={(e) => handleFieldChange('username', e.target.value)}
            />
            <TextField
            label="firstname"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={data.firstname}
            onChange={(e) => handleFieldChange('firstname', e.target.value)}
            />
            <TextField
            label="lastname"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={data.lastname}
            onChange={(e) => handleFieldChange('lastname', e.target.value)}
            />
            <TextField
            label="email"
            fullWidth
            margin="normal"
            variant='standard'
            size='small'
            required={true}
            value={data.email}
            onChange={(e) => handleFieldChange('email', e.target.value)}
            />
            <TextField
            label="Password"
            fullWidth
            type="password"
            margin="normal"
            variant='standard'
            size='small'
            helperText="Specify to change the password"
            value={data.password}
            onChange={(e) => handleFieldChange('password', e.target.value)}
            />
            <FormControl fullWidth margin='dense'>
                <SelectTags 
                    id="role"
                    options={availRoles.map(r => r.name)} 
                    values={roles.map(r => r.name)}
                    label="Roles *"
                    onSelectChange={(event, value) => {
                        const newRoles = availRoles.filter(r => value.indexOf(r.name) > -1);
                        handleFieldChange('roles', newRoles);
                    }}
                    variant='standard'
                    required={true}
                />
            </FormControl>
        </DialogContent>
        <DialogActions>
            <Button onClick={onClose} color='error'>Cancel</Button>
            <Button onClick={handleSave} color="primary">
            Save
            </Button>
        </DialogActions>
        </Dialog>
    );
};

export default EditUserDialog;
