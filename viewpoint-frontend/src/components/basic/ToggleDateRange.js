import React, { useState } from 'react';
import { DateRange } from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { Button, Popover, Typography } from '@mui/material';

const DateRangePicker = () => {
  const [state, setState] = useState([
    {
      startDate: new Date(),
      endDate: null,
      key: 'selection',
    },
  ]);
  const [anchorEl, setAnchorEl] = useState(null);

  const handleToggleCalendar = (event) => {
    setAnchorEl(anchorEl ? null : event.currentTarget);
  };

  const handleCloseCalendar = () => {
    setAnchorEl(null);
  };

  const handleDateChange = (item) => {
    setState([item.selection]);
    // handleCloseCalendar();
  };

  return (
    <div >
      <Button onClick={handleToggleCalendar} variant="outlined" fullWidth>
        {state.startDate
          ? `${state.startDate.toLocaleDateString()} - ${state.endDate.toLocaleDateString()}`
          : 'Open Calendar'}
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
            ranges={state}
          />
          <Button onClick={handleCloseCalendar} style={{ marginTop: 'auto' }}>
            Close
          </Button>
        </div>
      </Popover>
    </div>
  );
};

export const ToggleDateRange = () => {
  return <DateRangePicker />;
};
