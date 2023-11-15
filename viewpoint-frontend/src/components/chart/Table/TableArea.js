import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { height } from '@mui/system';

const TableArea = ({chartData}) => {
    const { rows, columns, data } = chartData;
    const { chartSettings } = chartData;
    const { dimensions } = chartSettings;

    return ( data &&
        <div style={{overflow: "scroll", height: "100%", marginTop: "10px"}}>
            <TableContainer >
                <Table size="small" aria-label="sticky table" stickyHeader >
                    <TableHead>
                        <TableRow>
                            {columns && columns.map((column) => (
                                <TableCell >{column}</TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/* {Object.keys(data).forEach((rowKey, rowIdx) => { */}
                        {Object.keys(data).map(key => {
                            return (
                                <TableRow
                                    key={key}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                    >
                                        {columns.map((column) => (
                                            <TableCell>{data[key][column]}</TableCell>
                                        ))}
                                </TableRow>
                        )})}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    )
}

export default TableArea;