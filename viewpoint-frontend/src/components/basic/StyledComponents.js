import React, { useState } from 'react';
import { DataGrid as MuiDataGrid } from '@mui/x-data-grid';
import { Box, Container, Paper, Stack, Typography, Button } from '@mui/material';
import { Search } from './SearchField';
import Add from '@mui/icons-material/Add';

export const DataGrid = (props) => {
    const { columns } = props;

    columns.forEach(c => {
        c.headerClassName = 'super-app-theme--header' ;
    });

    return (
    <MuiDataGrid
        {...props}
        initialState={{
        pagination: {
            paginationModel: { page: 0, pageSize: 10 },
        },
        }}
        pageSizeOptions={[10, 20, 50]}
        disableColumnMenu={true}
        rowHeight={45}
        columnHeaderHeight={45}
        sx={{
            border: "none",
        }}
        getRowClassName={(params) => `super-app-theme--row`}
        rowSpacingType='margin'
        disableRowSelectionOnClick={true}
        />)
}

export const MainDataGrid = (props) => {
    const {title, rows, filterField, onAddClick} = props;
    const [searchValue, setSearchValue] = useState('');

    const handleSearchChange = (event) => {
        setSearchValue(event.target.value);
    };

    const upTitle = title.charAt(0).toUpperCase() + title.slice(1);

    const filteredRows = rows && rows.filter(row =>
        row[filterField].toLowerCase().includes(searchValue.toLowerCase())
    );

    return (
        <Container maxWidth="xl" >
                <Stack
                    direction="row"
                    // justifyContent="left"
                    alignItems="center"
                    spacing={2}
                    sx={{mt: 6, mb: 6, height: 32}}
                >
                    <Typography variant="h3">
                        All {upTitle}
                    </Typography>
                    <Box sx={{width: 34, height: 25, backgroundColor: "#E5E7EB", borderRadius: 2, alignItems: "center"}}>
                        <Typography variant="h5" align='center' margin="3px">
                            {rows.length}
                        </Typography>
                    </Box>
                    <Stack direction="row" spacing={4} style={{marginLeft: "auto", height: "100%"}}> 
                        <Search title={title} onChange={handleSearchChange}/>
                        <Button 
                        variant='contained'
                        startIcon={<Add/>} 
                        sx={{fontSize: "80%"}}
                        onClick={onAddClick}
                        >
                            New
                        </Button>
                    </Stack>
                </Stack>
                <Paper elevation={3}
                    sx={{
                        border: "none",
                        backgroundColor: "white",
                        borderRadius: 3,
                        mb: "auto",
                        '& .super-app-theme--header': {
                            color: "#A1A1AA",
                            textTransform: "uppercase",
                            fontSize: 13,
                        },
                        '& .super-app-theme--row': {
                            border: "none",
                        },
                        '& .MuiDataGrid-withBorderColor': {
                            borderBottom: "1px solid",
                            borderColor: "#E4E4E7",
                        }
                    }}
                >
                    <DataGrid {...props} rows={filteredRows}/>
                </Paper>
        </Container>
    );
}