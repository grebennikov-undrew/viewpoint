import React, { useState } from 'react';
import { DateRange } from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { Button, Popover, Typography } from '@mui/material';

import dayjs from 'dayjs';

const DateRangePicker = (props) => {
    const { values, defaultRange, handleSettingDate } = props;
    const [state, setState] = useState(
        {
        startDate: new Date(),
        endDate: null,
        key: 'selection',
        },
    );
    const [anchorEl, setAnchorEl] = useState(null);

    const handleToggleCalendar = (event) => {
        setAnchorEl(anchorEl ? null : event.currentTarget);
    };

    const handleCloseCalendar = () => {
        setAnchorEl(null);
    };

    const handleDateChange = (item) => {
        setState(item.selection);
        handleSettingDate(item.selection.startDate, item.selection.endDate);
    };

    const label = values ? 
        `${dayjs(values[0]).format("YYYY-MM-DD")} ... ${dayjs(values[1]).format("YYYY-MM-DD")}` :
        "No filter"

    return (
        <div >
            <Button 
                onClick={handleToggleCalendar} 
                // variant="outlined" 
                style={{justifyContent: "flex-start", color: "black", border: "1px sold gray",  borderRadius: "40px", padding: "4px 12px", backgroundColor: "#efefef"}}
                // fullWidth
            >
                {label} 
            </Button>
            <Popover
                open={Boolean(anchorEl)}
                anchorEl={anchorEl}
                onClose={handleCloseCalendar}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                }}
            >
                <div style={{display: 'flex', flexDirection: 'column'}}>
                    <DateRange
                        // editableDateInputs
                        onChange={handleDateChange}
                        moveRangeOnFirstSelection={false}
                        ranges={[state]}
                    />
                    <Button onClick={handleCloseCalendar} style={{ marginTop: 'auto' }}>
                        Close
                    </Button>
                </div>
            </Popover>
        </div>
    );
};

export const ToggleDateRange = (props) => {
  return <DateRangePicker {...props} />;
};
