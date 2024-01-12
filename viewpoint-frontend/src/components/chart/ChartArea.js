import React, { useState, useEffect } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import Typography from '@mui/material/Typography';
import { IconButton } from '@mui/material';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Box from '@mui/material/Box';
import TableArea from './Table/TableArea';
import PieArea from './pie/PieArea';
import LineArea from './line/LineArea';
import BarArea from './bar/BarArea';
import Paper from '@mui/material/Paper';
import MoreVertIcon from '@mui/icons-material/MoreVert';

import { getData } from '../../service/httpQueries';
import { useAlert } from '../AlertContext';

const ChartArea = (props) => {
    const { showAlert } = useAlert();
    const { chartData } = props;
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const navigate = useNavigate();

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleExport = () => {
        getData(`chart/${chartData.id}/export`,showAlert)
        setAnchorEl(null);
    };

    const onEdit = () => {
        navigate(`/chart/${chartData.id}`)
    }

    return(
        chartData && chartData.data && 
        <Box 
            height={"100%"} 
            bgcolor={"white"} 
            component={Paper} 
            borderRadius={4}
            overflow={"hidden"}
            pt={1}
        >
            <div style={{display: "flex", alignItems: "center"}}>
                <Typography 
                    variant="h4" 
                    pl={4} pb={0} 
                    fontWeight={600} 
                    onClick={onEdit}
                    style={{cursor: "pointer"}}
                    // style={{display: "inline"}}
                >
                    {chartData.name}
                </Typography>
                <IconButton
                    id="basic-button"
                    aria-controls={open ? 'basic-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    onClick={handleClick}
                    // style={{display: "inline", marginLeft: "auto"}}
                    // style={{marginLeft: "auto"}}
                    style={{marginLeft: "auto"}}
                >
                    <MoreVertIcon/>
                </IconButton>
                    <Menu
                        id="basic-menu"
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleClose}
                        MenuListProps={{
                        'aria-labelledby': 'basic-button',
                        }}
                    >
                        <MenuItem onClick={onEdit}>Edit</MenuItem>
                        <MenuItem onClick={handleExport}>Export to CSV</MenuItem>
                    </Menu>
            </div>
                <Box height= {"100%"}>
                    <Chart {...props}/>
                </Box>
        </Box>
    )
}

const Chart = (props) => {
    const { chartData } = props;

    if (chartData.chartType.toLowerCase() === "table") {
        return (<TableArea {...props}/>);
    } else if (chartData.chartType.toLowerCase() === "pie") {
        return (<PieArea {...props}/>)
    } else if (chartData.chartType.toLowerCase() === "line") {
        return (<LineArea {...props}/>)
    } else if (chartData.chartType.toLowerCase() === "bar") {
        return (<BarArea {...props}/>)
    }
}

export default ChartArea;