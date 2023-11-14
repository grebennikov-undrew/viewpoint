import React, { useState, useEffect } from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import TableArea from './Table/TableArea';
import PieArea from './pie/PieArea';
import LineArea from './line/LineArea';
import BarArea from './bar/BarArea';
import Paper from '@mui/material/Paper';

const ChartArea = (props) => {
    const { chartData } = props;

    return(
        chartData && 
        <Box 
            height={"100%"} 
            bgcolor={"white"} 
            p={2} 
            component={Paper} 
            // elevation={1} 
            borderRadius={4}
            // style={{paddingBottom: "20px"}}
        >
            <Typography variant="h4" fontWeight={600}>{chartData.name}</Typography>
                <Chart {...props}/>
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