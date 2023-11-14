import React, { useState, useEffect } from 'react';
import { Fab, Popover, List, ListItem, ListItemText, IconButton, TextField, Typography } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

import { httpRequest } from '../../../service/httpRequest';

const fabStyle = {
  margin: 0,
  top: 'auto',
  right: 20,
  bottom: 20,
  left: 'auto',
  position: 'fixed',
};

const listStyle = {
    maxHeight: '350px', // Высота выпадающиего списка
    overflowY: 'auto',
    width: "300px"
};

const DropdownCheckboxList = (props) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [searchValue, setSearchValue] = useState('');
    const [chartList, setChartList] = useState([]);
    const { handleAddChart } = props;

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get('/chart/', {withCredentials: true});
                setChartList(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
    
        fetchData();
    }, []);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleSearchChange = (event) => {
        setSearchValue(event.target.value);
      };
    
    const filteredChartList = chartList.filter(option =>
        option["name"].toLowerCase().includes(searchValue.toLowerCase())
    );

    const open = Boolean(anchorEl);

    return (
        <div>
        <Fab color="primary" aria-label="add" style={fabStyle} onClick={handleClick} variant="extended">
            <AddIcon /> Add chart
        </Fab>
        <Popover
            open={open}
            anchorEl={anchorEl}
            onClose={handleClose}
            anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'right',
            }}
            transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
            }}
        >
            <Typography variant="h4" fontWeight={600} m={2} pb={0} mb={1}>
                Add chart to dashboard
            </Typography>
            <TextField
                label="Search"
                variant="filled"
                fullWidth
                margin="dense"
                onChange={handleSearchChange}
                />
            <List style={listStyle}>
            {filteredChartList.map((option) => (
                <ListItem key={option["id"]}>
                    <ListItemText primary={option["name"]} />
                    <IconButton edge="end" aria-label="add">
                    <AddCircleOutlineIcon />
                    </IconButton>
                </ListItem>
            ))}
            </List>
        </Popover>
        </div>
    );
};

export default DropdownCheckboxList;
