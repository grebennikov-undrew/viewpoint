import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';

const ResultTab = ({tableData}) => {
    
    const getColumn = (column) => {
        return {
            field: column,
            headerName: column
        }
    }

    if (!tableData) return;
    return (
        <DataGrid
            // getRowId={tableData && tableData.rows.map((row, idx) => idx)}
            getRowId={(row) => tableData.rows.indexOf(row)}
            rows={tableData && tableData.rows}
            columns={tableData && Object.keys(tableData.coltypes).map(c => getColumn(c))}
            initialState={{
            pagination: {
                paginationModel: { page: 0, pageSize: 10 },
            },
            }}
            pageSizeOptions={[10, 20, 50]}
            // checkboxSelection
            
            disableColumnMenu={true}
        />
    )
}

export default ResultTab;