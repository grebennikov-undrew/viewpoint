import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, IconButton, Paper, InputBase, TextField, Stack, FormLabel } from '@mui/material';
import Box from '@mui/material/Box';
import Slider from '@mui/material/Slider';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import { Button } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';

import { ToggleDateRange } from '../../basic/ToggleDateRange';
import SelectTags from '../../basic/SelectTags';
import DatePicker from '../../basic/DatePicker';
import { httpRequest } from '../../../service/httpRequest';

const Filters = (props) => {
    const { dashboardData, handleRefresh, mode } = props;
    const { filters, charts } = dashboardData;

    const [ filterValues, setFilterValues ] = useState();

    const handleSetFilterValues = (e, field, values) => {
        setFilterValues({
            ...filterValues,
            [field]: values,
        })
    }
    
    return ( filters && filters.length>0 &&
        <Grid item xs={2} height={"100%"} bgcolor={"white"} borderRadius={4} p={2} mt={2.5} component={Paper}>
            {/* <Typography variant="h4" fontWeight={600} mb={1}>Filters</Typography> */}
            <Grid container columns={1} spacing={3} >
                {filters.map(filter => {
                    if (filter.type === "String") {
                        return (
                            <Grid item xs={1}>
                                <Typography variant="h4" fontWeight={600} >{filter.name}</Typography>
                                {/* <FormLabel style={{marginLeft: "5px"}}>{filter.name}</FormLabel> */}
                                <StringFilter {...filter} handleSetFilterValues={handleSetFilterValues}/>
                            </Grid>
                        )
                    } else if (filter.type === "Double") {
                        return (
                            <Grid item xs={1} mr={"16px"}>
                                <Typography variant="h4" fontWeight={600} >{filter.name}</Typography>
                                <DoubleFilter {...filter} handleSetFilterValues={handleSetFilterValues} />
                            </Grid>
                        )
                    } else if (filter.type === "Timestamp") {
                        return (
                            <Grid item xs={1}>
                                <Typography variant="h4" fontWeight={600} >{filter.name}</Typography>
                                <TimestampFilter {...filter} handleSetFilterValues={handleSetFilterValues} />
                            </Grid>
                        )
                    }
                })}
                <Grid item xs={1}>
                    <Button 
                        startIcon={<FilterAltOutlinedIcon />} 
                        variant="contained" 
                        disabled={mode === "edit"}
                        fullWidth 
                        // onClick={handleExecuteQuery}
                        >
                        Apply
                    </Button>
                </Grid>
            </Grid>
        </Grid>
    )
}

const StringFilter = (filter) => {
    const { name, datasetId, values, handleSetFilterValues } = filter;
    const [ options, setOptions ] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get(`/dataset/${datasetId}/${name}/values`)
                setOptions(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchData();
    }, []);

    return (
        <SelectTags 
            options={options} 
            values={values}
            placeholder={"Options..."} 
            variant={'outlined'} 
            size={"small"}
            onSelectChange={(e, values) => handleSetFilterValues(e, name, values)}
        />
    )

}

const DoubleFilter = (filter) => {
    const { name, datasetId, values, handleSetFilterValues } = filter;
    const [ bounds, setBounds ] = useState([-Infinity, Infinity]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get(`/dataset/${datasetId}/${name}/bounds`)
                setBounds([response.data.min_value, response.data.max_value]);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchData();
    }, []);

    return (
        <Slider
            style={{marginLeft: "10px"}}
            aria-label="small"
            size="small"
            getAriaLabel={() => `range-${name}`}
            defaultValue={bounds}
            value={values}
            marks={[{value: bounds[0], label: bounds[0]}, {value: bounds[1], label: bounds[1]}]}
            min={bounds[0]}
            max={bounds[1]}
            onChange={(e, values) => handleSetFilterValues(e, name, values)}
            valueLabelDisplay="auto"
            // components={{
            //     ValueLabel: ValueLabelComponent,
            //   }}
      />
    )

}

const TimestampFilter = (filter) => {
    const { name, datasetId, values, handleSetFilterValues } = filter;
    const [ options, setOptions ] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await httpRequest.get(`/dataset/${datasetId}/${name}/values`)
                setOptions(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchData();
    }, []);

    return (
        <ToggleDateRange
        />
    )

}

const ValueLabelComponent = (props) => {
    const { children, value } = props;
  
    return (
      <Box sx={{ top: -30, position: 'relative', textAlign: 'center' }}>
        <Typography variant="caption" color="textSecondary">
          {value}
        </Typography>
        {children}
      </Box>
    );
  };


export default Filters;