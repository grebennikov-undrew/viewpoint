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
        chartData && chartData.datasetId &&
        <Box 
            height={"100%"} 
            bgcolor={"white"} 
            component={Paper} 
            borderRadius={4}
            overflow={"hidden"}
        >
                <Typography variant="h4" pt={2} pl={2} pb={0} fontWeight={600}>{chartData.name}</Typography>
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