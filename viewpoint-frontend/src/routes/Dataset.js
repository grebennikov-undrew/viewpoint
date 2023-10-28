import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';
import { Container } from '@mui/material';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import AddCircleIcon from '@mui/icons-material/AddCircle';

const customButtonStyle = {
    margin: 'auto 0', // Задаем отступы
    padding: '0 12px',
};

const Dataset = () => {
    const [data, setData] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/dataset/')
                setData(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
    
        fetchData();
    }, []);

    const handleRowClick = (
        params, // GridRowParams
        event, // MuiEvent<React.MouseEvent<HTMLElement>>
        details, // GridCallbackDetails
    ) => {
        navigate(`/dataset/${params.row.id}`)
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Name', width: 150 },
        { field: 'author', headerName: 'Author', width: 150, valueGetter: (params) => `${params.row.user.username}`},
        { field: 'source', headerName: 'Source', width: 150, valueGetter: (params) => `${params.row.source.name}` },
    ];

    return (
        <Container maxWidth="xl">
            <div style={{ display: 'flex', alignItems: 'center', paddingTop: '20px', paddingBottom: '5px' }}>
                <Typography variant="h2" >
                    Datasets
                </Typography>
                {/* <Button variant="text">Add</Button> */}
                <IconButton
                size="large"
                aria-label="Add dataset"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                style={customButtonStyle}
                href='/dataset/new'
                >
                <AddCircleIcon/>
                </IconButton>
            </div>
            <DataGrid
                rows={data}
                columns={columns}
                initialState={{
                pagination: {
                    paginationModel: { page: 0, pageSize: 10 },
                },
                }}
                pageSizeOptions={[10, 20, 50]}
                // checkboxSelection
                onRowClick={handleRowClick}
                disableColumnMenu={true}
            />
        </Container>
    );
}

export default Dataset;