import * as React from 'react';
import Paper from '@mui/material/Paper';
import InputBase from '@mui/material/InputBase';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import SearchIcon from '@mui/icons-material/Search';
import DirectionsIcon from '@mui/icons-material/Directions';
import { Box } from '@mui/material';

export function Search(props) {
    const {title, onChange, backgroundColor} = props;
    return (
        <Box
        sx={{display: 'flex', alignItems: 'center', borderRadius: 100, height: "100%", border: "1px solid", borderColor: "#CCCCCC", backgroundColor: backgroundColor || "white"}}
        >
        <IconButton type="button" aria-label="search" >
            <SearchIcon sx={{width: "80%"}} color='disabled'/>
        </IconButton>
        <InputBase
            sx={{ 
                ml: 1,
                flex: 1,
                fontSize: 14,
                }}
            placeholder={`Search ${title || ''}...`}
            onChange={onChange}
        />
        </Box>
    );
}