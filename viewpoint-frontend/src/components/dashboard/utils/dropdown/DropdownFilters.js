import React, { useState, useEffect } from 'react';
import { Fab, Popover, List, ListItem, ListItemText, IconButton, TextField, Typography, Checkbox } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

import { httpRequest } from '../../../../service/httpRequest';
import { TypeIcon } from '../../../basic/TypeIcon';

const fabStyle = {
//   margin: 0,
//   top: 'auto',
//   right: 20,
//   bottom: 20,
//   left: 'auto',
//   position: 'fixed',
};

const listStyle = {
    maxHeight: '350px', // Высота выпадающиего списка
    overflowY: 'auto',
    width: "300px"
};

const DropdownFilters = (props) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [searchValue, setSearchValue] = useState('');
    const [columnList, setColumnList] = useState([]);
    const { dashboardData, handleChangeFilter } = props;
    const { charts, filters } = dashboardData;

    const handleClick = (e) => {
        e.preventDefault();

        setAnchorEl(e.currentTarget);
        const body = charts.map(c => c.datasetId);
        httpRequest.post(`/dataset/columns`, body)
            .then(response => {
                setColumnList(response.data);
            })
            .catch(error => {
                console.error('Error submitting data:', error);
            });
    }

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleSearchChange = (event) => {
        setSearchValue(event.target.value);
      };
    
    const filteredColumnList = columnList.filter(option =>
        option["name"].toLowerCase().includes(searchValue.toLowerCase())
    );

    const open = Boolean(anchorEl);

    return (
        <div>
        <Fab color="primary" aria-label="add" style={fabStyle} onClick={handleClick} variant="extended">
            <AddIcon style={{marginRight: "5px"}}/>Add filter
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
                Add filter to dashboard
            </Typography>
            <TextField
                label="Search"
                variant="filled"
                fullWidth
                margin="dense"
                onChange={handleSearchChange}
                />
            <List style={listStyle}>
            {filteredColumnList.map((filter) => {
                const checked = filters && filters.find(existing => existing.name === filter.name && existing.type === filter.type);
                return(
                <ListItem key={filter["name"]} >
                    {/* <Checkbox
                        onChange={(e) => {
                            handleChangeFilter(e, filter)
                        }}
                        checked={checked}
                    /> */}
                    <CorrectCheckBox handleChangeFilter={handleChangeFilter} checked={checked} filter={filter}/>
                    <ListItemText primary={filter["name"]} />
                    <TypeIcon type={filter.type} color={'disabled'} />
                </ListItem>
            )})}
            </List>
        </Popover>
        </div>
    );
};

const CorrectCheckBox = ({handleChangeFilter, checked, filter}) => {
    // const [checkedState, setChecked] = useState(checked);

    return <Checkbox
        onChange={(e) => {
            handleChangeFilter(e, filter);
            // setChecked(e.target.checked);
        }}
        // checked={checkedState}
        defaultChecked={checked}
    />
}

export default DropdownFilters;
