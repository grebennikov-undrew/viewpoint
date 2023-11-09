import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

const TableArea = ({chartData, chartResult}) => {
    if (!chartResult) return;

    const { rows, coltypes } = chartResult;
    const { chartSettings } = chartData;
    const { dimensions } = chartSettings;

    
    return (
        <TableContainer component={Paper} style={{backgroundColor:"white"}}>
            <Table sx={{ minWidth: 650}} size="small" aria-label="a dense table">
                <TableHead>
                    <TableRow>
                        {dimensions.map((column) => (
                            <TableCell>{column.label}</TableCell>
                        ))}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row, idx) => (
                        <TableRow
                        key={idx}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            {dimensions.map((column) => (
                                <TableCell>{row[column.label]}</TableCell>
                            ))}
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default TableArea;